package lv.poznak.todolistapp.service;

import lv.poznak.todolistapp.model.Todo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

    ResponseEntity<List<Todo>> getTodoList(String title);

    ResponseEntity<Todo> getTodoById(long id);

    ResponseEntity<Todo> addTodo(Todo todo);

    ResponseEntity<Todo> updateTodo(long id,Todo todo);

    ResponseEntity<HttpStatus> deleteTodo(long id);

    ResponseEntity<HttpStatus> deleteAllTodo();

    ResponseEntity<List<Todo>> findByStatus();

}
