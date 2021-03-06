package se.sdaproject.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String body;
    private String authorName;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @ManyToMany()
    private List<Topic> topics;

    public Article(String title, String body, String authorName) {
        this.title = title;
        this.body = body;
        this.authorName = authorName;
    }

    public Article() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName() {
        this.authorName = authorName;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Topic> getTopics() {
        return topics;
    }
}

