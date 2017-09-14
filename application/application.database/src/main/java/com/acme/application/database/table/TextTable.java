package com.acme.application.database.table;

import java.util.Locale;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractTable;

public class TextTable extends AbstractTable {

	public static final String TABLE = "TEXT";
	public static final String KEY = "KEY";
	public static final String LOCALE = "LOCALE";
	public static final String TEXT = "TEXT";
	
	public static final String LOCALE_DEFAULT = Locale.ROOT.toLanguageTag();
	

	@Override
	public String getTableName() {
		return TABLE;
	}

	@Override
	public String getCreateSQL() {

		return getContext()
				.createTable(getTableName())
				.column(KEY, TYPE_STRING_M.nullable(false))
				.column(LOCALE, TYPE_STRING_S.nullable(false))
				.column(TEXT, TYPE_STRING_L.nullable(false))
				.constraints(
						DSL.constraint(getPKName()).primaryKey(KEY, LOCALE))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(TextTable.class);
	}
}