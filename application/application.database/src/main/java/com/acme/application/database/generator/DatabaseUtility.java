package com.acme.application.database.generator;

import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtility {

	public static List<String> getSchemaNames(Config config) {
		return config.getContext()
				.meta()
				.getSchemas()
				.stream()
				.map(schema -> { return schema.getName(); })
				.collect(Collectors.toList());
	}

	public static List<String> getTableNames(Config config) {
		return config.getContext()
				.meta()
				.getTables()
				.stream()
				.map(table -> { return table.getName(); })
				.collect(Collectors.toList());
	}
}
