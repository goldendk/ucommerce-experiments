package org.ucommerce.shared.kernel.exceptions;

import java.io.Serializable;
import java.util.Map;

/**
 * Base class for all service exceptions.
 */
public class ServiceException extends RuntimeException{

    private String type;
    private Map<String, ? extends Serializable> dataMap;

    public ServiceException(String message, String type, Map<String, ? extends Serializable> dataMap, Exception cause) {
        super(message, cause);
        this.type = type;
        this.dataMap = dataMap;
    }
}
