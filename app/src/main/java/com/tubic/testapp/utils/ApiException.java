package com.tubic.testapp.utils;

import java.io.IOException;

/**
 * Created by Uran on 1/16/2017.
 */

public class ApiException extends RuntimeException {
    public static ApiException httpError(String url, Integer code, String errorMessage) {
        return new ApiException(url, Kind.HTTP, code, errorMessage);
    }

    public static ApiException networkError(IOException exception) {
        return new ApiException(null, Kind.NETWORK, null, exception.getMessage());
    }

    public static ApiException unexpectedError(Throwable exception) {
        return new ApiException(null, Kind.UNEXPECTED, null, exception.getMessage());
    }

    /**
     * Identifies the event kind which triggered a {@link ApiException}.
     */
    public enum Kind {
        /**
         * An {@link IOException} occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }

    private final String url;
    private final Kind kind;
    private final Integer code;


    public ApiException(String url, Kind kind, Integer code, String errorMessage) {
        super(errorMessage);
        this.url = url;
        this.kind = kind;
        this.code = code;

    }

    /**
     * The request URL which produced the error.
     */
    public String getUrl() {
        return url;
    }


    /**
     * The event kind which triggered this error.
     */
    public Kind getKind() {
        return kind;
    }

    public Integer getCode() {
        return code;
    }
}
