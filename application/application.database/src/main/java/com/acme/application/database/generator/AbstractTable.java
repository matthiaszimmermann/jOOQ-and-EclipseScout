package com.acme.application.database.generator;

import java.sql.SQLException;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public abstract class AbstractTable implements IDatabaseTable {

	public static String PRIMARY_KEY_PREFIX = "PK_";

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
	public void create() {
		getLogger().info("SQL-DEV create table: {}", getTableName());

		String sql = getCreateSQL();
		getLogger().info("SQL-DEV sql statement: {}", sql);

		try {
			config.getConnection().createStatement().execute(sql);
		}
		catch (SQLException e) {
			throw new ProcessingException("Could not create table '" + getTableName() + "'.", e);
		}
	}

	@Override
	public void drop() {
		getLogger().info("SQL-DEV drop table: {}", getTableName());

		boolean exists = getContext().fetchExists(DSL.table(DSL.name(getTableName())));

		if(exists) {
			getContext()
			.dropTable(getTableName())
			.execute();
		}
	}

	protected DSLContext getContext() {
		return config.getContext();
	}

	protected String getPKName() {
		return PRIMARY_KEY_PREFIX + getTableName();
	}
}
