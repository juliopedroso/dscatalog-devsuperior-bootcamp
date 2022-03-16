package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.devsuperior.dscatalog.entities.Category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryResource {
    
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list = Arrays.asList(
            new Category(1L, "Books"),
            new Category(2L, "Electronics")
        );

        return ResponseEntity.ok().body(list);
    }

    

}
