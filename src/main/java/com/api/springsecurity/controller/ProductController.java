package com.api.springsecurity.controller;

import com.api.springsecurity.dto.SaveProduct;
import com.api.springsecurity.persistence.entity.Product;
import com.api.springsecurity.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @GetMapping
    public ResponseEntity<Page<Product>> findAll(Pageable pageable){

        Page<Product> productsPage = productService.findAll(pageable);

        if(productsPage.hasContent()){
            return ResponseEntity.ok(productsPage);
        }

        return ResponseEntity.notFound().build();
    }

//    @CrossOrigin(origins = "https://www.google.com")
    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{productId}")
    public ResponseEntity<Product> findOneById(@PathVariable Long productId){

        Optional<Product> product = productService.findOneById(productId);

        if(product.isPresent()){
            return ResponseEntity.ok(product.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAuthority('CREATE_ONE_PRODUCT')")
    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.createOne(saveProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PreAuthorize("hasAuthority('UPDATE_ONE_PRODUCT')")
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateOneById(@PathVariable Long productId ,
                                                 @RequestBody @Valid SaveProduct saveProduct){
        Product product = productService.updateOneById(productId, saveProduct);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasAuthority('DISABLE_ONE_PRODUCT')")
    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> disableOneById(@PathVariable Long productId){
        Product product = productService.disableOneById(productId);
        return ResponseEntity.ok(product);
    }
    @PreAuthorize("hasAuthority('ENABLE_ONE_PRODUCT')")
    @PutMapping("/{productId}/enabled")
    public ResponseEntity<Product> enableOneById(@PathVariable Long productId){
        Product product = productService.enableOneById(productId);
        return ResponseEntity.ok(product);
    }

}
