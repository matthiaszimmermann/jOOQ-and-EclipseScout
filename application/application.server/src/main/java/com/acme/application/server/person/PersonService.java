package com.acme.application.server.person;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.core.tables.Person;
import com.acme.application.database.or.core.tables.records.PersonRecord;
import com.acme.application.database.table.TableUtility;
import com.acme.application.server.common.AbstractBaseService;

public class PersonService extends AbstractBaseService<Person, PersonRecord> {

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(PersonService.class);
	}
	
	@Override
	public Person getTable() {
		return Person.PERSON;
	}

	@Override
	public Field<String> getIdColumn() {
		return Person.PERSON.ID;
	}

	public void updateName(String personId, String firstName, String lastName) {
		getLogger().info("Update person names to {} {} for {}", firstName, lastName, personId);

		try(Connection connection = getConnection()) {
			getContext(connection)
			.update(getTable())
			.set(getTable().FIRST_NAME, firstName)
			.set(getTable().LAST_NAME, lastName)
			.where(getIdColumn().eq(personId))
			.execute();
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute updateName(). exception: ", e);
		}
	}

	/**
	 * Returns the person object for the specified id.
	 * Returns a new empty person record if no such person exists.
	 */
	public PersonRecord getOrCreate(String personId) {
		PersonRecord person = get(personId);

		if(person != null) {
			return person;
		}

		person = new PersonRecord();
		person.setId(TableUtility.createId());
		person.setActive(true);

		return person;
	}
}
