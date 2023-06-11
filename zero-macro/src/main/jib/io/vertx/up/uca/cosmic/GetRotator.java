package io.vertx.up.uca.cosmic;

import io.modello.atom.app.KIntegrationApi;
import io.vertx.core.json.JsonObject;
import io.modello.atom.app.KIntegration;
import org.apache.http.client.methods.HttpGet;

public class GetRotator extends AbstractRotator {

    GetRotator(final KIntegration integration) {
        super(integration);
    }

    @Override
    public String request(final KIntegrationApi request, final JsonObject params) {
        /*
         * Turn On mock workflow when integration is `debug`
         */
        final HttpGet httpGet = new HttpGet(this.configPath(request, params));
        this.logger().info(INFO.HTTP_REQUEST, request.getPath(), request.getMethod(), params);
        return this.sendUrl(httpGet, request.getHeaders());
    }
}
