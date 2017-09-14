/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.sys.tables.records;


import com.acme.application.database.or.sys.tables.Sysperms;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


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
public class SyspermsRecord extends TableRecordImpl<SyspermsRecord> implements Record7<String, String, String, String, String, String, String> {

    private static final long serialVersionUID = -1042893450;

    /**
     * Setter for <code>SYS.SYSPERMS.UUID</code>.
     */
    public void setUuid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.UUID</code>.
     */
    public String getUuid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.OBJECTTYPE</code>.
     */
    public void setObjecttype(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.OBJECTTYPE</code>.
     */
    public String getObjecttype() {
        return (String) get(1);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.OBJECTID</code>.
     */
    public void setObjectid(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.OBJECTID</code>.
     */
    public String getObjectid() {
        return (String) get(2);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.PERMISSION</code>.
     */
    public void setPermission(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.PERMISSION</code>.
     */
    public String getPermission() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.GRANTOR</code>.
     */
    public void setGrantor(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.GRANTOR</code>.
     */
    public String getGrantor() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.GRANTEE</code>.
     */
    public void setGrantee(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.GRANTEE</code>.
     */
    public String getGrantee() {
        return (String) get(5);
    }

    /**
     * Setter for <code>SYS.SYSPERMS.ISGRANTABLE</code>.
     */
    public void setIsgrantable(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>SYS.SYSPERMS.ISGRANTABLE</code>.
     */
    public String getIsgrantable() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<String, String, String, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Sysperms.SYSPERMS.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Sysperms.SYSPERMS.OBJECTTYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Sysperms.SYSPERMS.OBJECTID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Sysperms.SYSPERMS.PERMISSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Sysperms.SYSPERMS.GRANTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Sysperms.SYSPERMS.GRANTEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Sysperms.SYSPERMS.ISGRANTABLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getObjecttype();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getObjectid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getPermission();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getGrantor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getGrantee();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getIsgrantable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value1(String value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value2(String value) {
        setObjecttype(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value3(String value) {
        setObjectid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value4(String value) {
        setPermission(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value5(String value) {
        setGrantor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value6(String value) {
        setGrantee(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord value7(String value) {
        setIsgrantable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SyspermsRecord values(String value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SyspermsRecord
     */
    public SyspermsRecord() {
        super(Sysperms.SYSPERMS);
    }

    /**
     * Create a detached, initialised SyspermsRecord
     */
    public SyspermsRecord(String uuid, String objecttype, String objectid, String permission, String grantor, String grantee, String isgrantable) {
        super(Sysperms.SYSPERMS);

        set(0, uuid);
        set(1, objecttype);
        set(2, objectid);
        set(3, permission);
        set(4, grantor);
        set(5, grantee);
        set(6, isgrantable);
    }
}