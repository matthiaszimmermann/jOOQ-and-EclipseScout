package com.acme.application.database.table.group_a;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiTable extends AbstractSvTable {

	public static final String TABLE = "FI";
	public static final String GEB_NR = "GEB_NR";
	public static final String ANL_NR = "ANL_NR";
	public static final String ANLAGE = "ANLAGE";
	public static final String ETAGE = "ETAGE";
	public static final String E_NR = "E_NR";
	public static final String STANDORT = "STANDORT";
	public static final String SICHERUNG = "SICHERUNG";
	public static final String IF = "IF";
	public static final String BEFUND = "BEFUND";
	public static final String PRUEFMET = "PRUEFMET";
	public static final String VISUM = "VISUM";
	public static final String DATUM = "DATUM";

	@Override
	public String getName() {
		return TABLE;
	}

	@Override
	public String createSQLInternal() {
		return getContext()
				.createTable(getName())
				.column(ID, TYPE_ID)
				.column(GEB_NR, TYPE_STRING_S)
				.column(ANL_NR, TYPE_STRING_S)
				.column(ANLAGE, TYPE_STRING_S)
				.column(ETAGE, TYPE_ID_OPTIONAL)
				.column(STANDORT, TYPE_STRING_S)
				.column(SICHERUNG, TYPE_STRING_S)
				.column(IF, TYPE_STRING_S)
				.column(BEFUND, TYPE_STRING_S)
				.column(PRUEFMET, TYPE_STRING_S)
				.column(VISUM, TYPE_STRING_S)
				.column(DATUM, TYPE_DATE)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(FiTable.class);
	}
}