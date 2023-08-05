package dev.ralo.example.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin";
    }

    @GetMapping("/user")
    public String user() {
        return "Hello, User";
    }

    @GetMapping("/all")
    public String all() {
        return "Hello! You are not authenticated";
    }
}
