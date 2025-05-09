package Phase2;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void testConstructorAndGetters() {
        LocalDate date = LocalDate.of(2024, 5, 9);
        Expense expense = new Expense("Lunch", 15.5, date);

        assertNotNull(expense.getId());
        assertEquals("Lunch", expense.getDescription());
        assertEquals(15.5, expense.getAmount());
        assertEquals(date, expense.getDate());
    }

    @Test
    void testSetters() {
        Expense expense = new Expense();
        expense.setId("12345");
        expense.setDescription("Groceries");
        expense.setAmount(100.0);
        LocalDate date = LocalDate.of(2024, 5, 8);
        expense.setDate(date);

        assertEquals("12345", expense.getId());
        assertEquals("Groceries", expense.getDescription());
        assertEquals(100.0, expense.getAmount());
        assertEquals(date, expense.getDate());
    }

    @Test
    void testToString() {
        LocalDate date = LocalDate.of(2024, 5, 9);
        Expense expense = new Expense("Dinner", 25.0, date);

        String result = expense.toString();
        assertTrue(result.contains("Dinner"));
        assertTrue(result.contains("25.0"));
        assertTrue(result.contains("2024-05-09"));
    }
}
