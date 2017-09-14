package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class CodeTable extends AbstractTable {

	public static final String TABLE = "CODE";
	public static final String ID = "ID";
	public static final String TYPE = "TYPE_ID";
	public static final String ICON = "ICON";
	public static final String VALUE = "VALUE";
	

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(ID, TYPE_ID)
				.column(TYPE, TYPE_ID)
				.column(ICON, TYPE_STRING_S)
				.column(VALUE, TYPE_STRING_S)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID, TYPE))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(CodeTable.class);
	}
}