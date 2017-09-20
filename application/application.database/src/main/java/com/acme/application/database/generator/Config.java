package com.acme.application.database.generator;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public class Config {

	public static final SQLDialect DIALECT_DEFAULT = GeneratorApplication.DB_DIALECT;

	private Connection connection;
	private SQLDialect dialect;
	private DSLContext context;
	private Settings settings;

	public Config(Config config) {
		this(config.getConnection(), config.dialect, config.settings);
	}

	public Config(Connection connection, SQLDialect dialect) {
		this(connection, dialect, null);
	}

	public Config(Connection connection, SQLDialect dialect, Settings settings) {
		assert connection != null;
		
		this.connection = connection;
		this.dialect = dialect;
		this.settings = settings;

		if(dialect == null) {
			dialect = DIALECT_DEFAULT;
		}

		if(settings == null) {
			settings = new Settings();
		}

		context = DSL.using(connection, dialect, settings);
	}
	
	public Config withSettings(Settings settings) {
		this.settings = settings;
		return this;
	}

	public Connection getConnection() {
		return connection;
	}

	public DSLContext getContext() {
		return context;
	}
	
	public SQLDialect getDialect() {
		return dialect;
	}
}
