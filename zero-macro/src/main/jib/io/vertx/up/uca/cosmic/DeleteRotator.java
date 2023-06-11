package io.vertx.up.uca.cosmic;

import io.modello.atom.app.KIntegrationApi;
import io.vertx.core.json.JsonObject;
import io.vertx.up.commune.config.Integration;
import org.apache.http.client.methods.HttpDelete;

public class DeleteRotator extends AbstractRotator {

    DeleteRotator(final Integration integration) {
        super(integration);
    }

    @Override
    public String request(final KIntegrationApi request, final JsonObject params) {
        final HttpDelete httpDelete = new HttpDelete(this.configPath(request, params));
        this.logger().info(INFO.HTTP_REQUEST, request.getPath(), request.getMethod(), params);
        return this.sendUrl(httpDelete, request.getHeaders());
    }
}
