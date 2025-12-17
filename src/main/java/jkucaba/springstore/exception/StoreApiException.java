package jkucaba.springstore.exception;

public abstract class StoreApiException extends RuntimeException{

    protected StoreApiException(String message) {
        super(message);
    }

    public abstract String getCode();
    public abstract int getHttpStatus();
}
