package com.acme.application.server.text;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.core.tables.Text;
import com.acme.application.database.or.core.tables.records.TextRecord;
import com.acme.application.database.table.TextTable;
import com.acme.application.server.ServerSession;
import com.acme.application.server.common.AbstractBaseService;
import com.acme.application.shared.text.ITextService;
import com.acme.application.shared.text.TextTablePageData;
import com.acme.application.shared.text.TextTablePageData.TextTableRowData;

public class TextService extends AbstractBaseService<Text, TextRecord> implements ITextService {

	public static final String LOCALE_DEFAULT = TextTable.LOCALE_DEFAULT;
	private static final String ID_SEPARATOR = ":";

	@Override
	public Text getTable() {
		return Text.TEXT;
	}

	@Override
	public Field<String> getIdColumn() {
		return Text.TEXT.KEY;
	}

	@Override
	public Logger getLogger() {
		return LoggerFactory.getLogger(TextService.class);
	}

	@Override
	public boolean exists(String id) {
		String key = toKey(id);
		String locale = toLocale(id);

		try(Connection connection = getConnection()) {
			DSLContext context = getContext(connection); 
			return context
					.fetchExists(
							context
							.select()
							.from(getTable())
							.where(getTable().KEY.eq(key)
									.and(getTable().LOCALE.eq(locale))));
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute exists(). key: {}, locale: {}, exception: ", key, locale, e);
		}

		return false;
	}

	@Override
	public TextRecord get(String id) {
		String key = toKey(id);
		String locale = toLocale(id);
		TextRecord record = get(locale, key);

		// TODO add tests to check if this is already covered by the TextDbProviderService -> then, cleanup
		// refine fall-back mechanism: language-country -> language -> undefined
		if (record == null) {
			record = get(LOCALE_DEFAULT, key);
		}

		return record;
	}

	public List<TextRecord> getAll(String key) {
		try(Connection connection = getConnection()) {
			return getContext(connection)
					.selectFrom(getTable())
					.where(getTable().KEY.eq(key))
					.stream()
					.collect(Collectors.toList());
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute getAll(). key: {}, exception: ", key, e);
		}

		return new ArrayList<TextRecord>();
	}

	/**
	 * Stores/updates the provided code record.
	 */
	public void store(TextRecord record) {
		String id = TextService.toId(record.getLocale(), record.getKey());
		store(id, record);

	}

	private TextRecord get(String locale, String key) {
		try(Connection connection = getConnection()) {
			return getContext(connection)
					.selectFrom(getTable())
					.where(getTable().KEY.eq(key)
							.and(getTable().LOCALE.eq(locale)))
					.fetchOne();
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute get(). locale: {}, key: {}, exception: ", locale, key, e);
		}

		return null;
	}

	public static String toId(String locale, String key) {
		if (key == null) {
			key = "";
		}

		if (locale == null) {
			return String.format("%s%s%s", LOCALE_DEFAULT, ID_SEPARATOR, key);
		} else {
			return String.format("%s%s%s", locale, ID_SEPARATOR, key);
		}
	}

	public static String toKey(String id) {
		if (!idIsValid(id)) {
			return null;
		}

		return id.substring(id.indexOf(ID_SEPARATOR) + 1);
	}

	public static String toLocale(String id) {
		if (!idIsValid(id)) {
			return null;
		}

		return id.substring(0, id.indexOf(ID_SEPARATOR));
	}

	/**
	 * Returns the string representation for the provided locale.
	 * For a null value the method returns value {@link TextService#LOCALE_DEFAULT}.
	 */
	public static String convertLocale(Locale locale) {
		if(locale == null) {
			return LOCALE_DEFAULT;
		}

		return locale.toLanguageTag();
	}

	public static Locale convertLocale(String locale) {
		return Locale.forLanguageTag(locale);
	}

	/**
	 * Returns true iff the provided id is a correct representation of a locale-key
	 * pair.
	 */
	private static boolean idIsValid(String id) {
		if (id == null) {
			return false;
		}

		int separatorIndex = id.indexOf(ID_SEPARATOR);

		if (separatorIndex <= 0) {
			return false;
		}

		if (separatorIndex + 1 >= id.length()) {
			return false;
		}

		return true;
	}

	@Override
	public Map<String, String> getTexts(String key) {
		Map<String, String> texts = new HashMap<>();

		try(Connection connection = getConnection()) {
			getContext(connection)
			.selectFrom(getTable())
			.where(getTable().KEY.eq(key))
			.fetchStream().forEach(text -> {
				texts.put(text.getLocale(), text.getText());
			});
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute getTexts(). key: {}, exception: ", key, e);
		}

		return texts;
	}

	@Override
	public void addText(String key, String locale, String text) {
		store(toId(locale, key), new TextRecord(key, locale, text));
	}

	@Override
	public void deleteText(String key, String locale) {
		try(Connection connection = getConnection()) {
			getContext(connection)
			.deleteFrom(getTable())
			.where(getTable().KEY.eq(key).and(getTable().LOCALE.eq(locale)))
			.execute();
		}
		catch (SQLException e) {
			getLogger().error("Failed to execute getTexts(). key: {}, exception: ", key, e);
		}
	}

	@Override
	public void invalidateCache() {
		BEANS.get(TextDbProviderService.class).invalidateCache();
	}

	@Override
	public TextTablePageData getTextTableData(SearchFilter filter) {
		TextTablePageData pageData = new TextTablePageData();
		Locale locale = ServerSession.get().getLocale();

		getAll()
		.stream()
		.forEach(text -> {
			String key = text.getKey();
			String localeId = text.getLocale();
			String textId = text.getText();

			TextTableRowData row = pageData.addRow();
			row.setKey(key);
			row.setLocale(TEXTS.getWithFallback(locale, localeId, localeId));
			row.setText(TEXTS.getWithFallback(locale, textId, textId));
		});

		return pageData;
	}
}
