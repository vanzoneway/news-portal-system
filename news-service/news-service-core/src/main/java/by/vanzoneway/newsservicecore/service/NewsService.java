package by.vanzoneway.newsservicecore.service;

import by.vanzoneway.newsserviceapi.dto.NewsDto;
import org.springframework.data.domain.Page;


public interface NewsService {

    Page<NewsDto> getPageNews(Integer offset, Integer limit);


    Page<NewsDto> getPageNewsByTitle(String title, Integer offset, Integer limit);

    NewsDto getNewsById(Long id);

    String deleteNewsById(Long id);

    NewsDto saveNews(NewsDto newsDto);

    NewsDto updateNewsById(Long id, NewsDto newsDto);

    Page<NewsDto> searchNews(String query, Integer offset, Integer limit);

}
