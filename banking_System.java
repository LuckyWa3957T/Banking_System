import java.sql.*;
import java.util.*;
public class BankingSystem {
    private String user_name;
    private String ph;
    private String dob;
    private String gender;
    private int balance = 1000;
    private int acc;
    private int pass;
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rd = new Random();
    BankingSystem() {
        System.out.println("1. Create Account\n2. Login");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                createAccount();
                break;
            case 2:
                loginAccount();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
    void createAccount() {
        System.out.print("Enter Aadhar (12 digits): ");
        String ac = sc.next();
        if (ac.length() != 12) {
            System.out.println("Invalid Aadhar");
            return;
        }
        System.out.print("Enter PAN (10 chars): ");
        String pan = sc.next();
        if (pan.length() != 10) {
            System.out.println("Invalid PAN");
            return;
        }
        System.out.print("Enter DOB (dd/MM/yyyy): ");
        this.dob = sc.next();
        System.out.print("Enter Gender (Male/Female): ");
        this.gender = sc.next();
        if (!(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))) {
            System.out.println("Invalid Gender");
            return;
        }
        System.out.print("Enter Name: ");
        this.user_name = sc.next();
        System.out.print("Enter Phone (10 digits): ");
        this.ph = sc.next();
        if (ph.length() != 10) {
            System.out.println("Invalid Phone");
            return;
        }
        System.out.print("Set 6-digit Password: ");
        this.pass = sc.nextInt();
        if (pass < 100000 || pass > 999999) {
            System.out.println("Invalid Password");
            return;
        }
        this.acc = 100000000 + rd.nextInt(900000000);
        try {
            PreparedStatement ps = DBConnection.con.prepareStatement(
                    "INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, acc);
            ps.setString(2, user_name);
            ps.setString(3, ph);
            ps.setString(4, dob);
            ps.setString(5, gender);
            ps.setInt(6, pass);
            ps.setInt(7, balance);
            ps.executeUpdate();
            System.out.println("\n✅ Account Created Successfully!");
            System.out.println("Account No: " + acc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void loginAccount() {
        System.out.print("Enter Account No: ");
        int accInput = sc.nextInt();
        System.out.print("Enter Password: ");
        int passInput = sc.nextInt();
        try {
            PreparedStatement ps = DBConnection.con.prepareStatement(
                    "SELECT * FROM accounts WHERE acc=? AND pass=?"
            );
            ps.setInt(1, accInput);
            ps.setInt(2, passInput);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("✅ Login Successful");

                this.acc = accInput;
                this.pass = passInput;
                this.balance = rs.getInt("balance");
                userMenu();
            } else {
                System.out.println("❌ Invalid Credentials");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    void userMenu() {
        while (true) {
            System.out.println("\n1. Check Balance\n2. Withdraw\n3. Deposit\n4. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
    void checkBalance() {
        System.out.println("Balance: " + balance);
    }
    void deposit() {
        System.out.print("Enter amount: ");
        int amt = sc.nextInt();
        if (amt <= 0) {
            System.out.println("Invalid amount");
            return;
        }
        balance += amt;
        updateBalance();
        System.out.println("Updated Balance: " + balance);
    }
    void withdraw() {
        System.out.print("Enter amount: ");
        int amt = sc.nextInt();
        if (amt > balance) {
            System.out.println("Insufficient balance");
            return;
        }
        balance -= amt;
        updateBalance();
        System.out.println("Remaining Balance: " + balance);
    }
    void updateBalance() {
        try {
            PreparedStatement ps = DBConnection.con.prepareStatement(
                    "UPDATE accounts SET balance=? WHERE acc=?"
            );
            ps.setInt(1, balance);
            ps.setInt(2, acc);

            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        DBConnection.connect();
        new BankingSystem();
    }
}
