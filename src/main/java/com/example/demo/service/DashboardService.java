package com.example.demo.service;

import com.example.demo.entity.Expense;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    // total category count
    public long getTotalCategories(String username) {
        return categoryRepository.countByUser_Username(username);
    }

    // total expense record count
    public long getTotalExpenses(String username) {
        return expenseRepository.countByUser_Username(username);
    }

    // total expense amount
    public double getTotalExpenseAmount(String username) {

        List<Expense> expenses = expenseRepository.findByUser_Username(username);

        double total = 0;

        for (Expense expense : expenses) {
            total = total + expense.getAmount();
        }

        return total;
    }
}
