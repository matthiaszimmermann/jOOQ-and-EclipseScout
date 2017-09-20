package com.acme.application.database.table;

import java.sql.SQLException;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.Config;
import com.acme.application.database.generator.IDatabaseSchema;

public class CoreSchema implements IDatabaseSchema {

	public static final String SCHEMA = "core";

	private Config config;

	@Override
	public void setConfig(Config config) {
		assert config != null;
		this.config = config;
	}

	@Override
	public Config getConfig() {
		return config;
	}

	@Override
	public String getName() {
		return SCHEMA;
	}

	@Override
	public String getCreateSQL() {
		return getContext()
				.createSchema(getName())
				.getSQL();
	}

	@Override
	public void create() {
		getLogger().info("SQL-DEV create schema: {}", getName());

		String sql = getCreateSQL();
		getLogger().info("SQL-DEV sql statement: {}", sql);

		try {
			if(StringUtility.hasText(sql)) {
				config.getConnection().createStatement().execute(sql);
			}
		}
		catch (SQLException e) {
			throw new ProcessingException("Could not create schema '" + getName() + "'.", e);
		}
	}

	@Override
	public void drop() {

	}

	protected DSLContext getContext() {
		return config.getContext();
	}

	public Logger getLogger() {
		return LoggerFactory.getLogger(RoleTable.class);
	}
}
