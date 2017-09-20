package com.acme.application.database.table;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;

import org.jooq.DSLContext;
import org.jooq.conf.MappedSchema;
import org.jooq.conf.RenderMapping;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.Config;
import com.acme.application.database.generator.IDataInitializer;
import com.acme.application.database.or.app.tables.records.CodeRecord;
import com.acme.application.database.or.app.tables.records.DocumentRecord;
import com.acme.application.database.or.app.tables.records.PersonRecord;
import com.acme.application.database.or.app.tables.records.RolePermissionRecord;
import com.acme.application.database.or.app.tables.records.RoleRecord;
import com.acme.application.database.or.app.tables.records.TextRecord;
import com.acme.application.database.or.app.tables.records.TypeRecord;
import com.acme.application.database.or.app.tables.records.UserRecord;
import com.acme.application.database.or.app.tables.records.UserRoleRecord;
import com.acme.application.shared.code.FileCodeType;
import com.acme.application.shared.code.LocaleCodeType;
import com.acme.application.shared.code.SexCodeType;
import com.acme.application.shared.common.DateTimeUtility;
import com.acme.application.shared.security.PasswordUtility;

public class TableDataInitializer extends TableUtility implements IDataInitializer {

	Logger LOG = LoggerFactory.getLogger(TableDataInitializer.class);

	// Code type IDs need to match with Java code
	public static final String TYPE_ID_LOCALE = LocaleCodeType.ID;
	public static final String TYPE_ID_SEX = SexCodeType.ID;
	public static final String TYPE_ID_FILE = FileCodeType.ID;

	public static final TypeRecord TYPE_LOCALE = new TypeRecord(TYPE_ID_LOCALE, CodeTypeEnum.LOCALE.type());
	public static final TypeRecord TYPE_FILE = new TypeRecord(TYPE_ID_FILE, CodeTypeEnum.STRING.type());
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

	public static byte [] DOCUMENT_README = "hello world".getBytes();
	public static String DOCUMENT_LOGO_NAME = "EclipseScout_Logo.png";
	public static String TIMESTAMP = DateTimeUtility.nowInUtcAsString();
	public static final DocumentRecord DOCUMENT_ALICE_1 = new DocumentRecord(createId(), "Readme.txt", "txt", getSize(DOCUMENT_README), DOCUMENT_README, USER_ALICE.getUsername(), TIMESTAMP, true);
	public static final DocumentRecord DOCUMENT_ALICE_2 = new DocumentRecord(createId(), DOCUMENT_LOGO_NAME, "png", getSize(null), null, USER_ALICE.getUsername(), TIMESTAMP, true);

	public static final TextRecord TEXT_TYPE_LOCALE = new TextRecord(TYPE_ID_LOCALE, TextTable.LOCALE_DEFAULT, "Locale");
	public static final TextRecord TEXT_TYPE_SEX = new TextRecord(TYPE_ID_SEX, TextTable.LOCALE_DEFAULT, "Sex");
	public static final TextRecord TEXT_TYPE_FILE = new TextRecord(TYPE_ID_FILE, TextTable.LOCALE_DEFAULT, "File Type");
	
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
		LOG.info("Insert minimal setup data");

		insertTexts();
		insertCodeTypes();
		insertPersons();
		insertUsers();
		insertRoles();
		insertUserRoles();
	}

	private void insertUserRoles() {
		insert(USER_ROLE_ROOT);
	}

	private void insertRoles() {
		insert(ROLE_ROOT);
		insert(ROLE_USER);
		insert(ROLE_GUEST);
	}

	private void insertUsers() {
		insert(USER_ROOT);
	}

	private void insertPersons() {
		insert(PERSON_ROOT);
	}

	/**
	 * Insert code types and codes. 
	 * Only add dynamic codes. Codes that are already defined in the 
	 * code type get overwritten by dynamic codes.
	 */
	private void insertCodeTypes() {
		insertLocaleCodeType();
		insertFileCodeType();
		insertSexCodeType();
	}

	private void insertFileCodeType() {
		insert(TYPE_FILE);
	}

	private void insertSexCodeType() {
		insert(TYPE_SEX);
		insert(CODE_UNDEFINED);
	}

	private void insertLocaleCodeType() {
		insert(TYPE_LOCALE);
		Arrays.stream(Locale.getAvailableLocales())
		.forEach(locale -> { 
			CodeRecord code = new CodeRecord(locale.toLanguageTag(), TYPE_ID_LOCALE, null, null, true);
			insert(code);
			
			Locale locDefault = Locale.forLanguageTag(TextTable.LOCALE_DEFAULT);
			TextRecord text = new TextRecord(code.getId(), locDefault.toLanguageTag(), locale.getDisplayName(locDefault));
			insert(text);
		});
	}

	private void insertTexts() {
		insert(TEXT_TYPE_FILE);
		insert(TEXT_TYPE_SEX);
		
		insert(TEXT_UNDEFINED);

		insert(TEXT_ROOT);
		insert(TEXT_USER);
		insert(TEXT_USER_DE);
		insert(TEXT_GUEST);
		insert(TEXT_GUEST_DE);
	}

	@Override
	public void addSamples() {
		LOG.info("Add sample data");

		insertSamplePersons();
		insertSampleUsers();
		insertSampleRoles();
		insertSampleDocuments();
	}

	private void insertSamplePersons() {
		insert(PERSON_ALICE);
		insert(PERSON_BOB);
	}

	private void insertSampleRoles() {
		insert(USER_ROLE_ALICE);
	}

	private void insertSampleUsers() {
		insert(USER_ALICE);
	}

	private void insertSampleDocuments() {
//		insert(DOCUMENT_ALICE_1);
		
		// load image file from src/main/resource folder into database
		byte [] content = loadResourceBytes("file/" + DOCUMENT_LOGO_NAME);
		DOCUMENT_ALICE_2.setContent(content);
		DOCUMENT_ALICE_2.setSize(BigDecimal.valueOf(content.length));
		insert(DOCUMENT_ALICE_2);
	}

	protected DSLContext getContext() {
		// TODO FIXME this seems to work for inserts, check if this is also good for table creation
		// in addition: figure out where the APP schema comes from. is this a jooq default?
		if(config.getContext().configuration().settings().getRenderMapping() == null) {
			config.getContext().configuration().settings()
				.setRenderMapping(new RenderMapping()
						.withSchemata(new MappedSchema()
								.withInput("APP")
								.withOutput("core")));
		}

		return config.getContext();
	}
	
	private void insert(org.jooq.Record record) {
		try { 
			if(record instanceof CodeRecord) { getContext().executeInsert((CodeRecord)record); return; }
			if(record instanceof DocumentRecord) { getContext().executeInsert((DocumentRecord)record); return; }
			if(record instanceof PersonRecord) { getContext().executeInsert((PersonRecord)record); return; }
			if(record instanceof RolePermissionRecord) { getContext().executeInsert((RolePermissionRecord)record); return; }
			if(record instanceof RoleRecord) { getContext().executeInsert((RoleRecord)record); return; }
			if(record instanceof TextRecord) { getContext().executeInsert((TextRecord)record); return; }
			if(record instanceof TypeRecord) { getContext().executeInsert((TypeRecord)record); return; }
			if(record instanceof UserRoleRecord) { getContext().executeInsert((UserRoleRecord)record); return; }
			if(record instanceof UserRecord) { getContext().executeInsert((UserRecord)record); return; }
		}
		catch(DataAccessException e) { 
			/* NOP */
		}
	}
	
	private static BigDecimal getSize(byte [] content) {
		return content != null ? BigDecimal.valueOf(content.length) : BigDecimal.ZERO;
	}
	
	private byte [] loadResourceBytes(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		byte [] content = null;
		
		try {
			content = Files.readAllBytes(file.toPath());
		} 
		catch (IOException e) {
			LOG.error("Exception readding file {}", fileName, e);
		}
		
		return content;
	}
}
