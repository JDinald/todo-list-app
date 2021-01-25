package lv.poznak.todolistapp.service;

import lv.poznak.todolistapp.model.Todo;
import lv.poznak.todolistapp.repository.TodoRepository;
import lv.poznak.todolistapp.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class TodoServiceImplTest {

  @Autowired private TodoRepository todoRepository;

  @AfterEach
  void tearDown() {
    todoRepository.deleteAll();
  }

  @Test
  void getTodoList() {
    Todo todo = new Todo(1,"Test 1", "Test info 1", true);
    todoRepository.save(todo);
    TodoService todoService = new TodoServiceImpl(todoRepository);

    ResponseEntity<List<Todo>> todoList = todoService.getTodoList(null);
    List<Todo> todoListBody = todoList.getBody();
    Todo lastTodo = todoListBody.get(todoListBody.size() - 1);

    assertEquals(todo.getDescription(), lastTodo.getDescription());
    assertEquals(todo.getTitle(), lastTodo.getTitle());
  }

    @Test
    void addTodo() {
        TodoService todoService = new TodoServiceImpl(todoRepository);
        Todo todoSample = new Todo(1, "Test 1", "Test info 1", true);

        todoService.addTodo(todoSample);

        assertEquals(1.0, todoRepository.count());
    }

  @Test
  void getTodoById() {
      Todo todo = new Todo(4,"Test 1", "Test info 1", true);
      long savedId = todoRepository.save(todo).getId();
      TodoService todoService = new TodoServiceImpl(todoRepository);

      ResponseEntity<Todo> foundTodo = todoService.getTodoById(savedId);
      Todo foundTodoBody = foundTodo.getBody();

      assertEquals(todo.getDescription(), foundTodoBody.getDescription());
      assertEquals(todo.getTitle(), foundTodoBody.getTitle());
      assertEquals(todo.getId(), foundTodoBody.getId());
  }

  @Test
  void updateTodo() {
      Todo oldTodo = new Todo(1,"Test 1", "Test info 1", false);
      Todo newTodo = new Todo(1,"Test 1", "Test info 1", true);
      long savedId = todoRepository.save(oldTodo).getId();
      TodoService todoService = new TodoServiceImpl(todoRepository);

      ResponseEntity<Todo> updatedTodo = todoService.updateTodo(savedId,newTodo);
      Todo foundTodoBody = updatedTodo.getBody();

      assertEquals(oldTodo.getDescription(), foundTodoBody.getDescription());
      assertEquals(oldTodo.getTitle(), foundTodoBody.getTitle());
      assertNotEquals(oldTodo.isStatus(), foundTodoBody.isStatus());
  }

  @Test
  void deleteTodo() {
      Todo secondTodo = new Todo(2,"Test 2", "Test info 2", true);
      todoRepository.save(secondTodo);
      TodoService todoService = new TodoServiceImpl(todoRepository);

      todoService.deleteTodo(2);

      assertEquals(todoService.getTodoById(2).getStatusCodeValue(), HttpStatus.NOT_FOUND.value());

  }

  @Test
  void deleteAllTodo() {
      Todo fistTodo = new Todo(1,"Test 1", "Test info 1", false);
      todoRepository.save(fistTodo);
      Todo secondTodo = new Todo(2,"Test 2", "Test info 2", true);
      todoRepository.save(secondTodo);
      TodoService todoService = new TodoServiceImpl(todoRepository);

      todoService.deleteAllTodo();

      assertEquals(todoService.getTodoList(null).getStatusCodeValue(), HttpStatus.NO_CONTENT.value());
  }

  @Test
  void findByStatus() {
      Todo todo = new Todo(1,"Test 1", "Test info 1", true);
      todoRepository.save(todo);
      TodoService todoService = new TodoServiceImpl(todoRepository);

      ResponseEntity<List<Todo>> todoList = todoService.findByStatus();
      List<Todo> todoListBody = todoList.getBody();
      Todo lastTodo = todoListBody.get(todoListBody.size() - 1);

      assertEquals(todo.getDescription(), lastTodo.getDescription());
      assertEquals(todo.getTitle(), lastTodo.getTitle());
      assertEquals(todo.isStatus(), lastTodo.isStatus());
  }
}
