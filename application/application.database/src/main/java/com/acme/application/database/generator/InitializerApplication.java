package com.acme.application.database.generator;

import java.sql.Connection;
import java.sql.DriverManager;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;

@ApplicationScoped
public class InitializerApplication {
	
	public static final boolean ADD_SAMPLE_DATA = true;

	public static void main(String[] args) throws Exception {
		Connection connection = null;
		try {
			Class.forName(GeneratorApplication.DB_DRIVER);
			connection = DriverManager.getConnection(GeneratorApplication.DB_MAPPING_NAME);
			Config config = new Config(connection, GeneratorApplication.DB_DIALECT);
			
			SchemaInitializer schema = BEANS.get(SchemaInitializer.class);
			schema.setConfig(config);
			schema.initialize();
			
			// insert initial data and sample data
			DataInitializer initializer = BEANS.get(DataInitializer.class);
			initializer.setConfig(config);
			initializer.initialize();

			if(ADD_SAMPLE_DATA) {
				initializer.addSamples();
			}
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
