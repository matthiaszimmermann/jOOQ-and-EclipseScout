package com.acme.application.server.sql;

import javax.security.auth.Subject;

import org.eclipse.scout.rt.platform.config.AbstractBooleanConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractStringConfigProperty;
import org.eclipse.scout.rt.platform.config.AbstractSubjectConfigProperty;

import com.acme.application.server.ServerSession;

public class DatabaseProperties {

	public static class DatabaseAutoCreateProperty extends AbstractBooleanConfigProperty {

		@Override
		protected Boolean getDefaultValue() {
			return Boolean.TRUE;
		}

		@Override
		public String getKey() {
			return "contacts.database.autocreate";
		}
	}

	public static class DatabaseAutoPopulateProperty extends AbstractBooleanConfigProperty {

		@Override
		protected Boolean getDefaultValue() {
			return Boolean.TRUE;
		}

		@Override
		public String getKey() {
			return "contacts.database.autopopulate";
		}
	}

	public static class JdbcMappingNameProperty extends AbstractStringConfigProperty {

		@Override
		protected String getDefaultValue() {
			return "jdbc:derby:memory:contacts-database";
		}

		@Override
		public String getKey() {
			return "contacts.database.jdbc.mapping.name";
		}
	}

	public static class SuperUserSubjectProperty extends AbstractSubjectConfigProperty {

		@Override
		protected Subject getDefaultValue() {
			return convertToSubject(ServerSession.SUPER_USER_ID);
		}

		@Override
		public String getKey() {
			return "contacts.superuser";
		}
	}
}
