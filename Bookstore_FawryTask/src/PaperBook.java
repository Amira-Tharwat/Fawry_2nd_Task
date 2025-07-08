public class PaperBook extends Book{
    int Stock;
    public PaperBook(String isbn, String title, int publishedYear, double price , int stock ){
        super( isbn,  title,  publishedYear,  price);
        this.Stock=stock;
    }

}
