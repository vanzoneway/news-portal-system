package by.vanzoneway.newsserviceapplication.controller.inner.impl;

import by.vanzoneway.newsserviceapi.dto.NewsDto;
import by.vanzoneway.newsserviceapplication.controller.inner.NewsOperations;
import by.vanzoneway.newsservicecore.service.NewsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/v1/news")
@AllArgsConstructor
public class NewsController implements NewsOperations {

    private final NewsService newsService;

    @Override
    public NewsDto getNewsById(@PathVariable Long newsId) {
        return newsService.getNewsById(newsId);
    }

    @Override
    public Page<NewsDto> getPageNewsByTitle(@RequestParam("title") String title,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
                                            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit) {
        return newsService.getPageNewsByTitle(title, offset, limit);
    }

    @Override
    public Page<NewsDto> search(@RequestParam("query") String query,
                                @RequestParam(defaultValue = "0") @Min(0) Integer offset,
                                @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit) {
        return newsService.searchNews(query, offset, limit);
    }

    @Override
    public Page<NewsDto> getPageNews(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit) {

        return newsService.getPageNews(offset, limit);
    }

    @Override
    public ResponseEntity<String> deleteNewsById(@PathVariable Long newsId) {
        return ResponseEntity.ok(newsService.deleteNewsById(newsId));
    }

    @Override
    public ResponseEntity<NewsDto> createNews(@RequestBody @Valid NewsDto newsDto) {
        return ResponseEntity.ok(newsService.saveNews(newsDto));
    }

    @Override
    public ResponseEntity<NewsDto> updateNewsById(@PathVariable Long newsId,@RequestBody NewsDto newsDto) {
        return ResponseEntity.ok(newsService.updateNewsById(newsId, newsDto));
    }

}
