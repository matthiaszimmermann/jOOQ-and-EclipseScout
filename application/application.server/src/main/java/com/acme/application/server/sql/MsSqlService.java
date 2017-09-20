package com.acme.application.server.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.server.jdbc.mssql.AbstractMsSqlSqlService;
import org.jooq.SQLDialect;

import com.acme.application.database.generator.Config;
import com.acme.application.server.sql.DatabaseProperties.JdbcMappingNameProperty;
import com.acme.application.server.sql.DatabaseProperties.PasswordProperty;
import com.acme.application.server.sql.DatabaseProperties.UsernameProperty;

public class MsSqlService extends AbstractMsSqlSqlService {

	private Config config = null;

	public Config getConfig() {
		if(config == null) {
			config = new Config(getConnection(), SQLDialect.SQLSERVER);
		}

		return config;
	}

	public void dropDB() {
		try {
			String jdbcMappingName = CONFIG.getPropertyValue(DatabaseProperties.JdbcMappingNameProperty.class);
			DriverManager.getConnection(jdbcMappingName + ";drop=true");
		}
		catch (SQLException e) {
			BEANS.get(PlatformExceptionTranslator.class).translate(e);
		}
	}

	@Override
	protected String getConfiguredJdbcMappingName() {
		String mappingName = CONFIG.getPropertyValue(JdbcMappingNameProperty.class);

		// add create attribute if we need to autocreate the db
		// ms sql server does not have such a property
//		if (CONFIG.getPropertyValue(DatabaseAutoCreateProperty.class)) {
//			return mappingName + ";create=true";
//		}

		return mappingName;
	}
	
	@Override
	protected String getConfiguredUsername() {
		return CONFIG.getPropertyValue(UsernameProperty.class);
	}
	
	@Override
	protected String getConfiguredPassword() {
		return CONFIG.getPropertyValue(PasswordProperty.class);
	}
}
