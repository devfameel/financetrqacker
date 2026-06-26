package com.example.demo.service;

import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    // add expense
    public Expense addExpense(Expense expense, String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            return null;
        }

        Long categoryId = expense.getCategory().getId();

        Category category = categoryRepository.findByIdAndUser_Username(categoryId, username);

        if (category == null) {
            return null;
        }

        expense.setCategory(category);
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    // get all expenses
    public List<Expense> getAllExpenses(String username) {
        return expenseRepository.findByUser_Username(username);
    }

    // get by id
    public Expense getById(Long id, String username) {
        return expenseRepository.findByIdAndUser_Username(id, username);
    }

    // update expense
    public Expense updateExpense(Long id, Expense expense, String username) {

        Expense existingExpense = expenseRepository.findByIdAndUser_Username(id, username);

        if (existingExpense == null) {
            return null;
        }

        Long categoryId = expense.getCategory().getId();

        Category category = categoryRepository.findByIdAndUser_Username(categoryId, username);

        if (category == null) {
            return null;
        }

        existingExpense.setTitle(expense.getTitle());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDate(expense.getDate());
        existingExpense.setCategory(category);

        return expenseRepository.save(existingExpense);
    }

    // delete expense
    public void deleteExpense(Long id, String username) {
        Expense expense = expenseRepository.findByIdAndUser_Username(id, username);

        if (expense != null) {
            expenseRepository.delete(expense);
        }
    }

    // get expenses by date range
    public List<Expense> getExpensesByDateRange(String username, LocalDate startDate, LocalDate endDate) {
        return expenseRepository.findByUser_UsernameAndDateBetween(username, startDate, endDate);
    }

    // get expenses by category name
    public List<Expense> getExpensesByCategoryName(String username, String categoryName) {
        return expenseRepository.findByUser_UsernameAndCategory_Name(username, categoryName);
    }

    // get total amount by date range
    public double getTotalAmountByDateRange(String username, LocalDate startDate, LocalDate endDate) {

        List<Expense> expenses = expenseRepository.findByUser_UsernameAndDateBetween(username, startDate, endDate);

        double total = 0 ;

        for (Expense expense : expenses) {
            total = total + expense.getAmount();
        }

        return total;
    }

    // get category wise total amount
    public Map<String, Double> getCategoryWiseTotal(String username) {

        List<Expense> expenses = expenseRepository.findByUser_Username(username);

        Map<String, Double> categoryTotal = new HashMap<>();

        for (Expense expense : expenses) {

            String categoryName = expense.getCategory().getName();
            double amount = expense.getAmount();

            if (categoryTotal.containsKey(categoryName)) {
                double oldTotal = categoryTotal.get(categoryName);
                categoryTotal.put(categoryName, oldTotal + amount);
            } else {
                categoryTotal.put(categoryName, amount);
            }
        }

        return categoryTotal;
    }
}
