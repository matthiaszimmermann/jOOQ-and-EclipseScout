package com.acme.application.server.text;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.application.database.or.app.tables.Text;
import com.acme.application.database.or.app.tables.records.TextRecord;
import com.acme.application.database.table.TextTable;
import com.acme.application.server.common.BaseService;
import com.acme.application.shared.text.ITextService;
import com.acme.application.shared.text.TextTablePageData;
import com.acme.application.shared.text.TextTablePageData.TextTableRowData;

public class TextService extends BaseService implements ITextService {

	private static final Logger LOG = LoggerFactory.getLogger(TextService.class);

	public static final String LOCALE_DEFAULT = TextTable.LOCALE_DEFAULT;
	private static final String ID_SEPARATOR = ":";

	public boolean exists(String id) {
		String key = toKey(id);
		String locale = toLocale(id);
		Text tt = Text.TEXT;
		DSLContext ctx = getContext();

		return ctx.fetchExists(ctx.select().from(tt).where(tt.KEY.eq(key).and(tt.LOCALE.eq(locale))));
	}

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

	public List<TextRecord> getAll() {
		return getContext().selectFrom(Text.TEXT).fetchStream().collect(Collectors.toList());
	}

	public void store(TextRecord text) {
		LOG.info("Persist text\n{}", text);
		
		String key = text.getKey();
		String locale = text.getLocale();

		if (exists(toId(locale, key))) {
			getContext().executeUpdate(text);
		} else {
			getContext().executeInsert(text);
		}
	}

	public List<TextRecord> getAll(String key) {
		Text text = Text.TEXT;

		return getContext().selectFrom(text).where(text.KEY.eq(key)).stream().collect(Collectors.toList());
	}

	private TextRecord get(String locale, String key) {
		Text text = Text.TEXT;

		return getContext().selectFrom(text).where(text.KEY.eq(key).and(text.LOCALE.eq(locale))).fetchOne();
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
		Text tt = Text.TEXT;

		getContext().selectFrom(tt).where(tt.KEY.eq(key)).fetchStream().forEach(text -> {
			texts.put(text.getLocale(), text.getText());
		});

		return texts;
	}

	@Override
	public void addText(String key, String locale, String text) {
		store(new TextRecord(key, locale, text));
	}

	@Override
	public void deleteText(String key, String locale) {
		Text tt = Text.TEXT;

		getContext().deleteFrom(tt).where(tt.KEY.eq(key).and(tt.LOCALE.eq(locale))).execute();
	}

	@Override
	public void invalidateCache() {
		BEANS.get(TextDbProviderService.class).invalidateCache();
	}

	@Override
	public TextTablePageData getTextTableData(SearchFilter filter) {
		TextTablePageData pageData = new TextTablePageData();

		getAll()
		.stream()
		.forEach(text -> {
			String key = text.getKey();

			TextTableRowData row = pageData.addRow();
			row.setKey(key);
			row.setLocale(text.getLocale());
			row.setText(text.getText());
		});

		return pageData;
	}
}
