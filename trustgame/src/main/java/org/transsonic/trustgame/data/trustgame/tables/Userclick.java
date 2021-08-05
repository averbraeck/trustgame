/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame.tables;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row9;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.transsonic.trustgame.data.trustgame.Indexes;
import org.transsonic.trustgame.data.trustgame.Keys;
import org.transsonic.trustgame.data.trustgame.Trustgame;
import org.transsonic.trustgame.data.trustgame.tables.records.UserclickRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Userclick extends TableImpl<UserclickRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>trustgame.userclick</code>
     */
    public static final Userclick USERCLICK = new Userclick();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserclickRecord> getRecordType() {
        return UserclickRecord.class;
    }

    /**
     * The column <code>trustgame.userclick.ID</code>.
     */
    public final TableField<UserclickRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>trustgame.userclick.GameUser_ID</code>.
     */
    public final TableField<UserclickRecord, Integer> GAMEUSER_ID = createField(DSL.name("GameUser_ID"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>trustgame.userclick.Timestamp</code>.
     */
    public final TableField<UserclickRecord, LocalDateTime> TIMESTAMP = createField(DSL.name("Timestamp"), SQLDataType.LOCALDATETIME(0).nullable(false).defaultValue(DSL.field("current_timestamp()", SQLDataType.LOCALDATETIME)), this, "");

    /**
     * The column <code>trustgame.userclick.ButtonOrField</code>. Name of the button or field where the user clicked. Not standardized in a table since the GUI can change.
     */
    public final TableField<UserclickRecord, String> BUTTONORFIELD = createField(DSL.name("ButtonOrField"), SQLDataType.VARCHAR(45).nullable(false), this, "Name of the button or field where the user clicked. Not standardized in a table since the GUI can change.");

    /**
     * The column <code>trustgame.userclick.Value</code>. If a user could fill something out, the value is given here
     */
    public final TableField<UserclickRecord, String> VALUE = createField(DSL.name("Value"), SQLDataType.VARCHAR(45).defaultValue(DSL.field("NULL", SQLDataType.VARCHAR)), this, "If a user could fill something out, the value is given here");

    /**
     * The column <code>trustgame.userclick.RoundNumber</code>.
     */
    public final TableField<UserclickRecord, Integer> ROUNDNUMBER = createField(DSL.name("RoundNumber"), SQLDataType.INTEGER.defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>trustgame.userclick.OrderNumber</code>.
     */
    public final TableField<UserclickRecord, Integer> ORDERNUMBER = createField(DSL.name("OrderNumber"), SQLDataType.INTEGER.defaultValue(DSL.field("0", SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>trustgame.userclick.ClientName</code>.
     */
    public final TableField<UserclickRecord, String> CLIENTNAME = createField(DSL.name("ClientName"), SQLDataType.VARCHAR(45).defaultValue(DSL.field("NULL", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>trustgame.userclick.CarrierName</code>.
     */
    public final TableField<UserclickRecord, String> CARRIERNAME = createField(DSL.name("CarrierName"), SQLDataType.VARCHAR(45).defaultValue(DSL.field("NULL", SQLDataType.VARCHAR)), this, "");

    private Userclick(Name alias, Table<UserclickRecord> aliased) {
        this(alias, aliased, null);
    }

    private Userclick(Name alias, Table<UserclickRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>trustgame.userclick</code> table reference
     */
    public Userclick(String alias) {
        this(DSL.name(alias), USERCLICK);
    }

    /**
     * Create an aliased <code>trustgame.userclick</code> table reference
     */
    public Userclick(Name alias) {
        this(alias, USERCLICK);
    }

    /**
     * Create a <code>trustgame.userclick</code> table reference
     */
    public Userclick() {
        this(DSL.name("userclick"), null);
    }

    public <O extends Record> Userclick(Table<O> child, ForeignKey<O, UserclickRecord> key) {
        super(child, key, USERCLICK);
    }

    @Override
    public Schema getSchema() {
        return Trustgame.TRUSTGAME;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.USERCLICK_FK_USERCLICK_GAMEUSER_IDX);
    }

    @Override
    public Identity<UserclickRecord, Integer> getIdentity() {
        return (Identity<UserclickRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<UserclickRecord> getPrimaryKey() {
        return Keys.KEY_USERCLICK_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserclickRecord>> getKeys() {
        return Arrays.<UniqueKey<UserclickRecord>>asList(Keys.KEY_USERCLICK_PRIMARY, Keys.KEY_USERCLICK_ID_UNIQUE);
    }

    @Override
    public List<ForeignKey<UserclickRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<UserclickRecord, ?>>asList(Keys.FK_USERCLICK_GAMEUSER);
    }

    private transient Gameuser _gameuser;

    public Gameuser gameuser() {
        if (_gameuser == null)
            _gameuser = new Gameuser(this, Keys.FK_USERCLICK_GAMEUSER);

        return _gameuser;
    }

    @Override
    public Userclick as(String alias) {
        return new Userclick(DSL.name(alias), this);
    }

    @Override
    public Userclick as(Name alias) {
        return new Userclick(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Userclick rename(String name) {
        return new Userclick(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Userclick rename(Name name) {
        return new Userclick(name, null);
    }

    // -------------------------------------------------------------------------
    // Row9 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row9<Integer, Integer, LocalDateTime, String, String, Integer, Integer, String, String> fieldsRow() {
        return (Row9) super.fieldsRow();
    }
}
