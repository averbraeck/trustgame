package org.transsonic.trustgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.transsonic.trustgame.data.trustgame.Tables;
import org.transsonic.trustgame.data.trustgame.tables.Gameuser;
import org.transsonic.trustgame.data.trustgame.tables.records.BriefingRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.CarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.CarrierreviewRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.ClientRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.FbreportRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameplayRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.GameuserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.OrdercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.PlayerorganizationRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.ReviewRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.RoundRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.SelectedcarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UsercarrierRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserorderRecord;
import org.transsonic.trustgame.data.trustgame.tables.records.UserroundRecord;
import org.transsonic.trustgame.round.RoundServlet;

public final class SqlUtils {

    private SqlUtils() {
        // utility class
    }

    public static PlayerorganizationRecord readPlayerOrganizationFromGameId(final TrustGameData data,
            final Integer gameId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        GameRecord game = dslContext.selectFrom(Tables.GAME).where(Tables.GAME.ID.eq(gameId)).fetchAny();
        if (game == null) {
            System.err.println("Could not load game");
            return null;
        }
        return dslContext.selectFrom(Tables.PLAYERORGANIZATION)
                .where(Tables.PLAYERORGANIZATION.ID.eq(game.getOrganizationId())).fetchAny();
    }

    public static PlayerorganizationRecord readPlayerOrganizationFromGame(final TrustGameData data,
            final GameRecord game) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.PLAYERORGANIZATION)
                .where(Tables.PLAYERORGANIZATION.ID.eq(game.getOrganizationId())).fetchAny();
    }

    public static GameRecord readGameFromGameId(final TrustGameData data, final Integer gameId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.GAME).where(Tables.GAME.ID.eq(gameId)).fetchAny();
    }

    public static GameplayRecord readGamePlayFromGamePlayId(final TrustGameData data, final Integer gamePlayId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.GAMEPLAY).where(Tables.GAMEPLAY.ID.eq(gamePlayId)).fetchAny();
    }

    public static GameRecord readGameFromGamePlayId(final TrustGameData data, final Integer gamePlayId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        GameplayRecord gamePlay = dslContext.selectFrom(Tables.GAMEPLAY).where(Tables.GAMEPLAY.ID.eq(gamePlayId))
                .fetchAny();
        return dslContext.selectFrom(Tables.GAME).where(Tables.GAME.ID.eq(gamePlay.getGameId())).fetchAny();
    }

    public static GameRecord readGameFromGamePlay(final TrustGameData data, final GameplayRecord gamePlay) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.GAME).where(Tables.GAME.ID.eq(gamePlay.getGameId())).fetchAny();
    }

    public static GameuserRecord readGameUserFromGameUserId(final TrustGameData data, final Integer gameUserId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.GAMEUSER).where(Tables.GAMEUSER.ID.eq(gameUserId)).fetchAny();
    }

    public static void updateGameUser(final TrustGameData data, final GameuserRecord gameUser) {
        gameUser.store();
    }

    public static GameplayRecord readGamePlayFromGameUserId(final TrustGameData data, final Integer gameUserId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        GameuserRecord gameUser = dslContext.selectFrom(Tables.GAMEUSER).where(Tables.GAMEUSER.ID.eq(gameUserId))
                .fetchAny();
        return dslContext.selectFrom(Tables.GAMEPLAY).where(Tables.GAMEPLAY.ID.eq(gameUser.getGameplayId())).fetchAny();
    }

    public static GameplayRecord readGamePlayFromGameUser(final TrustGameData data, final GameuserRecord gameUser) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.GAMEPLAY).where(Tables.GAMEPLAY.ID.eq(gameUser.getGameplayId())).fetchAny();
    }

    public static UserRecord readUserFromUserId(final TrustGameData data, final Integer userId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.USER).where(Tables.USER.ID.eq(userId)).fetchAny();
    }

    public static UserRecord readUserFromUsername(final TrustGameData data, final String username) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.USER).where(Tables.USER.USERNAME.eq(username)).fetchAny();
    }

    public static UserRecord readUserFromUserCode(final TrustGameData data, final String userCode) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.USER).where(Tables.USER.USERCODE.eq(userCode)).fetchAny();
    }

    public static List<GameuserRecord> readGameUsersFromUser(final TrustGameData data, final UserRecord user) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        List<GameuserRecord> gameUserRecords = dslContext.selectFrom(Gameuser.GAMEUSER)
                .where(Gameuser.GAMEUSER.USER_ID.eq(user.getId())).fetch();
        return gameUserRecords;
    }

    public static List<GameuserRecord> readGameUsersFromUserId(final TrustGameData data, final Integer userId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        List<GameuserRecord> gameUserRecords = dslContext.selectFrom(Gameuser.GAMEUSER)
                .where(Gameuser.GAMEUSER.USER_ID.eq(userId)).fetch();
        return gameUserRecords;
    }

    public static ClientRecord readClientFromClientId(final TrustGameData data, final Integer clientId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.CLIENT).where(Tables.CLIENT.ID.eq(clientId)).fetchAny();
    }

    public static CarrierRecord readCarrierFromCarrierId(final TrustGameData data, final Integer carrierId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.CARRIER).where(Tables.CARRIER.ID.eq(carrierId)).fetchAny();
    }

    public static BriefingRecord readBriefingFromBriefingId(final TrustGameData data, final Integer briefingId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.BRIEFING).where(Tables.BRIEFING.ID.eq(briefingId)).fetchAny();
    }

    public static List<ReviewRecord> getReviews(final TrustGameData data, final int carrierId, final int roundNumber) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        List<ReviewRecord> reviewList = dslContext.selectFrom(Tables.REVIEW)
                .where(Tables.REVIEW.CARRIER_ID.eq(carrierId)).fetch();
        List<ReviewRecord> filteredList = new ArrayList<>();
        for (int rn = 1; rn <= roundNumber; rn++) {
            RoundRecord round = data.getRoundMapByRoundNumber().get(rn);
            if (round != null) { // for after last round
                for (ReviewRecord review : reviewList) {
                    if (review.getRoundId().equals(round.getId())) {
                        filteredList.add(review);
                    }
                }
            }
        }
        return filteredList;
    }

    public static CarrierreviewRecord getCarrierReview(final TrustGameData data, final int carrierId,
            final int roundNumber) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        List<CarrierreviewRecord> carrierReviewList = dslContext.selectFrom(Tables.CARRIERREVIEW)
                .where(Tables.CARRIERREVIEW.CARRIER_ID.eq(carrierId)).fetch();
        CarrierreviewRecord crr = null;
        for (int rn = 1; rn <= roundNumber; rn++) {
            RoundRecord round = data.getRoundMapByRoundNumber().get(rn);
            if (round != null) { // for end of game
                for (CarrierreviewRecord carrierReview : carrierReviewList) {
                    if (carrierReview.getRoundId().equals(round.getId())) {
                        crr = carrierReview;
                        break;
                    }
                }
            }
        }
        return crr;
    }

    public static UsercarrierRecord readUserCarrierForCarrierId(final TrustGameData data, final int carrierId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UsercarrierRecord userCarrier = dslContext.selectFrom(Tables.USERCARRIER).where(Tables.USERCARRIER.CARRIER_ID
                .eq(carrierId).and(Tables.USERCARRIER.GAMEUSER_ID.eq(data.getGameUserId()))).fetchAny();
        return userCarrier;
    }

    public static List<UsercarrierRecord> readUserCarrierRecords(final TrustGameData data) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        List<UsercarrierRecord> userCarrierRecords = dslContext.selectFrom(Tables.USERCARRIER)
                .where(Tables.USERCARRIER.GAMEUSER_ID.eq(data.getGameUserId())).fetch();
        return userCarrierRecords;
    }

    public static FbreportRecord readFBReportForCarrierId(final TrustGameData data, final int carrierId) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        return dslContext.selectFrom(Tables.FBREPORT).where(Tables.FBREPORT.CARRIER_ID.eq(carrierId)).fetchAny();
    }

    public static UserroundRecord readOrInsertUserRoundRecord(final TrustGameData data) {
        if (isGameFinished(data))
            return data.getUserRoundMap().get(data.getRoundNumber() - 1);
        UserroundRecord userRound = data.getUserRoundMap().get(data.getRoundNumber());
        if (userRound == null) {
            DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
            userRound = dslContext.newRecord(Tables.USERROUND);
            userRound.setGameplayId(data.getGamePlayId());
            userRound.setRoundId(data.getRoundMapByRoundNumber().get(data.getRoundNumber()).getId());
            userRound.setGameuserId(data.getGameUserId());
            userRound.store();
            data.getUserRoundMap().put(data.getRoundNumber(), userRound);
        }
        return userRound;
    }

    public static boolean isGameFinished(TrustGameData data) {
        return !data.getOrderMap().keySet().contains(data.getRoundNumber());
    }

    public static void loadAttributes(HttpSession session, Integer gameUserId) {

        TrustGameData data = SessionUtils.getData(session);

        data.setGameUserId(gameUserId);
        data.setGameUser(readGameUserFromGameUserId(data, gameUserId));
        data.setRoundNumber(data.getGameUser().getRoundnumber().intValue());

        GameplayRecord gamePlay = SqlUtils.readGamePlayFromGameUserId(data, gameUserId);
        data.setGamePlayId(gamePlay.getId());
        data.setGamePlay(gamePlay);

        GameRecord game = SqlUtils.readGameFromGamePlayId(data, gamePlay.getId());
        data.setGameId(game.getId());
        data.setGame(game);

        PlayerorganizationRecord organization = SqlUtils.readPlayerOrganizationFromGameId(data, game.getId());
        data.setOrganization(organization);

        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);

        List<RoundRecord> roundRecords = dslContext.selectFrom(Tables.ROUND)
                .where(Tables.ROUND.GAME_ID.eq(game.getId())).fetch().sortAsc(Tables.ROUND.ROUNDNUMBER);
        SortedMap<Integer, RoundRecord> roundMap = new TreeMap<>();
        Map<Integer, RoundRecord> roundIdMap = new HashMap<>();
        for (RoundRecord roundRecord : roundRecords) {
            roundMap.put(roundRecord.getRoundnumber(), roundRecord);
            roundIdMap.put(roundRecord.getId(), roundRecord);
        }
        data.setRoundMapByRoundNumber(roundMap);
        data.setRoundMapByRoundId(roundIdMap);

        // System.out.println("\nROUNDMAP:\n" + roundMap);
        // System.out.println("\nROUNDIDMAP:\n" + roundIdMap);

        SortedMap<Integer, List<OrderRecord>> orderMap = new TreeMap<>();
        for (RoundRecord roundRecord : roundRecords) {
            List<OrderRecord> orderList = dslContext.selectFrom(Tables.ORDER)
                    .where(Tables.ORDER.ROUND_ID.eq(roundRecord.getId())).fetch().sortAsc(Tables.ORDER.ORDERNUMBER);
            orderMap.put(roundRecord.getRoundnumber(), orderList);
        }
        data.setOrderMap(orderMap);

        // System.out.println("\nORDERMAP:\n" + orderMap);

        Map<Integer, List<OrdercarrierRecord>> orderCarrierMap = new HashMap<>();
        for (int roundNumber : orderMap.keySet()) {
            for (OrderRecord order : orderMap.get(roundNumber)) {
                List<OrdercarrierRecord> orderCarrierList = dslContext.selectFrom(Tables.ORDERCARRIER)
                        .where(Tables.ORDERCARRIER.ORDER_ID.eq(order.getId())).fetch();
                orderCarrierMap.put(order.getId(), orderCarrierList);
            }
        }
        data.setOrderCarrierMap(orderCarrierMap);

        // System.out.println("\nORDERCARRIERMAP:\n" + orderCarrierMap);

        updateUserData(session, data);

        data.setRoundNumber(data.getGameUser().getRoundnumber().intValue());
        readOrInsertUserRoundRecord(data); // will only insert if does not yet exist
        data.setFooterText(makeFooterText(data));

        data.setMenuChoice(1);
        data.setContentChoice(1);

        data.setShowModalWindow(0);

        // Initialize the left and right pane of the screen
        // show briefing screen if no orders have been confirmed yet
        RoundServlet.handleOrganizationScoresOrders(data);
        if (SessionUtils.getConfirmedOrderListUpToRound(data, 1).size() == 0)
            RoundServlet.handleBriefing(data);
        else
            RoundServlet.handleOrderContent(data);
        RoundServlet.handleMessages(data);
    }

    public static void updateUserData(final HttpSession session, final TrustGameData data) {

        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);

        List<UserroundRecord> userRoundList = dslContext.selectFrom(Tables.USERROUND)
                .where(Tables.USERROUND.GAMEPLAY_ID.eq(data.getGamePlay().getId()))
                .and(Tables.USERROUND.GAMEUSER_ID.eq(data.getGameUserId())).fetch();
        SortedMap<Integer, UserroundRecord> userRoundMap = new TreeMap<>();
        for (UserroundRecord userRoundRecord : userRoundList) {
            Integer roundNumber = SessionUtils.getRoundRecord(session, userRoundRecord.getRoundId()).getId();
            userRoundMap.put(roundNumber, userRoundRecord);
        }
        data.setUserRoundMap(userRoundMap);

        // System.out.println("\nUSERROUNDMAP:\n" + userRoundMap);

        SortedMap<Integer, List<UserorderRecord>> userOrderMap = new TreeMap<>();
        for (int roundNumber : userRoundMap.keySet()) {
            UserroundRecord userRound = userRoundMap.get(roundNumber);
            List<UserorderRecord> userOrderList = dslContext.selectFrom(Tables.USERORDER)
                    .where(Tables.USERORDER.USERROUND_ID.eq(userRound.getId())).fetch();
            userOrderMap.put(roundNumber, userOrderList);
        }
        data.setUserOrderMap(userOrderMap);

        // System.out.println("\nUSERORDERMAP:\n" + userOrderMap);

        Map<Integer, SelectedcarrierRecord> selectedCarrierMap = new HashMap<>();
        for (int roundNumber : userOrderMap.keySet()) {
            for (UserorderRecord userOrder : userOrderMap.get(roundNumber)) {
                List<SelectedcarrierRecord> selectedCarrierList = dslContext.selectFrom(Tables.SELECTEDCARRIER)
                        .where(Tables.SELECTEDCARRIER.USERORDER_ID.eq(userOrder.getId())).fetch();
                if (selectedCarrierList.size() >= 1) {
                    selectedCarrierMap.put(userOrder.getId(), selectedCarrierList.get(0));
                }
            }
        }
        data.setSelectedCarrierMap(selectedCarrierMap);

        // System.out.println("\nSELECTEDCARRIERMAP:\n" + selectedCarrierMap);

        if (isGameFinished(data)) {
            if (data.getGamePlay().getDebriefingId() == null || data.getGamePlay().getDebriefingId().intValue() == 0)
                data.setDayButton(TrustGameData.dayButtonScoreOverview);
            else
                data.setDayButton(TrustGameData.dayButtonScoreDebrief);
        } else {
            switch (data.getGameUser().getRoundstatus()) {
            case 1:
                data.setDayButton(TrustGameData.dayButtonFinishDay);
                break;
            case 2:
                data.setDayButton(TrustGameData.dayButtonFinishDayInactive);
                break;
            case 3:
                data.setDayButton(TrustGameData.dayButtonNextDay);
                break;
            default:
                data.setDayButton(TrustGameData.dayButtonStartInactive);
                break;
            }
        }
    }

    public static String makeFooterText(final TrustGameData data) {
        StringBuffer s = new StringBuffer();
        s.append("Transport day: ");
        s.append(data.getRoundNumber());
        if (data.getRoundNumber() > data.getRoundMapByRoundId().size()) {
            s.append(" (game over)");
        } else {
            s.append(" of ");
            s.append(data.getRoundMapByRoundNumber().size());
            s.append(" days");
            if (data.getRoundMapByRoundId().get(data.getRoundNumber()).getTestround() != 0) {
                s.append(" (practice round)");
            }
        }
        return s.toString();
    }
}
