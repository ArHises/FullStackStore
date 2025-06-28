package com.arhises.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello, from Server!";
    }

    @PostMapping("/post")
    @ResponseBody
    public String poster(@RequestBody String posted){
        return "you posted: " + posted;
    }
}