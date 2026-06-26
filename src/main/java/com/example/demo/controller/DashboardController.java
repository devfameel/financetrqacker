package com.example.demo.controller;

import com.example.demo.service.DashboardService;
import com.example.demo.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private ExpenseService expenseService;

    // dashboard summary
    @GetMapping
    public Map<String, Object> getDashboardSummary(@RequestParam String username) {

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalCategories", dashboardService.getTotalCategories(username));
        dashboard.put("totalExpenses", dashboardService.getTotalExpenses(username));
        dashboard.put("totalExpenseAmount", dashboardService.getTotalExpenseAmount(username));
        dashboard.put("categoryWiseTotal", expenseService.getCategoryWiseTotal(username));

        return dashboard;
    }
}
