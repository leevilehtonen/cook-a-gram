package com.leevilehtonen.cookagram.config;

import org.hibernate.dialect.PostgreSQL94Dialect;

import java.sql.Types;

public class PostgreSQLDialectCustom extends PostgreSQL94Dialect {

    public PostgreSQLDialectCustom() {
        registerColumnType(Types.BLOB, "bytea");
        registerColumnType(Types.LONGVARBINARY, "bytea");
    }
    
}
