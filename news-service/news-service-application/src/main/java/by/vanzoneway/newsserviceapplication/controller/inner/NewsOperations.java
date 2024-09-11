package by.vanzoneway.newsserviceapplication.controller.inner;

import by.vanzoneway.newsserviceapi.dto.Marker;
import by.vanzoneway.newsserviceapi.dto.NewsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/default")
@Validated
public interface NewsOperations {

    @GetMapping("/{newsId}")
    NewsDto getNewsById(@PathVariable Long newsId);

    @GetMapping("/title/starts")
    Page<NewsDto> getPageNewsByTitle(@RequestParam("title") String title,
                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset,
                                     @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit);

    @GetMapping("/search")
    Page<NewsDto> search(@RequestParam("query") String query,
                         @RequestParam(defaultValue = "0") @Min(0) Integer offset,
                         @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit);

    @GetMapping
    Page<NewsDto> getPageNews(
            @RequestParam(defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer limit);

    @DeleteMapping("/{newsId}")
    ResponseEntity<String> deleteNewsById(@PathVariable Long newsId);

    @PostMapping
    @Validated(Marker.OnCreate.class)
    ResponseEntity<NewsDto> createNews(@RequestBody @Valid NewsDto newsDto);

    @PutMapping("/{newsId}")
    ResponseEntity<NewsDto> updateNewsById(@PathVariable Long newsId, @RequestBody @Valid NewsDto newsDto);


}
