package Phase2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseManagerTest {

    private ExpenseManager manager;
    private final File testFile = new File("src/Phase2/expenses.json");

    @BeforeEach
    public void setUp() {
        if (testFile.exists()) testFile.delete(); // Clean up before each test
        manager = new ExpenseManager();
    }

    @AfterEach
    public void tearDown() {
        if (testFile.exists()) testFile.delete(); // Clean up after each test
    }

    @Test
    public void testAddExpense() {
        Expense e = manager.addExpense("Lunch", 12.50, LocalDate.now());
        assertNotNull(e.getId());
        assertEquals("Lunch", e.getDescription());
        assertEquals(12.50, e.getAmount());
        assertEquals(1, manager.getAllExpenses().size());
    }

    @Test
    public void testUpdateExpense() {
        Expense e = manager.addExpense("Taxi", 20.0, LocalDate.now());
        boolean updated = manager.updateExpense(e.getId(), "Uber", 22.0, LocalDate.now());
        assertTrue(updated);
        Expense updatedExpense = manager.findExpenseById(e.getId());
        assertEquals("Uber", updatedExpense.getDescription());
        assertEquals(22.0, updatedExpense.getAmount());
    }

    @Test
    void testUpdateExpense_noUpdation() {
        Expense e = manager.addExpense("Taxi", 20.0, LocalDate.now());
        boolean updated = manager.updateExpense("random-id", "Dinner", 15.0, LocalDate.now());
        assertFalse(updated);
        Expense updatedExpense = manager.findExpenseById(e.getId());
        assertEquals("Taxi", updatedExpense.getDescription());
        assertEquals(20.0, updatedExpense.getAmount());
    }


    @Test
    public void testDeleteExpense() {
        Expense e = manager.addExpense("Groceries", 30.0, LocalDate.now());
        assertTrue(manager.deleteExpense(e.getId()));
        assertEquals(0, manager.getAllExpenses().size());
    }

    @Test
    void testDeleteExpense_noDeletion() {
        Expense e = manager.addExpense("Groceries", 30.0, LocalDate.now());
        assertFalse(manager.deleteExpense("random-id"));
        assertEquals(1, manager.getAllExpenses().size());
    }

    @Test
    public void testGetMonthlyExpense() {
        manager.addExpense("Food", 10.0, LocalDate.of(2024, 5, 1));
        manager.addExpense("Transport", 15.0, LocalDate.of(2024, 5, 2));
        manager.addExpense("Other", 20.0, LocalDate.of(2024, 4, 1));
        double mayTotal = manager.getMonthlyExpense(5, 2024);
        assertEquals(25.0, mayTotal);
    }

    @Test
    public void testSaveAndLoadExpenses() {
        Expense e = manager.addExpense("Book", 18.0, LocalDate.now());
        manager.saveExpenses();

        ExpenseManager newManager = new ExpenseManager();
        List<Expense> loaded = newManager.getAllExpenses();
        assertEquals(1, loaded.size());
        assertEquals("Book", loaded.get(0).getDescription());
    }
}
