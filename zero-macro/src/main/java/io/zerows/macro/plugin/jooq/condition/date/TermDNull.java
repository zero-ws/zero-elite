package io.zerows.macro.plugin.jooq.condition.date;

import io.zerows.macro.plugin.jooq.condition.Term;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

@SuppressWarnings("all")
public class TermDNull implements Term {
    @Override
    public Condition where(final Field field, final String fieldName, final Object value) {
        return DSL.field(fieldName).isNull();
    }
}
