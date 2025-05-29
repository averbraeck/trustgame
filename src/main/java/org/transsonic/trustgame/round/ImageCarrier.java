package org.transsonic.trustgame.round;

import java.io.IOException;

import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;
import org.transsonic.trustgame.data.trustgame.tables.records.CarrierRecord;
import org.transsonic.trustgame.image.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/imageCarrier")
public class ImageCarrier extends HttpServlet {

    /** */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer carrierId = Integer.valueOf(request.getParameter("id").toString());
        TrustGameData data = SessionUtils.getData(request.getSession());
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, carrierId);
        ImageUtil.makeResponse(response, carrier.getLogo());
    }

    
}
