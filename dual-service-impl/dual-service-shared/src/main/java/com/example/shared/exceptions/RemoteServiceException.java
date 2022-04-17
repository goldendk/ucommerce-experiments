package com.example.shared.exceptions;

import java.net.URI;
import java.text.MessageFormat;

public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException(int statusCode, URI uri) {
        super(MessageFormat.format("Received status [{0}] from URI [{1}]",
                statusCode, uri.toString()));
    }

    public RemoteServiceException(String s, Exception e) {
        super(s, e);
    }
}
