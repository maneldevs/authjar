package es.maneldevs.domain.exception;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super("Authentication required");
    }

}
