package com.example.demo.controller;

import com.example.demo.entity.Expense;
import com.example.demo.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // add expense
    @PostMapping
    public Object addExpense(@RequestParam String username, @Valid @RequestBody Expense expense) {

        Expense savedExpense = expenseService.addExpense(expense, username);

        if (savedExpense == null) {
            return "Category not found";
        }

        return savedExpense;
    }

    // get all expenses
    @GetMapping
    public List<Expense> getAllExpenses(@RequestParam String username) {
        return expenseService.getAllExpenses(username);
    }

    // get by id
    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id, @RequestParam String username) {
        return expenseService.getById(id, username);
    }

    // get expenses by category name
    @GetMapping("/category/{categoryName}")
    public List<Expense> getExpensesByCategoryName(@PathVariable String categoryName, @RequestParam String username) {
        return expenseService.getExpensesByCategoryName(username, categoryName);
    }

    // update expense
    @PutMapping("/{id}")
    public Object updateExpense(@PathVariable Long id, @RequestParam String username, @Valid @RequestBody Expense expense) {

        Expense updatedExpense = expenseService.updateExpense(id, expense, username);

        if (updatedExpense == null) {
            return "Expense or Category not found";
        }

        return updatedExpense;
    }

    // delete expense
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id, @RequestParam String username) {
        expenseService.deleteExpense(id, username);
    }

    // report by date range
    @GetMapping("/report")
    public List<Expense> getExpensesByDateRange(
            @RequestParam String username,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return expenseService.getExpensesByDateRange(username, start, end);
    }

    // total amount by date range
    @GetMapping("/report/total")
    public double getTotalAmountByDateRange(
            @RequestParam String username,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        return expenseService.getTotalAmountByDateRange(username, start, end);
    }

    // report by month
    @GetMapping("/report/month")
    public List<Expense> getExpensesByMonth(
            @RequestParam String username,
            @RequestParam int year,
            @RequestParam int month) {

        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return expenseService.getExpensesByDateRange(username, start, end);
    }

    // total amount by month
    @GetMapping("/report/month/total")
    public double getTotalAmountByMonth(
            @RequestParam String username,
            @RequestParam int year,
            @RequestParam int month) {

        YearMonth yearMonth = YearMonth.of(year, month);

        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        return expenseService.getTotalAmountByDateRange(username, start, end);
    }

    // report by financial year
    @GetMapping("/report/financial-year")
    public List<Expense> getExpensesByFinancialYear(@RequestParam String username, @RequestParam int year) {

        LocalDate start = LocalDate.of(year, 4, 1);
        LocalDate end = LocalDate.of(year + 1, 3, 31);

        return expenseService.getExpensesByDateRange(username, start, end);
    }

    // total amount by financial year
    @GetMapping("/report/financial-year/total")
    public double getTotalAmountByFinancialYear(@RequestParam String username, @RequestParam int year) {

        LocalDate start = LocalDate.of(year, 4, 1);
        LocalDate end = LocalDate.of(year + 1, 3, 31);

        return expenseService.getTotalAmountByDateRange(username, start, end);
    }

    // category wise total amount
    @GetMapping("/report/category-wise-total")
    public Map<String, Double> getCategoryWiseTotal(@RequestParam String username) {
        return expenseService.getCategoryWiseTotal(username);
    }
}
