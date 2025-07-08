
public class Admin extends  Person{

    public  Admin(String name ,String password ){
        super( name , password);
    }

    protected static void AdminMenu() {
        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println("1. Add Book");
            System.out.println("2. Add Customer");
            System.out.println("3. Add Admin");
            System.out.println("4. View Books");
            System.out.println("5. Edit Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");

            String choice = Main.scanner.nextLine();
            switch (choice) {
                case "1":
                    addBook();
                    break;
                case "2":
                    addCustomer();
                    break;
                case "3":
                    addAdmin();
                    break;
                case "4":
                    viewBooks();
                    break;
                case "5":
                    editBook();
                    break;
                case "6":
                    deleteBook();
                    break;
                case "7":
                   Main.current_person = null;
                    return;
                default:
                    System.out.println(Main.RED + "Invalid choice" + Main.RESET);
            }
        }
    }

    private static void viewBooks() {
        if (Main.books.isEmpty()) {
            System.out.println(Main.YELLOW + "No books available." + Main.RESET);
            return;
        }

        System.out.println("ID -- Title -- ISBN -- Year -- Price -- Type Info");
        System.out.println("____________________________________________________");

        for (int i = 0; i < Main.books.size(); i++) {
            Book b = Main.books.get(i);
            if(Main.currentYear- b.PublishedYear>Main.outdatedYears){
                continue;
            }
            String typeInfo = "";

            if (b instanceof PaperBook) {
                typeInfo = "PaperBook  ( Stock: " + ((PaperBook) b).Stock+" )";
            } else if (b instanceof EBook) {
                typeInfo = "EBook (FileType: " + ((EBook) b).FileType+" )";
            } else if (b instanceof DemoBook) {
                typeInfo = "Demo Book(Not for sale)";
            }

            System.out.println((i + 1) + " -- " + b.Title + " -- " + b.ISBN + " -- " +
                    b.PublishedYear + " -- " + b.Price + " -- " + typeInfo);
        }
    }
    private static void addBook() {

            System.out.println("Choose book type to add: ");
            System.out.println("1. Paper Book");
            System.out.println("2. EBook");
            System.out.println("3. Demo Book");

            String choice = Main.scanner.nextLine();


            System.out.print("Enter ISBN: ");
            String isbn = Main.scanner.nextLine();

            System.out.print("Enter Title: ");
            String title = Main.scanner.nextLine();

            System.out.print("Enter Published Year: ");
            int year = Main.scanner.nextInt();

            System.out.print("Enter Price: ");
            double price = Main.scanner.nextDouble();
            Main.scanner.nextLine();

            Book book = null;

            switch (choice) {
                case "1": // Paper Book
                    System.out.print("Enter Stock: ");
                    int stock = Main.scanner.nextInt();
                    book = new PaperBook(isbn, title, year, price, stock);
                    break;

                case "2": // EBook

                    System.out.print("Enter File Type: ");
                    String fileType = Main.scanner.nextLine();
                    book = new EBook(isbn, title, year, price, fileType);
                    break;

                case "3": // DemoBook
                    System.out.print("Enter The Content of book: ");
                    String Content = Main.scanner.nextLine();
                    book = new DemoBook(isbn, title, year, 0 , Content);
                    break;

                default:
                    System.out.println("Invalid option.");
                    return;
            }

            Main.books.add(book);
            System.out.println(Main.GREEN+ "Book added successfully"+Main.RESET);

    }
    private static void addAdmin() {
        System.out.print("\nEnter admin name: ");
        String name = Main.scanner.nextLine();
        for (Person person : Main.persons) {
            if (person.Name.equals(name)) {
                System.out.println(Main.YELLOW + "Name already exists" + Main.RESET);
                return;
            }
        }

        System.out.print("Enter password: ");
        String password = Main.scanner.nextLine();

        Admin newAdmin = new Admin(name, password);
        Main.persons.add(newAdmin);
        Main.admins.add(newAdmin);
        System.out.println(Main.GREEN + "Admin added successfully" + Main.RESET);
    }
    private static void editBook() {
        if (Main.books.isEmpty()) {
            System.out.println(Main.YELLOW + "No products available to edit" + Main.RESET);
            return;
        }

        viewBooks();
        System.out.print("\nEnter product ID to edit: ");
        try {
            int bookId = Integer.parseInt(Main.scanner.nextLine()) - 1;
            if (bookId < 0 || bookId >= Main.books.size()) {
                System.out.println(Main.RED + "Invalid product ID" + Main.RESET);
                return;
            }

            Book book = Main.books.get(bookId);

            System.out.println("\nEditing Book: " + book.Title);
            System.out.println("Dont write anything to keep current value");

            System.out.print("New Title (" + book.Title + "): ");
            String Ntitle = Main.scanner.nextLine();
            if (!Ntitle.isEmpty()) book.Title = Ntitle;

            System.out.print("New Published Year (" + book.PublishedYear + "): ");
            String NPublishedYear = Main.scanner.nextLine();
            if (!NPublishedYear.isEmpty()) book.PublishedYear = Integer.parseInt(NPublishedYear);

            if (!(book instanceof DemoBook)) {
                System.out.print("New Price (" + book.Price + "): ");
                String NPrice = Main.scanner.nextLine();
                if (!NPrice.isEmpty()) book.Price = Double.parseDouble(NPrice);
            }

            if (book instanceof PaperBook) {
                PaperBook paperbook = (PaperBook) book;
                System.out.print("New Stock (" + paperbook.Stock + "): ");
                String NStock = Main.scanner.nextLine();
                if (!NStock.isEmpty()) paperbook.Stock = Integer.parseInt(NStock);
            }

            if (book instanceof EBook) {
                EBook ebook = (EBook) book;
                System.out.print("New File Type (" + ebook.FileType + "): ");
                String NfileType = Main.scanner.nextLine();
                if (!NfileType.isEmpty()) ebook.FileType = NfileType;
            }
            if (book instanceof DemoBook) {
                DemoBook demoBook = (DemoBook) book;
                System.out.print("New Content : ");
                String Ncontent = Main.scanner.nextLine();
                if (!Ncontent.isEmpty()) demoBook.theBook = Ncontent;
            }

            System.out.println(Main.GREEN + "Book updated successfully" + Main.RESET);

        } catch (NumberFormatException e) {
            System.out.println(Main.RED + "Invalid input" + Main.RESET);
        }
    }
    private static void deleteBook() {
        if (Main.books.isEmpty()) {
            System.out.println(Main.YELLOW + "No books available to delete" + Main.RESET);
            return;
        }

        viewBooks();
        System.out.print("\nEnter book ID to delete: ");
        try {
            int bookID = Integer.parseInt(Main.scanner.nextLine()) - 1;
            if (bookID < 0 || bookID >= Main.books.size()) {
                System.out.println(Main.RED + "Invalid Book ID" + Main.RESET);
                return;
            }

            Book book = Main.books.get(bookID);
            System.out.print("Are you sure you want to delete " + book.Title + "? (Y/N): ");
            String confirmation = Main.scanner.nextLine();

            if (confirmation.equals("Y") || confirmation.equals("y")) {
                Main.books.remove(bookID);
                System.out.println(Main.GREEN + "Book deleted successfully" + Main.RESET);
            } else {
                System.out.println(Main.YELLOW + "The Book has not been deleted" + Main.RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(Main.RED + "Invalid Book ID" + Main.RESET);
        }
    }
    private static void addCustomer() {
        Main.register();
    }
}
