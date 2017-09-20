package com.acme.application.database.generator;

import java.sql.Connection;
import java.sql.DriverManager;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.jooq.SQLDialect;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(PlatformTestRunner.class)
@RunWithSubject("admin")
//@Ignore
public class CreateTableStatementMsSqlTest {


	@Test
	public void dumpTableNames()  throws Exception {
		for (IDatabaseTable task : BEANS.all(IDatabaseTable.class)) {
			System.out.println(task.getName());
		}
	}

	@Test
	public void dumpCreateTableStatements()  throws Exception {

		Connection connection = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection("jdbc:sqlserver://192.168.99.100:1433;DatabaseName=SCOUT;user=SA;password=<YourStrong!Passw0rd>");

			for (IDatabaseTable table : BEANS.all(IDatabaseTable.class)) {
				table.setConfig(new Config(connection, SQLDialect.SQLSERVER));
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
