package org.transsonic.trustgame.logging;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.transsonic.trustgame.TrustGameData;
import org.transsonic.trustgame.data.trustgame.Tables;
import org.transsonic.trustgame.data.trustgame.tables.records.UserclickRecord;

public class LoggingUtils {

    public static void insertClick(TrustGameData data, String button) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.store();
    }

    public static void insertClickOrder(TrustGameData data, String button, int orderNumber) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.setOrdernumber(orderNumber);
        clickRecord.store();
    }

    public static void insertClickOrderClient(TrustGameData data, String button, int orderNumber, String clientName) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.setOrdernumber(orderNumber);
        clickRecord.setClientname(clientName);
        clickRecord.store();
    }

    public static void insertClickOrderCarrier(TrustGameData data, String button, int orderNumber, String carrierName) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.setOrdernumber(orderNumber);
        clickRecord.setCarriername(carrierName);
        clickRecord.store();
    }

    public static void insertClickOrderValue(TrustGameData data, String button, int orderNumber, String value) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.setOrdernumber(orderNumber);
        clickRecord.setValue(value);
        clickRecord.store();
    }

    public static void insertClickCarrier(TrustGameData data, String button, String carrierName) {
        DSLContext dslContext = DSL.using(data.getDataSource(), SQLDialect.MYSQL);
        UserclickRecord clickRecord = dslContext.newRecord(Tables.USERCLICK);
        clickRecord.setGameuserId(data.getGameUserId());
        clickRecord.setButtonorfield(button);
        clickRecord.setRoundnumber(data.getRoundNumber());
        clickRecord.setCarriername(carrierName);
        clickRecord.store();
    }

}
