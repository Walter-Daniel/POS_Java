package Model;


public class CustomerModel {
    
    private int IdCustomer;
    private String firstName;
    private String lastName;
    private String DNI;

    public CustomerModel() {
    }
    
    public CustomerModel(int IdCustomer, String firstName, String lastName, String DNI) {
        this.IdCustomer = IdCustomer;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DNI = DNI;
    }

    public int getIdCustomer() {
        return IdCustomer;
    }

    public void setIdCustomer(int IdCustomer) {
        this.IdCustomer = IdCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    
    
    
    
}
