package by.vanzoneway.newsservicecore.repository;

import by.vanzoneway.newsserviceapi.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @NonNull
    Page<Comment> findAllByNewsId(Long newsId, @NonNull Pageable pageable);

    boolean existsById(@NonNull Long id);

    @Query(value = """
    SELECT c.*
    FROM news n 
    LEFT JOIN comment c ON n.id = c.news_id
    WHERE n.id = :newsId
        AND to_tsvector('english', c.text) @@ to_tsquery(:query || ':*')
""", nativeQuery = true)
    Page<Comment> searchCommentsByNewsId(@Param("newsId") Long newsId,
                                         @Param("query")String query,
                                         @NonNull Pageable pageable);


}

