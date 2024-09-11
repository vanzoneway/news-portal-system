package by.vanzoneway.newsserviceapplication.controller.inner;

import by.vanzoneway.newsserviceapi.dto.CommentCreatedDto;
import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.dto.Marker;
import by.vanzoneway.newsserviceapi.dto.NewsCommentDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/default")
@Validated
public interface NewsCommentOperations {

    @GetMapping
    ResponseEntity<NewsCommentDto> getPageNewsWithComments(
            @PathVariable Long newsId,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit);

    @PostMapping
    @Validated(Marker.OnCreate.class)
    ResponseEntity<CommentCreatedDto> createComment(@PathVariable Long newsId,
                                                        @RequestBody @Valid CommentDto commentDto);

    @PutMapping("/{commentsId}")
    ResponseEntity<CommentDto> updateCommentByNewsIdAndCommentsId(@PathVariable Long newsId,
                                                                  @PathVariable Long commentsId,
                                                                  @RequestBody @Valid CommentDto commentDto);


    @GetMapping("/search")
    Page<CommentDto> searchComments(@PathVariable Long newsId,
                                    @RequestParam String query,
                                    @RequestParam @Min(0) Integer offset,
                                    @RequestParam @Min(1) @Max(100) Integer limit);


}
