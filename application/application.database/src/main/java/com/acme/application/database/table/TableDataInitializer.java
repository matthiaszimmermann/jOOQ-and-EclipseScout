package com.acme.application.database.table;

import java.util.Arrays;
import java.util.Locale;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.Config;
import com.acme.application.database.generator.DataInitializer;
import com.acme.application.database.or.app.tables.records.CodeRecord;
import com.acme.application.database.or.app.tables.records.PersonRecord;
import com.acme.application.database.or.app.tables.records.RoleRecord;
import com.acme.application.database.or.app.tables.records.TextRecord;
import com.acme.application.database.or.app.tables.records.TypeRecord;
import com.acme.application.database.or.app.tables.records.UserRecord;
import com.acme.application.database.or.app.tables.records.UserRoleRecord;
import com.acme.application.shared.security.PasswordUtility;

public class TableDataInitializer extends TableUtility implements DataInitializer {

	Logger log = LoggerFactory.getLogger(TableDataInitializer.class);

	// Code type IDs need to match with Java code
	public static final String TYPE_ID_LOCALE = "af410ba5-b1a3-4181-a655-0bcfe9d53b78";
	public static final String TYPE_ID_SEX = "17f42353-e6e6-4654-a879-02535cc9c44f";

	public static final TypeRecord TYPE_LOCALE = new TypeRecord(TYPE_ID_LOCALE, CodeTypeEnum.LOCALE.type());
	public static final TypeRecord TYPE_SEX = new TypeRecord(TYPE_ID_SEX, CodeTypeEnum.STRING.type());

	// Male/female code ID's need to match with Java code
	public static final CodeRecord CODE_MALE = new CodeRecord("M", TYPE_ID_SEX, null, null, true);
	public static final CodeRecord CODE_FEMALE = new CodeRecord("F", TYPE_ID_SEX, null, null, true);
	public static final CodeRecord CODE_UNDEFINED = new CodeRecord("U", TYPE_ID_SEX, null, null, true);

	public static final PersonRecord PERSON_ROOT = new PersonRecord(createId(), "Root", "", CODE_MALE.getId(), true);
	public static final PersonRecord PERSON_ALICE = new PersonRecord(createId(), "Alice", "", CODE_FEMALE.getId(), true);
	public static final PersonRecord PERSON_BOB = new PersonRecord(createId(), "Bob", "", CODE_MALE.getId(), true);

	public static final UserRecord USER_ROOT = new UserRecord(
			UserTable.ROOT, 
			PERSON_ROOT.getId(), 
			TextTable.LOCALE_DEFAULT,
			PasswordUtility.calculateEncodedPassword("eclipse"), 
			true);

	public static final UserRecord USER_ALICE = new UserRecord(
			"alice", 
			PERSON_ALICE.getId(), 
			TextTable.LOCALE_DEFAULT,
			PasswordUtility.calculateEncodedPassword("test"), 
			true);

	public static final RoleRecord ROLE_ROOT = new RoleRecord(RoleTable.ROOT, true);
	public static final RoleRecord ROLE_USER = new RoleRecord(RoleTable.USER, true);
	public static final RoleRecord ROLE_GUEST = new RoleRecord(RoleTable.GUEST, false);

	public static final UserRoleRecord USER_ROLE_ROOT = new UserRoleRecord(UserTable.ROOT, RoleTable.ROOT);
	public static final UserRoleRecord USER_ROLE_ALICE = new UserRoleRecord(USER_ALICE.getUsername(), RoleTable.USER);

	public static final TextRecord TEXT_TYPE_LOCALE = new TextRecord(TYPE_ID_LOCALE, TextTable.LOCALE_DEFAULT, "Locale");
	public static final TextRecord TEXT_TYPE_SEX = new TextRecord(TYPE_ID_SEX, TextTable.LOCALE_DEFAULT, "Sex");
	
	public static final TextRecord TEXT_UNDEFINED = new TextRecord(CODE_UNDEFINED.getId(), TextTable.LOCALE_DEFAULT, "Undefined");

	public static final TextRecord TEXT_ROOT = new TextRecord(RoleTable.ROOT, TextTable.LOCALE_DEFAULT, "Root");
	public static final TextRecord TEXT_USER = new TextRecord(RoleTable.USER, TextTable.LOCALE_DEFAULT, "User");
	public static final TextRecord TEXT_GUEST = new TextRecord(RoleTable.GUEST, TextTable.LOCALE_DEFAULT, "Guest");

	public static final TextRecord TEXT_USER_DE = new TextRecord(RoleTable.USER, Locale.GERMAN.toLanguageTag(), "Benutzer");
	public static final TextRecord TEXT_GUEST_DE = new TextRecord(RoleTable.GUEST, Locale.GERMAN.toLanguageTag(), "Gast");	

	private Config config;

	@Override
	public void setConfig(Config config) {
		assert config != null;
		this.config = config;
	}

	@Override
	public Config getConfig() {
		return config;
	}

	@Override
	public void initialize() {
		log.info("Insert minimal setup data");

		insertTexts();
		insertCodeTypes();
		insertPersons();
		insertUsers();
		insertRoles();
		insertUserRoles();
	}

	private void insertUserRoles() {
		getContext().executeInsert(USER_ROLE_ROOT);
	}

	private void insertRoles() {
		getContext().executeInsert(ROLE_ROOT);
		getContext().executeInsert(ROLE_USER);
		getContext().executeInsert(ROLE_GUEST);
	}

	private void insertUsers() {
		getContext().executeInsert(USER_ROOT);
	}

	private void insertPersons() {
		getContext().executeInsert(PERSON_ROOT);
	}

	/**
	 * Insert code types and codes. 
	 * Only add dynamic codes. Codes that are already defined in the 
	 * code type get overwritten by dynamic codes.
	 */
	private void insertCodeTypes() {
		insertSexCodeType();
		insertLocaleCodeType();
	}

	private void insertSexCodeType() {
		getContext().executeInsert(TYPE_SEX);
		getContext().executeInsert(CODE_UNDEFINED);
	}

	private void insertLocaleCodeType() {
		getContext().executeInsert(TYPE_LOCALE);
		Arrays.stream(Locale.getAvailableLocales())
		.forEach(locale -> { 
			CodeRecord code = new CodeRecord(locale.toLanguageTag(), TYPE_ID_LOCALE, null, null, true);
			getContext().executeInsert(code);
			
			Locale locDefault = Locale.forLanguageTag(TextTable.LOCALE_DEFAULT);
			TextRecord text = new TextRecord(code.getId(), locDefault.toLanguageTag(), locale.getDisplayName(locDefault));
			getContext().executeInsert(text);
		});
	}

	private void insertTexts() {
		getContext().executeInsert(TEXT_TYPE_LOCALE);
		getContext().executeInsert(TEXT_TYPE_SEX);
		
		getContext().executeInsert(TEXT_UNDEFINED);

		getContext().executeInsert(TEXT_ROOT);
		getContext().executeInsert(TEXT_USER);
		getContext().executeInsert(TEXT_USER_DE);
		getContext().executeInsert(TEXT_GUEST);
		getContext().executeInsert(TEXT_GUEST_DE);
	}

	@Override
	public void addSamples() {
		log.info("Add sample data");

		insertSamplePersons();
		insertSampleUsers();
		insertSampleRoles();
	}

	private void insertSampleRoles() {
		getContext().executeInsert(USER_ROLE_ALICE);
	}

	private void insertSampleUsers() {
		getContext().executeInsert(USER_ALICE);
	}

	private void insertSamplePersons() {
		getContext().executeInsert(PERSON_ALICE);
		getContext().executeInsert(PERSON_BOB);
	}

	protected DSLContext getContext() {
		return config.getContext();
	}
}
