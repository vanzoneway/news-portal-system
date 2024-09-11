package by.vanzoneway.newsserviceapi.mapper;

import by.vanzoneway.newsserviceapi.dto.CommentCreatedDto;
import by.vanzoneway.newsserviceapi.dto.CommentDto;
import by.vanzoneway.newsserviceapi.model.Comment;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    Comment toEntity(CommentDto comment);

    CommentDto toDto(Comment comment);

    CommentCreatedDto toCommentCreatedDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(CommentDto productDto, @MappingTarget Comment product);

}
