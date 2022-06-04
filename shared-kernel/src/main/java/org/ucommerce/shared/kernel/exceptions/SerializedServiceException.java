package org.ucommerce.shared.kernel.exceptions;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Serialized version of a ServiceException.
 * @param exceptionClass the fully qualified name of the exception class.
 * @param message         the message of the exception.
 * @param type            the type of error caught.
 * @param dataMap         contains information regarding the context the exception occurred in.
 * @param causeStackTrace stack trace from the causing exception if any.
 */
public record SerializedServiceException(
        String exceptionClass,
        String message,
        String type,
        Map<String, ? extends Serializable> dataMap,
        List<String> causeStackTrace
) {
}
