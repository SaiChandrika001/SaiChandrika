package com.example.controllr;

import org.springframework.web.bind.annotation.GetMapping;

public class controller {
	

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/error-test")
    public String causeError() {
        int x = 10 / 0;  // ❌ ArithmeticException
        return "home";
    }

}
