package com.acme.application.database.generator;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class Config {
	
	public static final SQLDialect DIALECT_DEFAULT = GeneratorApplication.DB_DIALECT;
	
	private Connection connection;
	private SQLDialect dialect;
	private DSLContext context;
	
	public Config(Connection connection, SQLDialect dialect) {
		
		assert connection != null;
		this.connection = connection;
		
		if(dialect != null) {
			this.dialect = dialect;
		}
		else {
			this.dialect = DIALECT_DEFAULT;
		}
		
		context = DSL.using(connection, dialect);
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public SQLDialect getDialect() {
		return dialect;
	}
	
	public DSLContext getContext() {
		return context;
	}
}
