package ca.cmpt213.as5.exceptions;

public class CourseNotFoundException extends Exception{
    public String message;
    public CourseNotFoundException() {
        super();
    }

    public CourseNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
