package Phase3;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ExpenseManagerDB {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/expensetracker";
    private static final String USER = "postgres";
    private static final String PASS = "root";


    public ExpenseManagerDB() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS expenses (" +
                    "id UUID PRIMARY KEY," +
                    "description TEXT NOT NULL," +
                    "amount DOUBLE PRECISION NOT NULL," +
                    "date DATE NOT NULL)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Failed to initialize database: " + e.getMessage());
        }
    }

    public void addExpense(String description, double amount, LocalDate date) {
        String sql = "INSERT INTO expenses (id, description, amount, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, UUID.randomUUID());
            pstmt.setString(2, description);
            pstmt.setDouble(3, amount);
            pstmt.setDate(4, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding expense: " + e.getMessage());
        }
    }

    public boolean updateExpense(UUID id, String description, double amount, LocalDate date) {
        String sql = "UPDATE expenses SET description = ?, amount = ?, date = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setDouble(2, amount);
            pstmt.setDate(3, java.sql.Date.valueOf(date));
            pstmt.setObject(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating expense: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteExpense(UUID id) {
        String sql = "DELETE FROM expenses WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    public List<Expense> getAllExpenses() {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                UUID id = (UUID) rs.getObject("id");
                String description = rs.getString("description");
                double amount = rs.getDouble("amount");
                LocalDate date = rs.getDate("date").toLocalDate();
                list.add(new Expense(id, description, amount, date));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving expenses: " + e.getMessage());
        }
        return list;
    }

    public double getMonthlyExpense(int month, int year) {
        String sql = "SELECT SUM(amount) FROM expenses WHERE EXTRACT(MONTH FROM date) = ? AND EXTRACT(YEAR FROM date) = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, month);
            pstmt.setInt(2, year);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving monthly total: " + e.getMessage());
        }
        return 0.0;
    }

    public double getTotalExpense() {
        String sql = "SELECT SUM(amount) FROM expenses";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving total expense: " + e.getMessage());
        }
        return 0.0;
    }
}
