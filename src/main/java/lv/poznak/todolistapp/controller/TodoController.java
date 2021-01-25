package lv.poznak.todolistapp.controller;

import lombok.RequiredArgsConstructor;
import lv.poznak.todolistapp.model.Todo;

import lv.poznak.todolistapp.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/test")
  public String hello(){
    return "Test connection";
  }

  @GetMapping("/todos")
  public ResponseEntity<List<Todo>> getAllTodo(@RequestParam(required = false) String title) {
    return todoService.getTodoList(title);
  }

  @GetMapping("/todo/{id}")
  public ResponseEntity<Todo> findTodoById(@PathVariable("id") long id) {
    return todoService.getTodoById(id);
  }

  @PostMapping("/todo")
  public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
    return todoService.addTodo(todo);
  }

  @PutMapping("/todo/{id}")
  public ResponseEntity<Todo> updateTodoById(@PathVariable("id") long id, @RequestBody Todo todo) {
    return todoService.updateTodo(id, todo);
  }

  @DeleteMapping("/todo/{id}")
  public ResponseEntity<HttpStatus> deleteTodoById(@PathVariable("id") long id) {
    return todoService.deleteTodo(id);
  }

  @DeleteMapping("/todos")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    return todoService.deleteAllTodo();
  }

  @GetMapping("/todo/status")
  public ResponseEntity<List<Todo>> findTodoByStatus() {
    return todoService.findByStatus();
  }
}
