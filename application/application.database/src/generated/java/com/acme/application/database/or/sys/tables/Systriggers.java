/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.sys.tables;


import com.acme.application.database.or.sys.Sys;
import com.acme.application.database.or.sys.tables.records.SystriggersRecord;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Systriggers extends TableImpl<SystriggersRecord> {

    private static final long serialVersionUID = 361803803;

    /**
     * The reference instance of <code>SYS.SYSTRIGGERS</code>
     */
    public static final Systriggers SYSTRIGGERS = new Systriggers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SystriggersRecord> getRecordType() {
        return SystriggersRecord.class;
    }

    /**
     * The column <code>SYS.SYSTRIGGERS.TRIGGERID</code>.
     */
    public final TableField<SystriggersRecord, String> TRIGGERID = createField("TRIGGERID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.TRIGGERNAME</code>.
     */
    public final TableField<SystriggersRecord, String> TRIGGERNAME = createField("TRIGGERNAME", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.SCHEMAID</code>.
     */
    public final TableField<SystriggersRecord, String> SCHEMAID = createField("SCHEMAID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.CREATIONTIMESTAMP</code>.
     */
    public final TableField<SystriggersRecord, Timestamp> CREATIONTIMESTAMP = createField("CREATIONTIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.EVENT</code>.
     */
    public final TableField<SystriggersRecord, String> EVENT = createField("EVENT", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.FIRINGTIME</code>.
     */
    public final TableField<SystriggersRecord, String> FIRINGTIME = createField("FIRINGTIME", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.TYPE</code>.
     */
    public final TableField<SystriggersRecord, String> TYPE = createField("TYPE", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.STATE</code>.
     */
    public final TableField<SystriggersRecord, String> STATE = createField("STATE", org.jooq.impl.SQLDataType.CHAR.length(1).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.TABLEID</code>.
     */
    public final TableField<SystriggersRecord, String> TABLEID = createField("TABLEID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.WHENSTMTID</code>.
     */
    public final TableField<SystriggersRecord, String> WHENSTMTID = createField("WHENSTMTID", org.jooq.impl.SQLDataType.CHAR.length(36), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.ACTIONSTMTID</code>.
     */
    public final TableField<SystriggersRecord, String> ACTIONSTMTID = createField("ACTIONSTMTID", org.jooq.impl.SQLDataType.CHAR.length(36), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.REFERENCEDCOLUMNS</code>.
     */
    public final TableField<SystriggersRecord, Object> REFERENCEDCOLUMNS = createField("REFERENCEDCOLUMNS", org.jooq.impl.DefaultDataType.getDefaultDataType("org.apache.derby.catalog.ReferencedColumns"), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.TRIGGERDEFINITION</code>.
     */
    public final TableField<SystriggersRecord, String> TRIGGERDEFINITION = createField("TRIGGERDEFINITION", org.jooq.impl.SQLDataType.LONGVARCHAR, this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.REFERENCINGOLD</code>.
     */
    public final TableField<SystriggersRecord, Boolean> REFERENCINGOLD = createField("REFERENCINGOLD", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.REFERENCINGNEW</code>.
     */
    public final TableField<SystriggersRecord, Boolean> REFERENCINGNEW = createField("REFERENCINGNEW", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.OLDREFERENCINGNAME</code>.
     */
    public final TableField<SystriggersRecord, String> OLDREFERENCINGNAME = createField("OLDREFERENCINGNAME", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.NEWREFERENCINGNAME</code>.
     */
    public final TableField<SystriggersRecord, String> NEWREFERENCINGNAME = createField("NEWREFERENCINGNAME", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "");

    /**
     * The column <code>SYS.SYSTRIGGERS.WHENCLAUSETEXT</code>.
     */
    public final TableField<SystriggersRecord, String> WHENCLAUSETEXT = createField("WHENCLAUSETEXT", org.jooq.impl.SQLDataType.LONGVARCHAR, this, "");

    /**
     * Create a <code>SYS.SYSTRIGGERS</code> table reference
     */
    public Systriggers() {
        this("SYSTRIGGERS", null);
    }

    /**
     * Create an aliased <code>SYS.SYSTRIGGERS</code> table reference
     */
    public Systriggers(String alias) {
        this(alias, SYSTRIGGERS);
    }

    private Systriggers(String alias, Table<SystriggersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Systriggers(String alias, Table<SystriggersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Sys.SYS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Systriggers as(String alias) {
        return new Systriggers(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Systriggers rename(String name) {
        return new Systriggers(name, null);
    }
}
