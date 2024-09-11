package by.vanzoneway.newsservicecore.service.impl;

import by.vanzoneway.newsserviceapi.dto.NewsDto;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsDoesntExistsInDbException;
import by.vanzoneway.newsserviceapi.exceptions.news.NewsExistsInDbException;
import by.vanzoneway.newsserviceapi.mapper.NewsMapper;
import by.vanzoneway.newsserviceapi.model.News;
import by.vanzoneway.newsservicecore.cache.Cache;
import by.vanzoneway.newsservicecore.repository.NewsRepository;
import by.vanzoneway.newsservicecore.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@ComponentScan(basePackages = {"by.vanzoneway.newsserviceapi"})
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final Cache<Long, NewsDto> newsDtoCache;


    @Override
    public Page<NewsDto> getPageNews(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return newsRepository.findAll(pageable).map(newsMapper::toDto);
    }

    @Override
    public Page<NewsDto> getPageNewsByTitle(String title, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return newsRepository.findAllByTitleStartingWith(title, pageable).map(newsMapper::toDto);
    }

    @Override
    public NewsDto getNewsById(Long id) {
        return newsDtoCache.get(id).orElseGet(() -> {
            NewsDto newsDtoFromDb = newsRepository.findById(id).map(newsMapper::toDto)
                    .orElseThrow(() -> new NewsDoesntExistsInDbException("There is no news with such id in database"));
            newsDtoCache.put(id, newsDtoFromDb);
            return newsDtoFromDb;
        });
    }


    @Override
    public String deleteNewsById(Long id) {

        if (!newsRepository.existsById(id))
            throw new NewsDoesntExistsInDbException("There is no news with such id in database");
        newsRepository.deleteById(id);
        newsDtoCache.remove(id);
        return String.format("Deleted news with id=%d successfully", id);

    }

    @Override
    public NewsDto saveNews(NewsDto newsDto) {

        if (newsRepository.existsByTitle(newsDto.title()))
                throw new NewsExistsInDbException("News with same title already exists in database");
        newsRepository.save(newsMapper.toEntity(newsDto));
        News toCacheNews = newsRepository.findByTitle(newsDto.title());
        NewsDto toCacheNewsDto = newsMapper.toDto(toCacheNews);
        newsDtoCache.put(toCacheNews.getId(), toCacheNewsDto);

        return toCacheNewsDto;
    }

    @Override
    public NewsDto updateNewsById(Long id, NewsDto newsDto) {

        News updatedNews = newsMapper.partialUpdate(newsDto, newsRepository.findById(id)
                .orElseThrow(() -> new NewsDoesntExistsInDbException("There is no news with such id in database")));
        newsRepository.save(updatedNews);

        return newsMapper.toDto(updatedNews);
    }

    @Override
    public Page<NewsDto> searchNews(String query, Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        return newsRepository.search(query, pageable).map(newsMapper::toDto);
    }


}
