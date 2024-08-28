import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BankingInformtionSystem extends JFrame {
    private Bank bank;
    private JTextField accountNumberField, nameField, addressField, contactField, amountField, transferToAccountField;
    private JPasswordField passwordField, loginPasswordField, changePasswordField, confirmPasswordField;
    private JTextArea statementArea;
    private int currentAccountNumber;
    ImageIcon register;
    ImageIcon login;
    ImageIcon deposit;
    ImageIcon withdraw;
    ImageIcon transfer;
    ImageIcon statement;

    public BankingInformtionSystem() {
        bank = new Bank();
        setTitle("Banking System");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        register=new ImageIcon("User.png");
        login=new ImageIcon("login.png");
        deposit=new ImageIcon("deposit.png");
        withdraw=new ImageIcon("withdraw.png");
        transfer=new ImageIcon("transfer.png");
        statement=new ImageIcon("statement.png");
        
        // Set a background color
        Color backgroundColor = new Color(210, 225, 240); // Light blue color

        // Label at the top
        JLabel titleLabel = new JLabel("Banking System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setOpaque(true); // Allows background color
        titleLabel.setBackground(backgroundColor);
        add(titleLabel, BorderLayout.NORTH);

        // Panels for different functionalities
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor); // Set background color for the panel
        add(mainPanel, BorderLayout.CENTER);

        // Buttons with preferred size
        JButton registerButton = new JButton("Register User");
        registerButton.setIcon(register);
        JButton loginButton = new JButton("Login");
        loginButton.setIcon(login);
        JButton depositButton = new JButton("Deposit");
        depositButton.setIcon(deposit);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setIcon(withdraw);
        JButton transferButton = new JButton("Transfer");
        transferButton.setIcon(transfer);
        JButton statementButton = new JButton("View Statement");
        statementButton.setIcon(statement);
        
        Dimension buttonSize = new Dimension(150, 30);
        registerButton.setPreferredSize(buttonSize);
        loginButton.setPreferredSize(buttonSize);
        depositButton.setPreferredSize(buttonSize);
        withdrawButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);
        statementButton.setPreferredSize(buttonSize);

        // Center align the buttons
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        transferButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statementButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons to the panel
        mainPanel.add(Box.createVerticalStrut(10)); // Add space at the top
        mainPanel.add(registerButton);
        mainPanel.add(Box.createVerticalStrut(10)); // Add space between buttons
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(depositButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(withdrawButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(transferButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(statementButton);
        mainPanel.add(Box.createVerticalStrut(10)); // Add space at the bottom

        // Register User functionality
        registerButton.addActionListener(e -> showRegisterForm());

        // Login functionality
        loginButton.addActionListener(e -> showLoginForm());

        // Deposit functionality
        depositButton.addActionListener(e -> showDepositForm());

        // Withdraw functionality
        withdrawButton.addActionListener(e -> showWithdrawForm());

        // Transfer functionality
        transferButton.addActionListener(e -> showTransferForm());

        // View Statement functionality
        statementButton.addActionListener(e -> showStatement());
    }

    private void showRegisterForm() {
        // Load an icon
        ImageIcon register = new ImageIcon("user.png");

        JPanel panel = new JPanel(new GridLayout(6, 2));
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

        // Display the form with the icon
        int result = JOptionPane.showConfirmDialog(null, panel, "Register User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, register);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String address = addressField.getText();
                String contact = contactField.getText();
                double initialDeposit = Double.parseDouble(amountField.getText());
                String password = new String(passwordField.getPassword());

                User user = bank.registerUser(name, address, contact, initialDeposit, password);
                ImageIcon successIcon=new ImageIcon("success.png");
                JOptionPane.showMessageDialog(this, "User registered successfully. Account Number: " + user.getAccount().getAccountNumber(),"Registration Success", 
                        JOptionPane.INFORMATION_MESSAGE, 
                        successIcon);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }


	private void showLoginForm() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        accountNumberField = new JTextField();
        loginPasswordField = new JPasswordField();
        
        ImageIcon login =new ImageIcon("login.png");
        panel.add(new JLabel("Account Number:"));
        panel.add(accountNumberField);
        panel.add(new JLabel("Password:"));
        panel.add(loginPasswordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Login", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,login);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int accountNumber = Integer.parseInt(accountNumberField.getText());
                String password = new String(loginPasswordField.getPassword());
                ImageIcon success=new ImageIcon("success.png");
                if (bank.login(accountNumber, password)) {
                    currentAccountNumber = accountNumber;
                    JOptionPane.showMessageDialog(this, "Login successful.",null, JOptionPane.INFORMATION_MESSAGE,success);
                } else {
                	ImageIcon failed =new ImageIcon("no.png");
                    JOptionPane.showMessageDialog(this, "Invalid account number or password.",null, JOptionPane.INFORMATION_MESSAGE, failed);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void showDepositForm() {
        if (!isUserLoggedIn()) return;

        JPanel panel = new JPanel(new GridLayout(2, 2));
        amountField = new JTextField();
        
        ImageIcon deposit=new ImageIcon("deposit.png");
        panel.add(new JLabel("Amount to Deposit:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Deposit", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,deposit);

        if (result == JOptionPane.OK_OPTION) {
            try {
            	ImageIcon success=new ImageIcon("success.png");
                double amount = Double.parseDouble(amountField.getText());
                bank.deposit(currentAccountNumber, amount);
                JOptionPane.showMessageDialog(this, "Deposit successful.",null, JOptionPane.INFORMATION_MESSAGE,success);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void showWithdrawForm() {
        if (!isUserLoggedIn()) return;
        
        ImageIcon withdraw=new ImageIcon("withdraw.png");
        
        JPanel panel = new JPanel(new GridLayout(2, 2));
        amountField = new JTextField();

        panel.add(new JLabel("Amount to Withdraw:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Withdraw", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,withdraw);

        if (result == JOptionPane.OK_OPTION) {
            try {
            	ImageIcon success=new ImageIcon("success.png");
                double amount = Double.parseDouble(amountField.getText());
                bank.withdraw(currentAccountNumber, amount);
                JOptionPane.showMessageDialog(this, "Withdrawal successful.",null, JOptionPane.INFORMATION_MESSAGE,success);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void showTransferForm() {
        if (!isUserLoggedIn()) return;
        
        ImageIcon transfer=new ImageIcon("transfer.png");
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        transferToAccountField = new JTextField();
        amountField = new JTextField();

        panel.add(new JLabel("Transfer to Account:"));
        panel.add(transferToAccountField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Transfer Funds", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE,transfer);

        if (result == JOptionPane.OK_OPTION) {
            try {
            	ImageIcon success=new ImageIcon("success.png");
                int toAccount = Integer.parseInt(transferToAccountField.getText());
                double amount = Double.parseDouble(amountField.getText());
                bank.transfer(currentAccountNumber, toAccount, amount);
                JOptionPane.showMessageDialog(this, "Transfer successful.",null, JOptionPane.INFORMATION_MESSAGE,success);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void showStatement() {
    	
        if (!isUserLoggedIn()) return;

        try {
            statementArea = new JTextArea(10, 30);
            ArrayList<String> statement = bank.getStatement(currentAccountNumber);
            for (String entry : statement) {
                statementArea.append(entry + "\n");
            }
            ImageIcon state=new ImageIcon("statement.png");
            JOptionPane.showMessageDialog(this, new JScrollPane(statementArea), "Account Statement", JOptionPane.INFORMATION_MESSAGE,state);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private boolean isUserLoggedIn() {

        if (currentAccountNumber == 0) {
            JOptionPane.showMessageDialog(this, "Please login first.");
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankingInformtionSystem bankGUI = new BankingInformtionSystem();
            bankGUI.setVisible(true);
        });
    }
}
