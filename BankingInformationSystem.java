import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Locale;

public class BankingInformationSystem extends JFrame {
    private static final long serialVersionUID = 1L;

    private final Bank bank;
    private JTextField accountNumberField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField contactField;
    private JTextField amountField;
    private JTextField transferToAccountField;
    private JPasswordField passwordField;
    private JPasswordField loginPasswordField;
    private Integer currentAccountNumber;

    private final ImageIcon registerIcon;
    private final ImageIcon loginIcon;
    private final ImageIcon depositIcon;
    private final ImageIcon withdrawIcon;
    private final ImageIcon transferIcon;
    private final ImageIcon statementIcon;
    private final ImageIcon successIcon;
    private final ImageIcon failedIcon;

    public BankingInformationSystem() {
        bank = new Bank();
        setTitle("Banking Information System");
        setSize(460, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        registerIcon = new ImageIcon("User.png");
        loginIcon = new ImageIcon("login.png");
        depositIcon = new ImageIcon("deposit.png");
        withdrawIcon = new ImageIcon("withdraw.png");
        transferIcon = new ImageIcon("transfer.png");
        statementIcon = new ImageIcon("statement.png");
        successIcon = new ImageIcon("success.png");
        failedIcon = new ImageIcon("no.png");

        Color backgroundColor = new Color(210, 225, 240);

        JLabel titleLabel = new JLabel("Banking Information System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(backgroundColor);
        add(titleLabel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);
        add(mainPanel, BorderLayout.CENTER);

        JButton registerButton = createMenuButton("Register User", registerIcon);
        JButton loginButton = createMenuButton("Login", loginIcon);
        JButton depositButton = createMenuButton("Deposit", depositIcon);
        JButton withdrawButton = createMenuButton("Withdraw", withdrawIcon);
        JButton transferButton = createMenuButton("Transfer", transferIcon);
        JButton statementButton = createMenuButton("View Statement", statementIcon);
        JButton changePasswordButton = createMenuButton("Change Password", loginIcon);
        JButton logoutButton = createMenuButton("Logout", failedIcon);

        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(depositButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(withdrawButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(transferButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(statementButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(changePasswordButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(logoutButton);
        mainPanel.add(Box.createVerticalStrut(12));

        registerButton.addActionListener(e -> showRegisterForm());
        loginButton.addActionListener(e -> showLoginForm());
        depositButton.addActionListener(e -> showDepositForm());
        withdrawButton.addActionListener(e -> showWithdrawForm());
        transferButton.addActionListener(e -> showTransferForm());
        statementButton.addActionListener(e -> showStatement());
        changePasswordButton.addActionListener(e -> showChangePasswordForm());
        logoutButton.addActionListener(e -> logout());

        setLocationRelativeTo(null);
    }

    private JButton createMenuButton(String label, ImageIcon icon) {
        JButton button = new JButton(label);
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(220, 36));
        button.setMaximumSize(new Dimension(220, 36));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private void showRegisterForm() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        nameField = new JTextField();
        addressField = new JTextField();
        contactField = new JTextField();
        amountField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Contact:"));
        panel.add(contactField);
        panel.add(new JLabel("Initial Deposit:"));
        panel.add(amountField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Register User",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                registerIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String address = addressField.getText().trim();
                String contact = contactField.getText().trim();
                double initialDeposit = parseAmount(amountField);
                String password = new String(passwordField.getPassword());

                User user = bank.registerUser(name, address, contact, initialDeposit, password);
                JOptionPane.showMessageDialog(
                        this,
                        "User registered successfully.\nAccount Number: " + user.getAccount().getAccountNumber(),
                        "Registration Success",
                        JOptionPane.INFORMATION_MESSAGE,
                        successIcon
                );
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void showLoginForm() {
        JPanel panel = new JPanel(new GridLayout(2, 2));
        accountNumberField = new JTextField();
        loginPasswordField = new JPasswordField();

        panel.add(new JLabel("Account Number:"));
        panel.add(accountNumberField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                loginIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int accountNumber = parseAccountNumber(accountNumberField);
                String password = new String(loginPasswordField.getPassword());

                if (bank.login(accountNumber, password)) {
                    currentAccountNumber = accountNumber;
                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful.\nBalance: " + formatAmount(bank.getBalance(accountNumber)),
                            "Login Success",
                            JOptionPane.INFORMATION_MESSAGE,
                            successIcon
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid account number or password.",
                            "Login Failed",
                            JOptionPane.INFORMATION_MESSAGE,
                            failedIcon
                    );
                }
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void showDepositForm() {
        if (!isUserLoggedIn()) {
            return;
        }

        JPanel panel = new JPanel(new GridLayout(1, 2));
        amountField = new JTextField();
        panel.add(new JLabel("Amount to Deposit:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Deposit",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                depositIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = parseAmount(amountField);
                bank.deposit(currentAccountNumber, amount);
                showSuccessWithBalance("Deposit successful.");
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void showWithdrawForm() {
        if (!isUserLoggedIn()) {
            return;
        }

        JPanel panel = new JPanel(new GridLayout(1, 2));
        amountField = new JTextField();
        panel.add(new JLabel("Amount to Withdraw:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Withdraw",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                withdrawIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = parseAmount(amountField);
                bank.withdraw(currentAccountNumber, amount);
                showSuccessWithBalance("Withdrawal successful.");
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void showTransferForm() {
        if (!isUserLoggedIn()) {
            return;
        }

        JPanel panel = new JPanel(new GridLayout(2, 2));
        transferToAccountField = new JTextField();
        amountField = new JTextField();

        panel.add(new JLabel("Transfer to Account:"));
        panel.add(transferToAccountField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Transfer Funds",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                transferIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                int toAccount = parseAccountNumber(transferToAccountField);
                double amount = parseAmount(amountField);
                bank.transfer(currentAccountNumber, toAccount, amount);
                showSuccessWithBalance("Transfer successful.");
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void showStatement() {
        if (!isUserLoggedIn()) {
            return;
        }

        try {
            JTextArea statementArea = new JTextArea(12, 42);
            statementArea.setEditable(false);
            ArrayList<String> statement = bank.getStatement(currentAccountNumber);

            for (String entry : statement) {
                statementArea.append(entry + System.lineSeparator());
            }

            JOptionPane.showMessageDialog(
                    this,
                    new JScrollPane(statementArea),
                    "Account Statement",
                    JOptionPane.INFORMATION_MESSAGE,
                    statementIcon
            );
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void showChangePasswordForm() {
        if (!isUserLoggedIn()) {
            return;
        }

        JPanel panel = new JPanel(new GridLayout(2, 2));
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();

        panel.add(new JLabel("Old Password:"));
        panel.add(oldPasswordField);
        panel.add(new JLabel("New Password:"));
        panel.add(newPasswordField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Change Password",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                loginIcon
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                String oldPassword = new String(oldPasswordField.getPassword());
                String newPassword = new String(newPasswordField.getPassword());
                bank.changePassword(currentAccountNumber, oldPassword, newPassword);

                JOptionPane.showMessageDialog(
                        this,
                        "Password changed successfully.",
                        "Password Updated",
                        JOptionPane.INFORMATION_MESSAGE,
                        successIcon
                );
            } catch (Exception exception) {
                showError(exception);
            }
        }
    }

    private void logout() {
        if (!isUserLoggedIn()) {
            return;
        }

        currentAccountNumber = null;
        JOptionPane.showMessageDialog(
                this,
                "Logged out successfully.",
                "Logout",
                JOptionPane.INFORMATION_MESSAGE,
                successIcon
        );
    }

    private boolean isUserLoggedIn() {
        if (currentAccountNumber == null) {
            JOptionPane.showMessageDialog(this, "Please login first.");
            return false;
        }

        return true;
    }

    private int parseAccountNumber(JTextField field) throws Exception {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (NumberFormatException exception) {
            throw new Exception("Please enter a valid account number.");
        }
    }

    private double parseAmount(JTextField field) throws Exception {
        try {
            return Double.parseDouble(field.getText().trim());
        } catch (NumberFormatException exception) {
            throw new Exception("Please enter a valid amount.");
        }
    }

    private void showSuccessWithBalance(String message) throws Exception {
        JOptionPane.showMessageDialog(
                this,
                message + "\nCurrent Balance: " + formatAmount(bank.getBalance(currentAccountNumber)),
                "Success",
                JOptionPane.INFORMATION_MESSAGE,
                successIcon
        );
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, "Error: " + exception.getMessage());
    }

    private static String formatAmount(double amount) {
        return String.format(Locale.US, "%.2f", amount);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankingInformationSystem bankGUI = new BankingInformationSystem();
            bankGUI.setVisible(true);
        });
    }
}

class BankingInformtionSystem extends BankingInformationSystem {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        BankingInformationSystem.main(args);
    }
}
