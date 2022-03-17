package com.devsuperior.dscatalog.services;

import java.util.List;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    public final CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }

}
