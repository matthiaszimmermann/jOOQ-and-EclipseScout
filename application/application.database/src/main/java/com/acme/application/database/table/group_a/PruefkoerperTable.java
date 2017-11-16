package com.acme.application.database.table.group_a;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PruefkoerperTable extends AbstractAwtTable {

	public static final String TABLE = "PRUEFKOERPER";
	public static final String NR = "NR";
	public static final String PRODUKT_ID = "PRODUKT_ID";
	public static final String COLOR_ID = "COLOR_ID";
	public static final String COLOR_DESCRIPTION = "COLOR_DESCRIPTION";
	public static final String FORM = "FORM";
	public static final String SCHRANK = "SCHRANK";
	public static final String BOXE = "BOXE";
	public static final String LAENGE = "LAENGE";
	public static final String BREITE = "BREITE";
	public static final String DICKE = "DICKE";
	public static final String BESTAND = "BESTAND";

	@Override
	public String getName() {
		return TABLE;
	}

	@Override
	public String createSQLInternal() {
		return getContext()
				.createTable(getName())
				.column(ID, TYPE_ID)
				.column(NR, TYPE_INTEGER.nullable(false))
				.column(PRODUKT_ID, TYPE_ID)
				.column(COLOR_ID, TYPE_ID_OPTIONAL)
				.column(COLOR_DESCRIPTION, TYPE_STRING_S)
				.column(FORM, TYPE_STRING_S)
				.column(SCHRANK, TYPE_STRING_S)
				.column(BOXE, TYPE_STRING_S)
				.column(LAENGE, TYPE_INTEGER)
				.column(BREITE, TYPE_INTEGER)
				.column(DICKE, TYPE_INTEGER)
				.column(BESTAND, TYPE_INTEGER)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(PruefkoerperTable.class);
	}
}