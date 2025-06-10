package com.api.springsecurity.service.impl;

import com.api.springsecurity.dto.SaveProduct;
import com.api.springsecurity.exception.ObjectNotFoundException;
import com.api.springsecurity.persistence.entity.Category;
import com.api.springsecurity.persistence.entity.Product;
import com.api.springsecurity.persistence.repository.ProductRepository;
import com.api.springsecurity.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    //    @PreAuthorize("hasAuthority('READ_ALL_PRODUCTS')")
    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Optional<Product> findOneById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOne(SaveProduct saveProduct) {
        Product product = new Product();
        product.setName(saveProduct.getName());
        product.setPrice(saveProduct.getPrice());
        product.setStatus(Product.ProductStatus.ENABLED);

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public Product updateOneById(Long productId, SaveProduct saveProduct) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));

        productFromDB.setName(saveProduct.getName());
        productFromDB.setPrice(saveProduct.getPrice());

        Category category = new Category();
        category.setId(saveProduct.getCategoryId());
        productFromDB.setCategory(category);

        return productRepository.save(productFromDB);
    }

    @Override
    public Product disableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));
        productFromDB.setStatus(Product.ProductStatus.DISABLED);

        return productRepository.save(productFromDB);
    }

    @Override
    public Product enableOneById(Long productId) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id " + productId));
        productFromDB.setStatus(Product.ProductStatus.ENABLED);

        return productRepository.save(productFromDB);
    }
}
