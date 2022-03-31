package com.devsuperior.dscatalog.services;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockedStatic.Verification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    @Mock
    private CategoryRepository categoryRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private PageImpl<Product> page;
    private Product product;
    private ProductDTO productDTO;
    private CategoryDTO categoryDTO;

    @BeforeEach
    void setup() throws Exception{
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 4L;
        product = Factory.createProduct();
        productDTO = Factory.createProductDTO();
        categoryDTO = Factory.createCategoryDTO();
        page = new PageImpl<>(List.of(product));

        Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        Mockito.when(repository.getById(existingId)).thenReturn(product);
        Mockito.when(repository.getById(nonExistingId)).thenThrow(EntityNotFoundException.class);

        Mockito.when(categoryRepository.getById(existingId)).thenReturn(new Category(2L, "Electronics"));

        doNothing().when(repository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(()->{
            service.delete(existingId);
        });

        verify(repository, Mockito.times(1)).deleteById(existingId);

    }

    @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            service.delete(nonExistingId);
        });
        verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenDependentId(){

        Assertions.assertThrows(DatabaseException.class,()->{
            service.delete(dependentId);
        });

        verify(repository, Mockito.times(1)).deleteById(dependentId);
    }

    @Test
    void findAllPagedShouldReturnPage(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);

        verify(repository).findAll(pageable);
    }

    @Test
    void findByIdShouldReturnProductDTOWhenIdExists(){
        ProductDTO productDTO = service.findById(existingId);

        Assertions.assertNotNull(productDTO);

        verify(repository,Mockito.times(1)).findById(existingId);
    }

    @Test
    void findByIdShouldThrowResouceNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(nonExistingId);
        });

        verify(repository,Mockito.times(1)).findById(nonExistingId);
        
    }

    @Test
    void updateShouldReturnProductDTOWhenIdExists(){

        ProductDTO entity =  service.update(existingId, productDTO);

        Assertions.assertNotNull(entity);

        verify(repository,Mockito.times(1)).getById(existingId);
                
    }

    @Test
    void updateShouldThrowResouceNotFoundExceptionWhenIdDoesNotExist(){

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.update(nonExistingId, productDTO);
        });

        verify(repository,Mockito.times(1)).getById(nonExistingId);
        
    }

}
