package src;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class BankSystem {

    static final String FILE_NAME = "accounts.txt";

    ArrayList<Account> accounts = new ArrayList<Account>();

    int nextAccountNumber = 1001;


    void loadAccountsFromFile() {

        File file = new File(FILE_NAME);

        if (file.exists() == false) {
            System.out.println("  [Info] No saved data found. Starting fresh.");
            return;
        }

        try {

            FileReader fr = new FileReader(FILE_NAME);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();

            while (line != null) {

                line = line.trim();

                if (line.length() == 0 || line.startsWith("#")) {
                    line = reader.readLine();
                    continue;
                }

                Account acc = Account.fromCSV(line);
                accounts.add(acc);

                if (acc.getAccountNumber() >= nextAccountNumber) {
                    nextAccountNumber = acc.getAccountNumber() + 1;
                }

                line = reader.readLine();
            }

            reader.close();
            System.out.println("  [Info] " + accounts.size() + " account(s) loaded.");

        } catch (IOException e) {
            System.out.println("  [Error] Could not read file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("  [Error] File has corrupted data.");
        }
    }


    void saveAccountsToFile() {

        try {

            FileWriter fw = new FileWriter(FILE_NAME, false);
            BufferedWriter writer = new BufferedWriter(fw);

            writer.write("# Mini Banking System - Account Data");
            writer.newLine();
            writer.write("# Format: accountNumber,name,balance");
            writer.newLine();

            int i = 0;
            while (i < accounts.size()) {
                Account acc = accounts.get(i);
                writer.write(acc.toCSV());
                writer.newLine();
                i = i + 1;
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("  [Error] Could not save data: " + e.getMessage());
        }
    }


    void createAccount(Scanner sc) {

        System.out.println("\n  === Create New Account ===");

        System.out.print("  Enter your full name: ");
        sc.nextLine();
        String name = sc.nextLine().trim();

        if (name.length() == 0) {
            System.out.println("  [!] Name cannot be empty.");
            return;
        }

        System.out.print("  Enter initial deposit (minimum Rs. 1000): Rs. ");

        double initialDeposit = 0;

        try {
            initialDeposit = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid amount.");
            return;
        }

        if (initialDeposit < 1000) {
            System.out.println("  [!] Deposit must be at least Rs. 1000.");
            return;
        }

        Account newAccount = new Account(nextAccountNumber, name, initialDeposit);
        accounts.add(newAccount);
        nextAccountNumber = nextAccountNumber + 1;

        saveAccountsToFile();

        System.out.println("\n  [OK] Account created successfully!");
        newAccount.display();
    }


    void depositMoney(Scanner sc) {

        System.out.println("\n  === Deposit Money ===");
        System.out.print("  Enter Account Number: ");

        int accNum = 0;

        try {
            accNum = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid account number.");
            return;
        }

        Account foundAccount = null;
        int i = 0;

        while (i < accounts.size()) {
            if (accounts.get(i).getAccountNumber() == accNum) {
                foundAccount = accounts.get(i);
                break;
            }
            i = i + 1;
        }

        if (foundAccount == null) {
            System.out.println("  [!] Account not found.");
            return;
        }

        System.out.println("  Account found: " + foundAccount.getName());
        System.out.print("  Enter deposit amount: Rs. ");

        double amount = 0;

        try {
            amount = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid amount.");
            return;
        }

        boolean success = foundAccount.deposit(amount);

        if (success) {
            saveAccountsToFile();
        }
    }


    void withdrawMoney(Scanner sc) {

        System.out.println("\n  === Withdraw Money ===");
        System.out.print("  Enter Account Number: ");

        int accNum = 0;

        try {
            accNum = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid account number.");
            return;
        }

        Account foundAccount = null;
        int i = 0;

        while (i < accounts.size()) {
            if (accounts.get(i).getAccountNumber() == accNum) {
                foundAccount = accounts.get(i);
                break;
            }
            i = i + 1;
        }

        if (foundAccount == null) {
            System.out.println("  [!] Account not found.");
            return;
        }

        System.out.println("  Account found: " + foundAccount.getName());
        System.out.print("  Enter withdrawal amount: Rs. ");

        double amount = 0;

        try {
            amount = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid amount.");
            return;
        }

        boolean success = foundAccount.withdraw(amount);

        if (success) {
            saveAccountsToFile();
        }
    }


    void searchAccount(Scanner sc) {

        System.out.println("\n  === Search Account ===");
        System.out.print("  Enter Account Number to search: ");

        int accNum = 0;

        try {
            accNum = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("  [!] Invalid account number.");
            return;
        }

        Account foundAccount = null;
        int i = 0;

        while (i < accounts.size()) {
            if (accounts.get(i).getAccountNumber() == accNum) {
                foundAccount = accounts.get(i);
                break;
            }
            i = i + 1;
        }

        if (foundAccount != null) {
            System.out.println("  [OK] Account found:");
            foundAccount.display();
        } else {
            System.out.println("  [!] No account found with number " + accNum + ".");
        }
    }


    void displayAllAccounts() {

        System.out.println("\n  === All Accounts ===");

        if (accounts.size() == 0) {
            System.out.println("  No accounts created yet.");
            return;
        }

        System.out.println("  Total Accounts: " + accounts.size());

        int i = 0;
        while (i < accounts.size()) {
            accounts.get(i).display();
            i = i + 1;
        }
    }


    void run() {

        Scanner sc = new Scanner(System.in);

        loadAccountsFromFile();

        System.out.println("\n  ===================================");
        System.out.println("       Welcome to Mini Bank System   ");
        System.out.println("  ===================================");

        int choice = 0;

        while (choice != 6) {

            System.out.println("\n  --- Main Menu ---");
            System.out.println("  1. Create Account");
            System.out.println("  2. Deposit");
            System.out.println("  3. Withdraw");
            System.out.println("  4. Search Account");
            System.out.println("  5. Display All Accounts");
            System.out.println("  6. Exit");
            System.out.print("  Enter your choice (1-6): ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {

                case 1:
                    createAccount(sc);
                    break;

                case 2:
                    depositMoney(sc);
                    break;

                case 3:
                    withdrawMoney(sc);
                    break;

                case 4:
                    searchAccount(sc);
                    break;

                case 5:
                    displayAllAccounts();
                    break;

                case 6:
                    System.out.println("\n  Thank you for using Mini Bank System. Goodbye!");
                    break;

                default:
                    System.out.println("  [!] Invalid choice. Please enter a number between 1 and 6.");
            }
        }

        sc.close();
    }


    public static void main(String[] args) {
        BankSystem bank = new BankSystem();
        bank.run();
    }

}
