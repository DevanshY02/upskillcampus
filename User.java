import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String address;
    private String contactInfo;
    private final Account account;

    public User(String name, String address, String contactInfo, Account account) {
        setName(name);
        setAddress(address);
        setContactInfo(contactInfo);
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireText(name, "Name");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = requireText(address, "Address");
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = requireText(contactInfo, "Contact information");
    }

    public Account getAccount() {
        return account;
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }

        return value.trim();
    }
}
