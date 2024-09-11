package by.vanzoneway.securityserviceapi.exceptions.user;

public class UserInfoAlreadyExistsException extends RuntimeException {
    public UserInfoAlreadyExistsException(String message) {
        super(message);
    }
}
