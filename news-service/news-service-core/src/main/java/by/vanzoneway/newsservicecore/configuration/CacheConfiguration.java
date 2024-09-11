package by.vanzoneway.newsservicecore.configuration;

import by.vanzoneway.newsserviceapi.dto.NewsDto;
import by.vanzoneway.newsservicecore.cache.Cache;
import by.vanzoneway.newsservicecore.cache.LfuCache;
import by.vanzoneway.newsservicecore.cache.LruCache;
import by.vanzoneway.newsservicecore.cache.concurrent.ConcurrentCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class CacheConfiguration {

    @Value("${spring.cache.algorithm:LFU}")
    private String algorithm;

    @Value("${spring.cache.capacity:5}")
    private int capacity;

    @Bean
    public Cache<Long, NewsDto> newsDtoCache() {
        if ("LRU".equalsIgnoreCase(algorithm)) {
            return new ConcurrentCache<>(new LruCache<>(capacity));
        } else {
            return new ConcurrentCache<>(new LfuCache<>(capacity));
        }

    }


    @Bean
    public Cache<Long, Page<NewsDto>> pageNewsDtoCache() {
        if ("LRU".equalsIgnoreCase(algorithm)) {
            return new ConcurrentCache<>(new LruCache<>(capacity));
        } else {
            return new ConcurrentCache<>(new LfuCache<>(capacity));
        }

    }
}

