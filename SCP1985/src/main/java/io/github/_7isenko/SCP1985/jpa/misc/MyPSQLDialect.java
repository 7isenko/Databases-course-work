package io.github._7isenko.SCP1985.jpa.misc;

import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;

import java.sql.Types;

/**
 * @author 7isenko
 */
public class MyPSQLDialect extends PostgreSQL9Dialect {
    public MyPSQLDialect() {
        super();
        registerFunction("go_on_excursion", new StandardSQLFunction("go_on_excursion"));
    }
}
