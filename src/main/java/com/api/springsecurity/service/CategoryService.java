package com.api.springsecurity.service;

import com.api.springsecurity.dto.SaveCategory;
import com.api.springsecurity.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {
    Page<Category> findAll(Pageable pageable);

    Optional<Category> findOneById(Long categoryId);

    Category createOne(SaveCategory saveCategory);

    Category updateOneById(Long categoryId, SaveCategory saveCategory);

    Category disableOneById(Long categoryId);

    Category enableOneById(Long categoryId);
}
