package lv.poznak.todolistapp.controller;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*@RequestMapping("/api")*/
public class TodoController {

  @GetMapping("/test")
  public String hello(){
    return "Test connetcion";
  }
}
