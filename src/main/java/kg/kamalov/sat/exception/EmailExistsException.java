package kg.kamalov.sat.exception;

public class EmailExistsException extends RuntimeException{
    private String message;

    public EmailExistsException(String message){
        super(message);
    }
}
