import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter STATEMENT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final int accountNumber;
    private double balance;
    private final ArrayList<String> statement;

    public Account(int accountNumber, double initialDeposit) throws Exception {
        if (Double.isNaN(initialDeposit) || Double.isInfinite(initialDeposit) || initialDeposit < 0) {
            throw new Exception("Initial deposit cannot be negative.");
        }

        this.accountNumber = accountNumber;
        this.balance = 0;
        this.statement = new ArrayList<>();
        addStatement("Account created");

        if (initialDeposit > 0) {
            deposit(initialDeposit, "Initial deposit");
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws Exception {
        deposit(amount, "Deposited");
    }

    public void deposit(double amount, String label) throws Exception {
        validatePositiveAmount(amount);
        balance += amount;
        addStatement(label + ": " + formatAmount(amount) + " | Balance: " + formatAmount(balance));
    }

    public void withdraw(double amount) throws Exception {
        withdraw(amount, "Withdrew");
    }

    public void withdraw(double amount, String label) throws Exception {
        validatePositiveAmount(amount);

        if (amount > balance) {
            throw new Exception("Insufficient funds.");
        }

        balance -= amount;
        addStatement(label + ": " + formatAmount(amount) + " | Balance: " + formatAmount(balance));
    }

    public ArrayList<String> getStatement() {
        return new ArrayList<>(statement);
    }

    private void addStatement(String message) {
        statement.add(STATEMENT_FORMAT.format(LocalDateTime.now()) + " - " + message);
    }

    private static void validatePositiveAmount(double amount) throws Exception {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }
    }

    private static String formatAmount(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }
}
