package tech.itpark.projectdelivery.exception;

import java.io.IOException;

public class ServerResponseException extends RuntimeException {
    public ServerResponseException() {
    }

    public ServerResponseException(String message) {
        super(message);
    }

    public ServerResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerResponseException(Throwable cause) {
        super(cause);
    }

    public ServerResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ServerResponseException(IOException e) {

    }
}
