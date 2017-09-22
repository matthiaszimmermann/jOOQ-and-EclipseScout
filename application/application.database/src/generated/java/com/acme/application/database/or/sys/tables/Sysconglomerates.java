/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.sys.tables;


import com.acme.application.database.generator.converter.LongConverter;
import com.acme.application.database.or.sys.Sys;
import com.acme.application.database.or.sys.tables.records.SysconglomeratesRecord;

import java.math.BigDecimal;

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
public class Sysconglomerates extends TableImpl<SysconglomeratesRecord> {

    private static final long serialVersionUID = -1901967774;

    /**
     * The reference instance of <code>SYS.SYSCONGLOMERATES</code>
     */
    public static final Sysconglomerates SYSCONGLOMERATES = new Sysconglomerates();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SysconglomeratesRecord> getRecordType() {
        return SysconglomeratesRecord.class;
    }

    /**
     * The column <code>SYS.SYSCONGLOMERATES.SCHEMAID</code>.
     */
    public final TableField<SysconglomeratesRecord, String> SCHEMAID = createField("SCHEMAID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.TABLEID</code>.
     */
    public final TableField<SysconglomeratesRecord, String> TABLEID = createField("TABLEID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.CONGLOMERATENUMBER</code>.
     */
    public final TableField<SysconglomeratesRecord, BigDecimal> CONGLOMERATENUMBER = createField("CONGLOMERATENUMBER", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "", new LongConverter());

    /**
     * The column <code>SYS.SYSCONGLOMERATES.CONGLOMERATENAME</code>.
     */
    public final TableField<SysconglomeratesRecord, String> CONGLOMERATENAME = createField("CONGLOMERATENAME", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.ISINDEX</code>.
     */
    public final TableField<SysconglomeratesRecord, Boolean> ISINDEX = createField("ISINDEX", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.DESCRIPTOR</code>.
     */
    public final TableField<SysconglomeratesRecord, String> DESCRIPTOR = createField("DESCRIPTOR", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.ISCONSTRAINT</code>.
     */
    public final TableField<SysconglomeratesRecord, Boolean> ISCONSTRAINT = createField("ISCONSTRAINT", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>SYS.SYSCONGLOMERATES.CONGLOMERATEID</code>.
     */
    public final TableField<SysconglomeratesRecord, String> CONGLOMERATEID = createField("CONGLOMERATEID", org.jooq.impl.SQLDataType.CHAR.length(36).nullable(false), this, "");

    /**
     * Create a <code>SYS.SYSCONGLOMERATES</code> table reference
     */
    public Sysconglomerates() {
        this("SYSCONGLOMERATES", null);
    }

    /**
     * Create an aliased <code>SYS.SYSCONGLOMERATES</code> table reference
     */
    public Sysconglomerates(String alias) {
        this(alias, SYSCONGLOMERATES);
    }

    private Sysconglomerates(String alias, Table<SysconglomeratesRecord> aliased) {
        this(alias, aliased, null);
    }

    private Sysconglomerates(String alias, Table<SysconglomeratesRecord> aliased, Field<?>[] parameters) {
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
    public Sysconglomerates as(String alias) {
        return new Sysconglomerates(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Sysconglomerates rename(String name) {
        return new Sysconglomerates(name, null);
    }
}
