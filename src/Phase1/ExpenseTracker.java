package Phase1;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ExpenseTracker {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final ExpenseManager manager = new ExpenseManager();

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
        LocalDate date = readValidDate("Enter date (yyyy-MM-dd): ");

        Expense expense = manager.addExpense(description, amount, date);
        System.out.println("Expense added with ID: " + expense.getId());
    }

    private static void updateExpense() {
        System.out.print("Enter expense ID to update: ");
        String id = scanner.nextLine().trim();

        if (manager.findExpenseById(id) == null) {
            System.out.println("Expense not found.");
            return;
        }

        System.out.print("Enter new description: ");
        String description = scanner.nextLine();

        double amount = readValidAmount("Enter new amount: ");
        LocalDate date = readValidDate("Enter new date (yyyy-MM-dd): ");

        boolean updated = manager.updateExpense(id, description, amount, date);
        System.out.println(updated ? "Expense updated." : "Failed to update expense.");
    }

    private static void deleteExpense() {
        System.out.print("Enter expense ID to delete: ");
        String id = scanner.nextLine().trim();

        boolean deleted = manager.deleteExpense(id);
        System.out.println(deleted ? "Expense deleted." : "Expense not found.");
    }

    private static void viewExpenses() {
        var expenses = manager.getAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("All Expenses:");
            expenses.forEach(System.out::println);
        }
    }

    private static void viewSummaryAll() {
        double total = manager.getTotalExpense();
        System.out.printf("Total expenses: $%.2f%n", total);
    }

    private static void viewSummaryByMonth() {
        System.out.print("Enter month (1-12): ");
        int month;
        try {
            month = Integer.parseInt(scanner.nextLine());
            if (month < 1 || month > 12) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid month.");
            return;
        }

        System.out.print("Enter year: ");
        int year;
        try {
           year = Integer.parseInt(scanner.nextLine());
            if (year < 1990 || year > 2100) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println("Invalid year.");
            return;
        }

        double total = manager.getMonthlyExpense(month, year);

        String monthName = LocalDate.of(year, month, 1).getMonth().name();
        System.out.printf("Total expenses for %s %d: $%.2f%n", monthName, year, total);
    }

    private static double readValidAmount(String prompt) {
        double amount;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                amount = Double.parseDouble(input);
                if (amount < 0) throw new NumberFormatException();
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a positive number.");
            }
        }
    }

    private static LocalDate readValidDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            }
        }
    }
}
