package org.ucommerce.shared.rest.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ucommerce.shared.kernel.exceptions.SerializedServiceException;
import org.ucommerce.shared.kernel.exceptions.ServiceException;

import java.net.http.HttpResponse;
import java.util.stream.Collectors;

/**
 * Marshals / unmarshalls the Service exception.
 */
public class ServiceExceptionResponseHandler implements ServiceResponseHandler {


    @Override
    public <T> T processResponse(HttpResponse<String> response, Class<T> type) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        SerializedServiceException exceptionData = gson.fromJson(response.body(), SerializedServiceException.class);
        String causeStackTrace = exceptionData.causeStackTrace()
                .stream()
                .map(e -> "From service: " + e)
                .collect(Collectors.joining("\n"));

        //FIXME: error should include the source service module name.

        throw new ServiceException(exceptionData.message() + "\n\n" + causeStackTrace,
                exceptionData.type(),
                exceptionData.dataMap(), null);

    }


}
