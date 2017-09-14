package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class RolePermissionTable extends AbstractTable {

	public static String TABLE = "ROLE_PERMISSION";
	public static String ROLE_NAME = RoleTable.TABLE + "_NAME";
	public static String PERMISSION = "PERMISSION";

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(ROLE_NAME, TYPE_ID)
				.column(PERMISSION, TYPE_STRING_S.nullable(false))
				.constraints(
						DSL.constraint(getPKName()).primaryKey(
								ROLE_NAME,
								PERMISSION)
						)
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(RolePermissionTable.class);
	}
}