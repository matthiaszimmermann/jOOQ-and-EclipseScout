/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.awt.tables;


import com.acme.application.database.or.awt.Awt;
import com.acme.application.database.or.awt.Keys;
import com.acme.application.database.or.awt.tables.records.ProduktRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
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
public class Produkt extends TableImpl<ProduktRecord> {

    private static final long serialVersionUID = 1063845477;

    /**
     * The reference instance of <code>awt.PRODUKT</code>
     */
    public static final Produkt PRODUKT = new Produkt();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProduktRecord> getRecordType() {
        return ProduktRecord.class;
    }

    /**
     * The column <code>awt.PRODUKT.ID</code>.
     */
    public final TableField<ProduktRecord, String> ID = createField("ID", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>awt.PRODUKT.BEZEICHNUNG</code>.
     */
    public final TableField<ProduktRecord, String> BEZEICHNUNG = createField("BEZEICHNUNG", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>awt.PRODUKT.FAMILIE</code>.
     */
    public final TableField<ProduktRecord, String> FAMILIE = createField("FAMILIE", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

    /**
     * The column <code>awt.PRODUKT.ART</code>.
     */
    public final TableField<ProduktRecord, String> ART = createField("ART", org.jooq.impl.SQLDataType.VARCHAR.length(36), this, "");

    /**
     * The column <code>awt.PRODUKT.BESTAND_MIN</code>.
     */
    public final TableField<ProduktRecord, Integer> BESTAND_MIN = createField("BESTAND_MIN", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>awt.PRODUKT.ACTIVE</code>.
     */
    public final TableField<ProduktRecord, Boolean> ACTIVE = createField("ACTIVE", org.jooq.impl.SQLDataType.BOOLEAN, this, "");

    /**
     * Create a <code>awt.PRODUKT</code> table reference
     */
    public Produkt() {
        this("PRODUKT", null);
    }

    /**
     * Create an aliased <code>awt.PRODUKT</code> table reference
     */
    public Produkt(String alias) {
        this(alias, PRODUKT);
    }

    private Produkt(String alias, Table<ProduktRecord> aliased) {
        this(alias, aliased, null);
    }

    private Produkt(String alias, Table<ProduktRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Awt.AWT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProduktRecord> getPrimaryKey() {
        return Keys.PK_PRODUKT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProduktRecord>> getKeys() {
        return Arrays.<UniqueKey<ProduktRecord>>asList(Keys.PK_PRODUKT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Produkt as(String alias) {
        return new Produkt(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Produkt rename(String name) {
        return new Produkt(name, null);
    }
}
