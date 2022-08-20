package org.transsonic.trustgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.transsonic.trustgame.data.trustgame.tables.records.OrderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrdercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.RoundRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.SelectedcarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserorderRecord;

public final class SessionUtils {

    private SessionUtils() {
        // utility class
    }

    public static TrustGameData getData(final HttpSession session) {
        return (TrustGameData) session.getAttribute("trustGameData");
    }

    public static boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("userId") == null) {
            response.sendRedirect("jsp/trustgame/login.jsp");
            return false;
        }
        @SuppressWarnings("unchecked")
        Map<Integer, String> idSessionMap = (Map<Integer, String>) request.getServletContext()
                .getAttribute("idSessionMap");
        String storedSessionId = idSessionMap.get(request.getSession().getAttribute("userId"));
        if (!request.getSession().getId().equals(storedSessionId)) {
            response.sendRedirect("jsp/trustgame/login-session.jsp");
            return false;
        }
        return true;
    }

    public static RoundRecord getRoundRecord(final TrustGameData data, final Integer roundId) {
        SortedMap<Integer, RoundRecord> roundMap = data.getRoundMapByRoundNr();
        for (RoundRecord roundRecord : roundMap.values()) {
            if (roundId.equals(roundRecord.getId()))
                return roundRecord;
        }
        return null;
    }

    public static RoundRecord getRoundRecord(final HttpSession session, final Integer roundId) {
        return getRoundRecord(getData(session), roundId);
    }

    public static OrderRecord getOrderRecord(final TrustGameData data, final Integer orderId) {
        SortedMap<Integer, List<OrderRecord>> orderMap = data.getOrderMapByRoundNr();
        for (int roundNumber : orderMap.keySet()) {
            for (OrderRecord orderRecord : orderMap.get(roundNumber)) {
                if (orderId.equals(orderRecord.getId()))
                    return orderRecord;
            }
        }
        return null;
    }

    public static OrderRecord getOrderRecord(final HttpSession session, final Integer orderId) {
        return getOrderRecord(getData(session), orderId);
    }

    public static List<OrderRecord> getUnconfirmedOrderListForRound(final TrustGameData data, final int roundNumber) {
        SortedMap<Integer, List<OrderRecord>> orderMap = data.getOrderMapByRoundNr();
        if (orderMap != null && orderMap.containsKey(roundNumber)) {
            List<OrderRecord> orderList = new ArrayList<>();
            for (OrderRecord order : orderMap.get(roundNumber)) {
                if (!isOrderConfirmed(data, order))
                    orderList.add(order);
            }
            return orderList;
        } else
            return new ArrayList<>();
    }

    public static List<OrderRecord> getUnconfirmedOrderListForRound(final HttpSession session, final int roundNumber) {
        return getUnconfirmedOrderListForRound(getData(session), roundNumber);
    }

    public static boolean isOrderConfirmed(final TrustGameData data, final OrderRecord order) {
        SortedMap<Integer, List<UserorderRecord>> userOrders = data.getUserOrderMapByRoundNr();
        for (int roundNumber : userOrders.keySet()) {
            for (UserorderRecord userOrder : userOrders.get(roundNumber)) {
                if (userOrder.getOrderId().equals(order.getId()))
                    return true;
            }
        }
        return false;
    }

    public static List<OrderRecord> getConfirmedOrderListForRound(final TrustGameData data, final int roundNumber) {
        SortedMap<Integer, List<OrderRecord>> orderMap = data.getOrderMapByRoundNr();
        if (orderMap != null && orderMap.containsKey(roundNumber)) {
            List<OrderRecord> orderList = new ArrayList<>();
            for (OrderRecord order : orderMap.get(roundNumber)) {
                if (isOrderConfirmed(data, order))
                    orderList.add(0, order);
            }
            return orderList;
        } else
            return new ArrayList<>();
    }

    public static List<OrderRecord> getConfirmedOrderListForRound(final HttpSession session, final int roundNumber) {
        return getConfirmedOrderListForRound(getData(session), roundNumber);
    }

    public static List<OrderRecord> getConfirmedOrderListUpToRound(final TrustGameData data, final int roundNumber) {
        SortedMap<Integer, List<OrderRecord>> orderMap = data.getOrderMapByRoundNr();
        List<OrderRecord> returnList = new ArrayList<>();
        for (List<OrderRecord> orderList : orderMap.values()) {
            for (OrderRecord order : orderList) {
                if (getRoundNumberForOrder(data, order) <= roundNumber && isOrderConfirmed(data, order))
                    returnList.add(0, order);
            }
        }
        return returnList;
    }

    public static List<OrderRecord> getConfirmedOrderListUpToRound(final HttpSession session, final int roundNumber) {
        return getConfirmedOrderListUpToRound(getData(session), roundNumber);
    }

    public static int getRoundNumberForOrder(TrustGameData data, OrderRecord order) {
        RoundRecord round = getRoundRecord(data, order.getRoundId());
        return round.getRoundnumber();
    }

    public static OrdercarrierRecord getOrderCarrierRecord(final TrustGameData data, final Integer orderCarrierId) {
        Map<Integer, List<OrdercarrierRecord>> orderCarrierMap = data.getOrderCarrierMapByOrderId();
        for (int orderId : orderCarrierMap.keySet()) {
            for (OrdercarrierRecord orderCarrierRecord : orderCarrierMap.get(orderId)) {
                if (orderCarrierId.equals(orderCarrierRecord.getId()))
                    return orderCarrierRecord;
            }
        }
        return null;
    }

    public static OrdercarrierRecord getOrderCarrierRecord(final HttpSession session, final Integer orderCarrierId) {
        return getOrderCarrierRecord(getData(session), orderCarrierId);
    }

    public static UserorderRecord getUserOrderForOrder(final TrustGameData data, final OrderRecord order) {
        SortedMap<Integer, List<UserorderRecord>> userOrders = data.getUserOrderMapByRoundNr();
        if (userOrders == null)
            return null;
        for (int roundNumber : userOrders.keySet()) {
            for (UserorderRecord userOrder : userOrders.get(roundNumber)) {
                if (userOrder.getOrderId().equals(order.getId()))
                    return userOrder;
            }
        }
        return null;

    }

    public static SelectedcarrierRecord getSelectedCarrierForOrder(final TrustGameData data, final OrderRecord order) {
        UserorderRecord userOrder = getUserOrderForOrder(data, order);
        if (userOrder == null)
            return null;
        return data.getSelectedCarrierMapByUserOrderId().get(userOrder.getId());
    }

    public static List<OrderRecord> getAcceptedOrderListForRound(final TrustGameData data, final int roundNumber) {
        SortedMap<Integer, List<OrderRecord>> orderMap = data.getOrderMapByRoundNr();
        if (orderMap != null && orderMap.containsKey(roundNumber)) {
            List<OrderRecord> orderList = new ArrayList<>();
            for (OrderRecord order : orderMap.get(roundNumber)) {
                if (getSelectedCarrierForOrder(data, order) != null)
                    orderList.add(order);
            }
            return orderList;
        } else
            return new ArrayList<>();
    }

    public static boolean isOrderAccepted(final TrustGameData data, final OrderRecord order) {
        return getSelectedCarrierForOrder(data, order) != null;
    }

    /**
     * Return 0 = order unconfirmed; 1 = confirmed but not accepted; 2 = accepted and in transport; 3 = accepted and
     * unreviewed; 4 = accepted and reviewed.
     * 
     * @param data  user and session data
     * @param order the order to check
     * @return status of the order
     */
    public static int orderStatus(TrustGameData data, OrderRecord order) {
        if (!isOrderConfirmed(data, order))
            return 0;
        SelectedcarrierRecord selectedCarrier = getSelectedCarrierForOrder(data, order);
        if (selectedCarrier == null)
            return 1;
        if (selectedCarrier.getUserscore() != null)
            return 4;
        if (data.getGameUser().getRoundstatus().intValue() <= 1)
            return 2;
        else
            return 3;
    }
}
