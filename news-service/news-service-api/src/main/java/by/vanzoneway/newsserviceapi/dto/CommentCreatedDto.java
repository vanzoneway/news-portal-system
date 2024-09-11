package by.vanzoneway.newsserviceapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreatedDto {
    private String text;
    private String username;
}
