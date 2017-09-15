package com.acme.application.database.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.testing.platform.runner.PlatformTestRunner;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.application.database.or.app.tables.Code;
import com.acme.application.database.or.app.tables.Role;
import com.acme.application.database.or.app.tables.Text;
import com.acme.application.database.or.app.tables.Type;
import com.acme.application.database.or.app.tables.records.CodeRecord;
import com.acme.application.database.or.app.tables.records.RoleRecord;
import com.acme.application.database.or.app.tables.records.TextRecord;
import com.acme.application.database.or.app.tables.records.TypeRecord;
import com.acme.application.database.table.CodeTypeEnum;
import com.acme.application.database.table.RoleTable;
import com.acme.application.database.table.TableDataInitializer;
import com.acme.application.database.table.TextTable;

@RunWith(PlatformTestRunner.class)
@RunWithSubject("admin")
public class SetupTest {


	private static final String DB_DRIVER = GeneratorApplication.DB_DRIVER;
	private static final String DB_MAPPING_NAME = GeneratorApplication.DB_MAPPING_NAME;
	private static final SQLDialect DB_DIALECT = GeneratorApplication.DB_DIALECT;

	private static Config config;

	@BeforeClass
	public static void setup() throws Exception {
		Class.forName(DB_DRIVER);
		Connection connection = DriverManager.getConnection(DB_MAPPING_NAME);
		config = new Config(connection, DB_DIALECT);

		// TODO check why this does not work
		//		SchemaInitializer schema = BEANS.get(SchemaInitializer.class);
		//		schema.setConfig(config);
		//		schema.initialize();

		GeneratorApplication.setupDatabase(config);

		IDataInitializer initializer = BEANS.get(IDataInitializer.class);
		initializer.setConfig(config);
		initializer.initialize();		
	}

	@Test
	public void localeCodeTypeTest() {
		Type tt = Type.TYPE;
		TypeRecord type = getContext()
				.selectFrom(tt)
				.where(tt.ID.eq(TableDataInitializer.TYPE_ID_LOCALE))
				.fetchOne();

		Assert.assertNotNull("Type locale not found", type);
		Assert.assertEquals("Unexpected type", type.getCodeType(),  CodeTypeEnum.LOCALE.type());

		Code ct = Code.CODE;
		getContext()
		.selectFrom(ct)
		.where(ct.TYPE_ID.eq(type.getId()))
		.fetchStream()
		.forEach(code -> {
			Locale locale = Locale.forLanguageTag(code.getId());
			Assert.assertNotNull("Locale for code '" + code.getId() + "' not found", locale);

			Text xt = Text.TEXT;
			String localeDefault = TextTable.LOCALE_DEFAULT;
			TextRecord text = getContext()
					.selectFrom(xt)
					.where(xt.KEY.eq(code.getId()).and(xt.LOCALE.eq(localeDefault)))
					.fetchOne();

			Assert.assertNotNull("Locale name for code '" + code.getId() + "' and locale 'und' not found", text);
			String expectedText = locale.getDisplayName(Locale.forLanguageTag(localeDefault));
			
			if(text.getKey().equals("nn-NO")) {
				System.out.println("special case. expected: " + expectedText + ", actual text:" + text.getText());
			}
			else {
				Assert.assertEquals("Unexpected name for code '\" + code.getId() + \"' and locale 'und'",  expectedText, text.getText());
			}
		});


	}

	@Test
	public void sexCodeTypeTest() {
		Type tt = Type.TYPE;
		TypeRecord type = getContext()
				.selectFrom(tt)
				.where(tt.ID.eq(TableDataInitializer.TYPE_ID_SEX))
				.fetchOne();

		Assert.assertNotNull("Type sex not found", type);
		Assert.assertEquals("Unexpected type", type.getCodeType(),  CodeTypeEnum.STRING.type());

		Code ct = Code.CODE;
		List<CodeRecord> genders = getContext()
				.selectFrom(ct)
				.where(ct.TYPE_ID.eq(type.getId()))
				.fetchStream()
				.collect(Collectors.toList());

		Assert.assertEquals("Unexpected number of genders", 1, genders.size());

		CodeRecord undefined = getContext()
				.selectFrom(ct)
				.where(ct.ID.eq(TableDataInitializer.CODE_UNDEFINED.getId()))
				.fetchOne();

		Assert.assertNotNull("Code 'U' not found", undefined);
		Assert.assertTrue("Code 'U' is inactive", undefined.getActive());

		Text xt = Text.TEXT;
		TextRecord undefinedText = getContext()
				.selectFrom(xt)
				.where(xt.KEY.eq(undefined.getId()).and(xt.LOCALE.eq(TextTable.LOCALE_DEFAULT)))
				.fetchOne();

		Assert.assertNotNull("Text for code 'U' not found", undefinedText);
		Assert.assertEquals("Unexpected text for code 'U'", TableDataInitializer.TEXT_UNDEFINED.getText(), undefinedText.getText());
	}

	@Test
	public void rootRoleTest() {

		String root = TableDataInitializer.ROLE_ROOT.getName();
		RoleRecord rootResult = getContext()
				.selectFrom(Role.ROLE)
				.where(Role.ROLE.NAME.eq(RoleTable.ROOT))
				.fetchAny();

		Assert.assertNotNull("root role not found", rootResult);
		Assert.assertEquals("unexpected root role id", root, rootResult.getName());
	}

	@Test
	public void inactiveRoleTest() {

		String guest = TableDataInitializer.ROLE_GUEST.getName();
		RoleRecord rootResult = getContext()
				.selectFrom(Role.ROLE)
				.where(Role.ROLE.ACTIVE.isFalse())
				.fetchAny();

		Assert.assertNotNull("guest role not found", rootResult);
		Assert.assertEquals("unexpected guest role id", guest, rootResult.getName());
	}


	@Test
	public void roleCountTest() {

		int count = getContext()
				.selectCount()
				.from(Role.ROLE)
				.fetchOne(0, int.class);

		// root, user, guest
		Assert.assertEquals("unexpected number of roles",  3, count);
	}

	@AfterClass
	public static void teardown() {
		//		GeneratorApplication.teardownDatabase(config);
	}

	protected DSLContext getContext() {
		return config.getContext();
	}
}
