import java.util.HashMap;
import java.util.ArrayList;

public class Bank {
    HashMap<Integer, User> users;
    HashMap<Integer, String> passwords; // Stores account numbers and their corresponding passwords
    int nextAccountNumber = 1000;

    public Bank() {
        users = new HashMap<>();
        passwords = new HashMap<>();
    }

    public User registerUser(String name, String address, String contactInfo, double initialDeposit, String password) throws Exception {
        int accountNumber = nextAccountNumber++;
        Account account = new Account(accountNumber, initialDeposit);
        User user = new User(name, address, contactInfo, account);
        users.put(accountNumber, user);
        passwords.put(accountNumber, password); // Store the password associated with the account number
        return user;
    }

    public boolean login(int accountNumber, String password) {
        if (passwords.containsKey(accountNumber)) {
            return passwords.get(accountNumber).equals(password);
        }
        return false;
    }

    public void deposit(int accountNumber, double amount) throws Exception {
        if (!users.containsKey(accountNumber)) {
            throw new Exception("Account does not exist.");
        }
        Account account = users.get(accountNumber).getAccount();
        account.deposit(amount);
    }

    public void withdraw(int accountNumber, double amount) throws Exception {
        if (!users.containsKey(accountNumber)) {
            throw new Exception("Account does not exist.");
        }
        Account account = users.get(accountNumber).getAccount();
        account.withdraw(amount);
    }

    public void transfer(int fromAccount, int toAccount, double amount) throws Exception {
        if (!users.containsKey(fromAccount) || !users.containsKey(toAccount)) {
            throw new Exception("One or both accounts do not exist.");
        }
        Account from = users.get(fromAccount).getAccount();
        Account to = users.get(toAccount).getAccount();
        from.withdraw(amount);
        to.deposit(amount);
    }

    public ArrayList<String> getStatement(int accountNumber) throws Exception {
        if (!users.containsKey(accountNumber)) {
            throw new Exception("Account does not exist.");
        }
        Account account = users.get(accountNumber).getAccount();
        return account.getStatement();
    }

    public void changePassword(int accountNumber, String oldPassword, String newPassword) throws Exception {
        if (login(accountNumber, oldPassword)) {
            passwords.put(accountNumber, newPassword); // Update the password
        } else {
            throw new Exception("Old password is incorrect.");
        }
    }
}
