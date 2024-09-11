package by.vanzoneway.securityserviceapi.violation;



import java.util.List;

public record ValidationErrorResponse(List<Violation> violations)
{}
