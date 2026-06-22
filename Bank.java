import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
    private static final String DATA_FILE = "bank-data.ser";
    private static final int MIN_PASSWORD_LENGTH = 4;

    private HashMap<Integer, User> users;
    private HashMap<Integer, PasswordRecord> passwords;
    private int nextAccountNumber = 1000;

    public Bank() {
        users = new HashMap<>();
        passwords = new HashMap<>();
        loadData();
    }

    public User registerUser(String name, String address, String contactInfo, double initialDeposit, String password) throws Exception {
        requireText(name, "Name");
        requireText(address, "Address");
        requireText(contactInfo, "Contact information");
        validateInitialDeposit(initialDeposit);
        validatePassword(password);

        int accountNumber = nextAccountNumber++;
        Account account = new Account(accountNumber, initialDeposit);
        User user = new User(name, address, contactInfo, account);

        users.put(accountNumber, user);
        passwords.put(accountNumber, PasswordRecord.create(password));
        saveData();

        return user;
    }

    public boolean login(int accountNumber, String password) {
        PasswordRecord record = passwords.get(accountNumber);
        return record != null && record.matches(password);
    }

    public void deposit(int accountNumber, double amount) throws Exception {
        Account account = getAccount(accountNumber);
        account.deposit(amount);
        saveData();
    }

    public void withdraw(int accountNumber, double amount) throws Exception {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
        saveData();
    }

    public void transfer(int fromAccount, int toAccount, double amount) throws Exception {
        if (fromAccount == toAccount) {
            throw new Exception("Cannot transfer to the same account.");
        }

        validatePositiveAmount(amount);
        Account from = getAccount(fromAccount);
        Account to = getAccount(toAccount);

        from.withdraw(amount, "Transfer to account " + toAccount);
        to.deposit(amount, "Transfer from account " + fromAccount);
        saveData();
    }

    public double getBalance(int accountNumber) throws Exception {
        return getAccount(accountNumber).getBalance();
    }

    public ArrayList<String> getStatement(int accountNumber) throws Exception {
        return getAccount(accountNumber).getStatement();
    }

    public void changePassword(int accountNumber, String oldPassword, String newPassword) throws Exception {
        PasswordRecord record = passwords.get(accountNumber);

        if (record == null || !record.matches(oldPassword)) {
            throw new Exception("Old password is incorrect.");
        }

        validatePassword(newPassword);
        passwords.put(accountNumber, PasswordRecord.create(newPassword));
        saveData();
    }

    private Account getAccount(int accountNumber) throws Exception {
        User user = users.get(accountNumber);

        if (user == null) {
            throw new Exception("Account does not exist.");
        }

        return user.getAccount();
    }

    private void loadData() {
        File dataFile = new File(DATA_FILE);

        if (!dataFile.exists()) {
            return;
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(dataFile))) {
            DataStore dataStore = (DataStore) input.readObject();
            users = dataStore.users != null ? dataStore.users : new HashMap<>();
            passwords = dataStore.passwords != null ? dataStore.passwords : new HashMap<>();
            nextAccountNumber = Math.max(dataStore.nextAccountNumber, 1000);
        } catch (IOException | ClassNotFoundException exception) {
            System.err.println("Could not load saved bank data: " + exception.getMessage());
            users = new HashMap<>();
            passwords = new HashMap<>();
            nextAccountNumber = 1000;
        }
    }

    private void saveData() throws Exception {
        DataStore dataStore = new DataStore(users, passwords, nextAccountNumber);

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            output.writeObject(dataStore);
        }
    }

    private static void requireText(String value, String fieldName) throws Exception {
        if (value == null || value.trim().isEmpty()) {
            throw new Exception(fieldName + " is required.");
        }
    }

    private static void validateInitialDeposit(double amount) throws Exception {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount < 0) {
            throw new Exception("Initial deposit cannot be negative.");
        }
    }

    private static void validatePositiveAmount(double amount) throws Exception {
        if (Double.isNaN(amount) || Double.isInfinite(amount) || amount <= 0) {
            throw new Exception("Amount must be greater than zero.");
        }
    }

    private static void validatePassword(String password) throws Exception {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new Exception("Password must contain at least " + MIN_PASSWORD_LENGTH + " characters.");
        }
    }

    private static class DataStore implements Serializable {
        private static final long serialVersionUID = 1L;

        private final HashMap<Integer, User> users;
        private final HashMap<Integer, PasswordRecord> passwords;
        private final int nextAccountNumber;

        private DataStore(HashMap<Integer, User> users, HashMap<Integer, PasswordRecord> passwords, int nextAccountNumber) {
            this.users = users;
            this.passwords = passwords;
            this.nextAccountNumber = nextAccountNumber;
        }
    }

    private static class PasswordRecord implements Serializable {
        private static final long serialVersionUID = 1L;

        private final byte[] salt;
        private final byte[] hash;

        private PasswordRecord(byte[] salt, byte[] hash) {
            this.salt = salt;
            this.hash = hash;
        }

        private static PasswordRecord create(String password) throws NoSuchAlgorithmException {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            return new PasswordRecord(salt, hashPassword(password, salt));
        }

        private boolean matches(String password) {
            if (password == null) {
                return false;
            }

            try {
                return MessageDigest.isEqual(hash, hashPassword(password, salt));
            } catch (NoSuchAlgorithmException exception) {
                return false;
            }
        }

        private static byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(salt);
            return digest.digest(password.getBytes(StandardCharsets.UTF_8));
        }
    }
}
