package se.sdaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.models.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}