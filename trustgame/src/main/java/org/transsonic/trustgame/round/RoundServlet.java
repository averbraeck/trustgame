package org.transsonic.trustgame.round;

import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;
import org.transsonic.trustgame.SessionUtils;
import org.transsonic.trustgame.SqlUtils;
import org.transsonic.trustgame.TrustGameData;
import org.transsonic.trustgame.data.trustgame.Tables;
import org.transsonic.trustgame.data.trustgame.tables.records.CarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.CarrierreviewRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.ClientRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.FbreportRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameuserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrdercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.ReviewRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.SelectedcarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UsercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserorderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserroundRecord;
import org.transsonic.trustgame.logging.LoggingUtils;

@WebServlet("/round")
public class RoundServlet extends HttpServlet {

    /** */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        TrustGameData data = SessionUtils.getData(session);
        if (data == null) {
            response.sendRedirect("/trustgame/login");
            return;
        }

        String click = request.getParameter("click").toString();
        if ("organization".equals(click)) {
            data.setMenuChoice(1);
            LoggingUtils.insertClick(data, "Organization");

        } else if ("scores".equals(click)) {
            data.setMenuChoice(2);
            LoggingUtils.insertClick(data, "Score");

        } else if ("orders".equals(click)) {
            data.setMenuChoice(3);
            LoggingUtils.insertClick(data, "UnconfirmedOrders");

        } else if ("message".equals(click)) {
            LoggingUtils.insertClick(data, "Message");

        } else if ("openClientInfo".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            OrderRecord order = SessionUtils.getOrderRecord(data, clickedOrderId);
            if (order != null) {
                data.setShowModalWindow(1);
                ClientRecord client = SqlUtils.readClientFromClientId(data, order.getClientId());
                StringBuffer s = new StringBuffer();
                s.append("        <div class=\"tg-modal-client-body\">");
                s.append("          <div class=\"tg-modal-client-icon\">");
                s.append("<img src=\"/trustgame/imageClient?id=" + client.getId() + "\" />");
                s.append("</div>\n");
                s.append("          <div class=\"tg-modal-client-text\">");
                s.append("Client: ");
                s.append(client.getName());
                s.append("<br>Type: ");
                s.append(client.getType());
                s.append("</div>\n");
                s.append("        </div>\n");
                data.setModalWindowHtml(makeModalWindow("Client information", s.toString(), "clickCloseClientInfo()"));
                LoggingUtils.insertClickOrderClient(data, "ClientInformationOpen", order.getOrdernumber().intValue(),
                        client.getName());
            }

        } else if ("closeClientInfo".equals(click)) {
            data.setShowModalWindow(0);
            data.setModalWindowHtml("");
            LoggingUtils.insertClick(data, "ClientInformationClose");

        } else if ("publishOrder".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            int roundNumber = data.getRoundNumber();
            OrderRecord order = SessionUtils.getOrderRecord(data, clickedOrderId);
            UserroundRecord userRound = data.getUserRoundMap().get(roundNumber);
            if (userRound == null) {
                userRound = SqlUtils.readOrInsertUserRoundRecord(data); // not created yet...
            }
            UserorderRecord userOrder = DSL.using(data.getDataSource(), SQLDialect.MYSQL).newRecord(Tables.USERORDER);
            userOrder.set(Tables.USERORDER.USERROUND_ID, userRound.getId());
            userOrder.set(Tables.USERORDER.ORDER_ID, order.getId());
            userOrder.set(Tables.USERORDER.PUBLISHED, (byte) 1);
            userOrder.store();
            LoggingUtils.insertClickOrder(data, "PublishOrder", order.getOrdernumber().intValue());
            SqlUtils.updateUserData(session, data);
            handleOrderContent(data);

        } else if ("publishedOrders".equals(click)) {
            handleOrderContent(data);
            LoggingUtils.insertClick(data, "OrderOverview");

        } else if ("carrierOverview".equals(click)) {
            handleCarrierOverviewContent(data);
            LoggingUtils.insertClick(data, "CarrierOverview");

        } else if ("carrierDetails".equals(click)) {
            data.setCarrierDetails(1);
            int clickedCarrierId = Integer.parseInt(request.getParameter("clickedCarrierId"));
            int carrierDetailChoice = Integer.parseInt(request.getParameter("carrierDetailChoice"));
            switch (carrierDetailChoice) {
            case 1: {
                CarrierRecord carrier = handleCarrierDetails(data, clickedCarrierId);
                LoggingUtils.insertClickCarrier(data, "CarrierDetailsCompany", carrier.getName());
                break;
            }
            case 2: {
                CarrierRecord carrier = handleCarrierWebsite(data, clickedCarrierId);
                LoggingUtils.insertClickCarrier(data, "CarrierDetailsWebsite", carrier.getName());
                break;
            }
            case 3: {
                CarrierRecord carrier = handleCarrierGoogle(data, clickedCarrierId);
                LoggingUtils.insertClickCarrier(data, "CarrierDetailsGoogle", carrier.getName());
                break;
            }
            case 4: {
                CarrierRecord carrier = handleCarrierReviews(data, clickedCarrierId);
                LoggingUtils.insertClickCarrier(data, "CarrierDetailsReviews", carrier.getName());
                break;
            }
            case 5: {
                CarrierRecord carrier = handleCarrierReport(data, clickedCarrierId);
                LoggingUtils.insertClickCarrier(data, "CarrierDetailsReport", carrier.getName());
                break;
            }
            case 6: {
                if (data.getGameUser().getScoreprofit() < 5) {
                    String s = "<p>You do not have enough money to buy this report<br/>Profit is less than 5 tokens</p>\n";
                    data.setModalWindowHtml(makeOkModalWindow("No money to buy report", s));
                    data.setShowModalWindow(1);
                    LoggingUtils.insertClick(data, "CarrierDetailsBuyReportNoMoney");
                } else {
                    CarrierRecord carrier = buyCarrierReport(data, clickedCarrierId);
                    LoggingUtils.insertClickCarrier(data, "CarrierDetailsBuyReport", carrier.getName());
                }
                break;
            }
            }

        } else if ("acceptQuote".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            OrderRecord order = SessionUtils.getOrderRecord(data, clickedOrderId);
            int clickedOrderCarrierId = Integer.parseInt(request.getParameter("clickedOrderCarrierId"));
            OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data, clickedOrderCarrierId);
            CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
            data.setModalWindowHtml(acceptQuoteModalWindow(data, order, orderCarrier, carrier));
            data.setShowModalWindow(1);
            LoggingUtils.insertClickOrderCarrier(data, "AcceptQuote", order.getOrdernumber().intValue(),
                    carrier.getName());

        } else if ("acceptQuoteYes".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            OrderRecord order = SessionUtils.getOrderRecord(data, clickedOrderId);
            int clickedOrderCarrierId = Integer.parseInt(request.getParameter("clickedOrderCarrierId"));
            OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data, clickedOrderCarrierId);
            CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
            UserorderRecord userOrder = SessionUtils.getUserOrderForOrder(data, order);
            DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
            SelectedcarrierRecord selectedCarrier = dslContext.newRecord(Tables.SELECTEDCARRIER);
            selectedCarrier.setUserorderId(userOrder.getId());
            selectedCarrier.setOrdercarrierId(orderCarrier.getId());
            selectedCarrier.store();
            SqlUtils.updateUserData(session, data);

            // have we accepted all orders? Then the button at the top right can become 'Finish day'
            if (SessionUtils.getConfirmedOrderListForRound(data, data.getRoundNumber()).size() == SessionUtils
                    .getAcceptedOrderListForRound(data, data.getRoundNumber()).size()
                    && SessionUtils.getUnconfirmedOrderListForRound(data, data.getRoundNumber()).size() == 0) {
                data.setDayButton(TrustGameData.dayButtonFinishDay);
                data.getGameUser().setRoundstatus(1);
                data.getGameUser().store();
            }
            // refresh the order content screen which IS visible (!)
            handleOrderContent(data);
            data.setModalWindowHtml("");
            data.setShowModalWindow(0);
            LoggingUtils.insertClickOrderCarrier(data, "AcceptQuoteYes", order.getOrdernumber().intValue(),
                    carrier.getName());

        } else if ("acceptQuoteNo".equals(click)) {
            data.setModalWindowHtml("");
            data.setShowModalWindow(0);
            LoggingUtils.insertClick(data, "AcceptQuoteNo");

        } else if ("finishDay".equals(click)) {
            String finishText = "<center>We are now at the end of the day.<br/><br/>"
                    + "Before we move to the next day, let's check out<br/>the transport outcome of day. "
                    + data.getRoundNumber()
                    + "<br/><br/>and<br/><br/>Give a star rating to the carriers for each transported order.<br/>"
                    + "Once that has finished, you can receive orders for the next day.</center>";
            data.setModalWindowHtml(makeOkModalWindow("Finish transport day " + data.getRoundNumber(), finishText));
            data.setDayButton(TrustGameData.dayButtonFinishDayInactive);
            GameuserRecord gameUser = data.getGameUser();
            gameUser.setRoundstatus(2);
            List<OrderRecord> acceptedOrders = SessionUtils.getAcceptedOrderListForRound(data, data.getRoundNumber());
            for (OrderRecord order : acceptedOrders) {
                SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
                OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data,
                        selectedCarrier.getOrdercarrierId());
                gameUser.setScoreprofit(gameUser.getScoreprofit() + order.getTransportearnings()
                        - orderCarrier.getQuoteoffer() + orderCarrier.getExtraprofit());
                gameUser.setScoresatisfaction(gameUser.getScoresatisfaction() + orderCarrier.getOutcomesatisfaction());
                gameUser.setScoresustainability(
                        gameUser.getScoresustainability() + orderCarrier.getOutcomesustainability());
            }
            gameUser.store();
            if (data.getContentChoice() == 1)
                handleOrderContent(data);
            data.setMenuChoice(2); // jump to score
            LoggingUtils.insertClick(data, "FinishDay");

        } else if ("nextDay".equals(click)) {
            data.setRoundNumber(data.getRoundNumber() + 1);
            data.getGameUser().setRoundnumber(UInteger.valueOf(data.getRoundNumber()));
            data.getGameUser().setRoundstatus(0);
            data.getGameUser().store();
            SqlUtils.updateUserData(session, data);
            data.setMenuChoice(3); // jump to new orders to confirm them
            LoggingUtils.insertClick(data, "NextDay");

        } else if ("modalWindowOk".equals(click)) {
            data.setModalWindowHtml("");
            data.setShowModalWindow(0);
            LoggingUtils.insertClick(data, "ModalWindowOk");

        } else if ("giveStars".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            int nrStars = Integer.parseInt(request.getParameter("clickedNumberStars"));
            handleReviewStars(data, clickedOrderId, nrStars);
            LoggingUtils.insertClickOrderValue(data, "GiveStars", clickedOrderId, String.valueOf(nrStars));

        } else if ("confirmStars".equals(click)) {
            data.setModalWindowHtml("");
            data.setShowModalWindow(0);
            LoggingUtils.insertClick(data, "ConfirmStarsOk");

        } else if ("viewTransportOutcome".equals(click)) {
            int clickedOrderId = Integer.parseInt(request.getParameter("clickedOrderId"));
            data.setModalWindowHtml(makeTransportOutcome(data, clickedOrderId));
            data.setShowModalWindow(1);
            LoggingUtils.insertClickOrder(data, "ViewTransportOutcome", clickedOrderId);

        } else if ("debrief".equals(click)) {
            handleDebrief(data);
            data.setMenuChoice(2);
            LoggingUtils.insertClick(data, "Debrief");

        } else {
            System.err.println("UNKNOWN CLICK CHOICE: " + click);
        }

        handleOrganizationScoresOrders(data);
        handleMessages(data);

        response.sendRedirect("jsp/trustgame/round.jsp");
    }

    public static void handleOrganizationScoresOrders(final TrustGameData data) {
        switch (data.getMenuChoice()) {
        case 1: { // organization
            StringBuilder s = new StringBuilder();
            s.append("            <div class=\"tg-menu-organization\">\n");
            s.append("              <div class=\"tg-menu-organization-header\">");
            s.append(data.getOrganization().getName());
            s.append("</div><br/>\n");
            s.append("              <div class=\"tg-menu-organization-body\">\n");
            s.append(data.getOrganization().getDescription());
            s.append("</div>\n");
            s.append("            </div>\n");
            data.setOrgScoresOrdersHtml(s.toString());
            break;
        }
        case 2: { // scores
            data.setOrgScoresOrdersHtml(makeScoreDiv(data));
            break;
        }
        case 3: { // orders
            StringBuilder s = new StringBuilder();
            s.append("            <div class=\"tg-menu-orders\">\n");
            for (OrderRecord order : SessionUtils.getUnconfirmedOrderListForRound(data, data.getRoundNumber())) {
                s.append("              <div class=\"tg-menu-order\">\n");
                s.append("                <div class=\"tg-menu-order-header\">Order #");
                s.append(order.getOrdernumber());
                s.append("</div><br/>\n");
                s.append("                <div class=\"tg-menu-order-body\">");
                s.append(order.getDescription());
                if (order.getNote() != null && order.getNote().length() > 0) {
                    s.append("<br><b><i>Note: ");
                    s.append(order.getNote());
                    s.append("</i></b>");
                }
                s.append("</div>\n");
                s.append("                <div class=\"tg-menu-order-buttons\">\n");
                s.append(
                        "                  <div class=\"tg-button-small tg-menu-order-button\" onclick=\"clickOpenClientInfo(");
                s.append(order.getId());
                s.append(")\">");
                s.append("Client information</div>\n");
                s.append(
                        "                  <div class=\"tg-button-small tg-menu-order-button\" onclick=\"clickPublishOrder(");
                s.append(order.getId());
                s.append(")\">");
                s.append("Publish order</div>\n");
                s.append("                </div>\n");
                s.append("              </div>\n");
            }
            s.append("            </div>\n");
            data.setOrgScoresOrdersHtml(s.toString());
            break;
        }
        }
    }

    public static String makeScoreDiv(TrustGameData data) {
        StringBuilder s = new StringBuilder();
        s.append("            <div class=\"tg-menu-score\">\n");
        s.append("              <div class=\"tg-menu-score-header\">Current score</div>\n");
        s.append("              <div class=\"tg-menu-score-row\">\n");
        s.append("                <div class=\"tg-menu-score-leftcol\"></div>");
        s.append("                <div class=\"tg-menu-max-score\">");
        s.append(2 * data.getOrganization().getTargetprofit());
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-max-score\">");
        s.append(2 * data.getOrganization().getTargetsatisfaction());
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-max-score\">");
        s.append(2 * data.getOrganization().getTargetsustainability());
        s.append("                </div>\n");
        s.append("              </div>\n");

        s.append("              <div class=\"tg-menu-score-dashline\"></div>\n");
        s.append("              <div class=\"tg-menu-middle-row\">\n");
        s.append("                <div class=\"tg-menu-score-leftcol tg-menu-goal-text\">Goal</div>");
        s.append("                <div class=\"tg-menu-goal-score\">");
        s.append(data.getOrganization().getTargetprofit());
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-goal-score\">");
        s.append(data.getOrganization().getTargetsatisfaction());
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-goal-score\">");
        s.append(data.getOrganization().getTargetsustainability());
        s.append("                </div>\n");
        s.append("              </div>\n");

        s.append("              <div class=\"tg-menu-score-bars\">\n");
        s.append("                <div class=\"tg-menu-score-leftcol\"></div>");

        // profit
        s.append("                <div class=\"tg-menu-score-progress\">\n");
        s.append("                  <div class=\"tg-menu-score-progress-bar\">\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-bottom\" style=\"height:");
        int score = 50 * data.getGameUser().getScoreprofit() / data.getOrganization().getTargetprofit();
        score = Math.max(0, Math.min(100, score));
        s.append(score + "%");
        if (data.getGameUser().getScoreprofit() < data.getOrganization().getTargetprofit())
            s.append("; background-color:red");
        s.append(";\"><p>");
        s.append(data.getGameUser().getScoreprofit());
        s.append("</p></div>\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-top\"></div>\n");
        s.append("                  </div>\n");
        s.append("                </div>\n");

        // satisfaction
        s.append("                <div class=\"tg-menu-score-progress\">\n");
        s.append("                  <div class=\"tg-menu-score-progress-bar\">\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-bottom\" style=\"height:");
        score = 50 * data.getGameUser().getScoresatisfaction() / data.getOrganization().getTargetsatisfaction();
        score = Math.max(0, Math.min(100, score));
        s.append(score + "%");
        if (data.getGameUser().getScoresatisfaction() < data.getOrganization().getTargetsatisfaction())
            s.append("; background-color:red");
        s.append(";\"><p>");
        s.append(data.getGameUser().getScoresatisfaction());
        s.append("</p></div>\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-top\"></div>\n");
        s.append("                  </div>\n");
        s.append("                </div>\n");

        // sustainability
        s.append("                <div class=\"tg-menu-score-progress\">\n");
        s.append("                  <div class=\"tg-menu-score-progress-bar\">\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-bottom\" style=\"height:");
        score = 50 * data.getGameUser().getScoresustainability() / data.getOrganization().getTargetsustainability();
        score = Math.max(0, Math.min(100, score));
        s.append(score + "%");
        if (data.getGameUser().getScoresustainability() < data.getOrganization().getTargetsustainability())
            s.append("; background-color:red");
        s.append(";\"><p>");
        s.append(data.getGameUser().getScoresustainability());
        s.append("</p></div>\n");
        s.append("                    <div class=\"tg-menu-score-progress-bar-top\"></div>\n");
        s.append("                  </div>\n");
        s.append("                </div>\n");
        s.append("              </div>\n");

        s.append("              <div class=\"tg-menu-score-row\">\n");
        s.append("                <div class=\"tg-menu-score-leftcol\"></div>");
        s.append("                <div class=\"tg-menu-score-progress-icon\">");
        s.append("<img src=\"images/euro.png\" width=\"24\" height=\"24\" />");
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-score-progress-icon\">");
        s.append("<img src=\"images/smile.png\" width=\"24\" height=\"24\" />");
        s.append("                </div>\n");
        s.append("                <div class=\"tg-menu-score-progress-icon\">");
        s.append("<img src=\"images/leaf.png\" width=\"24\" height=\"24\" />");
        s.append("                </div>\n");
        s.append("              </div>\n");

        s.append("            </div>\n");
        return s.toString();
    }

    public static void handleOrderContent(TrustGameData data) {
        List<OrderRecord> orderList = SessionUtils.getConfirmedOrderListUpToRound(data, data.getRoundNumber());
        StringBuilder s = new StringBuilder();

        s.append("            <div class=\"tg-orders\">\n");

        if (orderList.size() == 0 || (orderList.size() > 0 && SessionUtils.orderStatus(data, orderList.get(0)) < 4)) {
            s.append("              <div class=\"tg-orders-header-row\">\n");
            s.append("                <div class=\"tg-orders-header-left\">Published transport orders</div>\n");
            s.append("                <div class=\"tg-orders-header-right\">Carrier information for today</div>\n");
            s.append("              </div>\n");
            s.append("              <div class=\"tg-orders-hr-row\">\n");
            s.append("                <div class=\"tg-orders-hr-left\"></div>");
            s.append("                <div class=\"tg-orders-hr-right\"></div>");
            s.append("              </div>\n");
        }

        boolean switchPastDay = false;

        for (OrderRecord order : orderList) {

            // look what the status is: 1: quote not accepted, 2: in transport, 3: to be scored, 4: scored
            int status = SessionUtils.orderStatus(data, order);

            if (status == 4 & !switchPastDay) {
                switchPastDay = true;
                s.append("              <div class=\"tg-orders-header-row\">\n");
                s.append("                <div class=\"tg-orders-header-left\">Completed transport orders</div>\n");
                s.append("                <div class=\"tg-orders-header-right\">Selected carrier and outcome</div>\n");
                s.append("              </div>\n");
                s.append("              <div class=\"tg-orders-hr-row\">\n");
                s.append("                <div class=\"tg-orders-hr-left\"></div>");
                s.append("                <div class=\"tg-orders-hr-right\"></div>");
                s.append("              </div>\n");
            }

            s.append("              <div class=\"tg-order-row\">\n");
            s.append("                <div class=\"tg-order\">\n");
            s.append("                  <div class=\"tg-order-header\">Order #");
            s.append(order.getOrdernumber());
            s.append("</div>\n");
            s.append("                  <div class=\"tg-order-body\">");
            s.append(order.getDescription());
            if (order.getNote() != null && order.getNote().length() > 0) {
                s.append("<br><b><i>Note: ");
                s.append(order.getNote());
                s.append("</i></b>");
            }
            s.append("</div>\n");
            s.append("                  <div class=\"tg-order-button-body-row\">");
            s.append("                    <div class=\"tg-button-small\" onclick=\"clickOpenClientInfo(");
            s.append(order.getId());
            s.append(")\">");
            s.append("Client information</div>\n");
            s.append("                  </div>\n");
            s.append("                </div>\n");

            if (status <= 1) {

                s.append("                <div class=\"tg-quotes\">\n");
                s.append("                  <div class=\"tg-quote-row\">\n");
                s.append("                    <div class=\"tg-quote-row-header-name\">Choose the carrier</div>\n");
                s.append("                    <div class=\"tg-quote-row-header-cost\">Cost</div>\n");
                s.append("                    <div class=\"tg-quote-row-header-review\">Avg. Review</div>\n");
                s.append("                    <div class=\"tg-quote-row-header-button\">Choose one</div>\n");
                s.append("                  </div>\n");
                for (OrdercarrierRecord orderCarrier : data.getOrderCarrierMap().get(order.getId())) {
                    CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
                    CarrierreviewRecord carrierReview = SqlUtils.getCarrierReview(data, carrier.getId(),
                            data.getRoundNumber());
                    s.append("                  <div class=\"tg-quote-row\">\n");
                    s.append("                    <div class=\"tg-quote-row-name\">");
                    s.append(carrier.getName());
                    s.append("</div>\n");
                    s.append("                    <div class=\"tg-quote-row-cost\">");
                    s.append(orderCarrier.getQuoteoffer());
                    s.append("</div>\n");
                    s.append("                    <div class=\"tg-quote-review\">");
                    s.append(formatStars(carrierReview.getOverallstars()));
                    s.append("</div>\n");
                    s.append(
                            "                    <div class=\"tg-button-small tg-quote-accept-button\"onclick=\"clickAcceptQuote(");
                    s.append(order.getId());
                    s.append(", ");
                    s.append(orderCarrier.getId());
                    s.append(")\">Accept quote</div>");
                    s.append("                  </div>\n");
                }
                s.append("                </div>\n"); // tg-quotes

            }

            else {

                s.append("                <div class=\"tg-accepted-quote\">\n");
                s.append("                  <div class=\"tg-accepted-quote-header\">\n");
                s.append("                    <div class=\"tg-accepted-quote-header-carrier\">Carrier</div>\n");
                if (status <= 1)
                    s.append(
                            "                    <div class=\"tg-accepted-quote-header-review\">No carrier selected</div>\n");
                else if (status == 2)
                    s.append("                    <div class=\"tg-accepted-quote-header-review\">Order status</div>\n");
                else if (status == 3)
                    s.append(
                            "                    <div class=\"tg-accepted-quote-header-review\">Please review the carrier</div>\n");
                else
                    s.append("                    <div class=\"tg-accepted-quote-header-review\">Your review</div>\n");
                s.append("                  </div>\n");
                s.append("                  <div class=\"tg-accepted-quote-body\">\n");
                s.append("                    <div class=\"tg-accepted-quote-body-carrier\">");
                if (status <= 1)
                    s.append("No carrier quote accepted yet");
                else {
                    SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
                    if (selectedCarrier != null) {
                        OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data,
                                selectedCarrier.getOrdercarrierId());
                        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
                        s.append(carrier.getName());
                    }
                }
                s.append("</div>\n");
                s.append("                    <div class=\"tg-accepted-quote-body-review\">");
                if (status <= 1)
                    s.append("No carrier quote accepted yet");
                else if (status == 2)
                    s.append("ORDER IS BEING TRANSPORTED");
                else if (status == 3) {
                    s.append(scoreReviewStars(order.getId()));
                } else if (status == 4) {
                    SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
                    if (selectedCarrier != null) {
                        if (selectedCarrier.getUserscore() != null)
                            s.append(displayReviewStars(selectedCarrier.getUserscore()));
                        else
                            s.append(displayReviewStars(0.0));
                    } else
                        s.append(displayReviewStars(0.0));
                }
                s.append("                    </div>\n");
                s.append("                  </div>\n");
                if (status > 2) {
                    s.append("                  <div class=\"tg-carrier-button-body-row\">");
                    s.append("                    <div class=\"tg-button-small\" onclick=\"clickViewTransportOutcome(");
                    s.append(order.getId());
                    s.append(")\">");
                    s.append("Transport outcome</div>\n");
                    s.append("                  </div>\n");
                }
                s.append("                </div>\n");
            }
            s.append("              </div>\n");

        }

        s.append("            </div>\n"); // tg-orders

        data.setContentHtml(s.toString());
    }

    private static void handleCarrierOverviewContent(TrustGameData data) {
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-carrier-list\">\n");
        SortedSet<Integer> carrierIds = new TreeSet<>();
        for (Integer orderNr : data.getOrderCarrierMap().keySet()) {
            for (OrdercarrierRecord orderCarrier : data.getOrderCarrierMap().get(orderNr)) {
                carrierIds.add(orderCarrier.getCarrierId());
            }
        }
        for (Integer carrierId : carrierIds) {
            CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, carrierId);
            s.append("  <div class=\"tg-carrier\">\n");
            s.append("    <div class=\"tg-carrier-icon\"><img src=\"/trustgame/imageCarrier?id=" + carrierId
                    + "\" /></div>\n");
            s.append("    <div class=\"tg-carrier-middle\">\n");
            s.append("      <div class=\"tg-carrier-middle-name\">");
            s.append(carrier.getName());
            s.append("</div>\n"); // carrier-middle-name
            s.append("      <div class=\"tg-carrier-middle-slogan\">");
            s.append(carrier.getSlogan());
            s.append("</div>\n"); // carrier-middle-slogan
            List<ReviewRecord> reviews = SqlUtils.getReviews(data, carrierId, data.getRoundNumber());
            CarrierreviewRecord carrierReview = SqlUtils.getCarrierReview(data, carrierId, data.getRoundNumber());
            s.append("      <div class=\"tg-carrier-middle-stars\">\n");
            s.append(formatStars(carrierReview == null ? 0.0 : carrierReview.getOverallstars()));
            s.append("      <span class=\"tg-carrier-middle-reviews\">");
            s.append(reviews.size() + " review" + (reviews.size() != 1 ? "s" : ""));
            s.append("</span>\n"); // carrier-middle-reviews
            s.append("      </div>\n"); // carrier-middle-stars
            s.append("    </div>\n"); // carrier-middle
            s.append("    <div class=\"tg-button-small tg-carrier-button\" onclick=\"clickCarrierDetails(");
            s.append(carrierId);
            s.append(", 1)\">View carrier details</div>");
            s.append("  </div>\n"); // carrier
        }
        s.append("</div>\n"); // carrierList
        data.setContentHtml(s.toString());
    }

    private CarrierRecord handleCarrierDetails(TrustGameData data, int clickedCarrierId) {
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, clickedCarrierId);
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-carrier-details\">\n");
        s.append(carrierDetailsHeader(data, carrier));
        s.append("  <div class=\"tg-carrier-details-description\">\n");
        s.append(carrier.getCompanydescription());
        s.append("  </div>\n"); // carrier-details-description
        s.append(carrierDetailsMenu(clickedCarrierId));
        s.append("</div>\n"); // carrier-details
        data.setContentHtml(s.toString());
        return carrier;
    }

    private CarrierRecord handleCarrierWebsite(TrustGameData data, int clickedCarrierId) {
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, clickedCarrierId);
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-carrier-website\">\n");
        if (carrier.getCarrierwebimage() == null) {
            s.append(carrierDetailsHeader(data, carrier));
            s.append("  <div class=\"tg-carrier-nowebsite-image\">No website available for this carrier</div>\n");
        } else {
            s.append("  <div class=\"tg-carrier-website-scroll\">\n");
            s.append("    <div class=\"tg-carrier-website-image\"><img src=\"/trustgame/imageCarrierWebsite?id="
                    + clickedCarrierId + "\" /></div>\n");
            s.append("  </div>\n"); // carrier-website-scroll
        }
        s.append(carrierDetailsMenu(clickedCarrierId));
        s.append("  </div>\n"); // carrier-details
        data.setContentHtml(s.toString());
        return carrier;
    }

    private CarrierRecord handleCarrierGoogle(TrustGameData data, int clickedCarrierId) {
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, clickedCarrierId);
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-carrier-website\">\n");
        if (carrier.getGoogleimage() == null) {
            s.append(carrierDetailsHeader(data, carrier));
            s.append("  <div class=\"tg-carrier-nowebsite-image\">No Google page available for this carrier</div>\n");
        } else {
            s.append("  <div class=\"tg-carrier-website-scroll\">\n");
            s.append("    <div class=\"tg-carrier-website-image\"><img src=\"/trustgame/imageCarrierGoogle?id="
                    + clickedCarrierId + "\" /></div>\n");
            s.append("  </div>\n"); // carrier-website-scroll
        }
        s.append(carrierDetailsMenu(clickedCarrierId));
        s.append("</div>\n"); // carrier-details
        data.setContentHtml(s.toString());
        return carrier;
    }

    private CarrierRecord handleCarrierReviews(TrustGameData data, int clickedCarrierId) {
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, clickedCarrierId);
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-carrier-reviews\">\n");
        s.append(carrierDetailsHeader(data, carrier));
        List<ReviewRecord> reviews = SqlUtils.getReviews(data, clickedCarrierId, data.getRoundNumber());
        for (ReviewRecord review : reviews) {
            s.append("  <div class=\"tg-carrier-review\">\n");
            s.append("    <div class=\"tg-carrier-review-col1\">\n");
            s.append(formatStars(review.getStars()));
            s.append("      <div class=\"tg-carrier-review-when\">");
            s.append(review.getWhen());
            s.append("</div>\n"); // carrier-review-when
            s.append("    </div>\n"); // carrier-review-col1
            s.append("    <div class=\"tg-carrier-review-col2\">\n");
            if (review.getReviewtext() == null || "null".equals(review.getReviewtext()))
                s.append("No comments");
            else
                s.append(review.getReviewtext());
            s.append("    </div>\n"); // carrier-review-col1
            s.append("  </div>\n"); // carrier-review
        }
        s.append(carrierDetailsMenu(clickedCarrierId));
        s.append("</div>\n"); // carrier-reviews
        data.setContentHtml(s.toString());
        return carrier;
    }

    private CarrierRecord handleCarrierReport(TrustGameData data, int clickedCarrierId) {
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, clickedCarrierId);
        UsercarrierRecord userCarrier = SqlUtils.readUserCarrierForCarrierId(data, clickedCarrierId);
        StringBuffer s = new StringBuffer();
        // popup if info not yet bought; contents if bought
        if (userCarrier == null) {
            s.append("\n<div class=\"tg-carrier-report-ask\">\n");
            s.append(carrierDetailsHeader(data, carrier));
            s.append("  <div class=\"tg-carrier-report-ask-text\">");
            s.append("You can get a more detailed report from FeightBooking.com for 5 tokens.<br>");
            s.append("Do you want to buy the official report of ");
            s.append(carrier.getName());
            s.append("?</div>\n");
            s.append("    <div class=\"tg-carrier-report-ask-buttons\">\n");
            s.append(
                    "      <div class=\"tg-button-small tg-carrier-report-ask-button\" onclick=\"clickCarrierDetails(");
            s.append(clickedCarrierId);
            s.append(", 6)\">Buy report</div>\n");
            s.append(
                    "      <div class=\"tg-button-small tg-carrier-report-ask-button\" onclick=\"clickCarrierDetails(");
            s.append(clickedCarrierId);
            s.append(", 1)\">Cancel</div>\n");
            s.append("    </div>\n");
            s.append("    <hr />\n"); // ask-buttons
            s.append("  </div>\n"); // carrier-review
        }

        else

        {
            s.append("\n<div class=\"tg-carrier-report\">\n");
            s.append("  <div class=\"tg-carrier-report-columns\">\n");

            // col1
            s.append("    <div class=\"tg-carrier-report-col1\">\n");
            s.append("      <div class=\"tg-carrier-details-icon\"><img src=\"/trustgame/imageCarrier?id="
                    + carrier.getId() + "\" /></div>\n");
            s.append("      <div class=\"tg-carrier-details-name\">");
            s.append(carrier.getName());
            s.append("</div>\n"); // carrier-details-name
            s.append("      <div class=\"tg-carrier-details-stars\">");
            CarrierreviewRecord carrierReview = SqlUtils.getCarrierReview(data, carrier.getId(), data.getRoundNumber());
            FbreportRecord fb = SqlUtils.readFBReportForCarrierId(data, carrier.getId());
            s.append(formatStars(carrierReview == null ? 0.0 : carrierReview.getOverallstars()));
            s.append("      </div>\n"); // carrier-details-stars
            s.append("      <hr />\n");
            s.append("      <div class=\"tg-carrier-report-official\">Official report by FreightBooking.com</div>\n");
            s.append("      <div class=\"tg-carrier-report-header\">General information</div>\n");
            s.append("      <div class=\"tg-carrier-report-line\">Registration number:<br/>");
            s.append(fb.getFbregistration());
            s.append("</div>\n");
            s.append("      <div class=\"tg-carrier-report-line\">Country: ");
            s.append(fb.getCountrycode());
            s.append("</div>\n");
            s.append("      <div class=\"tg-carrier-report-line\">Address company:<br/>");
            s.append(fb.getAddress());
            s.append("</div>\n");
            s.append("      <div class=\"tg-carrier-report-line\">Freightbooking member since:<br/>");
            s.append(fb.getFbmembersince());
            s.append("</div>\n");
            s.append("    </div>\n"); // carrier-report-col1

            // col2
            s.append("    <div class=\"tg-carrier-report-col2\">\n");
            s.append("      <div class=\"tg-carrier-report-header\">Services</div>\n");
            s.append("      <div class=\"tg-carrier-fb-graph\"><img src=\"/trustgame/imageFB?id="
                    + carrier.getId() + "&image=1" + "\" /></div>\n");
            s.append("      <div class=\"tg-carrier-fb-graph\"><img src=\"/trustgame/imageFB?id="
                    + carrier.getId() + "&image=2" + "\" /></div>\n");
            s.append("    </div>\n"); // carrier-report-col2

            // col3
            s.append("    <div class=\"tg-carrier-report-col3\">\n");
            s.append("      <div class=\"tg-carrier-report-header\">Technical details</div>\n");
            s.append("      <div class=\"tg-carrier-fb-graph\"><img src=\"/trustgame/imageFB?id="
                    + carrier.getId() + "&image=3" + "\" /></div>\n");
            s.append("      <div class=\"tg-carrier-fb-graph\"><img src=\"/trustgame/imageFB?id="
                    + carrier.getId() + "&image=4" + "\" /></div>\n");
            s.append("    </div>\n"); // carrier-report-col3

            s.append("  </div>\n"); // carrier-report-columns
            s.append("</div>\n"); // carrier-report
        }
        s.append(carrierDetailsMenu(clickedCarrierId));
        data.setContentHtml(s.toString());
        return carrier;
    }

    private CarrierRecord buyCarrierReport(TrustGameData data, int clickedCarrierId) {
        // update score
        data.getGameUser().setScoreprofit(data.getGameUser().getScoreprofit() - 5);
        SqlUtils.updateGameUser(data, data.getGameUser());

        // register buying of report in database
        UsercarrierRecord userCarrier = DSL.using(data.getDataSource(), SQLDialect.MYSQL).newRecord(Tables.USERCARRIER);
        userCarrier.setCarrierId(clickedCarrierId);
        userCarrier.setGameuserId(data.getGameUserId());
        userCarrier.setBoughtreport((byte) 1);
        userCarrier.setRoundnumber(data.getRoundNumber());
        userCarrier.store();
        return handleCarrierReport(data, clickedCarrierId);
    }

    private String carrierDetailsMenu(int clickedCarrierId) {
        StringBuffer s = new StringBuffer();
        s.append("  <div class=\"tg-carrier-details-buttons\">\n");
        s.append("    <div class=\"tg-button-small tg-carrier-details-button\" onclick=\"clickCarrierDetails(");
        s.append(clickedCarrierId);
        s.append(", 1)\">Company</div>");
        s.append("    <div class=\"tg-button-small tg-carrier-details-button\" onclick=\"clickCarrierDetails(");
        s.append(clickedCarrierId);
        s.append(", 2)\">Website</div>");
        s.append("    <div class=\"tg-button-small tg-carrier-details-button\" onclick=\"clickCarrierDetails(");
        s.append(clickedCarrierId);
        s.append(", 3)\">Google</div>");
        s.append("    <div class=\"tg-button-small tg-carrier-details-button\" onclick=\"clickCarrierDetails(");
        s.append(clickedCarrierId);
        s.append(", 4)\">Reviews</div>");
        s.append("    <div class=\"tg-button-small tg-carrier-details-button\" onclick=\"clickCarrierDetails(");
        s.append(clickedCarrierId);
        s.append(", 5)\">Official report</div>");
        s.append("  </div>\n"); // carrier-details-buttons
        return s.toString();
    }

    private String carrierDetailsHeader(TrustGameData data, CarrierRecord carrier) {
        StringBuffer s = new StringBuffer();
        s.append("  <div class=\"tg-carrier-details-row\">\n");
        s.append("    <div class=\"tg-carrier-details-icon\"><img src=\"/trustgame/imageCarrier?id=" + carrier.getId()
                + "\" /></div>\n");
        s.append("    <div class=\"tg-carrier-details-name-stars\">\n");
        s.append("      <div class=\"tg-carrier-details-name\">");
        s.append(carrier.getName());
        s.append("</div>\n"); // carrier-details-name
        s.append("      <div class=\"tg-carrier-details-stars\">");
        CarrierreviewRecord carrierReview = SqlUtils.getCarrierReview(data, carrier.getId(), data.getRoundNumber());
        s.append(formatStars(carrierReview == null ? 0.0 : carrierReview.getOverallstars()));
        s.append("      </div>\n"); // carrier-details-stars
        s.append("    </div>\n"); // carrier-details-name-stars
        s.append("  </div>\n"); // carrier-details-row
        s.append("  <hr />\n");
        return s.toString();
    }

    private static String formatStars(double stars) {
        StringBuffer s = new StringBuffer();
        s.append("    <div class=\"tg-quote-review-stars\">\n");
        for (double i = 0.1; i < 5.0; i += 1.0) {
            if (stars > i) {
                if (stars - 0.5 > i)
                    s.append("      <img src=\"images/star-orange-full.png\" />\n");
                else
                    s.append("      <img src=\"images/star-orange-half.png\" />\n");
            } else {
                s.append("      <img src=\"images/star-orange-open.png\" />\n");
            }
        }
        s.append("    </div>\n");
        return s.toString();
    }

    private static String scoreReviewStars(int orderId) {
        StringBuffer s = new StringBuffer();
        s.append("    <div class=\"tg-review-stars\">\n");
        for (int i = 1; i <= 5; i++) {
            s.append("      <span onclick=\"clickGiveStars(");
            s.append(orderId);
            s.append(", ");
            s.append(i);
            s.append(")\" >&#9734;</span>\n");
        }
        s.append("    </div>\n");
        return s.toString();
    }

    private static String displayReviewStars(double d) {
        int stars = (int) Math.round(d);
        StringBuffer s = new StringBuffer();
        s.append("    <div class=\"tg-display-stars\">\n");
        for (int i = 1; i <= 5; i++) {
            s.append("      <span>");
            if (i <= stars)
                s.append("&#9733;");
            else
                s.append("&#9734;");
            s.append("</span>\n");
        }
        s.append("    </div>\n");
        return s.toString();
    }

    /* messages to show are the ones for the CURRENT round for the ACCEPTED orders. */
    public static void handleMessages(TrustGameData data) {
        StringBuilder s = new StringBuilder();
        s.append("    <div class=\"tg-messages\">\n");
        List<OrderRecord> acceptedOrders = SessionUtils.getAcceptedOrderListForRound(data, data.getRoundNumber());
        for (OrderRecord order : acceptedOrders) {
            SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
            if (selectedCarrier != null) {
                OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data,
                        selectedCarrier.getOrdercarrierId());
                if (orderCarrier != null) {
                    CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
                    if (carrier != null) {
                        s.append("      <div class=\"tg-message\">\n");
                        s.append("        <div class=\"tg-message-carrier-line\">");
                        s.append("Message from carrier: ");
                        s.append(carrier.getName());
                        s.append("</div>\n");
                        s.append("        <div class=\"tg-message-carrier-line\">");
                        s.append("For order number: ");
                        s.append(order.getOrdernumber());
                        s.append("</div>\n");
                        s.append("        <div class=\"tg-message-carrier-line\">");
                        s.append(orderCarrier.getTransportmessage() == null
                                ? "No message has been received from the carrier"
                                : orderCarrier.getTransportmessage());
                        s.append("</div>\n");
                        s.append("      </div>\n");
                    }
                }
            }
        }
        s.append("    </div>\n");
        data.setMessagesHtml(s.toString());
    }

    private String makeModalWindow(String title, String content, String onClickClose) {
        StringBuffer s = new StringBuffer();
        s.append("    <div class=\"tg-modal\">\n");
        s.append("      <div class=\"tg-modal-window\" id=\"tg-modal-window\">\n");
        s.append("        <div class=\"tg-modal-window-header\">");
        s.append("          <span class=\"tg-modal-close\" onclick=\"");
        s.append(onClickClose);
        s.append("\">");
        s.append("&times;</span>\n");
        s.append("          <p>");
        s.append(title);
        s.append("</p>\n");
        s.append("        </div>\n");
        s.append(content);
        s.append("      </div>\n");
        s.append("    </div>\n");
        s.append("    <script>");
        s.append("      dragElement(document.getElementById(\"tg-modal-window\"));");
        s.append("    </script>");
        return s.toString();
    }

    private String acceptQuoteModalWindow(TrustGameData data, OrderRecord order, OrdercarrierRecord orderCarrier,
            CarrierRecord carrier) {
        StringBuilder s = new StringBuilder();
        s.append("        <div class=\"tg-modal-body\">");
        s.append("          <div class=\"tg-modal-text\">\n");
        s.append("            <p>Are you sure you want to accept the quote for order #");
        s.append(order.getOrdernumber());
        s.append("<br/>offered by ");
        s.append(carrier.getName());
        s.append("?</p>\n");
        s.append("          </div>\n");
        s.append("          <div class=\"tg-modal-button-row\">\n");
        s.append("            <div class=\"tg-button-small\" onclick=\"clickAcceptQuoteNo()\">No</div>\n");
        s.append("            <div class=\"tg-button-small\" onclick=\"clickAcceptQuoteYes(");
        s.append(order.getId());
        s.append(", ");
        s.append(orderCarrier.getId());
        s.append(")\">Yes</div>\n");
        s.append("          </div>\n");
        s.append("        </div>\n");
        return makeModalWindow("Accept Quote", s.toString(), "clickAcceptQuoteNo()");
    }

    private String makeOkModalWindow(String title, String htmlText) {
        StringBuffer s = new StringBuffer();
        s.append("        <div class=\"tg-modal-body\">");
        s.append("          <div class=\"tg-modal-text\">\n");
        s.append("            <p>\n");
        s.append(htmlText);
        s.append("            </p>\n");
        s.append("          <div class=\"tg-modal-button-row\">\n");
        s.append("            <div class=\"tg-button-small\" onclick=\"clickModalWindowOk()\">OK</div>\n");
        s.append("          </div>\n");
        s.append("        </div>\n");
        return makeModalWindow(title, s.toString(), "clickModalWindowOk()");
    }

    private String makeTransportOutcome(TrustGameData data, int orderId) {
        StringBuffer s = new StringBuffer();
        OrderRecord order = SessionUtils.getOrderRecord(data, orderId);
        SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
        OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data, selectedCarrier.getOrdercarrierId());
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
        String title = "Transport outcome day " + data.getRoundNumber() + " for order #" + order.getOrdernumber();
        s.append("<p style=\"font-size: 1.2em;\"><b>Carrier: " + carrier.getName() + "</b></p>\n<center>");
        s.append(orderCarrier.getOutcomemessage());
        s.append("</center><br/><br/>\n<table width=\"100%\"><tr><td width=\"25%\">Consequences</td>");
        s.append("<td width=\"8%\"><img src=\"images/euro.png\" width=\"24\" height=\"24\" /></td><td width=\"15%\">");
        s.append(order.getTransportearnings() - orderCarrier.getQuoteoffer());
        s.append("</td>\n");
        s.append("<td width=\"8%\"><img src=\"images/smile.png\" width=\"24\" height=\"24\" /></td><td width=\"15%\">");
        s.append(orderCarrier.getOutcomesatisfaction());
        s.append("</td>\n");
        s.append("<td width=\"8%\"><img src=\"images/leaf.png\" width=\"24\" height=\"24\" /></td><td width=\"15%\">");
        s.append(orderCarrier.getOutcomesustainability());
        s.append("</td><td width=\"6%\">&nbsp;</td></tr></table>\n");
        return makeOkModalWindow(title, s.toString());
    }

    private void handleReviewStars(TrustGameData data, int orderId, int nrStars) {
        OrderRecord order = SessionUtils.getOrderRecord(data, orderId);
        SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
        OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data, selectedCarrier.getOrdercarrierId());
        CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, orderCarrier.getCarrierId());
        selectedCarrier.setUserscore(1.0 * nrStars);
        selectedCarrier.store();

        // last review?
        int roundNumber = data.getRoundNumber();
        boolean lastReview = SessionUtils.getUnconfirmedOrderListForRound(data, roundNumber).size() == 0;
        if (lastReview) {
            for (OrderRecord o : SessionUtils.getConfirmedOrderListForRound(data, roundNumber)) {
                SelectedcarrierRecord sc = SessionUtils.getSelectedCarrierForOrder(data, o);
                if (sc == null) {
                    lastReview = false;
                    break;
                }
                if (sc.getUserscore() == null) {
                    lastReview = false;
                    break;
                }
            }
        }
        if (lastReview) {
            GameuserRecord gameUser = data.getGameUser();
            gameUser.setRoundstatus(3);
            gameUser.store();
            data.setDayButton(TrustGameData.dayButtonNextDay);
        }

        if (data.getContentChoice() == 1)
            handleOrderContent(data);

        // make popup
        StringBuffer s = new StringBuffer();
        s.append("<p>Thanks for reviewing the transport of order #");
        s.append(order.getOrdernumber());
        s.append(" by carrier ");
        s.append(carrier.getName());
        s.append(".</p>\n");
        if (lastReview) {
            s.append("<br/><p>Since you have reviewed all transports, you can go to the next day by clicking on the ");
            s.append("<b>Next Day</b> button at the top right of the screen.</p><br/>\n");
        } else {
            s.append("<br/><p>Please review all transports before you can go to the next day</p><br/>\n");
        }
        data.setModalWindowHtml(makeOkModalWindow("Thanks for your review!", s.toString()));
        data.setShowModalWindow(1);
    }

    private void handleDebrief(TrustGameData data) {
        StringBuffer s = new StringBuffer();
        s.append("\n<div class=\"tg-debrief\">\n");
        s.append("  <div class=\"tg-debrief-header\">Debriefing screen</div>\n");
        s.append("  <div class=\"tg-debrief-hr\"></div>\n");
        s.append("  <div class=\"tg-debrief-container\">\n");
        s.append("    <div class=\"tg-debrief-round-table\">\n");
        s.append("      <table>\n");
        s.append(
                "        <thead><tr><td>Round</td><td>Profit</td><td>Satisfaction</td><td>Sustainability</td></tr></thead>\n");
        s.append("        <tbody>\n");

        s.append("           <tr><td>Start</td><td>");
        s.append(data.getOrganization().getStartprofit());
        s.append("</td><td>");
        s.append(data.getOrganization().getStartsatisfaction());
        s.append("</td><td>");
        s.append(data.getOrganization().getStartsustainability());
        s.append("</td></tr>\n");

        SortedSet<Integer> carrierIds = new TreeSet<>();
        for (Integer orderNr : data.getOrderCarrierMap().keySet()) {
            for (OrdercarrierRecord orderCarrier : data.getOrderCarrierMap().get(orderNr)) {
                carrierIds.add(orderCarrier.getCarrierId());
            }
        }
        SortedMap<Integer, CarrierDebriefRecord> carrierDebriefMap = new TreeMap<>(); // carrierId -> record
        for (Integer carrierId : carrierIds) {
            CarrierRecord carrier = SqlUtils.readCarrierFromCarrierId(data, carrierId);
            CarrierDebriefRecord cdr = new CarrierDebriefRecord();
            carrierDebriefMap.put(carrierId, cdr);
            cdr.id = carrierId;
            cdr.name = carrier.getName();
            CarrierreviewRecord carrierReview = SqlUtils.getCarrierReview(data, carrierId, data.getRoundNumber());
            cdr.fbstars = carrierReview.getOverallstars();
        }

        for (int round = 1; round < data.getRoundNumber(); round++) {
            s.append("           <tr><td>");
            s.append(round);
            s.append("</td><td>");
            int sprof = 0;
            int ssat = 0;
            int ssus = 0;
            for (OrderRecord order : SessionUtils.getAcceptedOrderListForRound(data, round)) {
                SelectedcarrierRecord selectedCarrier = SessionUtils.getSelectedCarrierForOrder(data, order);
                OrdercarrierRecord orderCarrier = SessionUtils.getOrderCarrierRecord(data,
                        selectedCarrier.getOrdercarrierId());
                sprof += order.getTransportearnings() - orderCarrier.getQuoteoffer() + orderCarrier.getExtraprofit();
                ssat += orderCarrier.getOutcomesatisfaction();
                ssus += orderCarrier.getOutcomesustainability();
                CarrierDebriefRecord cdr = carrierDebriefMap.get(orderCarrier.getCarrierId());
                cdr.profit += order.getTransportearnings() - orderCarrier.getQuoteoffer()
                        + orderCarrier.getExtraprofit();
                cdr.satisfaction += orderCarrier.getOutcomesatisfaction();
                cdr.sustainability += orderCarrier.getOutcomesustainability();
                cdr.timesUsed++;
                cdr.sumUserStars += selectedCarrier.getUserscore();
            }
            s.append(sprof);
            s.append("</td><td>");
            s.append(ssat);
            s.append("</td><td>");
            s.append(ssus);
            s.append("</td></tr>\n");
        }

        s.append("           <tr><td>Total</td><td>");
        s.append(data.getGameUser().getScoreprofit());
        s.append("</td><td>");
        s.append(data.getGameUser().getScoresatisfaction());
        s.append("</td><td>");
        s.append(data.getGameUser().getScoresustainability());
        s.append("</td></tr>\n");

        s.append("        </tbody>\n");
        s.append("      </table>\n");
        s.append("    </div>\n"); // debrief-round-table

        s.append("    <div class=\"tg-debrief-carrier-table\">\n");
        s.append("      <table>\n");
        s.append("        <thead><tr><td>Carrier</td><td>Times used</td><td>Your stars</td><td>FB stars</td>");
        s.append("<td>Profit</td><td>Satisfaction</td><td>Sustainability</td></tr></thead>\n");
        s.append("        <tbody>\n");

        for (CarrierDebriefRecord cdr : carrierDebriefMap.values()) {
            s.append("           <tr><td>");
            s.append(cdr.name);
            s.append("</td><td>");
            s.append(cdr.timesUsed);
            s.append("</td><td>");
            double stars = cdr.timesUsed == 0 ? 0.0 : 0.5 * Math.round(2.0 * cdr.sumUserStars / cdr.timesUsed);
            s.append(formatStars(stars));
            s.append("</td><td>");
            s.append(formatStars(cdr.fbstars));
            s.append("</td><td>");
            s.append(cdr.profit);
            s.append("</td><td>");
            s.append(cdr.satisfaction);
            s.append("</td><td>");
            s.append(cdr.sustainability);
            s.append("</td></tr>\n");
        }

        s.append("        </tbody>\n");
        s.append("      </table>\n");
        s.append("    </div>\n"); // debrief-carrier-table

        s.append("  </div>\n"); // debrief-container
        s.append("</div>\n"); // debrief
        data.setContentHtml(s.toString());
    }

    class CarrierDebriefRecord {
        public int id;
        public String name;
        public int profit;
        public int satisfaction;
        public int sustainability;
        public double fbstars;
        public double sumUserStars;
        public int timesUsed;
    }
}
