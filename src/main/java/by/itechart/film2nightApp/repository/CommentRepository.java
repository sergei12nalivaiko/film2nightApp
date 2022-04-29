package by.itechart.film2nightApp.repository;

import by.itechart.film2nightApp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
