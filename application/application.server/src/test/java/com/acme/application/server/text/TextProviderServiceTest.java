package com.acme.application.server.text;

import java.util.Locale;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.testing.platform.runner.RunWithSubject;
import org.eclipse.scout.rt.testing.server.runner.RunWithServerSession;
import org.eclipse.scout.rt.testing.server.runner.ServerTestRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.acme.application.database.table.RoleTable;
import com.acme.application.database.table.TableDataInitializer;
import com.acme.application.database.table.TableUtility;
import com.acme.application.database.table.TextTable;
import com.acme.application.server.ServerSession;
import com.acme.application.server.helloworld.HelloWorldServiceTest;

@RunWith(ServerTestRunner.class)
@RunWithSubject(HelloWorldServiceTest.SUBJECT_NAME)
@RunWithServerSession(ServerSession.class)
public class TextProviderServiceTest {

	@Test
	public void testPropertyText() {
		String key = "AuthorizationFailed";
		String expectedText = "Permission denied";
		String text = TEXTS.get(key);
		
		Assert.assertNotNull("text for key 'AuthorizationFailed' not found", text);
		Assert.assertEquals("unexpected text for key 'AuthorizationFailed'", expectedText, text);
	}
	
	@Test
	public void testExistingDBText() {
		String key = RoleTable.USER;
		String expectedText = TableDataInitializer.TEXT_USER.getText();
		String text = TEXTS.get(key);
		
		Assert.assertNotNull("text for key 'user' not found", text);
		Assert.assertEquals("unexpected text for key 'user'", expectedText, text);
		
		Locale localeGerman = Locale.GERMAN;
		String expectedTextGerman = TableDataInitializer.TEXT_USER_DE.getText();
		String textGerman = TEXTS.get(localeGerman, key);
		
		Assert.assertNotNull("german text for key 'user' not found", text);
		Assert.assertEquals("unexpected german text for key 'user'", expectedTextGerman, textGerman);
	}
	
	@Test
	public void testMissingDBText() {
		String key = TableUtility.createId();
		String expectedText = TableUtility.createValue();
		String expectedMissingText = "undefined text " + key;
		String locale = TextTable.LOCALE_DEFAULT;
		
		String text = TEXTS.get(key);
		Assert.assertNotEquals("did not expect text for new key", expectedMissingText, text);
		
		BEANS.get(TextService.class).addText(key, locale, expectedText);
		BEANS.get(TextService.class).invalidateCache();
		
		text = TEXTS.get(key);
		Assert.assertNotNull("new text not found", text);
		Assert.assertEquals("unexpected translation for new text", expectedText, text);
	}
}
