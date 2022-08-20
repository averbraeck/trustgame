/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;
import org.transsonic.trustgame.data.trustgame.tables.Userround;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserroundRecord extends UpdatableRecordImpl<UserroundRecord> implements Record4<Integer, Integer, Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>trustgame.userround.ID</code>. The table indicates that a user reached a certain round
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>trustgame.userround.ID</code>. The table indicates that a user reached a certain round
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>trustgame.userround.GamePlay_ID</code>.
     */
    public void setGameplayId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>trustgame.userround.GamePlay_ID</code>.
     */
    public Integer getGameplayId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>trustgame.userround.Round_ID</code>.
     */
    public void setRoundId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>trustgame.userround.Round_ID</code>.
     */
    public Integer getRoundId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>trustgame.userround.GameUser_ID</code>.
     */
    public void setGameuserId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>trustgame.userround.GameUser_ID</code>.
     */
    public Integer getGameuserId() {
        return (Integer) get(3);
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
    public Row4<Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, Integer, Integer, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Userround.USERROUND.ID;
    }

    @Override
    public Field<Integer> field2() {
        return Userround.USERROUND.GAMEPLAY_ID;
    }

    @Override
    public Field<Integer> field3() {
        return Userround.USERROUND.ROUND_ID;
    }

    @Override
    public Field<Integer> field4() {
        return Userround.USERROUND.GAMEUSER_ID;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public Integer component2() {
        return getGameplayId();
    }

    @Override
    public Integer component3() {
        return getRoundId();
    }

    @Override
    public Integer component4() {
        return getGameuserId();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public Integer value2() {
        return getGameplayId();
    }

    @Override
    public Integer value3() {
        return getRoundId();
    }

    @Override
    public Integer value4() {
        return getGameuserId();
    }

    @Override
    public UserroundRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public UserroundRecord value2(Integer value) {
        setGameplayId(value);
        return this;
    }

    @Override
    public UserroundRecord value3(Integer value) {
        setRoundId(value);
        return this;
    }

    @Override
    public UserroundRecord value4(Integer value) {
        setGameuserId(value);
        return this;
    }

    @Override
    public UserroundRecord values(Integer value1, Integer value2, Integer value3, Integer value4) {
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
     * Create a detached UserroundRecord
     */
    public UserroundRecord() {
        super(Userround.USERROUND);
    }

    /**
     * Create a detached, initialised UserroundRecord
     */
    public UserroundRecord(Integer id, Integer gameplayId, Integer roundId, Integer gameuserId) {
        super(Userround.USERROUND);

        setId(id);
        setGameplayId(gameplayId);
        setRoundId(roundId);
        setGameuserId(gameuserId);
    }
}