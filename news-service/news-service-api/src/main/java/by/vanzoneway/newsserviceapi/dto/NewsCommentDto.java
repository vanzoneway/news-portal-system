package by.vanzoneway.newsserviceapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class NewsCommentDto implements Serializable {

    private Long id;
    private LocalDateTime time;
    private String title;
    private String text;
    private List<CommentDto> comments;
}