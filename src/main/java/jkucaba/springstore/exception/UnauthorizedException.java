package jkucaba.springstore.exception;

public class UnauthorizedException extends StoreApiException {

    public UnauthorizedException(String message) {
        super(message);
    }

    @Override
    public String getCode() {
        return "UNAUTHORIZED";
    }

    @Override
    public int getHttpStatus() {
        return 401;
    }
}