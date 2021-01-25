package lv.poznak.todolistapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        .perform(MockMvcRequestBuilders.get("/todos").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andDo(print());
  }

  @Test
  void findTodoById() throws Exception {

    Todo todo = new Todo(1, "Test 1", "Found by id", false);

    when(todoService.getTodoById(1)).thenReturn(new ResponseEntity<>(todo, HttpStatus.OK));

    mockMvc
        .perform(MockMvcRequestBuilders.get("/todo/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.description").value("Found by id"))
        .andDo(print());
  }

  @Test
  void createTodo() throws Exception {
    Todo todo = new Todo(1, "Test 1", "Test", false);
    when(todoService.addTodo(any(Todo.class)))
        .thenReturn(new ResponseEntity<>(todo, HttpStatus.CREATED));
    ObjectMapper objectMapper = new ObjectMapper();
    String todoJSON = objectMapper.writeValueAsString(todo);

    ResultActions result =
        mockMvc.perform(
            post("/todo").contentType(MediaType.APPLICATION_JSON).content(todoJSON));

    result
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.description").value("Test"))
        .andExpect(jsonPath("$.status").value(false));
  }

  @Test
  void updateTodoById() throws Exception {
    Todo newTodo = new Todo(1, "Test 1", "New", true);
    when(todoService.updateTodo(anyLong(), any(Todo.class)))
        .thenReturn(new ResponseEntity<>(newTodo, HttpStatus.CREATED));
    ObjectMapper objectMapper = new ObjectMapper();
    String todoJSON = objectMapper.writeValueAsString(newTodo);

    ResultActions result =
        mockMvc.perform(
            put("/todo/1").contentType(MediaType.APPLICATION_JSON).content(todoJSON));

    result
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.description").value("New"))
        .andExpect(jsonPath("$.status").value(true));
  }

  @Test
  void deleteTodoById() throws Exception {
    when(todoService.deleteTodo(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/todo/1").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void deleteAllTutorials() throws Exception {
    when(todoService.deleteAllTodo()).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    mockMvc.perform(
        MockMvcRequestBuilders.delete("/todos").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void findTodoByStatus() throws Exception {
    List<Todo> todoList = new ArrayList<>();
    todoList.add(new Todo(1, "Test 1", "Found by status", true));

    when(todoService.findByStatus()).thenReturn(new ResponseEntity<>(todoList, HttpStatus.OK));

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/todo/status").contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].description").value("Found by status"))
        .andExpect(jsonPath("$[0].status").value(true))
        .andDo(print());
  }
}
