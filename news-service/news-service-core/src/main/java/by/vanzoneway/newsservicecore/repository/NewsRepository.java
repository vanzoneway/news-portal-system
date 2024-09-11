package by.vanzoneway.newsservicecore.repository;

import by.vanzoneway.newsserviceapi.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @NonNull
    Page<News> findAll(@NonNull Pageable pageable);

    Page<News> findAllByTitleStartingWith(@NonNull String title, Pageable pageable);

    void deleteById(@NonNull Long id);

    boolean existsById(@NonNull Long id);

    boolean existsByTitle(@NonNull String title);

    News findByTitle(@NonNull String title);

    @Query(value = """
    SELECT *
    FROM news
    WHERE to_tsvector('english', title || ' ' || text) @@ to_tsquery(:query || ':*')
    """, nativeQuery = true)
    Page<News> search(@Param("query") String query, Pageable pageable);
}
