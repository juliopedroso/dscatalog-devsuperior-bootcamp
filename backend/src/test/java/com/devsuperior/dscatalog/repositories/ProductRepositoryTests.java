package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import com.devsuperior.dscatalog.entities.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
public class ProductRepositoryTests {
    
    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        Long existentId = 1L;

        repository.deleteById(existentId);

        Optional<Product> result = repository.findById(existentId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void deleteShouldThrowExceptionWhenIdDoesNotExists(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{
            Long nonExistingId = 1000L;
            repository.deleteById(nonExistingId);
        });
        
    }

}
