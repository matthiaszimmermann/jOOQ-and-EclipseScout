package com.acme.application.database.table;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.IDataInitializer;
import com.acme.application.database.or.core.tables.records.CodeRecord;
import com.acme.application.database.or.core.tables.records.DocumentRecord;
import com.acme.application.database.or.core.tables.records.PersonRecord;
import com.acme.application.database.or.core.tables.records.RolePermissionRecord;
import com.acme.application.database.or.core.tables.records.RoleRecord;
import com.acme.application.database.or.core.tables.records.TextRecord;
import com.acme.application.database.or.core.tables.records.TypeRecord;
import com.acme.application.database.or.core.tables.records.UserRecord;
import com.acme.application.database.or.core.tables.records.UserRoleRecord;
import com.acme.application.database.or.sv.tables.Fi;
import com.acme.application.database.or.sv.tables.records.FiRecord;
import com.acme.application.group_a.shared.sv.EtageCodeType;
import com.acme.application.shared.code.FileCodeType;
import com.acme.application.shared.code.LocaleCodeType;
import com.acme.application.shared.code.SexCodeType;
import com.acme.application.shared.common.DateTimeUtility;
import com.acme.application.shared.security.PasswordUtility;

public class TableDataInitializer extends TableUtility implements IDataInitializer {

	Logger LOG = LoggerFactory.getLogger(TableDataInitializer.class);

	//--- Core Schema Items ------------------------------------------------------------//
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

	public static final PersonRecord PERSON_ROOT = new PersonRecord("8be7647c-a5d4-408f-b7b6-7b36461b56c4", "Root", "", CODE_MALE.getId(), true);
	public static final PersonRecord PERSON_ALICE = new PersonRecord("368a17b5-eda6-4d98-ae89-114e6244736c", "Alice", "", CODE_FEMALE.getId(), true);
	public static final PersonRecord PERSON_BOB = new PersonRecord("5cb35b19-a17c-40fa-834e-c6a9222480be", "Bob", "", CODE_MALE.getId(), true);

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
	public static final DocumentRecord DOCUMENT_ALICE_1 = new DocumentRecord("a4e8fea9-e646-4e8e-897f-5f3b1d96202a", "Readme.txt", "txt", getSize(DOCUMENT_README), DOCUMENT_README, USER_ALICE.getUsername(), TIMESTAMP, true);
	public static final DocumentRecord DOCUMENT_ALICE_2 = new DocumentRecord("39060692-ad7f-4804-83fc-d16b1407379e", DOCUMENT_LOGO_NAME, "png", getSize(null), null, USER_ALICE.getUsername(), TIMESTAMP, true);

	public static final TextRecord TEXT_TYPE_LOCALE = new TextRecord(TYPE_ID_LOCALE, TextTable.LOCALE_DEFAULT, "Locale");
	public static final TextRecord TEXT_TYPE_SEX = new TextRecord(TYPE_ID_SEX, TextTable.LOCALE_DEFAULT, "Sex");
	public static final TextRecord TEXT_TYPE_FILE = new TextRecord(TYPE_ID_FILE, TextTable.LOCALE_DEFAULT, "File Type");

	public static final TextRecord TEXT_UNDEFINED = new TextRecord(CODE_UNDEFINED.getId(), TextTable.LOCALE_DEFAULT, "Undefined");

	public static final TextRecord TEXT_ROOT = new TextRecord(RoleTable.ROOT, TextTable.LOCALE_DEFAULT, "Root");
	public static final TextRecord TEXT_USER = new TextRecord(RoleTable.USER, TextTable.LOCALE_DEFAULT, "User");
	public static final TextRecord TEXT_GUEST = new TextRecord(RoleTable.GUEST, TextTable.LOCALE_DEFAULT, "Guest");

	public static final TextRecord TEXT_USER_DE = new TextRecord(RoleTable.USER, Locale.GERMAN.toLanguageTag(), "Benutzer");
	public static final TextRecord TEXT_GUEST_DE = new TextRecord(RoleTable.GUEST, Locale.GERMAN.toLanguageTag(), "Gast");	

	//--- Sv Schema Items --------------------------------------------------------------//

	public static final String TYPE_ID_ETAGE = EtageCodeType.ID;
	public static final TypeRecord TYPE_ETAGE = new TypeRecord(TYPE_ID_ETAGE, CodeTypeEnum.STRING.type());

	public static final FiRecord FI_700_1 = new FiRecord()
			.with(Fi.FI.ID, "cf3b8996-4e6b-4016-97cf-29d803d14aa6")
			.with(Fi.FI.GEB_NR, "700")
			.with(Fi.FI.ANL_NR, "99.01")
			.with(Fi.FI.ANLAGE, "Steckdoseverteilung")
			.with(Fi.FI.ETAGE, EtageCodeType.EG.ID)
			.with(Fi.FI.STANDORT, "Südwand")
			.with(Fi.FI.SICHERUNG, "F 201")
			.with(Fi.FI.IF, "30mA")
			.with(Fi.FI.ACTIVE, true);

	//----------------------------------------------------------------------------------//

	/**
	 * initialize data for all schemes
	 */
	@Override
	public void initialize(DSLContext context) {
		LOG.info("Insert minimal setup data");
		initializeCore(context);		
		initializeSv(context);
	}

	/**
	 * insert minimal core data
	 */
	private void initializeCore(DSLContext ctx) {
		insertTexts(ctx);
		insertCodeTypes(ctx);
		insertPersons(ctx);
		insertUsers(ctx);
		insertRoles(ctx);
		insertUserRoles(ctx);
	}

	/**
	 * insert minimal sv data
	 */
	private void initializeSv(DSLContext ctx) {
		insertFi(ctx);
	}

	private void insertFi(DSLContext ctx) {
		insert(ctx, FI_700_1);
	}

	private void insertUserRoles(DSLContext ctx) {
		insert(ctx, USER_ROLE_ROOT);
	}

	private void insertRoles(DSLContext ctx) {
		insert(ctx, ROLE_ROOT);
		insert(ctx, ROLE_USER);
		insert(ctx, ROLE_GUEST);
	}

	private void insertUsers(DSLContext ctx) {
		insert(ctx, USER_ROOT);
	}

	private void insertPersons(DSLContext ctx) {
		insert(ctx, PERSON_ROOT);
	}

	/**
	 * Insert code types and codes. 
	 * Only add dynamic codes. Codes that are already defined in the 
	 * code type get overwritten by dynamic codes.
	 */
	private void insertCodeTypes(DSLContext ctx) {		
		insertLocaleCodeType(ctx);
		insertFileCodeType(ctx);
		insertSexCodeType(ctx);

		insert(ctx, TYPE_ETAGE);
	}

	private void insertFileCodeType(DSLContext ctx) {
		insert(ctx, TYPE_FILE);
	}

	private void insertSexCodeType(DSLContext ctx) {
		insert(ctx, TYPE_SEX);
		insert(ctx, CODE_UNDEFINED);
	}

	private void insertLocaleCodeType(DSLContext ctx) {
		insert(ctx, TYPE_LOCALE);

		Arrays.stream(Locale.getAvailableLocales())
		.forEach(locale -> { 
			CodeRecord code = new CodeRecord(locale.toLanguageTag(), TYPE_ID_LOCALE, null, null, true);
			insert(ctx, code);

			Locale locDefault = Locale.forLanguageTag(TextTable.LOCALE_DEFAULT);
			TextRecord text = new TextRecord(code.getId(), locDefault.toLanguageTag(), locale.getDisplayName(locDefault));
			insert(ctx, text);
		});
	}

	private void insertTexts(DSLContext ctx) {
		insert(ctx, TEXT_TYPE_FILE);
		insert(ctx, TEXT_TYPE_SEX);

		insert(ctx, TEXT_UNDEFINED);

		insert(ctx, TEXT_ROOT);
		insert(ctx, TEXT_USER);
		insert(ctx, TEXT_USER_DE);
		insert(ctx, TEXT_GUEST);
		insert(ctx, TEXT_GUEST_DE);
	}

	@Override
	public void addSamples(DSLContext ctx) {
		LOG.info("Add sample data");
		addCoreSamples(ctx);
	}

	private void addCoreSamples(DSLContext ctx) {
		insertSamplePersons(ctx);
		insertSampleUsers(ctx);
		insertSampleRoles(ctx);
		insertSampleDocuments(ctx);
	}

	private void insertSamplePersons(DSLContext ctx) {
		insert(ctx, PERSON_ALICE);
		insert(ctx, PERSON_BOB);
	}

	private void insertSampleRoles(DSLContext ctx) {
		insert(ctx, USER_ROLE_ALICE);
	}

	private void insertSampleUsers(DSLContext ctx) {
		insert(ctx, USER_ALICE);
	}

	private void insertSampleDocuments(DSLContext ctx) {
		insert(ctx, DOCUMENT_ALICE_1);

		// load image file from src/main/resource folder into database
		byte [] content = loadResourceBytes("file/" + DOCUMENT_LOGO_NAME);
		DOCUMENT_ALICE_2.setContent(content);
		DOCUMENT_ALICE_2.setSize(BigDecimal.valueOf(content.length));
		insert(ctx, DOCUMENT_ALICE_2);
	}

	private void insert(DSLContext ctx, org.jooq.Record record) {
		try { 

			// core records
			if(record instanceof CodeRecord) { ctx.executeInsert((CodeRecord)record); return; }
			if(record instanceof DocumentRecord) { ctx.executeInsert((DocumentRecord)record); return; }
			if(record instanceof PersonRecord) { ctx.executeInsert((PersonRecord)record); return; }
			if(record instanceof RolePermissionRecord) { ctx.executeInsert((RolePermissionRecord)record); return; }
			if(record instanceof RoleRecord) { ctx.executeInsert((RoleRecord)record); return; }
			if(record instanceof TextRecord) { ctx.executeInsert((TextRecord)record); return; }
			if(record instanceof TypeRecord) { ctx.executeInsert((TypeRecord)record); return; }
			if(record instanceof UserRoleRecord) { ctx.executeInsert((UserRoleRecord)record); return; }
			if(record instanceof UserRecord) { ctx.executeInsert((UserRecord)record); return; }

			// sv records
			if(record instanceof FiRecord) { ctx.executeInsert((FiRecord)record); return; }

			LOG.warn("unknown record type '{}' in insert(). skipping insert ", record.getClass().getSimpleName());
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
