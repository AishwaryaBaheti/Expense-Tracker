package Phase3;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManagerDB manager = new ExpenseManagerDB();



        while (true) {
            System.out.println("\n--- Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Update Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Monthly Summary");
            System.out.println("6. Total Expenses");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    try {
                        System.out.print("Enter description: ");
                        String desc = scanner.nextLine();

                        System.out.print("Enter amount: ");
                        double amt = Double.parseDouble(scanner.nextLine());

                        System.out.print("Enter date (YYYY-MM-DD): ");
                        LocalDate date = LocalDate.parse(scanner.nextLine());

                        manager.addExpense(desc, amt, date);
                        System.out.println("Expense added successfully.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "2":
                    List<Expense> allExpenses = manager.getAllExpenses();
                    if (allExpenses.isEmpty()) {
                        System.out.println("No expenses found.");
                    } else {
                        for (Expense exp : allExpenses) {
                            System.out.println(exp);
                        }
                    }
                    break;

                case "3":
                    try {
                        System.out.print("Enter expense ID to update: ");
                        UUID updateId = UUID.fromString(scanner.nextLine());

                        System.out.print("Enter new description: ");
                        String newDesc = scanner.nextLine();

                        System.out.print("Enter new amount: ");
                        double newAmt = Double.parseDouble(scanner.nextLine());

                        System.out.print("Enter new date (YYYY-MM-DD): ");
                        LocalDate newDate = LocalDate.parse(scanner.nextLine());

                        boolean updated = manager.updateExpense(updateId, newDesc, newAmt, newDate);
                        System.out.println(updated ? "Expense updated." : "Expense not found.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case "4":
                    try {
                        System.out.print("Enter expense ID to delete: ");
                        UUID deleteId = UUID.fromString(scanner.nextLine());

                        boolean deleted = manager.deleteExpense(deleteId);
                        System.out.println(deleted ? "Expense deleted." : "Expense not found.");
                    } catch (Exception e) {
                        System.out.println("Invalid UUID format.");
                    }
                    break;

                case "5":
                    try {
                        System.out.print("Enter month (1-12): ");
                        int month = Integer.parseInt(scanner.nextLine());

                        System.out.print("Enter year: ");
                        int year = Integer.parseInt(scanner.nextLine());

                        double total = manager.getMonthlyExpense(month, year);
                        System.out.printf("Total expense for %d/%d: %.2f%n", month, year, total);
                    } catch (Exception e) {
                        System.out.println("Invalid input.");
                    }
                    break;

                case "6":
                    try {
                        double total = manager.getTotalExpense();
                        System.out.printf("Total expenses: %.2f%n", total);
                    } catch (Exception e) {
                        System.out.println("Failed to retrieve total expenses.");
                    }
                    break;

                case "7":
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
