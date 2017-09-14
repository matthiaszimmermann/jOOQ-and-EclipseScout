package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class PersonTable extends AbstractTable {

	public static String TABLE = "PERSON";
	public static String FIRST_NAME = "FIRST_NAME";
	public static String LAST_NAME = "LAST_NAME";
	public static String SEX = "SEX";

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(ID, TYPE_ID)
				.column(FIRST_NAME, TYPE_STRING_S)
				.column(LAST_NAME, TYPE_STRING_S)
				.column(SEX, TYPE_CODE)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(PersonTable.class);
	}
}
