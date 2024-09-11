package by.vanzoneway.newsserviceapi.exceptions.comment;

public class CommentDoesntExistsInDbException extends RuntimeException {
    public CommentDoesntExistsInDbException(String message) {
        super(message);
    }
}
