package io.vertx.up.atom;

import io.vertx.core.json.JsonObject;
import io.vertx.up.util.Ut;
import io.zerows.core.domain.atom.commune.mapping.Mirror;
import io.zerows.core.feature.unit.testing.ZeroBase;
import org.junit.Test;

public class MirrorTc extends ZeroBase {

    @Test
    public void testFromRule() {
        final JsonObject input = Ut.ioJObject(this.ioString("user.json"));
        final JsonObject user = Mirror.create(this.getClass())
            .mount("user").connect(input).from().result();
        System.out.println(user);
    }

    @Test
    public void testToRule() {
        final JsonObject input = Ut.ioJObject(this.ioString("to.json"));
        final JsonObject user = Mirror.create(this.getClass())
            .mount("user").connect(input).to().result();
        System.out.println(user);
    }

    @Test
    public void testFromPojo() {
        final JsonObject input = Ut.ioJObject(this.ioString("user.json"));
        final Mirror mirror = Mirror.create(this.getClass())
            .mount("user").connect(input).from();
        final JsonObject user = mirror.result();
        final User entity = mirror.get();
        System.out.println(user);
        System.out.println(entity);
    }

    @Test
    public void testApply() {
        final JsonObject input = Ut.ioJObject(this.ioString("user.json"));
        final User user = new User();
        user.setAge(13);
        final Mirror mirror = Mirror.create(this.getClass())
            .mount("user").connect(input).apply((old) -> old.substring(1, 2).toLowerCase() +
                old.substring(2));
        System.out.println(mirror.json(user, false));
    }
}
