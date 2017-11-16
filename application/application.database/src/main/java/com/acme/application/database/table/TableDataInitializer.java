package com.acme.application.database.table;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.generator.IDataInitializer;
import com.acme.application.database.or.awt.tables.Produkt;
import com.acme.application.database.or.awt.tables.Pruefkoerper;
import com.acme.application.database.or.awt.tables.records.ProduktRecord;
import com.acme.application.database.or.awt.tables.records.PruefkoerperRecord;
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
import com.acme.application.group_a.shared.awt.FarbeCodeType;
import com.acme.application.group_a.shared.awt.ProduktFamilieCodeType;
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
	public static final CodeRecord CODE_FEMALE = new CodeRecord("F", TYPE_ID_SEX, 10.0, null, null, true);
	public static final CodeRecord CODE_MALE = new CodeRecord("M", TYPE_ID_SEX, 20.0, null, null, true);
	public static final CodeRecord CODE_UNDEFINED = new CodeRecord("U", TYPE_ID_SEX, 30.0, null, null, true);

	public static final PersonRecord PERSON_ROOT = new PersonRecord("8be7647c-a5d4-408f-b7b6-7b36461b56c4", "Root", "", CODE_MALE.getId(), true);
	public static final PersonRecord PERSON_ALICE = new PersonRecord("368a17b5-eda6-4d98-ae89-114e6244736c", "Alice", "", CODE_FEMALE.getId(), true);
	public static final PersonRecord PERSON_BOB = new PersonRecord("5cb35b19-a17c-40fa-834e-c6a9222480be", "Bob", "", CODE_MALE.getId(), true);

	public static final UserRecord USER_ROOT = new UserRecord(
			UserTable.ROOT, 
			PERSON_ROOT.getId(), 
			TextTable.LOCALE_GERMAN,
			PasswordUtility.calculateEncodedPassword("eclipse"), 
			true);

	public static final UserRecord USER_ALICE = new UserRecord(
			"alice", 
			PERSON_ALICE.getId(), 
			TextTable.LOCALE_DEFAULT,
			PasswordUtility.calculateEncodedPassword("test"), 
			true);

	public static final RoleRecord ROLE_ROOT = new RoleRecord(RoleTable.ROOT, RoleTable.ROOT, true);
	public static final RoleRecord ROLE_USER = new RoleRecord(RoleTable.USER, RoleTable.USER, true);
	public static final RoleRecord ROLE_GUEST = new RoleRecord(RoleTable.GUEST, RoleTable.GUEST, false);

	public static final UserRoleRecord USER_ROLE_ROOT = new UserRoleRecord(UserTable.ROOT, RoleTable.ROOT);
	public static final UserRoleRecord USER_ROLE_ALICE = new UserRoleRecord(USER_ALICE.getUsername(), RoleTable.USER);

	public static byte [] DOCUMENT_README = "hello world".getBytes();
	public static String DOCUMENT_LOGO_NAME = "EclipseScout_Logo.png";
	public static String TIMESTAMP = DateTimeUtility.nowInUtcAsString();
	public static final DocumentRecord DOCUMENT_ALICE_1 = new DocumentRecord("a4e8fea9-e646-4e8e-897f-5f3b1d96202a", "Readme.txt", "txt", getSize(DOCUMENT_README), DOCUMENT_README, USER_ALICE.getUsername(), TIMESTAMP, true);
	public static final DocumentRecord DOCUMENT_ALICE_2 = new DocumentRecord("39060692-ad7f-4804-83fc-d16b1407379e", DOCUMENT_LOGO_NAME, "png", getSize(null), null, USER_ALICE.getUsername(), TIMESTAMP, true);

	public static final TextRecord TEXT_TYPE_LOCALE = new TextRecord(TYPE_ID_LOCALE, TextTable.LOCALE_DEFAULT, "Locale");
	public static final TextRecord TEXT_TYPE_FILE = new TextRecord(TYPE_ID_FILE, TextTable.LOCALE_DEFAULT, "File Type");
	public static final TextRecord TEXT_TYPE_SEX = new TextRecord(TYPE_ID_SEX, TextTable.LOCALE_DEFAULT, "Sex");

	public static final TextRecord TEXT_UNDEFINED = new TextRecord(CODE_UNDEFINED.getId(), TextTable.LOCALE_DEFAULT, "Undefined");

	public static final TextRecord TEXT_ROOT = new TextRecord(RoleTable.toTextKey(RoleTable.ROOT), TextTable.LOCALE_DEFAULT, "Root");
	public static final TextRecord TEXT_USER = new TextRecord(RoleTable.toTextKey(RoleTable.USER), TextTable.LOCALE_DEFAULT, "User");
	public static final TextRecord TEXT_GUEST = new TextRecord(RoleTable.toTextKey(RoleTable.GUEST), TextTable.LOCALE_DEFAULT, "Guest");

	public static final TextRecord TEXT_TYPE_FILE_DE = new TextRecord(TYPE_ID_FILE, TextTable.LOCALE_GERMAN, "Dateityp");
	public static final TextRecord TEXT_TYPE_SEX_DE = new TextRecord(TYPE_ID_SEX, TextTable.LOCALE_GERMAN, "Geschlecht");
	public static final TextRecord TEXT_USER_DE = new TextRecord(RoleTable.toTextKey(RoleTable.USER), Locale.GERMAN.toLanguageTag(), "Benutzer");
	public static final TextRecord TEXT_GUEST_DE = new TextRecord(RoleTable.toTextKey(RoleTable.GUEST), Locale.GERMAN.toLanguageTag(), "Gast");	

	//--- Sv Schema Items --------------------------------------------------------------//

	public static final String TYPE_ID_ETAGE = EtageCodeType.ID;
	public static final TypeRecord TYPE_ETAGE = new TypeRecord(TYPE_ID_ETAGE, CodeTypeEnum.STRING.type());

	public static final CodeRecord CODE_UG = new CodeRecord("UG", TYPE_ID_ETAGE, -10.0, null, null, true);
	public static final CodeRecord CODE_EG = new CodeRecord("EG", TYPE_ID_ETAGE, 0.0, null, null, true);
	public static final CodeRecord CODE_OG_1 = new CodeRecord("OG01", TYPE_ID_ETAGE, 10.0, null, null, true);
	public static final CodeRecord CODE_OG_2 = new CodeRecord("OG02", TYPE_ID_ETAGE, 20.0 , null, null, true);

	public static final TextRecord TEXT_TYPE_ETAGE = new TextRecord(TYPE_ID_ETAGE, TextTable.LOCALE_DEFAULT, "Floor");
	public static final TextRecord TEXT_CODE_UG = new TextRecord(CODE_UG.getId(), TextTable.LOCALE_DEFAULT, "UG");
	public static final TextRecord TEXT_CODE_EG = new TextRecord(CODE_EG.getId(), TextTable.LOCALE_DEFAULT, "EG");
	public static final TextRecord TEXT_CODE_OG_1 = new TextRecord(CODE_OG_1.getId(), TextTable.LOCALE_DEFAULT, "1.OG");
	public static final TextRecord TEXT_CODE_OG_2 = new TextRecord(CODE_OG_2.getId(), TextTable.LOCALE_DEFAULT, "2.OG");

	public static final TextRecord TEXT_TYPE_ETAGE_DE = new TextRecord(TYPE_ID_ETAGE, TextTable.LOCALE_GERMAN, "Etage");

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

	//--- AWT Schema Items --------------------------------------------------------------//

	public static final String TYPE_ID_FARBE = FarbeCodeType.ID;
	public static final TypeRecord TYPE_FARBE = new TypeRecord(TYPE_ID_FARBE, CodeTypeEnum.STRING.type());
	public static final CodeRecord CODE_ANTHRACITE = new CodeRecord(FarbeCodeType.Anthracite.ID, TYPE_ID_FARBE, 10.0, null, null, true);
	public static final CodeRecord CODE_BLAU = new CodeRecord(FarbeCodeType.Blau.ID, TYPE_ID_FARBE, 20.0, null, null, true);
	public static final CodeRecord CODE_SCHWARZ = new CodeRecord(FarbeCodeType.Schwarz.ID, TYPE_ID_FARBE, 90.0, null, null, true);

	public static final String TYPE_ID_PRODUKT_FAMILIE = ProduktFamilieCodeType.ID;
	public static final TypeRecord TYPE_PRODUKT_FAMILIE = new TypeRecord(TYPE_ID_PRODUKT_FAMILIE, CodeTypeEnum.STRING.type());
	public static final CodeRecord CODE_ROH = new CodeRecord(ProduktFamilieCodeType.Roh.ID, TYPE_ID_PRODUKT_FAMILIE, 10.0, null, null, true);
	public static final CodeRecord CODE_H4 = new CodeRecord(ProduktFamilieCodeType.H4.ID, TYPE_ID_PRODUKT_FAMILIE, 110.0, null, null, true);

	public static final TextRecord TEXT_TYPE_FARBE = new TextRecord(TYPE_ID_FARBE, TextTable.LOCALE_DEFAULT, "Farbe");
	public static final TextRecord TEXT_CODE_ANTHRACITE = new TextRecord(CODE_ANTHRACITE.getId(), TextTable.LOCALE_DEFAULT, "Anthracite");
	public static final TextRecord TEXT_CODE_BLAU = new TextRecord(CODE_BLAU.getId(), TextTable.LOCALE_DEFAULT, "Blau");
	public static final TextRecord TEXT_CODE_SCHWARZ = new TextRecord(CODE_SCHWARZ.getId(), TextTable.LOCALE_DEFAULT, "Schwarz");

	public static final TextRecord TEXT_TYPE_PRODUKT_FAMILIE = new TextRecord(TYPE_ID_PRODUKT_FAMILIE, TextTable.LOCALE_DEFAULT, "P-Familie");
	public static final TextRecord TEXT_CODE_ROH = new TextRecord(CODE_ROH.getId(), TextTable.LOCALE_DEFAULT, "roh");
	public static final TextRecord TEXT_CODE_H4 = new TextRecord(CODE_H4.getId(), TextTable.LOCALE_DEFAULT, "H4");

	public static final ProduktRecord RI_20E = new ProduktRecord()
			.with(Produkt.PRODUKT.ID, "23")
			.with(Produkt.PRODUKT.BEZEICHNUNG, "RI 20E")
			.with(Produkt.PRODUKT.FAMILIE, CODE_ROH.getId())
			.with(Produkt.PRODUKT.BESTAND_MIN, 10)
			.with(Produkt.PRODUKT.ACTIVE, true);

	public static final PruefkoerperRecord PK_5689 = new PruefkoerperRecord()
			.with(Pruefkoerper.PRUEFKOERPER.ID, "51c10002-2920-40d7-b130-50019c60957f")
			.with(Pruefkoerper.PRUEFKOERPER.NR, 5689)
			.with(Pruefkoerper.PRUEFKOERPER.PRODUKT_ID, RI_20E.getId())
			.with(Pruefkoerper.PRUEFKOERPER.COLOR_ID, CODE_SCHWARZ.getId())
			.with(Pruefkoerper.PRUEFKOERPER.COLOR_DESCRIPTION, "")
			.with(Pruefkoerper.PRUEFKOERPER.FORM, "Platte")
			.with(Pruefkoerper.PRUEFKOERPER.SCHRANK, "1")
			.with(Pruefkoerper.PRUEFKOERPER.BOXE, "20")
			.with(Pruefkoerper.PRUEFKOERPER.LAENGE, 100)
			.with(Pruefkoerper.PRUEFKOERPER.BREITE, 100)
			.with(Pruefkoerper.PRUEFKOERPER.DICKE, 3)
			.with(Pruefkoerper.PRUEFKOERPER.BESTAND, 17)
			.with(Pruefkoerper.PRUEFKOERPER.ACTIVE, true);

	//----------------------------------------------------------------------------------//
	private int index;

	//----------------------------------------------------------------------------------//

	/**
	 * initialize data for all schemes
	 */
	@Override
	public void initialize(DSLContext context) {
		LOG.info("Insert minimal setup data");
		initializeCore(context);		
		initializeSv(context);
		initializeAwt(context);
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
	 * insert minimal sv data
	 */
	private void initializeSv(DSLContext ctx) {
		insert(ctx, TYPE_ETAGE);
		insert(ctx, CODE_UG);
		insert(ctx, CODE_EG);
		insert(ctx, CODE_OG_1);
		insert(ctx, CODE_OG_2);

		insert(ctx, TEXT_TYPE_ETAGE);
		insert(ctx, TEXT_TYPE_ETAGE_DE);

		insert(ctx, TEXT_CODE_UG);
		insert(ctx, TEXT_CODE_EG);
		insert(ctx, TEXT_CODE_OG_1);
		insert(ctx, TEXT_CODE_OG_2);

		insertFi(ctx);		
	}

	private void insertFi(DSLContext ctx) {
		insert(ctx, FI_700_1);
	}


	/**
	 * insert minimal awt data
	 */
	private void initializeAwt(DSLContext ctx) {
		insert(ctx, TYPE_FARBE);
		insert(ctx, CODE_ANTHRACITE);
		insert(ctx, CODE_BLAU);
		insert(ctx, CODE_SCHWARZ);

		insert(ctx, TYPE_PRODUKT_FAMILIE);
		insert(ctx, CODE_ROH);
		insert(ctx, CODE_H4);

		insert(ctx, TEXT_TYPE_FARBE);
		insert(ctx, TEXT_CODE_ANTHRACITE);
		insert(ctx, TEXT_CODE_BLAU);
		insert(ctx, TEXT_CODE_SCHWARZ);

		insert(ctx, TEXT_TYPE_PRODUKT_FAMILIE);
		insert(ctx, TEXT_CODE_ROH);
		insert(ctx, TEXT_CODE_H4);

		insertProduktFamilie(ctx);
		insertPruefkoerper(ctx);
	}

	private void insertProduktFamilie(DSLContext ctx) {
		insert(ctx, RI_20E);
	}

	private void insertPruefkoerper(DSLContext ctx) {
		insert(ctx, PK_5689);
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
		index = 0;

		Arrays.stream(Locale.getAvailableLocales())
		.forEach(locale -> { 
			CodeRecord code = new CodeRecord(locale.toLanguageTag(), TYPE_ID_LOCALE, null, null, null, true);
			insert(ctx, code);
			index += 10;

			Locale locDefault = Locale.forLanguageTag(TextTable.LOCALE_DEFAULT);
			TextRecord text = new TextRecord(code.getId(), locDefault.toLanguageTag(), locale.getDisplayName(locDefault));
			insert(ctx, text);
		});
	}

	private void insertTexts(DSLContext ctx) {
		insert(ctx, TEXT_TYPE_LOCALE);
		insert(ctx, TEXT_TYPE_FILE);
		insert(ctx, TEXT_TYPE_FILE_DE);
		insert(ctx, TEXT_TYPE_SEX);
		insert(ctx, TEXT_TYPE_SEX_DE);

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

		// TODO does not work on tomcat, find out why
		// load image file from src/main/resource folder into database
		//		byte [] content = loadResourceBytes("file/" + DOCUMENT_LOGO_NAME);
		//		DOCUMENT_ALICE_2.setContent(content);
		//		DOCUMENT_ALICE_2.setSize(BigDecimal.valueOf(content.length));
		//		insert(ctx, DOCUMENT_ALICE_2);
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

			// awt records
			if(record instanceof ProduktRecord) { ctx.executeInsert((ProduktRecord)record); return; }
			if(record instanceof PruefkoerperRecord) { ctx.executeInsert((PruefkoerperRecord)record); return; }

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
		byte [] content = null;

		try {
			ClassLoader classLoader = getClass().getClassLoader();
			String filePath = classLoader.getResource(fileName).getPath();
			LOG.info("Raw path {}", filePath);

			if(System.getProperty("os.name").contains("indow")) {
				if(filePath.startsWith("file:/") && filePath.charAt(7) == ':') {
					filePath = filePath.substring(6);
				}
			}

			LOG.info("Processed path {}", filePath);
			File file = new File(filePath);

			content = Files.readAllBytes(file.toPath());
			LOG.info("Resource {} successfully loaded", filePath);
		} 
		catch (Exception e) {
			LOG.error("Exception reading resource {}", fileName, e);
		}

		return content;
	}
}
