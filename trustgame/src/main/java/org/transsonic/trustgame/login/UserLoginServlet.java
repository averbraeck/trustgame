package org.transsonic.trustgame.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;

import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;
import org.transsonic.trustgame.data.trustgame.tables.records.GameRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameplayRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameuserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserRecord;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    /** */
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        System.getProperties().setProperty("org.jooq.no-logo", "true");

        // determine the connection pool, and create one if it does not yet exist (first use after server restart)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        try {
            Context ctx = new InitialContext();
            try {
                ctx.lookup("/trustgame_datasource");
            } catch (NamingException ne) {
                final HikariConfig config = new HikariConfig();
                config.setJdbcUrl("jdbc:mysql://localhost:3306/trustgame");
                config.setUsername("trustgame");
                config.setPassword("TG%s1naval%2105");
                config.setMaximumPoolSize(10);
                config.setDriverClassName("com.mysql.cj.jdbc.Driver");
                DataSource dataSource = new HikariDataSource(config);
                ctx.bind("/trustgame_datasource", dataSource);
            }
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        MessageDigest md;
        String hashedPassword;
        try {
            // https://www.baeldung.com/java-md5
            md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] digest = md.digest();
            hashedPassword = DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e1) {
            throw new ServletException(e1);
        }
        HttpSession session = request.getSession();

        TrustGameData data = new TrustGameData();
        session.setAttribute("trustGameData", data);
        try {
            data.setDataSource((DataSource) new InitialContext().lookup("/trustgame_datasource"));
        } catch (NamingException e) {
            throw new ServletException(e);
        }

        UserRecord user = SqlUtils.readUserFromUsername(data, username);
        String userPassword = user == null ? "" : user.getPassword() == null ? "" : user.getPassword();
        if (user != null && userPassword.equals(hashedPassword)) {
            data.setUsername(user.getName());
            data.setUserId(user.getId());
            data.setUser(user);
            @SuppressWarnings("unchecked")
            Map<Integer, String> idSessionMap = (Map<Integer, String>) request.getServletContext()
                    .getAttribute("idSessionMap");
            if (idSessionMap == null) {
                idSessionMap = new HashMap<>();
                request.getServletContext().setAttribute("idSessionMap", idSessionMap);
            }
            idSessionMap.put(user.getId(), request.getSession().getId());
            List<GameuserRecord> gameUserRecords = SqlUtils.readGameUsersFromUserId(data, user.getId());
            if (gameUserRecords.size() == 0) {
                session.removeAttribute("trustGameData");
                response.sendRedirect("jsp/trustgame/nogame.jsp");
            } else if (gameUserRecords.size() == 1) {
                Integer gameUserId = gameUserRecords.get(0).getId();
                data.setGameUserId(gameUserId);
                data.setGameUser(gameUserRecords.get(0));
                SqlUtils.loadAttributes(session, gameUserId);
                response.sendRedirect("jsp/trustgame/round.jsp");
            } else {
                response.sendRedirect("jsp/trustgame/selectgame.jsp");
            }
        } else {
            session.removeAttribute("trustGameData");
            response.sendRedirect("jsp/trustgame/login.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("jsp/trustgame/login.jsp");
    }

    public static String selectGameTable(HttpServletRequest request) {
        TrustGameData data = SessionUtils.getData(request.getSession());
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        StringBuilder s = new StringBuilder();
        s.append("<p>Select the game to play:</p>\n");
        List<GameuserRecord> gameUserRecords = SqlUtils.readGameUsersFromUserId(data, userId);
        for (GameuserRecord record : gameUserRecords) {
            Integer gamePlayId = record.getValue("GamePlay_ID", Integer.class);
            GameplayRecord gamePlay = SqlUtils.readGamePlayFromGamePlayId(data, gamePlayId);
            GameRecord game = SqlUtils.readGameFromGamePlay(data, gamePlay);
            s.append("<a href=\"/trustgame/selectgame?gameUserId=" + record.get("ID").toString() + "\">"
                    + game.getName() + ", group = " + gamePlay.getGroupdescription() + "</a><br>\n");
        }
        return s.toString();
    }
}
