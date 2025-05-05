package Phase2;

import Phase1.Expense;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Create an instance of ExpenseManager
        ExpenseManager expenseManager = new ExpenseManager();

        // Test: Add new expenses
        Expense expense1 = expenseManager.addExpense("Lunch", 12.50, LocalDate.of(2025, 5, 5));
        Expense expense2 = expenseManager.addExpense("Groceries", 45.30, LocalDate.of(2025, 5, 4));

        // Print all expenses
        System.out.println("All Expenses:");
        expenseManager.getAllExpenses().forEach(System.out::println);

        // Test: Update an expense
        expenseManager.updateExpense(expense1.getId(), "Lunch at Cafe", 15.00, LocalDate.of(2025, 5, 5));

        // Print all expenses after update
        System.out.println("\nAfter Update:");
        expenseManager.getAllExpenses().forEach(System.out::println);

        // Test: Delete an expense
        expenseManager.deleteExpense(expense2.getId());

        // Print all expenses after deletion
        System.out.println("\nAfter Deletion:");
        expenseManager.getAllExpenses().forEach(System.out::println);

        // Test: Get total expense
        System.out.println("\nTotal Expense: $" + expenseManager.getTotalExpense());

        // Test: Get monthly expense
        System.out.println("Monthly Expense for May 2025: $" + expenseManager.getMonthlyExpense(5, 2025));
    }
}
