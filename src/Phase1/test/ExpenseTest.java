package Phase1.test;

import Phase1.Expense;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {
    @Test
    void testExpenseCreationAndGetters() {
        LocalDate date = LocalDate.of(2024, 4, 1);
        Expense expense = new Expense("Lunch", 12.50, date);

        assertNotNull(expense.getId());
        assertEquals("Lunch", expense.getDescription());
        assertEquals(12.50, expense.getAmount());
        assertEquals(date, expense.getDate());
    }

    @Test
    void testSetters() {
        Expense expense = new Expense("Old", 1.00, LocalDate.now());

        expense.setDescription("New");
        expense.setAmount(20.00);
        LocalDate newDate = LocalDate.of(2024, 1, 1);
        expense.setDate(newDate);

        assertEquals("New", expense.getDescription());
        assertEquals(20.00, expense.getAmount());
        assertEquals(newDate, expense.getDate());
    }
}