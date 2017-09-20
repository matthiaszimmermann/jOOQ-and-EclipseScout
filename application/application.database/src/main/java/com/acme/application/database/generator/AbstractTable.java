package com.acme.application.database.generator;

import java.sql.SQLException;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public abstract class AbstractTable implements IDatabaseTable {

	public static String PRIMARY_KEY_PREFIX = "PK_";

	private Config config;

	@Override
	public String getSchemaName() {
		return "";
	}
	
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
		getLogger().info("SQL-DEV create table: {}", getName());

		String sql = getCreateSQL();
		getLogger().info("SQL-DEV sql statement: {}", sql);

		try {
			config.getConnection().createStatement().execute(sql);
		}
		catch (SQLException e) {
			throw new ProcessingException("Could not create table '" + getName() + "'.", e);
		}
	}

	@Override
	public void drop() {
		getLogger().info("SQL-DEV drop table: {}", getName());

		boolean exists = getContext().fetchExists(DSL.table(DSL.name(getName())));

		if(exists) {
			getContext()
			.dropTable(getName())
			.execute();
		}
	}
	
	protected DSLContext getContext() {
		return config.getContext();
	}

	protected String getPKName() {
		return PRIMARY_KEY_PREFIX + getName();
	}
	
	@Override
	public String getCreateSQL() {
		return postProcessForSchema(getContext(), createSQLInternal());
	}
	
	/**
	 * Super specific post-processing for creating tabels for specific schemas with ms sql server.
	 */
	protected String postProcessForSchema(DSLContext context, String sql) {
		if(!StringUtility.hasText(sql)) {
			return null;
		}
		
		if(!StringUtility.hasText(getSchemaName())) {
			return sql;
		}
		
		if(config.getDialect().equals(SQLDialect.SQLSERVER)) {
			if(sql.startsWith("create table [")) {
				return String.format("create table [%s].%s", getSchemaName(), sql.substring(13));
			}
		}
		
		return sql;
	}
}
