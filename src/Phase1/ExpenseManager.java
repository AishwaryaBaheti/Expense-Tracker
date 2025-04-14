package Phase1;

import java.time.LocalDate;
import java.util.*;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();

    public Expense addExpense(String description, double amount, LocalDate date) {
        Expense expense = new Expense(description, amount, date);
        expenses.add(expense);
        return expense;
    }

    public boolean updateExpense(String id, String newDescription, double newAmount, LocalDate newDate) {
        Expense expense = findExpenseById(id);
        if (expense == null)
            return false;

        expense.setDescription(newDescription);
        expense.setAmount(newAmount);
        expense.setDate(newDate);
        return true;
    }

    public boolean deleteExpense(String id) {
        Expense expense = findExpenseById(id);
        return expense != null && expenses.remove(expense);
    }

    public List<Expense> getAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public double getTotalExpense() {
        return expenses.stream().mapToDouble(Expense::getAmount).sum();
    }

    public double getMonthlyExpense(int month, int year) {
        return expenses.stream()
                .filter(e -> e.getDate().getMonthValue() == month && e.getDate().getYear() == year)
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public Expense findExpenseById(String id) {
        return expenses.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
