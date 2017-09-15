package com.acme.application.database.generator;

import java.sql.Connection;
import java.sql.DriverManager;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(PlatformTestRunner.class)
@RunWithSubject("admin")
//@Ignore
public class CreateTableStatementTest {


	@Test
	public void dumpTableNames()  throws Exception {
		for (IDatabaseTable task : BEANS.all(IDatabaseTable.class)) {
			System.out.println(task.getTableName());
		}
	}

	@Test
	public void dumpCreateTableStatements()  throws Exception {

		Connection connection = null;
		try {
			Class.forName(GeneratorApplication.DB_DRIVER);
			connection = DriverManager.getConnection(GeneratorApplication.DB_MAPPING_NAME);

			for (IDatabaseTable table : BEANS.all(IDatabaseTable.class)) {
				table.setConfig(new Config(connection, GeneratorApplication.DB_DIALECT));
				System.out.println(table.getCreateSQL() + ";");
				System.out.println();
			}
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}		
	}
}
