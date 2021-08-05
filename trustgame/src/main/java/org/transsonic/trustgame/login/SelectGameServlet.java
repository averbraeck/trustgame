package org.transsonic.trustgame.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;

@WebServlet("/selectgame")
public class SelectGameServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!SessionUtils.checkLogin(request, response)) {
			return;
		}
		HttpSession session = request.getSession();
		TrustGameData data = SessionUtils.getData(session);
		Integer gameUserId = Integer.parseInt(request.getParameter("gameUserId"));
		data.setGameUserId(gameUserId);
        data.setGameUser(SqlUtils.readGameUserFromGameUserId(data, gameUserId));
        SqlUtils.loadAttributes(session, gameUserId);
		response.sendRedirect("jsp/trustgame/round.jsp");
	}
}
