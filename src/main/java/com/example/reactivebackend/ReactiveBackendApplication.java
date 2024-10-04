package com.example.reactivebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ReactiveBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveBackendApplication.class, args);
    }

}
@RestController
class GreetingController {
    @GetMapping("/hello")
    public String greet() {
        return "Happy learning!";
    }
}