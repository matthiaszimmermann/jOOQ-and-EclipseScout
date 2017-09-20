package com.acme.application.database.table;

import com.acme.application.database.generator.AbstractTable;

public abstract class AbstractCoreTable extends AbstractTable {

	public static final String SCHEMA = "core";
	
	@Override
	public String getSchemaName() {
		return SCHEMA;
	}

}
