package org.ucommerce.shared.rest.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpResponse;

/**
 * Handles all 2xx responses from ucommerce services.
 */
public class OKResponseHandler implements ServiceResponseHandler {
    @Override
    public <T> T processResponse(HttpResponse<String> response, Class<T> type) {

        Gson gson = new GsonBuilder().serializeNulls().create();

        if (Void.class.equals(type)) {
            return null;
        }

        return gson.fromJson(response.body(), type);
    }
}
