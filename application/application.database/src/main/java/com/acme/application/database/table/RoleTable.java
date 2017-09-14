package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class RoleTable extends AbstractTable {

	public static final String TABLE = "ROLE";
	public static final String NAME = "NAME";
	
	public static final String ROOT = "root";
	public static final String USER = "user";
	public static final String GUEST = "guest";

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {
		return getContext()
				.createTable(getTableName())
				.column(NAME, TYPE_ID)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(NAME))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(RoleTable.class);
	}
}