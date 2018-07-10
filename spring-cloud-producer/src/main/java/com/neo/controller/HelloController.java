package com.neo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {
	
    @RequestMapping("/hello")
    public ResponseEntity<?> index() {
        return new ResponseEntity<>("Hello，this is first messge.", HttpStatus.OK);
    }
}