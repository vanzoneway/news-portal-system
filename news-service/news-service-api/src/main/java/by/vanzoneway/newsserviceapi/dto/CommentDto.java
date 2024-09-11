package by.vanzoneway.newsserviceapi.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link by.vanzoneway.newsserviceapi.model.Comment}
 */

@Getter
@Setter
public class CommentDto implements Serializable {

    private Long id;

    @PastOrPresent(message = "Time cannot be future in time field of comment")
    private LocalDateTime time;

    @NotEmpty(groups = Marker.OnCreate.class, message = "Empty text field of comment")
    private String text;

    @NotEmpty(groups = Marker.OnCreate.class, message = "Empty username field of comment")
    private String username;

    private Long newsId;

}