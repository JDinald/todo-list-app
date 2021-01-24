package lv.poznak.todolistapp.controller;

import lv.poznak.todolistapp.model.Todo;
import lv.poznak.todolistapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class TodoControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean private TodoService todoService;

  @Test
  void getAllTodo() throws Exception {
    List<Todo> todoList = new ArrayList<>();
    todoList.add(new Todo(1, "Test 1", "Test info 1", false));
    todoList.add(new Todo(2, "Test 2", "Test info 2", false));

    when(todoService.getTodoList(any())).thenReturn(new ResponseEntity<>(todoList, HttpStatus.OK));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/todos").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andDo(print());
  }

  @Test
  void findTodoById() throws Exception {

    Todo todo = new Todo(1, "Test 1", "Test info 1", false);

    when(todoService.getTodoById(1)).thenReturn(new ResponseEntity<>(todo, HttpStatus.OK));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/todo/1").contentType(MediaType.APPLICATION_JSON))
        .andDo(print());
  }

  @Test
  void createTodo() throws Exception {
    Todo todo = new Todo(1, "Test 1", "Test info 1", false);

    when(todoService.addTodo(todo)).thenReturn(new ResponseEntity<>(todo, HttpStatus.OK));

    mockMvc
            .perform(MockMvcRequestBuilders.post("/api/todo").contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
  }

  @Test
  void updateTodoById() throws Exception {}

  @Test
  void deleteTodoById() throws Exception {}

  @Test
  void deleteAllTutorials() throws Exception {}

  @Test
  void findTodoByStatus() throws Exception {
    List<Todo> todoList = new ArrayList<>();
    todoList.add(new Todo(1, "Test 1", "Test info 1", true));

    when(todoService.findByStatus()).thenReturn(new ResponseEntity<>(todoList, HttpStatus.OK));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/api/todo/status").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andDo(print());
  }
}
