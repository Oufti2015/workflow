package workflow.exceptions;

public class XMLFileException extends Exception {
    public XMLFileException(String message) {
        super(message);
    }

    public XMLFileException(String message, Exception e) {
        super(message, e);
    }
}
