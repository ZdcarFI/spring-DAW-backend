package com.api.springsecurity.persistence.repository;

import com.api.springsecurity.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
