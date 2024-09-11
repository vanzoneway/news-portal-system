package by.vanzoneway.newsserviceapi.mapper;

import by.vanzoneway.newsserviceapi.dto.NewsCommentDto;
import by.vanzoneway.newsserviceapi.dto.NewsDto;
import by.vanzoneway.newsserviceapi.model.News;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
    componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {

    News toEntity(NewsDto news);

    NewsDto toDto(News news);

    NewsCommentDto toNewsCommentDto(News news);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    News partialUpdate(NewsDto newsDto, @MappingTarget News news);
}
