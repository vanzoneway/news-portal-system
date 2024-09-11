package by.vanzoneway.newsservicecore.service.impl;

import by.vanzoneway.newsserviceapi.dto.CommentCreatedDto;
import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.dto.NewsCommentDto;
import by.vanzoneway.newsserviceapi.exceptions.comment.CommentDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.mapper.CommentMapper;
import by.vanzoneway.newsserviceapi.mapper.NewsMapper;
import by.vanzoneway.newsserviceapi.model.Comment;
import by.vanzoneway.newsserviceapi.model.News;
import by.vanzoneway.newsservicecore.repository.CommentRepository;
import by.vanzoneway.newsservicecore.repository.NewsRepository;
import by.vanzoneway.newsservicecore.service.NewsCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@ComponentScan(basePackages = {"by.vanzoneway.newsserviceapi"})
@RequiredArgsConstructor
public class NewsCommentServiceImpl implements NewsCommentService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final NewsMapper newsMapper;
    private final CommentMapper commentMapper;

    @Override
    public NewsCommentDto getPageNewsWithComments(Long newsId, Integer offset, Integer limit) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsDoesntExistsInDbException("News with id " + newsId + " not found"));
        NewsCommentDto newsCommentDto = newsMapper.toNewsCommentDto(news);

        Pageable pageable = PageRequest.of(offset, limit);
        commentRepository.findAllByNewsId(newsId, pageable);
        List<Comment> comments = commentRepository.findAllByNewsId(newsId, pageable).getContent();
        newsCommentDto.setComments(commentMapper.toDtoList(comments).stream()
                .peek(commentDto -> commentDto.setNewsId(newsId))
                .toList());

        return newsCommentDto;
    }

    @Override
    public CommentCreatedDto createComment(Long newsId, CommentDto commentDto) {
        News updatedNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsDoesntExistsInDbException("News with id " + newsId + " not found"));

        Comment newComment = commentMapper.toEntity(commentDto);

        newComment.setNews(updatedNews);

        newComment.setTime(LocalDateTime.now());

        updatedNews.getComments().add(newComment);

        newsRepository.save(updatedNews);

        return commentMapper.toCommentCreatedDto(newComment);
    }

    @Override
    public CommentDto updateCommentByNewsIdAndCommentsId(Long newsId, Long commentId, CommentDto commentDto) {
        Comment updatedComment = commentMapper.partialUpdate(commentDto, commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentDoesntExistsInDbException(
                        String.format("There is no comment with id=%d in database", commentId))));

        News updatedNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsDoesntExistsInDbException("News with id " + newsId + " not found"));

        updatedComment.setNews(updatedNews);
        updatedNews.getComments().add(updatedComment);

        newsRepository.save(updatedNews);

        CommentDto updatedCommentDto = commentMapper.toDto(updatedComment);
        updatedCommentDto.setNewsId(updatedComment.getNews().getId());

        return updatedCommentDto;
    }

    @Override
    public Page<CommentDto> searchCommentsByNewsId(Long newsId, String query, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return commentRepository.searchCommentsByNewsId(newsId, query, pageable).map(commentMapper::toDto);

    }


}
