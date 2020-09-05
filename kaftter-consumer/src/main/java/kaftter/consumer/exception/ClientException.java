package kaftter.consumer.exception;

import okhttp3.Response;

public class ClientException extends RuntimeException {
    public ClientException(final Response response) {
        super("Client side http exception, code=" + response.code());
    }
}
