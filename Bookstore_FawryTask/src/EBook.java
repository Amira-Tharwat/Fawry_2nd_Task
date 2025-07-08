public class EBook extends Book{
    String FileType;
    public  EBook (String isbn, String title, int publishedYear, double price ,String filetype){
        super( isbn,  title,  publishedYear,  price);
        this.FileType=filetype;
    }

}
