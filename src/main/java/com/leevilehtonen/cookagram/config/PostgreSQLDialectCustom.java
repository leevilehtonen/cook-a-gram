package com.leevilehtonen.cookagram.config;

import org.hibernate.dialect.PostgreSQL82Dialect;

import java.sql.Types;

public class PostgreSQLDialectCustom extends PostgreSQL82Dialect {

    public PostgreSQLDialectCustom() {
        registerColumnType(Types.BLOB, "bytea");
        registerColumnType(Types.LONGVARBINARY, "bytea");
    }

}
