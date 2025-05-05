package Phase1;

import java.time.LocalDate;
import java.util.*;

/**
 * Manages a collection of expenses in memory.
 * Supports operations to add, update, delete, and summarize expenses.
 */
public class ExpenseManager {
    // Internal list to store expenses
    private final List<Expense> expenses = new ArrayList<>();

    //Adds a new expense to the list.
    public Expense addExpense(String description, double amount, LocalDate date) {
        Expense expense = new Expense(description, amount, date);
        expenses.add(expense);
        return expense;
    }

    //Updates an existing expense if found by ID.
    public boolean updateExpense(String id, String newDescription, double newAmount, LocalDate newDate) {
        Expense expense = findExpenseById(id);
        if (expense == null)
            return false;

        expense.setDescription(newDescription);
        expense.setAmount(newAmount);
        expense.setDate(newDate);
        return true;
    }

    //Deletes an expense by its ID.
    public boolean deleteExpense(String id) {
        Expense expense = findExpenseById(id);
        return expense != null && expenses.remove(expense);
    }

    //Returns a list of all recorded expenses
    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    //Calculates the total of all expenses.
    public double getTotalExpense() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    // Calculates the total expenses for a specific month and year.
    public double getMonthlyExpense(int month, int year) {
        return expenses.stream()
                .filter(e -> e.getDate().getMonthValue() == month && e.getDate().getYear() == year)
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    //Finds an expense by its unique ID.
    public Expense findExpenseById(String id) {
        return expenses.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
