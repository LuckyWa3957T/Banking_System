{
    private static String user_name;
    private static String ph;
    private static String dob;
    private static String gender;
    private static final Scanner sc = new Scanner(System.in);
    private static int balance = 1000;
    public static Random rd = new Random();
    private static int acc;
    private static int pass;

    banking_System() {
        System.out.println("1.Create a new  Account:\n2.Login :");
        System.out.println("Enter your choice :");
        int choice1 = sc.nextInt();
        switch (choice1) {
            case 1:
                createAccount();
                break;
            case 2:
                loginAccount();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public static void setUser_name(String user_name) {
        banking_System.user_name = user_name;
    }

    void createAccount() {

        System.out.println("Enter your Aadhar Card Number (12-digits):");
        String ac = sc.next();
        if (ac.length() != 12) {
            System.out.println("Invalid Aadhar Card Number");
        } else {
            System.out.println("Enter your Pan card Number(10-digits) :");
            String pan = sc.next();
            if (pan.length() != 10) {
                System.out.println("Invalid Pan card Number");
            } else {
                System.out.println("Enter your Date of Birth (dd/MM/yyyy):");
                String dob = sc.next();
                System.out.println("Enter your Gender (Male or Female):");
                String gender = sc.next();
                if ((gender.equals("Male") || gender.equals("male")) ||
                        (gender.equals("Female") || gender.equals("female"))) {
                    System.out.println("Enter your full name :");
                    String user_name = sc.next();
                    System.out.println("Enter your phone number(10-digits) :");
                    String ph = sc.next();
                    if (ph.length() != 10) {
                        System.out.println("Invalid phone number");
                    } else {
                        System.out.println("Enter your password(only-6 Digit):");
                        pass = sc.nextInt();
                        if (pass > 100000 && pass < 1000000) {
                            acc = 100000000 + rd.nextInt(900000000);
                            System.out.println("\nAccount Created Successfully :");
                            System.out.println("Account Number :" + acc);
                            System.out.println("Password  :" + pass);
                            System.out.println("Name : " + user_name);
                            System.out.println("Phone Number : " + ph);
                            System.out.println("Date of Birth : " + dob);
                            System.out.println("Gender : " + gender);
                        } else {
                            System.out.println("Invalid Password");
                        }
                    }
                } else {
                    System.out.println("Invalid gender");
                    System.exit(0);
                }
                try {

                    PreparedStatement ps = DBConnection.con.prepareStatement("INSERT INTO accounts VALUES (?, ?, ?, ?, ?, ?, ?)");

                    ps.setInt(1, acc);
                    ps.setString(2, user_name);
                    ps.setString(3, ph);
                    ps.setString(4, dob);
                    ps.setString(5, gender);
                    ps.setInt(6, pass);
                    ps.setInt(7, balance);

                    ps.executeUpdate();

                    System.out.println("Data saved in database");

                } catch (Exception e) {
                    System.out.println(e);
                }
            }


            loginAccount();
        }
    }
    void loginAccount(){
        System.out.println("Enter your Account Number :");
        int acccc=sc.nextInt();
        System.out.println("Enter your Password :");
        int passc =sc.nextInt();
        if(passc==pass && acccc==acc){
            System.out.println("Account Successfully Login");
            user();
        }
    }
    void Deposit(){
        System.out.println("Enter your Password (only digits) :");
        int password=sc.nextInt();
        if (password == pass) {
            System.out.println("Enter your Balance you can Deposit :");
            int deposit = sc.nextInt();
            balance = balance + deposit;
            System.out.println("Balance is :" + balance);
        }
    }
    void ckeckbalance (){

        System.out.println("Enter your Password (only digits) :");
        int password=sc.nextInt();
        if(password==pass){
            System.out.println("Balance is :"+balance);
        }else{
            System.out.println("Invalid Password");
            System.out.println("Please Try Again");
            System.exit(0);
        }
    }
    void Withdrow () {
        System.out.println("Enter your Password (only digits) :");

        int password = sc.nextInt();
        if (password == pass) {
            System.out.println("Enter your Balance  you can withdrow :");
            int amount = sc.nextInt();
            if (amount > balance) {
                System.out.println("\nInsufficient Balance\n");
            } else {
                balance = balance - amount;
                System.out.println("Successful withdrow  :" + amount);
                System.out.println("Balance is :" + balance);

            }
        }

    }
    void user(){
                while (true) {
                System.out.println("1.Balance \n2.Withdraw \n3.Deposit \n4.Exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        ckeckbalance();
                        break;
                    case 2:
                        Withdrow();
                        break;
                    case 3:
                        Deposit();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Wrong Choice ");
                        break;
                }

            }
        }
   public static void main(String[] args) {
       DBConnection.connect();
        new banking_System();
    }
    }
