package Phase2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * ExpenseManager handles CRUD operations on expenses
 * and persists them in a JSON file only when changes occur.
 */
public class ExpenseManager {
    private final File dataFile = new File("src/Phase2/expenses.json");
    private final List<Expense> expenses = new ArrayList<>();
    private final ObjectMapper mapper;
    private boolean isDirty = false;

    public ExpenseManager() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        loadExpenses(); // Load expenses on startup
    }

    public Expense addExpense(String description, double amount, LocalDate date) {
        Expense expense = new Expense(description, amount, date);
        expenses.add(expense);
        isDirty = true;
        return expense;
    }

    public boolean updateExpense(String id, String newDescription, double newAmount, LocalDate newDate) {
        Expense expense = findExpenseById(id);
        if (expense == null) return false;

        expense.setDescription(newDescription);
        expense.setAmount(newAmount);
        expense.setDate(newDate);
        isDirty = true;
        return true;
    }

    public boolean deleteExpense(String id) {
        Expense expense = findExpenseById(id);
        if (expense != null) {
            expenses.remove(expense);
            isDirty = true;
            return true;
        }
        return false;
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

    private void loadExpenses() {
        if (dataFile.exists()) {
            try {
                List<Expense> loaded = mapper.readValue(dataFile, new TypeReference<List<Expense>>() {});
                expenses.addAll(loaded);
            } catch (IOException e) {
                System.err.println("Failed to load expenses: " + e.getMessage());
            }
        }
    }

    public void saveExpenses() {
        if (!isDirty) return; // No changes, no save needed
        try {
            mapper.writeValue(dataFile, expenses);
            isDirty = false;
        } catch (IOException e) {
            System.err.println("Failed to save expenses: " + e.getMessage());
        }
    }
}
