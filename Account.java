import java.util.ArrayList;

public class Account {
    int accountNumber;
    double balance;
    ArrayList<String> statement;

    public Account(int accountNumber, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.balance = initialDeposit;
        this.statement = new ArrayList<>();
        statement.add("Account created with initial deposit: " + initialDeposit);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        statement.add("Deposited: " + amount + " | Balance: " + balance);
    }

    public void withdraw(double amount) throws Exception {
        if (amount > balance) {
            throw new Exception("Insufficient funds.");
        }
        balance -= amount;
        statement.add("Withdrew: " + amount + " | Balance: " + balance);
    }

    public ArrayList<String> getStatement() {
        return statement;
    }
}
