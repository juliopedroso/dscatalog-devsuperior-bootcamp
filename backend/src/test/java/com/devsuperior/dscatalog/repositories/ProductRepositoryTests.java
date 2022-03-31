package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private Long existentId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setup() throws Exception {
        existentId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    void saveShouldPersistWithAutoincrementWhenIdIsNull(){

        Product entity = Factory.createProduct();
        entity.setId(null);
        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());
        Assertions.assertEquals(countTotalProducts + 1, entity.getId());

    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existentId);

        Optional<Product> result = repository.findById(existentId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    void deleteShouldThrowExceptionWhenIdDoesNotExists() {

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });

    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists(){

        Optional<Product> result = repository.findById(existentId);
        Assertions.assertTrue(result.isPresent());

    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdNotExist(){
        
        Optional<Product> result = repository.findById(nonExistingId);
        Assertions.assertTrue(result.isEmpty());

    }
}
