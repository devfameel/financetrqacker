package com.example.demo.repository;

import com.example.demo.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    boolean existsByCategory_Id(Long categoryId);

    boolean existsByCategory_IdAndUser_Username(Long categoryId, String username);

    long countByUser_Username(String username);

    List<Expense> findByUser_Username(String username);

    Expense findByIdAndUser_Username(Long id, String username);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByUser_UsernameAndDateBetween(String username, LocalDate startDate, LocalDate endDate);

    List<Expense> findByCategory_Name(String categoryName);

    List<Expense> findByUser_UsernameAndCategory_Name(String username, String categoryName);
}
