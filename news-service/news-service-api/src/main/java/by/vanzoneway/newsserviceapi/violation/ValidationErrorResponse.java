package by.vanzoneway.newsserviceapi.violation;



import java.util.List;

public record ValidationErrorResponse(List<Violation> violations)
{}
