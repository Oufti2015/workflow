package workflow.exceptions;

public class ExecutionException extends Exception {
    public ExecutionException(String message) {
        super(message);
    }

    public ExecutionException(String message, Exception e) {
        super(message, e);
    }
}
