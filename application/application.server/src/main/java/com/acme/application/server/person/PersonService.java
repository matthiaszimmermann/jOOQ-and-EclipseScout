package com.acme.application.server.person;

import java.util.List;
import java.util.stream.Collectors;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.app.tables.Person;
import com.acme.application.database.or.app.tables.records.PersonRecord;
import com.acme.application.database.table.TableUtility;
import com.acme.application.server.common.BaseService;

public class PersonService extends BaseService {

	private static final Logger LOG = LoggerFactory.getLogger(PersonService.class);

	/**
	 * Returns true iff a person with the specified id exists.
	 */
	public boolean exists(String personId) {
		Person pt = Person.PERSON;
		DSLContext ctx = getContext();

		return ctx.fetchExists(
				ctx.select()
				.from(pt)
				.where(pt.ID.eq(personId))
				);
	}

	/**
	 * Returns the person object for the specified id.
	 * Returns null if no such person exists.
	 */
	public PersonRecord get(String personId) {
		Person pt = Person.PERSON;

		return getContext()
				.selectFrom(pt)
				.where(pt.ID.eq(personId))
				.fetchOne();
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

	/**
	 * Returns all available persons.
	 */
	public List<PersonRecord> getAll() {
		return getContext()
				.selectFrom(Person.PERSON)
				.fetchStream()
				.collect(Collectors.toList());
	}

	/**
	 * Persists the provided person. 
	 */
	public void store(PersonRecord person) {
		LOG.info("persist person {}", person);
		
		if(exists(person.getId())) { getContext().executeUpdate(person); }
		else { getContext().executeInsert(person); }
	}

	public void updateName(String personId, String firstName, String lastName) {
		LOG.info("update person names to {} {} for {}", firstName, lastName, personId);
		
		Person pt = Person.PERSON;
		getContext()
		.update(pt)
		.set(pt.FIRST_NAME, firstName)
		.set(pt.LAST_NAME, lastName)
		.where(pt.ID.eq(personId))
		.execute();
	}
}
