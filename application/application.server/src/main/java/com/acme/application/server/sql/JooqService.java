package com.acme.application.server.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

import com.acme.application.server.ServerProperties;
import com.acme.application.server.ServerProperties.DatabaseAutoCreateProperty;

@ApplicationScoped
public class JooqService {

	private SQLDialect dialect = null;
	private String jdbcMappingName = null;
	private String username = null;
	private String password = null;
	private Configuration configuration = null;

	/**
	 * Default constructor. Applys config propertes.
	 */
	public JooqService() {
		super();
		applyConfigProperties();
		initializeConfiguration();
	}

	/**
	 * Returns a Jooq DSL context for the provided JDBC connection.
	 */
	protected DSLContext getContext(Connection connection) {
		configuration.set(connection);
		
		return DSL.using(configuration);
	}

	/**
	 * Returns a JDBC connection.
	 */
	protected Connection getConnection() throws SQLException {
		return DriverManager.getConnection(jdbcMappingName, username, password);
	}

	private void applyConfigProperties() {
		// FIXME accessing this property does not work
		dialect = CONFIG.getPropertyValue(ServerProperties.DialectProperty.class);
//		dialect = SQLDialect.SQLSERVER;
//		dialect = SQLDialect.DERBY;
		
		jdbcMappingName = CONFIG.getPropertyValue(ServerProperties.JdbcMappingNameProperty.class);
		jdbcMappingName = postProcessMappingName(jdbcMappingName);
		
		username = CONFIG.getPropertyValue(ServerProperties.UsernameProperty.class);
		password = CONFIG.getPropertyValue(ServerProperties.PasswordProperty.class);
	}

	private String postProcessMappingName(String mappingName) {
		if(!SQLDialect.DERBY.equals(dialect)) {
			return mappingName;
		}
		else if (!CONFIG.getPropertyValue(DatabaseAutoCreateProperty.class)) {
			return mappingName;
		}
		else {
			return mappingName + ";create=true"; // <1>
		}
	}

	private void initializeConfiguration() {
		configuration = new DefaultConfiguration();
		configuration.set(dialect);
	}
}
