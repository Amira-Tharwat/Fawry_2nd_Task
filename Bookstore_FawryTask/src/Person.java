
public class Person {
    public String Name;
    private String Password;

    public Person(String name ,String password ){
        this.Name=name;
        setPassword(password);

    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }


}
