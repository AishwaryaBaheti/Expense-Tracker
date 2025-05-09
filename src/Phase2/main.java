package Phase2;

import java.time.LocalDate;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        ExpenseManager manager = new ExpenseManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        Runtime.getRuntime().addShutdownHook(new Thread(manager::saveExpenses));

        while (running) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. View Monthly Summary");
            System.out.println("6. View Total Expense");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amt = scanner.nextDouble();
                    System.out.print("Date (YYYY-MM-DD): ");
                    String dateStr = scanner.next();
                    LocalDate date = LocalDate.parse(dateStr);
                    Expense e = manager.addExpense(desc, amt, date);
                    System.out.println("Added expense with ID: " + e.getId());
                }
                case 2 -> {
                    System.out.println("All Expenses:");
                    for (Expense e : manager.getAllExpenses()) {
                        System.out.println(e);
                    }
                }
                case 3 -> {
                    System.out.print("Expense ID to update: ");
                    String id = scanner.nextLine();
                    System.out.print("New Description: ");
                    String newDesc = scanner.nextLine();
                    System.out.print("New Amount: ");
                    double newAmt = scanner.nextDouble();
                    System.out.print("New Date (YYYY-MM-DD): ");
                    String newDateStr = scanner.next();
                    LocalDate newDate = LocalDate.parse(newDateStr);
                    boolean success = manager.updateExpense(id, newDesc, newAmt, newDate);
                    System.out.println(success ? "Updated successfully." : "Expense not found.");
                }
                case 4 -> {
                    System.out.print("Expense ID to delete: ");
                    String id = scanner.nextLine();
                    boolean deleted = manager.deleteExpense(id);
                    System.out.println(deleted ? "Deleted successfully." : "Expense not found.");
                }
                case 5 -> {
                    System.out.print("Enter month (1-12): ");
                    int month = scanner.nextInt();
                    System.out.print("Enter year (e.g., 2024): ");
                    int year = scanner.nextInt();
                    double monthlyTotal = manager.getMonthlyExpense(month, year);
                    System.out.println("Total for " + month + "/" + year + ": " + monthlyTotal);
                }
                case 6 -> System.out.println("Total Expense: " + manager.getTotalExpense());
                case 7 -> {
                    running = false;
                    System.out.println("Exiting... Data will be saved.");
                }
                default -> System.out.println("Invalid option.");
            }
        }

        scanner.close();
        manager.saveExpenses(); // Extra save for environments that don't handle shutdown hooks well
    }
}
