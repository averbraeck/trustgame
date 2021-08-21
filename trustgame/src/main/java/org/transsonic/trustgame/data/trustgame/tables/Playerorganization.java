/*
 * This file is generated by jOOQ.
 */
package org.transsonic.trustgame.data.trustgame.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row12;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import org.transsonic.trustgame.data.trustgame.Keys;
import org.transsonic.trustgame.data.trustgame.Trustgame;
import org.transsonic.trustgame.data.trustgame.tables.records.PlayerorganizationRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Playerorganization extends TableImpl<PlayerorganizationRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>trustgame.playerorganization</code>
     */
    public static final Playerorganization PLAYERORGANIZATION = new Playerorganization();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<PlayerorganizationRecord> getRecordType() {
        return PlayerorganizationRecord.class;
    }

    /**
     * The column <code>trustgame.playerorganization.ID</code>.
     */
    public final TableField<PlayerorganizationRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>trustgame.playerorganization.Name</code>.
     */
    public final TableField<PlayerorganizationRecord, String> NAME = createField(DSL.name("Name"), SQLDataType.VARCHAR(45).nullable(false), this, "");

    /**
     * The column <code>trustgame.playerorganization.Description</code>. HTML or Markdown
     */
    public final TableField<PlayerorganizationRecord, String> DESCRIPTION = createField(DSL.name("Description"), SQLDataType.CLOB.nullable(false), this, "HTML or Markdown");

    /**
     * The column <code>trustgame.playerorganization.TargetProfit</code>. Target line of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> TARGETPROFIT = createField(DSL.name("TargetProfit"), SQLDataType.INTEGER.nullable(false), this, "Target line of the score chart");

    /**
     * The column <code>trustgame.playerorganization.TargetSatisfaction</code>. Target line of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> TARGETSATISFACTION = createField(DSL.name("TargetSatisfaction"), SQLDataType.INTEGER.nullable(false), this, "Target line of the score chart");

    /**
     * The column <code>trustgame.playerorganization.TargetSustainability</code>. Target line of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> TARGETSUSTAINABILITY = createField(DSL.name("TargetSustainability"), SQLDataType.INTEGER.nullable(false), this, "Target line of the score chart");

    /**
     * The column <code>trustgame.playerorganization.StartProfit</code>. Start score of profit for a player
     */
    public final TableField<PlayerorganizationRecord, Integer> STARTPROFIT = createField(DSL.name("StartProfit"), SQLDataType.INTEGER.nullable(false), this, "Start score of profit for a player");

    /**
     * The column <code>trustgame.playerorganization.StartSatisfaction</code>. Start score of satisfaction for a player
     */
    public final TableField<PlayerorganizationRecord, Integer> STARTSATISFACTION = createField(DSL.name("StartSatisfaction"), SQLDataType.INTEGER.nullable(false), this, "Start score of satisfaction for a player");

    /**
     * The column <code>trustgame.playerorganization.StartSustainability</code>. Start score of sustainability for a player
     */
    public final TableField<PlayerorganizationRecord, Integer> STARTSUSTAINABILITY = createField(DSL.name("StartSustainability"), SQLDataType.INTEGER.nullable(false), this, "Start score of sustainability for a player");

    /**
     * The column <code>trustgame.playerorganization.MaxProfit</code>. Top of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> MAXPROFIT = createField(DSL.name("MaxProfit"), SQLDataType.INTEGER.nullable(false), this, "Top of the score chart");

    /**
     * The column <code>trustgame.playerorganization.MaxSatisfaction</code>. Top of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> MAXSATISFACTION = createField(DSL.name("MaxSatisfaction"), SQLDataType.INTEGER.nullable(false), this, "Top of the score chart");

    /**
     * The column <code>trustgame.playerorganization.MaxSustainability</code>. Top of the score chart
     */
    public final TableField<PlayerorganizationRecord, Integer> MAXSUSTAINABILITY = createField(DSL.name("MaxSustainability"), SQLDataType.INTEGER.nullable(false), this, "Top of the score chart");

    private Playerorganization(Name alias, Table<PlayerorganizationRecord> aliased) {
        this(alias, aliased, null);
    }

    private Playerorganization(Name alias, Table<PlayerorganizationRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>trustgame.playerorganization</code> table reference
     */
    public Playerorganization(String alias) {
        this(DSL.name(alias), PLAYERORGANIZATION);
    }

    /**
     * Create an aliased <code>trustgame.playerorganization</code> table reference
     */
    public Playerorganization(Name alias) {
        this(alias, PLAYERORGANIZATION);
    }

    /**
     * Create a <code>trustgame.playerorganization</code> table reference
     */
    public Playerorganization() {
        this(DSL.name("playerorganization"), null);
    }

    public <O extends Record> Playerorganization(Table<O> child, ForeignKey<O, PlayerorganizationRecord> key) {
        super(child, key, PLAYERORGANIZATION);
    }

    @Override
    public Schema getSchema() {
        return Trustgame.TRUSTGAME;
    }

    @Override
    public Identity<PlayerorganizationRecord, Integer> getIdentity() {
        return (Identity<PlayerorganizationRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<PlayerorganizationRecord> getPrimaryKey() {
        return Keys.KEY_PLAYERORGANIZATION_PRIMARY;
    }

    @Override
    public List<UniqueKey<PlayerorganizationRecord>> getKeys() {
        return Arrays.<UniqueKey<PlayerorganizationRecord>>asList(Keys.KEY_PLAYERORGANIZATION_PRIMARY, Keys.KEY_PLAYERORGANIZATION_ID_UNIQUE, Keys.KEY_PLAYERORGANIZATION_NAME_UNIQUE);
    }

    @Override
    public Playerorganization as(String alias) {
        return new Playerorganization(DSL.name(alias), this);
    }

    @Override
    public Playerorganization as(Name alias) {
        return new Playerorganization(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Playerorganization rename(String name) {
        return new Playerorganization(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Playerorganization rename(Name name) {
        return new Playerorganization(name, null);
    }

    // -------------------------------------------------------------------------
    // Row12 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row12<Integer, String, String, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> fieldsRow() {
        return (Row12) super.fieldsRow();
    }
}
