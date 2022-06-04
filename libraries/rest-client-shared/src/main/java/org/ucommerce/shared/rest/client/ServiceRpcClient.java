package org.ucommerce.shared.rest.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.Charsets;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

/**
 * Base class for all service REST clients, allowing the code-generating tools to delegate much of the boiler plate
 * functionality to this class.
 *
 * <p>
 * Assumes responsibility for transmitting the request and parsing the incoming response. In doing so it will check the
 * response for any errors thrown by the called service and re-throw the same exception on the client side if possible.
 */
public class ServiceRpcClient {
    private static String EXCEPTION_INDICATOR_HEADER = "ucommerce-exception";
    private static final Gson STRINGIFIER_GSON = new GsonBuilder().setPrettyPrinting().create();
    private HttpClient client;

    public void initialize() {
        client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

    }

    private boolean is2xx(int statusCode) {
        return 200 <= statusCode && 299 >= statusCode;
    }

    /**
     * Execute the http request synchronously.
     *
     * @param request
     */
    public <T> T execute(HttpRequest request, Class<T> responseType) {

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString(Charset.forName(StandardCharsets.UTF_8.name())));

            if (is2xx(response.statusCode())) {
                return new OKResponseHandler().processResponse(response, responseType);
            } else {
                HttpHeaders headers = response.headers();
                //check for server side exception.
                boolean hasException = false;
                Optional<String> exceptionHeader = headers.firstValue(EXCEPTION_INDICATOR_HEADER);
                if (exceptionHeader.isPresent() && Boolean.parseBoolean(exceptionHeader.get().toLowerCase())) {
                    hasException = true;
                }

                if (hasException) {
                    return new ServiceExceptionResponseHandler().processResponse(response, responseType);
                } else {
                    throw new RuntimeException("No valid response from service, serialized exception expected, not: " + response.body());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpRequest.Builder createRequestBuilder() {
        return HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header("Accept", "application/json");
    }

    public String stringify(Object object) {
        return STRINGIFIER_GSON.toJson(object);
    }
}
