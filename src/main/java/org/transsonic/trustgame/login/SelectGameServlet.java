package org.transsonic.trustgame.login;

import java.io.IOException;

import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/selectgame")
public class SelectGameServlet extends HttpServlet {

	/** */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		TrustGameData data = SessionUtils.getData(session);
		Integer gameUserId = Integer.parseInt(request.getParameter("recordNr"));
		data.setGameUserId(gameUserId);
        data.setGameUser(SqlUtils.readGameUserFromGameUserId(data, gameUserId));
        SqlUtils.loadAttributes(session, gameUserId);
		response.sendRedirect("jsp/trustgame/round.jsp");
	}

}
