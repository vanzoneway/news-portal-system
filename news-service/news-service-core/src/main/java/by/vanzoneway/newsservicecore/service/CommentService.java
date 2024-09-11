package by.vanzoneway.newsservicecore.service;

import by.vanzoneway.newsserviceapi.dto.CommentDto;


public interface CommentService {



    CommentDto getCommentById(Long commentId);

    String deleteCommentById(Long commentId);






}
