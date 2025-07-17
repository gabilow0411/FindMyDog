package gabi.low.findmydog;



public class Subscriber {
    private String email;
    private String fullName;
    private String phone;
    private String address;
    String id;


    // Constructor
    public Subscriber(String email, String fullName, String phone, String address) {
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }
    public Subscriber()
    {

    }

    // Getters
    public String getEmail() {
        return email;
    }


    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }


    public String getId() {
        return id;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }


    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }
}

