package by.vanzoneway.newsserviceapi.exceptions.news;

public class NewsDoesntExistsInDbException extends RuntimeException {
    public NewsDoesntExistsInDbException(String message) {
        super(message);
    }
}
