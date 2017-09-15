package com.acme.application.database.generator;

import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;

@ApplicationScoped
public class SchemaInitializer {

	private Config config;

	public void setConfig(Config config) {
		assert config != null;
		this.config = config;
	}

	public void initialize() {
		assert config != null;

		try {
			Statement statement = config.getConnection().createStatement();

			for (IDatabaseTable table : BEANS.all(IDatabaseTable.class)) {
				table.setConfig(config);
				statement.executeUpdate(table.getCreateSQL());
			}
		} 
		catch (SQLException e) {
			throw new ProcessingException("failed to initialize database schema", e);
		}
	}
}
