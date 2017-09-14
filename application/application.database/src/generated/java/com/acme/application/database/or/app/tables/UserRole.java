/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.app.tables;


import com.acme.application.database.or.app.App;
import com.acme.application.database.or.app.Keys;
import com.acme.application.database.or.app.tables.records.UserRoleRecord;

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
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserRole extends TableImpl<UserRoleRecord> {

    private static final long serialVersionUID = -1237391542;

    /**
     * The reference instance of <code>APP.USER_ROLE</code>
     */
    public static final UserRole USER_ROLE = new UserRole();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRoleRecord> getRecordType() {
        return UserRoleRecord.class;
    }

    /**
     * The column <code>APP.USER_ROLE.USERNAME</code>.
     */
    public final TableField<UserRoleRecord, String> USERNAME = createField("USERNAME", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false), this, "");

    /**
     * The column <code>APP.USER_ROLE.ROLE_NAME</code>.
     */
    public final TableField<UserRoleRecord, String> ROLE_NAME = createField("ROLE_NAME", org.jooq.impl.SQLDataType.VARCHAR.length(36).nullable(false), this, "");

    /**
     * Create a <code>APP.USER_ROLE</code> table reference
     */
    public UserRole() {
        this("USER_ROLE", null);
    }

    /**
     * Create an aliased <code>APP.USER_ROLE</code> table reference
     */
    public UserRole(String alias) {
        this(alias, USER_ROLE);
    }

    private UserRole(String alias, Table<UserRoleRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserRole(String alias, Table<UserRoleRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return App.APP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserRoleRecord> getPrimaryKey() {
        return Keys.PK_USER_ROLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserRoleRecord>> getKeys() {
        return Arrays.<UniqueKey<UserRoleRecord>>asList(Keys.PK_USER_ROLE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRole as(String alias) {
        return new UserRole(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserRole rename(String name) {
        return new UserRole(name, null);
    }
}