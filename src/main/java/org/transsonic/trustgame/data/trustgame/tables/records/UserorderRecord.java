/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.transsonic.trustgame.data.trustgame.tables.Userorder;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserorderRecord extends UpdatableRecordImpl<UserorderRecord> implements Record4<Integer, Integer, Integer, Byte> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>trustgame.userorder.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>trustgame.userorder.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>trustgame.userorder.UserRound_ID</code>.
     */
    public void setUserroundId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>trustgame.userorder.UserRound_ID</code>.
     */
    public Integer getUserroundId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>trustgame.userorder.Order_ID</code>.
     */
    public void setOrderId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>trustgame.userorder.Order_ID</code>.
     */
    public Integer getOrderId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>trustgame.userorder.Published</code>. Boolean 0 or 1
     */
    public void setPublished(Byte value) {
        set(3, value);
    }

    /**
     * Getter for <code>trustgame.userorder.Published</code>. Boolean 0 or 1
     */
    public Byte getPublished() {
        return (Byte) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, Integer, Integer, Byte> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, Integer, Byte> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Userorder.USERORDER.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Userorder.USERORDER.USERROUND_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Userorder.USERORDER.ORDER_ID;
    }

    @Override
    public Field<Byte> field4() {
        return Userorder.USERORDER.PUBLISHED;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getUserroundId();
    }

    @Override
    public Integer component3() {
        return getOrderId();
    }

    @Override
    public Byte component4() {
        return getPublished();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getUserroundId();
    }

    @Override
    public Integer value3() {
        return getOrderId();
    }

    @Override
    public Byte value4() {
        return getPublished();
    }

    @Override
    public UserorderRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UserorderRecord value2(Integer value) {
        setUserroundId(value);
        return this;
    }

    @Override
    public UserorderRecord value3(Integer value) {
        setOrderId(value);
        return this;
    }

    @Override
    public UserorderRecord value4(Byte value) {
        setPublished(value);
        return this;
    }

    @Override
    public UserorderRecord values(Integer value1, Integer value2, Integer value3, Byte value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserorderRecord
     */
    public UserorderRecord() {
        super(Userorder.USERORDER);
    }

    /**
     * Create a detached, initialised UserorderRecord
     */
    public UserorderRecord(Integer id, Integer userroundId, Integer orderId, Byte published) {
        super(Userorder.USERORDER);

        setId(id);
        setUserroundId(userroundId);
        setOrderId(orderId);
        setPublished(published);
    }
}