package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // add category
    @PostMapping
    public Category addCategory(@RequestParam String username, @Valid @RequestBody Category category) {
        return categoryService.addCategory(category, username);
    }

    // get all categories
    @GetMapping
    public List<Category> getAllCategories(@RequestParam String username) {
        return categoryService.getAllCategories(username);
    }

    // get by id
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id, @RequestParam String username) {
        return categoryService.getById(id, username);
    }

    // update category
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestParam String username, @Valid @RequestBody Category category) {
        return categoryService.updateCategory(id, category, username);
    }

    // delete category
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id, @RequestParam String username) {

        boolean deleted = categoryService.deleteCategory(id, username);

        if (deleted) {
            return "Category deleted successfully";
        } else {
            return "Cannot delete category because it is used in expense";
        }
    }
}
