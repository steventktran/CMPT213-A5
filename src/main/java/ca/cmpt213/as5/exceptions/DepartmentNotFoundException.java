package ca.cmpt213.as5.exceptions;

public class DepartmentNotFoundException extends Exception {
    public String message;
    public DepartmentNotFoundException() {
        super();
    }

    public DepartmentNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
