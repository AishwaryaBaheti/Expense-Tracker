
import java.util.*;
import java.time.LocalDate;
import java.util.UUID;

class Expense {
    private final String id;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense(String description, double amount) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return String.format("ID: %s | Description: %s | Amount: $%.2f | Date: %s",
                id, description, amount, date);
    }
}

public class ExpenseTracker {
    private static final List<Expense> expenses = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=== Expense Tracker ===");

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addExpense();
                case "2" -> updateExpense();
                case "3" -> deleteExpense();
                case "4" -> viewExpenses();
                case "5" -> viewSummaryAll();
                case "6" -> viewSummaryByMonth();
                case "7" -> {
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("""
                
                1. Add Expense
                2. Update Expense
                3. Delete Expense
                4. View All Expenses
                5. View Total Expense Summary
                6. View Monthly Expense Summary
                7. Exit
                Choose an option:\s""");
    }

    private static void addExpense() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        double amount = readValidAmount("Enter amount: ");
        Expense expense = new Expense(description, amount);
        expenses.add(expense);

        System.out.println("Expense added with ID: " + expense.getId());
    }

    private static void updateExpense() {
        String id = readId("Enter expense ID to update: ");
        Expense expense = findExpenseById(id);

        if (expense == null) {
            System.out.println("Expense not found.");
            return;
        }

        System.out.print("Enter new description: ");
        String newDescription = scanner.nextLine();

        double newAmount = readValidAmount("Enter new amount: ");
        expense.setDescription(newDescription);
        expense.setAmount(newAmount);

        System.out.println("Expense updated.");
    }

    private static void deleteExpense() {
        String id = readId("Enter expense ID to delete: ");
        Expense expense = findExpenseById(id);

        if (expense == null) {
            System.out.println("Expense not found.");
            return;
        }

        expenses.remove(expense);
        System.out.println("Expense deleted.");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("All Expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private static void viewSummaryAll() {
        double total = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("Total expenses: $%.2f%n", total);
    }

    private static void viewSummaryByMonth() {
        System.out.print("Enter month (1-12): ");
        String input = scanner.nextLine();
        int month;

        try {
            month = Integer.parseInt(input);
            if (month < 1 || month > 12) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid month.");
            return;
        }

        int currentYear = LocalDate.now().getYear();

        double total = expenses.stream()
                .filter(e -> e.getDate().getMonthValue() == month && e.getDate().getYear() == currentYear)
                .mapToDouble(Expense::getAmount)
                .sum();

        String monthName = LocalDate.of(currentYear, month, 1).getMonth().name();
        System.out.printf("Total expenses for %s %d: $%.2f%n", monthName, currentYear, total);
    }

    private static double readValidAmount(String prompt) {
        double amount;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                amount = Double.parseDouble(input);
                if (amount < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive number.");
            }
        }
        return amount;
    }

    private static String readId(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static Expense findExpenseById(String id) {
        return expenses.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
