package com.acme.application.database.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.jooq.SQLDialect;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.Settings;
import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.Configuration;
import org.jooq.util.jaxb.CustomType;
import org.jooq.util.jaxb.Database;
import org.jooq.util.jaxb.ForcedType;
import org.jooq.util.jaxb.Generator;
import org.jooq.util.jaxb.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.app.tables.Code;
import com.acme.application.database.table.CodeTable;

@ApplicationScoped
public class GeneratorApplication {

	private static final Logger LOG = LoggerFactory.getLogger(GeneratorApplication.class);

	public static final String OUTPUT_DIRECTORY = "src/generated/java";
	public static final String OUTPUT_PACKAGE = "com.acme.application.database.or";

	public static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	public static final String DB_MAPPING_NAME = "jdbc:derby:memory:generate-or-database;create=true";
	public static final String DB_JOOQ_NAME = "org.jooq.util.derby.DerbyDatabase";
	public static final SQLDialect DB_DIALECT = SQLDialect.DERBY; 

	// TODO ask how to do this for uuid, does not seem to work (using workaround with varchar(46) for id columns now
	private static final String CONVERTER_DATE = "com.acme.application.database.generator.converter.DateConverter";
	private static final String CONVERTER_LONG = "com.acme.application.database.generator.converter.LongConverter";
	private static final String CONVERTER_UUID = "com.acme.application.database.generator.converter.UUIDConverter";

	public static void main(String[] args) {
		try {
			Configuration configuration = new Configuration()
					.withGenerator(
							new Generator()
							.withName("org.jooq.util.JavaGenerator")
							.withDatabase(
									new Database()
									.withCustomTypes(
											new CustomType()
											.withConverter(CONVERTER_UUID)
											.withName("java.util.UUID"),
											new CustomType()
											.withConverter(CONVERTER_DATE)
											.withName("java.util.Date"),
											new CustomType()
											.withConverter(CONVERTER_LONG)
											.withName("java.math.BigDecimal")
											)
									.withForcedTypes(
											new ForcedType()
											.withName("java.util.UUID")
											.withTypes("varchar(36)"),
											new ForcedType()
											.withName("java.util.Date")
											.withTypes("date"),
											new ForcedType()
											.withName("java.math.BigDecimal")
											.withTypes("bigint"))
									.withName(DB_JOOQ_NAME)
									.withIncludes(".*")
									.withExcludes(""))
							.withTarget(
									new Target()
									.withDirectory(OUTPUT_DIRECTORY)
									.withPackageName(OUTPUT_PACKAGE)));

			new GeneratorTool().run(configuration);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param connection
	 * @throws SQLException
	 */
	public static void setupDatabase(Config config) {

		// TODO verify why this does not work
		//		SchemaInitializer schema = BEANS.get(SchemaInitializer.class);
		//		schema.setConfig(config);
		//		schema.initialize();

		try {
			Statement statement = config.getConnection().createStatement();

			// create database schemas
			List<String> schemas = DatabaseUtility.getSchemaNames(config);
			for (IDatabaseSchema schema : BEANS.all(IDatabaseSchema.class)) {
				createDatabaseObject(config, statement, schemas, schema);
			}

			// create database tables
			List<String> tables = DatabaseUtility.getTableNames(config);
			// TODO cleanup (for debugging only)
			//			List<String> tables = new ArrayList<String>();
			for (IDatabaseTable table : BEANS.all(IDatabaseTable.class)) {
				createDatabaseObject(config, statement, tables, table);
			}

			statement.close();
		}
		catch (Exception e) {
			LOG.error("Failed to create database schema: {}", e.getMessage());
			throw new ProcessingException("Failed to create database schema", e);
		}
	}

	private static void createDatabaseObject(Config config, Statement statement, List<String> objects,
			IDatabaseObject object) throws SQLException 
	{
		Config localConfig = new Config(config);

		if(IDatabaseTable.class.isInstance(object)) {
			IDatabaseTable table = (IDatabaseTable) object;
			String schema = table.getSchemaName();

			if(StringUtility.hasText(schema)) {
				Settings settings = new Settings()
						.withRenderMapping(new RenderMapping()
								.withSchemata(
										new MappedSchema().withInput("").withOutput(schema)));

				localConfig.withSettings(settings);
			}
		}

		String name = object.getName();
		if(!objects.contains(name)) {
			object.setConfig(localConfig);
			String sql = object.getCreateSQL();
			statement.executeUpdate(sql);
		}
		else {
			object.getLogger().warn("Database object {} already exists, nothing created", name);					
		}
	}

	public static void teardownDatabase(Config config) {
		for (IDatabaseTable table : BEANS.all(IDatabaseTable.class)) {
			table.setConfig(config);
			table.drop();
		}
	}

	public static class GeneratorTool extends GenerationTool {

		@Override
		public void run(Configuration configuration) throws Exception {
			Connection connection = null;
			try {
				Class.forName(DB_DRIVER);
				connection = DriverManager.getConnection(DB_MAPPING_NAME);

				GeneratorApplication.setupDatabase(new Config(connection, DB_DIALECT));
				setConnection(connection);
				super.run(configuration);
			}
			finally {
				if (connection != null) {
					connection.close();
				}
			}
		}
	}
}
