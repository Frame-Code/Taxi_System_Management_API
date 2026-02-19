package com.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Forward keeps URL as '/' and serves the static file directly
        return "forward:/src/modules/customer/views/index.html";
    }

}
