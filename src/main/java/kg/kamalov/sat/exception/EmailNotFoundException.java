package kg.kamalov.sat.exception;

public class EmailNotFoundException extends RuntimeException{
    private String message;

    public EmailNotFoundException(String msg){
        super(msg);
    }
}
