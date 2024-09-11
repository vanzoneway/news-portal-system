package by.vanzoneway.newsserviceapplication.exception;


import by.vanzoneway.newsserviceapi.exceptions.ApiExceptionDto;
import by.vanzoneway.newsserviceapi.exceptions.comment.CommentDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.comment.CommentExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsExistsInDbException;
import by.vanzoneway.newsserviceapi.violation.ValidationErrorResponse;
import by.vanzoneway.newsserviceapi.violation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler({NewsDoesntExistsInDbException.class, CommentDoesntExistsInDbException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public ApiExceptionDto handleNewsDoesntExistsInDbException(Exception e) {
        return new ApiExceptionDto(HttpStatus.NO_CONTENT, e.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler({NewsExistsInDbException.class, CommentExistsInDbException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ApiExceptionDto handleNewsExistsInDbException(Exception e) {
        return new ApiExceptionDto(HttpStatus.CONFLICT, e.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

}
