package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    // add category
    public Category addCategory(Category category, String username) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        category.setUser(user);
        return categoryRepository.save(category);
    }

    // get all categories
    public List<Category> getAllCategories(String username) {
        return categoryRepository.findByUser_Username(username);
    }

    // get by id
    public Category getById(Long id, String username) {
        return categoryRepository.findByIdAndUser_Username(id, username);
    }

    // update category
    public Category updateCategory(Long id, Category category, String username) {

        Category existingCategory = categoryRepository.findByIdAndUser_Username(id, username);

        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            return categoryRepository.save(existingCategory);
        }

        return null;
    }

    // delete category
    public boolean deleteCategory(Long id, String username) {

        Category existingCategory = categoryRepository.findByIdAndUser_Username(id, username);

        if (existingCategory == null) {
            return true;
        }

        boolean categoryUsed = expenseRepository.existsByCategory_IdAndUser_Username(id, username);

        if (categoryUsed) {
            return false;
        }

        categoryRepository.delete(existingCategory);
        return true;
    }
}
