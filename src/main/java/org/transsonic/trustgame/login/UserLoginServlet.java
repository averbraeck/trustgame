package org.transsonic.trustgame.login;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
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

import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;
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

        HttpSession session = request.getSession();
        TrustGameData data = new TrustGameData();
        session.setAttribute("trustGameData", data);
        try {
            data.setDataSource((DataSource) new InitialContext().lookup("/trustgame_datasource"));
        } catch (NamingException e) {
            throw new ServletException(e);
        }

        UserRecord user;
        String userCode = request.getParameter("usercode");
        if (userCode != null && userCode.length() > 0) {
            user = SqlUtils.readUserFromUserCode(data, userCode);
            if (user == null) {
                session.removeAttribute("trustGameData");
                response.sendRedirect("jsp/trustgame/login.jsp");
                return;
            }
        }

        else

        {
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

            user = SqlUtils.readUserFromUsername(data, username);
            if (user == null) {
                session.removeAttribute("trustGameData");
                response.sendRedirect("jsp/trustgame/login.jsp");
                return;
            }
            String userPassword = user.getPassword() == null ? "" : user.getPassword();
            if (!userPassword.equals(hashedPassword)) {
                session.removeAttribute("trustGameData");
                response.sendRedirect("jsp/trustgame/login.jsp");
                return;
            }
        }

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

        // check validity of the game
        LocalDateTime date = LocalDateTime.now();
        for (int i = gameUserRecords.size() - 1; i >= 0; i--) {
            GameplayRecord gamePlay = SqlUtils.readGamePlayFromGameUser(data, gameUserRecords.get(i));
            if ((gamePlay.getStartplaydate() != null && date.isBefore(gamePlay.getStartplaydate()))
                    || (gamePlay.getEndplaydate() != null && date.isAfter(gamePlay.getEndplaydate())))
                gameUserRecords.remove(i);
        }

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
            makeSelectUserHtml(data, gameUserRecords);
            response.sendRedirect("jsp/trustgame/selectgame.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("usercode") != null) {
            doPost(request, response);
            return;
        }

        response.sendRedirect("jsp/trustgame/login.jsp");
    }

    private void makeSelectUserHtml(TrustGameData data, List<GameuserRecord> gameUserRecords) {
        StringBuilder s = new StringBuilder();

        s.append("<div class=\"tg-form\">\n");
        s.append("  <form id=\"selectForm\" action=\"/trustgame/selectgame\" method=\"POST\" >\n");
        s.append("    <input id=\"recordNr\" type=\"hidden\" name=\"recordNr\" value=\"0\" />\n");
        s.append("    <fieldset>\n");
        s.append("      <div class=\"tg-select-table\">\n");
        s.append("        <table>\n");

        s.append("          <tr>\n");
        s.append("            <td width=\"25%\">Select&nbsp;game:</td>\n");
        s.append("            <td width=\"75%\">\n");
        s.append("              <select name=\"gameUser\" id=\"gameUser\" onchange=\"submitSelectedGameUser(); \">");
        s.append("                <option value=\"0\">&nbsp;</option>\n");
        for (GameuserRecord gameUser : gameUserRecords) {
            GameplayRecord gamePlay = SqlUtils.readGamePlayFromGameUser(data, gameUser);
            s.append("              <option value=\"");
            s.append(gameUser.getId());
            s.append("\">");
            s.append(gamePlay.getGroupdescription());
            s.append("</option>\n");
        }
        s.append("              </select>\n");
        s.append("            </td>\n");
        s.append("          </tr>\n");

        s.append("        </table>\n");
        s.append("      </div>\n");
        s.append("    </fieldset>\n");
        s.append("  </form>\n");
        s.append("</div>\n");

        s.append("<p><a href=\"/trustgame/jsp/trustgame/login.jsp\">Return to the login screen</a></p>");
        data.setSelectUserHtml(s.toString());
    }

}
