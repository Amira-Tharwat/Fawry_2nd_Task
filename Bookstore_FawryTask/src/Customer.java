import java.util.ArrayList;

public class Customer extends Person {

    private double balance;
    private String Email;
    private String Address;
    public Customer(String name, String password,String email , String address, double balance) {
        super(name, password );
        setAddress(address);
        setBalance(balance);
        setEmail(email);
    }
    public double getBalance() {
        return balance;
    }
    public String getEmail() {
        return Email;
    }
    public String getAddress() {
        return Address;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public void setAddress(String address) {
        Address = address;
    }



    public static void CustomerMenu() {
        Customer customer = (Customer) Main.current_person;

        while (true) {
            System.out.println("\n=== Customer Menu ===");
            System.out.println("1. View Books");
            System.out.println("2. View My Balance");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");

            String choice = Main.scanner.nextLine();
            switch (choice) {
                case "1":
                    displayBooks();
                    break;
                case "2":
                    viewBalance(customer);
                    break;
                case "3":
                    Main.current_person = null;
                    return;
                default:
                    System.out.println(Main.RED + "Invalid choice" + Main.RESET);
            }
        }
    }

    private static void viewBalance(Customer customer) {
        System.out.printf("\nYour current balance: " + Main.GREEN + customer.balance + Main.RESET + "\n");

        System.out.println("\nPress enter to go back");
        Main.scanner.nextLine();
    }

    public static void displayBooks() {
        System.out.println(Main.PURPLE + "\nChoose Book Type:" + Main.RESET);
        System.out.println("1. Paper Books");
        System.out.println("2. EBooks");
        System.out.println("3. Demo Books(Not for sale)");
        System.out.println("0. Back");
        System.out.print("Your choice: ");
        String type = Main.scanner.nextLine();

        switch (type) {
            case "1": // Paper Books
                ArrayList<PaperBook> paperBooks = new ArrayList<>();
                for (Book b : Main.books) {
                    if (b instanceof PaperBook) {
                        if(Main.currentYear- b.PublishedYear>Main.outdatedYears){
                            continue;
                        }
                        paperBooks.add((PaperBook) b);
                    }
                }

                if (paperBooks.isEmpty()) {
                    System.out.println(Main.YELLOW + "No paper books available." + Main.RESET);
                    return;
                }

                System.out.println("\nAvailable Paper Books:");
                System.out.println("Title -- ISBN -- Published Year -- Price -- Stock");
                System.out.println("___________________________________________________");
                for (PaperBook book : paperBooks) {
                    System.out.println(book.Title + " -- " + book.ISBN + " -- " +
                            book.PublishedYear + " -- " + book.Price + " -- " + book.Stock);
                }

                System.out.print("\nEnter ISBN of the book you want to buy (or '0' to go back): ");
                String pIsbn = Main.scanner.nextLine();
                if (pIsbn.equals("0")) return;

                PaperBook selectedBook = null;
                for (PaperBook book : paperBooks) {
                    if (book.ISBN.equals(pIsbn)) {
                        selectedBook = book;
                        break;
                    }
                }

                if (selectedBook == null) {
                    System.out.println(Main.RED + "Book with this ISBN not found." + Main.RESET);
                    return;
                }

                System.out.print("Enter quantity to buy: ");
                try {
                    int quantity = Integer.parseInt(Main.scanner.nextLine());

                    if (quantity <= 0) {
                        System.out.println(Main.RED + "Quantity must be positive." + Main.RESET);
                        return;
                    } else if (quantity > selectedBook.Stock) {
                        System.out.println(Main.YELLOW + "Not enough stock available." + Main.RESET);
                        return;
                    }


                        Customer customer = (Customer) Main.current_person;
                        System.out.println("Your Address: " + customer.getAddress());
                        System.out.print("Do you want to update your address? (Y/N): ");
                        String AChoice = Main.scanner.nextLine();
                        if (AChoice.equals("Y")||AChoice.equals("y")) {
                            System.out.print("Enter new address: ");
                            String newAddress = Main.scanner.nextLine();
                            if (!newAddress.isEmpty()) customer.setAddress(newAddress);
                        }
                        selectedBook.Stock -= quantity;
                        customer.setBalance( (customer.getBalance())-(selectedBook.Price*quantity));
                        ShippingService(selectedBook , quantity);



                } catch (NumberFormatException e) {
                    System.out.println(Main.RED + "Please enter a valid quantity." + Main.RESET);
                }
                break;


            case "2": // EBooks
                ArrayList<EBook> eBooks = new ArrayList<>();
                for (Book b : Main.books) {
                    if (b instanceof EBook) {
                        if(Main.currentYear- b.PublishedYear>Main.outdatedYears){
                            continue;
                        }
                        eBooks.add((EBook) b);
                    }
                }

                if (eBooks.isEmpty()) {
                    System.out.println(Main.YELLOW + "No EBooks available." + Main.RESET);
                    return;
                }

                System.out.println("\nAvailable EBooks:");
                System.out.println("Title -- ISBN -- Published Year -- Price -- File Type");
                System.out.println("________________________________________________________");
                for (EBook book : eBooks) {
                    System.out.println(book.Title + " -- " + book.ISBN + " -- " +
                            book.PublishedYear + " -- " + book.Price + " -- " + book.FileType);
                }

                System.out.print("\nEnter ISBN of the book you want to buy (or '0' to go back): ");
                String eIsbn = Main.scanner.nextLine();
                if (eIsbn.equals("0")) return;

                EBook selectedEBook = null;
                for (EBook book : eBooks) {
                    if (book.ISBN.equals(eIsbn)) {
                        selectedEBook = book;
                        break;
                    }
                }

                if (selectedEBook == null) {
                    System.out.println(Main.RED + "Book with this ISBN not found." + Main.RESET);
                    return;
                }

                Customer customer = (Customer) Main.current_person;

                System.out.println("Your Email: " + customer.getEmail());
                System.out.print("Do you want to update your email? (Y/N): ");
                String EChoice = Main.scanner.nextLine();
                if (EChoice.equals("Y")||EChoice.equals("y")) {
                    System.out.print("Enter new email: ");
                    String newEmail = Main.scanner.nextLine();
                    if (!newEmail.isEmpty()) customer.setEmail(newEmail);
                }
                customer.setBalance( (customer.getBalance())-(selectedEBook.Price));
                MailService(selectedEBook);


                break;


            case "3": // Demo Books
                ArrayList<DemoBook> demoBooks = new ArrayList<>();
                for (Book b : Main.books) {
                    if (b instanceof DemoBook) {
                        if(Main.currentYear- b.PublishedYear>Main.outdatedYears){
                            continue;
                        }
                        demoBooks.add((DemoBook) b);
                    }
                }

                if (demoBooks.isEmpty()) {
                    System.out.println(Main.YELLOW + "No demo books available." + Main.RESET);
                    return;
                }

                System.out.println("\nAvailable Demo Books :");
                System.out.println("Title -- ISBN -- Published Year");
                System.out.println("_______________________________________");
                for (DemoBook book : demoBooks) {
                    System.out.println(book.Title + " -- " + book.ISBN + " -- " + book.PublishedYear);
                }

                System.out.print("\nEnter ISBN of the demo book to read (or '0' to go back): ");
                String demoIsbn = Main.scanner.nextLine();
                if (demoIsbn.equals("0")) return;

                DemoBook selectedDemoBook = null;
                for (DemoBook book : demoBooks) {
                    if (book.ISBN.equals(demoIsbn)) {
                        selectedDemoBook = book;
                        break;
                    }
                }

                if (selectedDemoBook == null) {
                    System.out.println(Main.RED + "Demo book with this ISBN not found." + Main.RESET);
                    return;
                }

                System.out.println(Main.PURPLE  + selectedDemoBook.Title  + Main.RESET);
                System.out.println("----------------------------------------------------------");
                System.out.println(selectedDemoBook.theBook);
                System.out.println("----------------------------------------------------------");

                break;


            case "0":
                return;

            default:
                System.out.println(Main.RED + "Invalid option." + Main.RESET);
        }
    }

    public  static  void  ShippingService(Book book ,int quantity ){
        System.out.println(Main.GREEN + "You bought " + quantity + " x " + book.Title  + " successful"+  Main.RESET);

    }
    public  static  void MailService(Book book){
        System.out.println(Main.GREEN + "You bought : " + book.Title + " successfully" + Main.RESET);

    }





}
