package jkucaba.springstore.exception;

public class NotFoundException extends StoreApiException {

    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "NOT_FOUND";
    }

    @Override
    public int getHttpStatus() {
        return 404;
    }
}