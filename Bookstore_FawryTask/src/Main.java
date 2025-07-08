import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Person> persons=new ArrayList<>();
    public static ArrayList<Customer>customers=new ArrayList<>();
    public static ArrayList<Admin>admins=new ArrayList<>();
    public static ArrayList<Book>books =new ArrayList<>();
    public static Person current_person=null;
    public static Scanner scanner = new Scanner(System.in);
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String PURPLE = "\033[0;35m";
    public static int currentYear=2026;
    public static int outdatedYears=5;
    public static void main(String[] args) {
        books.add(new PaperBook("abc123","paper book1" ,2019,200,20));
        books.add(new PaperBook("abc456","paper book2" ,2022,150,20));
        books.add(new PaperBook("abc789","paper book3" ,2021,100,20));
        books.add(new PaperBook("abc321","paper book4" ,2022,250,20));
        books.add(new EBook("def123","Ebook1" ,2022,50,"pdf"));
        books.add(new EBook("def456","Ebook2" ,2020,30,"pdf"));
        books.add(new EBook("def789","Ebook3" ,2021,70,"pdf"));
        books.add(new EBook("def321","Ebook4" ,2024,100,"pdf"));
        books.add(new DemoBook("ghi123","Demo book1" ,2024,0,"this is the demo book 1 .............."));
        books.add(new DemoBook("ghi456","Demo book2" ,2021,0,"this is the demo book 2 .............."));
        books.add(new DemoBook("ghi789","Demo book3" ,2020,0,"this is the demo book 3 .............."));
        books.add(new DemoBook("ghi321","Demo book4" ,2022,0,"this is the demo book 4 .............."));

        Customer customer1 = new Customer("amira", "amira123","amira@gmail.com","cairo",5000);
        persons.add(customer1);
        customers.add(customer1);
        Admin admin1=new Admin("admin1","admin1");
        persons.add(admin1);
        admins.add(admin1);


        while (true) {
            if (current_person == null) {
                showLoginMenu();

            } else {
                if (isAdmin(current_person)) {
                    Admin.AdminMenu();
                } else {
                    Customer.CustomerMenu();
                }
            }
        }
    }
    public static void showLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(PURPLE+"\n=== Welcome ==="+RESET);
        System.out.println("1. Login");
        System.out.println("2. Register (Customer)");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
        String choice =  scanner.nextLine();
        switch (choice) {
            case "1":
                login();
                break;
            case "2":
                register();
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println(RED+"Invalid choice"+RESET);
        }
    }
    public static void login() {

        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        for (Person person : persons) {
            if (person.Name.equals(name) && person.getPassword().equals(password)) {
                current_person = person;
                System.out.println(GREEN+"Login successful"+RESET);
                return;
            }
        }
        System.out.println(RED+"Invalid username or password"+RESET);

    }
    public static void register() {
        System.out.print("\nEnter your name: ");
        String username = scanner.nextLine();

        for (Person person : persons) {
            if (person.Name.equals(username)) {
                System.out.println(YELLOW + "Username already exists" + RESET);
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        double balance = 0;
        while (true) {
            System.out.print("Enter your balance: ");
            String input = scanner.nextLine();
            try {
                balance = Double.parseDouble(input);
                if (balance < 0) {
                    System.out.println(RED + "Balance cannot be negative." + RESET);
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println(RED + "Invalid number, Try again." + RESET);
            }
        }

        Customer newCustomer = new Customer(username, password, email, address, balance);
        persons.add(newCustomer);
        customers.add(newCustomer);
        System.out.println(GREEN + "Registration successful" + RESET);
    }
    public static boolean isAdmin(Person person) {
        return (person instanceof Admin);
    }






}