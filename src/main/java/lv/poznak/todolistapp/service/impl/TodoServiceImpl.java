package lv.poznak.todolistapp.service.impl;

import lombok.RequiredArgsConstructor;
import lv.poznak.todolistapp.model.Todo;
import lv.poznak.todolistapp.repository.TodoRepository;
import lv.poznak.todolistapp.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;

  @Override
  public ResponseEntity<List<Todo>> getTodoList(String title) {
    try {
      List<Todo> todos = new ArrayList<Todo>();

      if (title == null) todoRepository.findAll().forEach(todos::add);
      else todoRepository.findByTitleContaining(title).forEach(todos::add);

      if (todos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(todos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<Todo> getTodoById(long id) {
    Optional<Todo> todoData = todoRepository.findById(id);

    if (todoData.isPresent()) {
      return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<Todo> addTodo(Todo todo) {
    try {
      Todo createdTodo =
          todoRepository.save(
              Todo.builder()
                  .title(todo.getTitle())
                  .description(todo.getDescription())
                  .status(false)
                  .build());
      return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<Todo> updateTodo(long id, Todo todo) {
    Optional<Todo> todoForUpdate = todoRepository.findById(id);

    if (todoForUpdate.isPresent()) {
      Todo tempTodo = todoForUpdate.get();
      tempTodo.setTitle(todo.getTitle());
      tempTodo.setDescription(todo.getDescription());
      tempTodo.setStatus(todo.isStatus());
      return new ResponseEntity<>(todoRepository.save(tempTodo), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public ResponseEntity<HttpStatus> deleteTodo(long id) {
    try {
      todoRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<HttpStatus> deleteAllTodo() {
    try {
      todoRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  public ResponseEntity<List<Todo>> findByStatus() {
    try {
      List<Todo> todos = todoRepository.findByStatus(true);

      if (todos.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(todos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
