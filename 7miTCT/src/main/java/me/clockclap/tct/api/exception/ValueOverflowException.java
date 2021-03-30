package me.clockclap.tct.api.exception;

public class ValueOverflowException extends Exception {

    private static final long serialVersionUID = 1L;

    public ValueOverflowException() {
        super();
    }

    public ValueOverflowException(String message){
        super(message);
    }

}
