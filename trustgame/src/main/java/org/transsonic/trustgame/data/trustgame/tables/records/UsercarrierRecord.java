/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
import org.transsonic.trustgame.data.trustgame.tables.Usercarrier;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UsercarrierRecord extends UpdatableRecordImpl<UsercarrierRecord> implements Record5<Integer, Integer, Integer, Byte, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>trustgame.usercarrier.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>trustgame.usercarrier.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>trustgame.usercarrier.GameUser_ID</code>.
     */
    public void setGameuserId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>trustgame.usercarrier.GameUser_ID</code>.
     */
    public Integer getGameuserId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>trustgame.usercarrier.Carrier_ID</code>.
     */
    public void setCarrierId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>trustgame.usercarrier.Carrier_ID</code>.
     */
    public Integer getCarrierId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>trustgame.usercarrier.BoughtReport</code>.
     */
    public void setBoughtreport(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>trustgame.usercarrier.BoughtReport</code>.
     */
    public Byte getBoughtreport() {
        return (Byte) get(3);
    }

    /**
     * Setter for <code>trustgame.usercarrier.RoundNumber</code>. Indicates in which round a report was bought
     */
    public void setRoundnumber(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>trustgame.usercarrier.RoundNumber</code>. Indicates in which round a report was bought
     */
    public Integer getRoundnumber() {
        return (Integer) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, Integer, Integer, Byte, Integer> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, Integer, Integer, Byte, Integer> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Usercarrier.USERCARRIER.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Usercarrier.USERCARRIER.GAMEUSER_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Usercarrier.USERCARRIER.CARRIER_ID;
    }

    @Override
    public Field<Byte> field4() {
        return Usercarrier.USERCARRIER.BOUGHTREPORT;
    }

    @Override
    public Field<Integer> field5() {
        return Usercarrier.USERCARRIER.ROUNDNUMBER;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getGameuserId();
    }

    @Override
    public Integer component3() {
        return getCarrierId();
    }

    @Override
    public Byte component4() {
        return getBoughtreport();
    }

    @Override
    public Integer component5() {
        return getRoundnumber();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getGameuserId();
    }

    @Override
    public Integer value3() {
        return getCarrierId();
    }

    @Override
    public Byte value4() {
        return getBoughtreport();
    }

    @Override
    public Integer value5() {
        return getRoundnumber();
    }

    @Override
    public UsercarrierRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UsercarrierRecord value2(Integer value) {
        setGameuserId(value);
        return this;
    }

    @Override
    public UsercarrierRecord value3(Integer value) {
        setCarrierId(value);
        return this;
    }

    @Override
    public UsercarrierRecord value4(Byte value) {
        setBoughtreport(value);
        return this;
    }

    @Override
    public UsercarrierRecord value5(Integer value) {
        setRoundnumber(value);
        return this;
    }

    @Override
    public UsercarrierRecord values(Integer value1, Integer value2, Integer value3, Byte value4, Integer value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UsercarrierRecord
     */
    public UsercarrierRecord() {
        super(Usercarrier.USERCARRIER);
    }

    /**
     * Create a detached, initialised UsercarrierRecord
     */
    public UsercarrierRecord(Integer id, Integer gameuserId, Integer carrierId, Byte boughtreport, Integer roundnumber) {
        super(Usercarrier.USERCARRIER);

        setId(id);
        setGameuserId(gameuserId);
        setCarrierId(carrierId);
        setBoughtreport(boughtreport);
        setRoundnumber(roundnumber);
    }
}
