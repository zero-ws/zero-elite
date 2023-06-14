package io.vertx.up.atom.extension;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.horizon.atom.datamation.KDictConfig;
import io.horizon.atom.datamation.KDictUse;
import io.vertx.core.json.JsonObject;
import io.vertx.up.eon.KName;
import io.vertx.up.util.Ut;
import io.zerows.jackson.databind.JsonObjectDeserializer;
import io.zerows.jackson.databind.JsonObjectSerializer;

import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="http://www.origin-x.cn">Lang</a>
 */
public class KTransform implements Serializable {
    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject fabric;

    private KTree tree;

    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject initial;

    @JsonSerialize(using = JsonObjectSerializer.class)
    @JsonDeserialize(using = JsonObjectDeserializer.class)
    private JsonObject mapping;

    public JsonObject getFabric() {
        return this.fabric;
    }

    public void setFabric(final JsonObject fabric) {
        this.fabric = fabric;
    }

    public KTree getTree() {
        return this.tree;
    }

    public void setTree(final KTree tree) {
        this.tree = tree;
    }

    public JsonObject getInitial() {
        return Ut.valueJObject(this.initial);
    }

    public void setInitial(final JsonObject initial) {
        this.initial = initial;
    }

    public JsonObject getMapping() {
        return Ut.valueJObject(this.mapping);
    }

    public void setMapping(final JsonObject mapping) {
        this.mapping = mapping;
    }

    public ConcurrentMap<String, KDictUse> epsilon() {
        final JsonObject dictionary = Ut.valueJObject(this.fabric);
        return KDictUse.epsilon(Ut.valueJObject(dictionary.getJsonObject(KName.EPSILON)));
    }

    public KDictConfig source() {
        final JsonObject dictionary = Ut.valueJObject(this.fabric);
        return new KDictConfig(Ut.valueJArray(dictionary.getJsonArray(KName.SOURCE)));
    }
}
