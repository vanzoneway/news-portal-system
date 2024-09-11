package by.vanzoneway.newsserviceapi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;

/**
 * DTO for {@link by.vanzoneway.newsserviceapi.model.News}
 */
public record NewsDto(Long id,
                      @PastOrPresent(message = "Time field cannot be in the future in news") LocalDateTime time,
                      @NotEmpty(groups = Marker.OnCreate.class, message = "Empty title field in news") String title,
                      @NotEmpty(groups = Marker.OnCreate.class, message = "Empty text field in news") String text)
        implements Serializable {}