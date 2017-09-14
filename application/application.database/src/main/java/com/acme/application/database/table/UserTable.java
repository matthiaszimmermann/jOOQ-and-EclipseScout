package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class UserTable extends AbstractTable {

	public static final String TABLE = "USER";
	public static final String USERNAME = "USERNAME";
	public static final String PERSON_ID = "PERSON_ID";
	public static final String LOCALE = "LOCALE";
	public static final String PASSWORD_ENCRYPTED = "PASSWORD_ENCRYPTED";

	public static final String ROOT = "root";
	
	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(USERNAME, TYPE_ID)
				// foreign key to person
				.column(PERSON_ID, TYPE_ID)
				.column(LOCALE, TYPE_CODE)
				.column(PASSWORD_ENCRYPTED, TYPE_STRING_M.nullable(false))
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(USERNAME))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(UserTable.class);
	}
}
