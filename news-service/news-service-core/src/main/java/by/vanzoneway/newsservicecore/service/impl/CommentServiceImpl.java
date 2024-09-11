package by.vanzoneway.newsservicecore.service.impl;


import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.exceptions.comment.CommentDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.mapper.CommentMapper;
import by.vanzoneway.newsserviceapi.model.Comment;
import by.vanzoneway.newsservicecore.repository.CommentRepository;
import by.vanzoneway.newsservicecore.repository.NewsRepository;
import by.vanzoneway.newsservicecore.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = {"by.vanzoneway.newsserviceapi"})
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final NewsRepository newsRepository;

    @Override
    public CommentDto getCommentById(Long id) {
        return commentRepository.findById(id).map((x) -> {
                    Long newsId = x.getNews().getId();
                    CommentDto commentDto = commentMapper.toDto(x);
                    commentDto.setNewsId(newsId);
                    return commentDto;
                })
                .orElseThrow(() -> new NewsDoesntExistsInDbException("There is no news with such id in database"));
    }

    @Override
    public String deleteCommentById(Long commentId) {

        if (!commentRepository.existsById(commentId))
            throw new CommentDoesntExistsInDbException(String.format("There is no news with id=%d in database", commentId));
        commentRepository.deleteById(commentId);
        return String.format("Deleted comment with id=%d successfully", commentId);
    }



    public CommentDto updateCommentById(Long commentId, CommentDto commentDto) {
        Comment updatedComment = commentMapper.partialUpdate(commentDto, commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentDoesntExistsInDbException(
                        String.format("There is no comment with id=%d in database", commentId))));

        commentRepository.save(updatedComment);
        CommentDto updatedCommentDto = commentMapper.toDto(updatedComment);
        updatedCommentDto.setNewsId(updatedComment.getNews().getId());
        return updatedCommentDto;
    }


}
