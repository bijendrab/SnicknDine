package com.config.dialect;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;
public class CustomMySqlDialect extends MySQL5Dialect {
    public CustomMySqlDialect() {
        super();
        registerFunction("group_concat"
                , new StandardSQLFunction("group_concat",new StringType()));
    }
}
