package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUser_Username(String username);

    Category findByIdAndUser_Username(Long id, String username);

    long countByUser_Username(String username);
}
