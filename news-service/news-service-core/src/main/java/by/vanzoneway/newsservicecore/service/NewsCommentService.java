package by.vanzoneway.newsservicecore.service;

import by.vanzoneway.newsserviceapi.dto.CommentCreatedDto;
import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.dto.NewsCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface NewsCommentService {

    NewsCommentDto getPageNewsWithComments(@PathVariable Long newsId, Integer offset, Integer limit);
    CommentCreatedDto createComment(Long newsId, CommentDto commentDto);
    CommentDto updateCommentByNewsIdAndCommentsId(Long newsId, Long commentId, CommentDto commentDto);
    Page<CommentDto> searchCommentsByNewsId(Long newsId, String query, Integer offset, Integer limit);
}
