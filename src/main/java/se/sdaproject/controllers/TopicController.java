package se.sdaproject.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.sdaproject.models.Article;
import se.sdaproject.models.Topic;
import se.sdaproject.repositories.ArticleRepository;
import se.sdaproject.repositories.TopicRepository;
import se.sdaproject.ResourceNotFoundException;


import java.util.List;


@RestController
public class TopicController {

    TopicRepository topicRepository;
    ArticleRepository articleRepository;


    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    //Create new topic
    @PostMapping("/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    //List all topics
    @GetMapping("/topics")
    public List<Topic> listAllTopics() {
        return topicRepository.findAll();
    }

    //List articles associated with one topic
    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> listArticlesInTopic(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        List<Article> articles = topic.getArticles();
        return ResponseEntity.ok(articles);
    }

    //Associate topic with an article
    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> associateTopic(@PathVariable Long articleId, @RequestBody Topic topic) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topic topicToAssociate = topicRepository.findById(topic.getId()).orElse(null);
        if (topicToAssociate == null) {
            topicToAssociate = topicRepository.save(topic);
        }
        topicToAssociate.getArticles().add(article);
        topicRepository.save(topicToAssociate);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    //Update topic
    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic) {
        topicRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedTopic.setId(id);
        Topic topic = topicRepository.save(updatedTopic);
        return ResponseEntity.ok(topic);
    }

    //Delete a topic
    @DeleteMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTopic(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        topicRepository.delete(topic);
    }

    //Delete association of a topic for one article
    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssociation(@PathVariable Long topicId, @PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        if (article.getTopics().contains(topic)) {
            article.getTopics().remove(topic);
            articleRepository.save(article);
        } else {
            throw new ResourceNotFoundException();
        }
    }


}
