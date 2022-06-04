package org.ucommerce.shared.rest.client;

import java.net.http.HttpResponse;

public interface ServiceResponseHandler {

    public <T> T processResponse(HttpResponse<String> response, Class<T> type);
}
