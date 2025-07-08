public class DemoBook extends Book{
   String theBook;
    public  DemoBook (String isbn, String title, int publishedYear, double price , String theBook ){
        super( isbn,  title,  publishedYear,  0 );
        this.theBook=theBook;
    }
}
