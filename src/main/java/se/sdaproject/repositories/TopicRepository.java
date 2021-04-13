package se.sdaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import se.sdaproject.models.Topic;


public interface TopicRepository extends JpaRepository<Topic, Long> {
}