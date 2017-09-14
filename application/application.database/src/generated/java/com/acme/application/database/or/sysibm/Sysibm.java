/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.sysibm;


import com.acme.application.database.or.DefaultCatalog;
import com.acme.application.database.or.sysibm.tables.Sysdummy1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sysibm extends SchemaImpl {

    private static final long serialVersionUID = 1499132749;

    /**
     * The reference instance of <code>SYSIBM</code>
     */
    public static final Sysibm SYSIBM = new Sysibm();

    /**
     * The table <code>SYSIBM.SYSDUMMY1</code>.
     */
    public final Sysdummy1 SYSDUMMY1 = com.acme.application.database.or.sysibm.tables.Sysdummy1.SYSDUMMY1;

    /**
     * No further instances allowed
     */
    private Sysibm() {
        super("SYSIBM", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Sysdummy1.SYSDUMMY1);
    }
}