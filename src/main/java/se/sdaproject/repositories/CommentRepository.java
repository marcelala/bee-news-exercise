package se.sdaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.sdaproject.models.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByAuthorName(@Param("authorName") String authorName);
    List<Comment> findByArticleId(@Param("id") Long articleID);
}