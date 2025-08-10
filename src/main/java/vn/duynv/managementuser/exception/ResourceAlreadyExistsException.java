package vn.duynv.managementuser.exception;

public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exist with %s: %s", resourceName, fieldName, fieldValue));
    }
}
