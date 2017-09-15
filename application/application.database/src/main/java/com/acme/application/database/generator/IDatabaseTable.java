package com.acme.application.database.generator;

import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.jooq.DataType;
import org.jooq.SQLDialect;
import org.jooq.impl.SQLDataType;
import org.jooq.types.UInteger;
import org.slf4j.Logger;

@ApplicationScoped
public interface IDatabaseTable {

	public static final String ID = "ID";
	public static final DataType<String> TYPE_ID = SQLDataType.VARCHAR.length(36).nullable(false);
	public static final DataType<String> TYPE_ID_OPTIONAL = SQLDataType.VARCHAR.length(36).nullable(true);
	
	public static final String ACTIVE = "ACTIVE";
	public static final DataType<Boolean> TYPE_BOOLEAN = SQLDataType.BOOLEAN;
	
	public static final DataType<String> TYPE_CODE = SQLDataType.VARCHAR.length(36).nullable(true);
	
	public static final DataType<String> TYPE_STRING_XXS = SQLDataType.VARCHAR.length(1);
	public static final DataType<String> TYPE_STRING_XS = SQLDataType.VARCHAR.length(16);
	public static final DataType<String> TYPE_STRING_S = SQLDataType.VARCHAR.length(64);
	public static final DataType<String> TYPE_STRING_M = SQLDataType.VARCHAR.length(128);
	public static final DataType<String> TYPE_STRING_L = SQLDataType.VARCHAR.length(512);
	public static final DataType<String> TYPE_STRING_XL = SQLDataType.VARCHAR.length(1024);
	
	public static final DataType<byte[]> TYPE_BLOB = SQLDataType.BLOB;
	public static final DataType<UInteger> TYPE_SIZE = SQLDataType.INTEGERUNSIGNED;
	
	public static String DEFAULT_TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final DataType<String> TYPE_TIMESTAMP = SQLDataType.VARCHAR.length(DEFAULT_TIMESTAMP_PATTERN.length());
	
	public static final SQLDialect SQL_DIALECT = GeneratorApplication.DB_DIALECT;

	
	Config getConfig();
	void setConfig(Config config);
	
	String getTableName();
	String getCreateSQL();
	
	void create();
	void drop();
	
	Logger getLogger();
}
