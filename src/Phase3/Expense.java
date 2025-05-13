package Phase3;

import java.time.LocalDate;
import java.util.UUID;

public class Expense {
    private UUID id;
    private String description;
    private double amount;
    private LocalDate date;

    public Expense() {
        // Default constructor
    }

    public Expense(String description, double amount, LocalDate date) {
        this.id = UUID.randomUUID();
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Expense(UUID id, String description, double amount, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
