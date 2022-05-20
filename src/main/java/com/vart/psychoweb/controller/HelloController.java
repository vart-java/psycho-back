package com.vart.psychoweb.controller;

import com.vart.psychoweb.model.security.annotations.CreatePermission;
import com.vart.psychoweb.model.security.annotations.DeletePermission;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class HelloController {

    @DeletePermission
    @GetMapping
    public ResponseEntity<String> getHelloWorld() {
        return ResponseEntity.ok("Hello world!");
    }

    @CreatePermission
    @GetMapping("/1")
    public ResponseEntity<String> getWorldHello() {
        return ResponseEntity.ok("World Hello!");
    }
}
