package com.acme.application.database.table.group_a;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.AbstractSchema;

public class AwtSchema extends AbstractSchema {

	public static final String SCHEMA = "awt";

	@Override
	public String getName() {
		return SCHEMA;
	}

	public Logger getLogger() {
		return LoggerFactory.getLogger(AwtSchema.class);
	}
}
