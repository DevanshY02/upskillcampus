# Banking Information System

A Java Swing desktop application for basic banking operations. Users can register an account, log in, deposit money, withdraw money, transfer funds, change their password, and view an account statement.

## Features

- User registration with account number generation
- Login with password verification
- Deposit, withdrawal, and fund transfer
- Account statement with timestamps
- Change password option
- Persistent account data using a local serialized data file
- Password storage using salted SHA-256 hashes
- Input validation for empty fields, invalid account numbers, and invalid amounts
- Swing-based desktop interface with project image assets

## Project Files

- `BankingInformationSystem.java` - Main Swing GUI
- `Bank.java` - Banking service logic, login, persistence, and password hashing
- `Account.java` - Account balance and transaction statement logic
- `User.java` - User profile model
- `*.png` - UI icons used by the Swing application
- `BankingInformationSystem_Devansh_USC_UCT.pdf` - Project report

## Requirements

- Java JDK 8 or newer

Check Java installation:

```bash
java -version
javac -version
```

## How To Run

Compile the project:

```bash
javac *.java
```

Run the application:

```bash
java BankingInformationSystem
```

The old misspelled class name is still supported for compatibility:

```bash
java BankingInformtionSystem
```

## Data Storage

The app creates a local `bank-data.ser` file after the first registration or account update. This file stores account data locally so users and balances remain available after closing and reopening the app.

The file is ignored by Git because it contains local user data.

## Recent Upgrades

- Added validation to block negative deposits, withdrawals, and transfers
- Added validation for required registration fields
- Added persistent storage for account data
- Replaced plain-text password storage with salted password hashes
- Added timestamped transaction statements
- Added change-password and logout actions in the UI
- Renamed the main class to `BankingInformationSystem`
- Added `.gitignore` and project documentation

## Future Improvements

- Replace serialized file storage with SQLite or MySQL
- Add an admin dashboard for viewing all accounts
- Export account statements as PDF or CSV
- Add unit tests for banking rules
- Improve the Swing layout with a modern dashboard design
