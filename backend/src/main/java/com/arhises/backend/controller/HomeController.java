package com.arhises.backend.controller;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
public class HomeController {

    @GetMapping("/hello")
    @ResponseBody
    public String home() {
        return "Hello, from Roman!";
    }

    @PostMapping("/post")
    @ResponseBody
    public String poster(@RequestBody String posted){
        return "you posted: " + posted;
    }
}
