package com.developerjorney.application.product.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> getListProduct() {
        return ResponseEntity.ok("Hello world");
    }
}
