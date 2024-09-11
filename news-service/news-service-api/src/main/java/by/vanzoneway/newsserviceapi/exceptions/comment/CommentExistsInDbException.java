package by.vanzoneway.newsserviceapi.exceptions.comment;

public class CommentExistsInDbException extends RuntimeException {
    public CommentExistsInDbException(String message) {
        super(message);
    }
}
