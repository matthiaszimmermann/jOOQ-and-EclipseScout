package com.acme.application.database.table.group_a;

import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProduktTable extends AbstractAwtTable {

	public static final String TABLE = "PRODUKT";
	public static final String BEZEICHNUNG = "BEZEICHNUNG";
	public static final String FAMILIE = "FAMILIE";
	public static final String ART = "ART";
	public static final String BESTAND_MIN = "BESTAND_MIN";

	@Override
	public String getName() {
		return TABLE;
	}

	@Override
	public String createSQLInternal() {
		return getContext()
				.createTable(getName())
				.column(ID, TYPE_ID)
				.column(BEZEICHNUNG, TYPE_STRING_S.nullable(false))
				.column(FAMILIE, TYPE_ID)
				.column(ART, TYPE_ID_OPTIONAL)
				.column(BESTAND_MIN, TYPE_INTEGER)
				.column(ACTIVE, TYPE_BOOLEAN)
				.constraints(
						DSL.constraint(getPKName()).primaryKey(ID))
				.getSQL();
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(ProduktTable.class);
	}
}