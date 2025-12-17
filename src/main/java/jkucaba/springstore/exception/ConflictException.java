package jkucaba.springstore.exception;

public class ConflictException extends StoreApiException{
    public ConflictException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "CONFLICT";
    }

    @Override
    public int getHttpStatus() {
        return 409;
    }
}
