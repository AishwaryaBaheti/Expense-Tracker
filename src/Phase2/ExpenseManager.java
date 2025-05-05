package Phase2;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import Phase1.Expense;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ExpenseManager {
    private final List<Expense> expenses = new ArrayList<>();
    private final File dataFile = new File("expenses.json");
    private final ObjectMapper mapper;

    public ExpenseManager() {
        // Configure ObjectMapper
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Load data on initialization
        loadExpenses();
    }

    public Expense addExpense(String description, double amount, LocalDate date) {
        Expense expense = new Expense(description, amount, date);
        expenses.add(expense);
        saveExpenses();
        return expense;
    }

    public boolean updateExpense(String id, String newDescription, double newAmount, LocalDate newDate) {
        Expense expense = findExpenseById(id);
        if (expense == null) return false;

        expense.setDescription(newDescription);
        expense.setAmount(newAmount);
        expense.setDate(newDate);
        saveExpenses();
        return true;
    }

    public boolean deleteExpense(String id) {
        Expense expense = findExpenseById(id);
        if (expense != null) {
            expenses.remove(expense);
            saveExpenses();
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
        if (!dataFile.exists()) return;

        try {
            List<Expense> loadedExpenses = mapper.readValue(dataFile, new TypeReference<>() {});
            expenses.clear();
            expenses.addAll(loadedExpenses);
        } catch (IOException e) {
            System.err.println("Failed to load expenses: " + e.getMessage());
        }
    }

    private void saveExpenses() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(dataFile, expenses);
        } catch (IOException e) {
            System.err.println("Failed to save expenses: " + e.getMessage());
        }
    }
}
