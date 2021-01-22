package lv.poznak.todolistapp.repository;

import lv.poznak.todolistapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

  List<Todo> findByStatus(boolean status);

  List<Todo> findByTitleContaining(String title);
}
