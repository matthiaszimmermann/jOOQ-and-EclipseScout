package com.acme.application.database.table.group_a;

import com.acme.application.database.generator.AbstractTable;

public abstract class AbstractAwtTable extends AbstractTable {

	@Override
	public String getSchemaName() {
		return AwtSchema.SCHEMA;
	}
}
