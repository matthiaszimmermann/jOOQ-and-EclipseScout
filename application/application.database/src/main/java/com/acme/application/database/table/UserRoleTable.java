package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class UserRoleTable extends AbstractTable {

	public static String TABLE = "USER_ROLE";
	public static String ROLE_NAME = RoleTable.TABLE + "_NAME";

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(UserTable.USERNAME, TYPE_STRING_S.nullable(false))
				.column(ROLE_NAME, TYPE_ID)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(
								UserTable.USERNAME, 
								ROLE_NAME)
						)
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(UserRoleTable.class);
	}
}