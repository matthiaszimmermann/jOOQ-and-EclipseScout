/*
 * This file is generated by jOOQ.
*/
package com.acme.application.database.or.sys.tables.records;


import com.acme.application.database.or.sys.tables.Sysroles;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.TableRecordImpl;


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
public class SysrolesRecord extends TableRecordImpl<SysrolesRecord> implements Record6<String, String, String, String, String, String> {

    private static final long serialVersionUID = -860004364;

    /**
     * Setter for <code>SYS.SYSROLES.UUID</code>.
     */
    public void setUuid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.UUID</code>.
     */
    public String getUuid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>SYS.SYSROLES.ROLEID</code>.
     */
    public void setRoleid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.ROLEID</code>.
     */
    public String getRoleid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>SYS.SYSROLES.GRANTEE</code>.
     */
    public void setGrantee(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.GRANTEE</code>.
     */
    public String getGrantee() {
        return (String) get(2);
    }

    /**
     * Setter for <code>SYS.SYSROLES.GRANTOR</code>.
     */
    public void setGrantor(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.GRANTOR</code>.
     */
    public String getGrantor() {
        return (String) get(3);
    }

    /**
     * Setter for <code>SYS.SYSROLES.WITHADMINOPTION</code>.
     */
    public void setWithadminoption(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.WITHADMINOPTION</code>.
     */
    public String getWithadminoption() {
        return (String) get(4);
    }

    /**
     * Setter for <code>SYS.SYSROLES.ISDEF</code>.
     */
    public void setIsdef(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>SYS.SYSROLES.ISDEF</code>.
     */
    public String getIsdef() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<String, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return Sysroles.SYSROLES.UUID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Sysroles.SYSROLES.ROLEID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Sysroles.SYSROLES.GRANTEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Sysroles.SYSROLES.GRANTOR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Sysroles.SYSROLES.WITHADMINOPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Sysroles.SYSROLES.ISDEF;
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
        return getRoleid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getGrantee();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getGrantor();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getWithadminoption();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getIsdef();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value1(String value) {
        setUuid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value2(String value) {
        setRoleid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value3(String value) {
        setGrantee(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value4(String value) {
        setGrantor(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value5(String value) {
        setWithadminoption(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord value6(String value) {
        setIsdef(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysrolesRecord values(String value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SysrolesRecord
     */
    public SysrolesRecord() {
        super(Sysroles.SYSROLES);
    }

    /**
     * Create a detached, initialised SysrolesRecord
     */
    public SysrolesRecord(String uuid, String roleid, String grantee, String grantor, String withadminoption, String isdef) {
        super(Sysroles.SYSROLES);

        set(0, uuid);
        set(1, roleid);
        set(2, grantee);
        set(3, grantor);
        set(4, withadminoption);
        set(5, isdef);
    }
}
