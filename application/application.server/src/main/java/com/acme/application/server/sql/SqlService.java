package com.acme.application.server.sql;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.PlatformExceptionTranslator;
import org.eclipse.scout.rt.server.jdbc.derby.AbstractDerbySqlService;
import org.jooq.SQLDialect;

import com.acme.application.database.generator.Config;
import com.acme.application.server.sql.DatabaseProperties.DatabaseAutoCreateProperty;
import com.acme.application.server.sql.DatabaseProperties.JdbcMappingNameProperty;

public class SqlService extends AbstractDerbySqlService {

	private Config config = null;

	@Override
	protected String getConfiguredJdbcMappingName() {
		String mappingName = CONFIG.getPropertyValue(JdbcMappingNameProperty.class);

		// add create attribute if we need to autocreate the db
		if (CONFIG.getPropertyValue(DatabaseAutoCreateProperty.class)) {
			return mappingName + ";create=true"; // <1>
		}

		return mappingName;
	}

	public Config getConfig() {
		if(config == null) {
			config = new Config(getConnection(), SQLDialect.DERBY);
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
}
