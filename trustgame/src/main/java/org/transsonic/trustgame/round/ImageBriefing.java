package org.transsonic.trustgame.round;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;
import org.transsonic.trustgame.data.trustgame.tables.records.BriefingRecord;
import org.transsonic.trustgame.image.ImageUtil;

@WebServlet("/imageBriefing")
public class ImageBriefing extends HttpServlet {

    /** */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer briefingId = Integer.valueOf(request.getParameter("id").toString());
        TrustGameData data = SessionUtils.getData(request.getSession());
        BriefingRecord briefing = SqlUtils.readBriefingFromBriefingId(data, briefingId);
        ImageUtil.makeResponse(response, briefing.getBriefingimage());
    }

}
