package src;
public class Account {
    
    private int accountNumber;   
    private String name;         
    private double balance;      

    private static final double MIN_BALANCE = 1000.0;
    public Account(int accountNumber, String name, double initialDeposit) {

        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialDeposit;
    }
    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }
    public double getBalance() {
        return balance;
    }
    public boolean deposit(double amount) {

        if (amount <= 0) {
            System.out.println("  [!] Deposit amount must be greater than zero.");
            return false;  
        }

        balance = balance + amount;

        System.out.println("  [+] Rs. " + amount + " deposited successfully.");
        System.out.println("  Current Balance: Rs. " + balance);

        return true;  
    }
    public boolean withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("  [!] Withdrawal amount must be greater than zero.");
            return false;
        }

        if ((balance - amount) < MIN_BALANCE) {
            System.out.println("  [!] Cannot withdraw. Minimum balance of Rs. " + MIN_BALANCE + " must be kept.");
            System.out.println("  Current Balance    : Rs. " + balance);
            System.out.println("  Maximum Withdrawal : Rs. " + (balance - MIN_BALANCE));
            return false;
        }
        balance = balance - amount;

        System.out.println("  [-] Rs. " + amount + " withdrawn successfully.");
        System.out.println("  Current Balance: Rs. " + balance);

        return true;  
    }


    public String toCSV() {
        return accountNumber + "," + name + "," + balance;
    }


    
    public static Account fromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        int accNum = Integer.parseInt(parts[0].trim());
        String accName = parts[1].trim();
        double accBalance = Double.parseDouble(parts[2].trim());
        return new Account(accNum, accName, accBalance);
    }

    public void display() {
        System.out.println("  ---------------------------------");
        System.out.println("  Account Number : " + accountNumber);
        System.out.println("  Account Holder : " + name);
        System.out.println("  Balance        : Rs. " + balance);
        System.out.println("  ---------------------------------");
    }

}  
