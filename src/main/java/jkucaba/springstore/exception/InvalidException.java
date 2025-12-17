package jkucaba.springstore.exception;

public class InvalidException extends StoreApiException{
    public InvalidException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "INVALID";
    }

    @Override
    public int getHttpStatus() {
        return 422;
    }
}
