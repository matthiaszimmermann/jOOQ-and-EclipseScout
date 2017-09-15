package com.acme.application.database.table;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class DocumentTable extends AbstractTable {

	public static final String TABLE = "DOCUMENT";
	public static final String NAME = "NAME";
	public static final String SIZE = "SIZE";
	public static final String TYPE = "TYPE";
	public static final String CONTENT = "CONTENT";
	public static final String UPLOADED = "UPLOADED";
	public static final String USER_ID = "USER_ID";
	
	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(ID, TYPE_ID)
				.column(NAME, TYPE_STRING_M)
				.column(TYPE, TYPE_STRING_XS)
				.column(SIZE, TYPE_SIZE)
				.column(CONTENT, TYPE_BLOB)
				// foreign key to user
				.column(USER_ID, TYPE_ID)
				.column(UPLOADED, TYPE_TIMESTAMP)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(DocumentTable.class);
	}
}
