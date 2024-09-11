package by.vanzoneway.newsserviceapi.exceptions.news;

public class NewsExistsInDbException extends RuntimeException {
    public NewsExistsInDbException(String message) {
        super(message);
    }
}
