package ca.cmpt213.as5.exceptions;

public class OfferingNotFoundException extends Exception{
    public String message;
    public OfferingNotFoundException() {
        super();
    }

    public OfferingNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

