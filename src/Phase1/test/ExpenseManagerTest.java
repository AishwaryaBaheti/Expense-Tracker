package Phase1.test;

import Phase1.Expense;
import Phase1.ExpenseManager;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {

    private ExpenseManager manager;

    @BeforeEach
    void setup() {
        manager = new ExpenseManager();
    }

    @Test
    void testAddExpense() {
        Expense e = manager.addExpense("Coffee", 4.5, LocalDate.of(2025, 4, 10));
        assertNotNull(e.getId());
        assertEquals(1, manager.getAllExpenses().size());
    }

    @Test
    void testUpdateExpense() {
        Expense e = manager.addExpense("Lunch", 10, LocalDate.now());
        boolean updated = manager.updateExpense(e.getId(), "Dinner", 15, LocalDate.now());
        assertTrue(updated);
        assertEquals("Dinner", manager.findExpenseById(e.getId()).getDescription());
    }

    @Test
    void testDeleteExpense() {
        Expense e = manager.addExpense("Taxi", 20, LocalDate.now());
        assertTrue(manager.deleteExpense(e.getId()));
        assertEquals(0, manager.getAllExpenses().size());
    }

    @Test
    void testDeleteExpense_noDeletion() {
        Expense e = manager.addExpense("Taxi", 20, LocalDate.now());
        assertFalse(manager.deleteExpense("random-id"));
        assertEquals(1, manager.getAllExpenses().size());
    }

    @Test
    void testMonthlySummary() {
        manager.addExpense("Rent", 1000, LocalDate.of(2025, 4, 1));
        manager.addExpense("Food", 200, LocalDate.of(2025, 4, 15));
        manager.addExpense("Gym", 50, LocalDate.of(2025, 3, 20));

        double aprilTotal = manager.getMonthlyExpense(4, 2025);
        assertEquals(1200, aprilTotal, 0.01);
    }
}
