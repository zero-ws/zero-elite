package io.zerows.core.metadata.uca.stable;

import io.horizon.eon.VValue;
import io.horizon.exception.ProgramException;
import io.horizon.uca.log.Annal;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.fn.Fn;
import io.vertx.up.util.Ut;
import io.zerows.core.metadata.exception.DaemonJElementException;

/**
 * @author lang
 */
public abstract class AbstractInsurer implements Insurer {

    @Override
    public void flumen(final JsonArray array,
                       final JsonObject elementRule)
        throws ProgramException {
        Fn.bugAt(() -> {
            // 1. Verify the element must be json object for each
            final int size = array.size();
            for (int idx = VValue.IDX; idx < size; idx++) {
                final Object value = array.getValue(idx);
                // 2. Call check method to confirm JsonObject
                Fn.outBug(!Ut.isJObject(value), this.getLogger(),
                    DaemonJElementException.class,
                    this.getClass(), idx, value);

                final JsonObject item = (JsonObject) value;
                // 3. Apply the rule to each object.
                this.flumen(item, elementRule);
            }
        }, array, elementRule);
    }

    protected Annal getLogger() {
        return Annal.get(this.getClass());
    }
}
