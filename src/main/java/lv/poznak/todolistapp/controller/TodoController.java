package lv.poznak.todolistapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*@RequestMapping("/api")*/
@RequiredArgsConstructor
public class TodoController {

  @GetMapping("/test")
  public String hello(){
    return "Test connetcion";
  }
}
