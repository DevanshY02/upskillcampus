public class User {
    String name;
    String address;
    String contactInfo;
    Account account;

    public User(String name, String address, String contactInfo, Account account) {
        this.name = name;
        this.setAddress(address);
        this.setContactInfo(contactInfo);
        this.account = account;
    }
    public String getName() {
        return name;
    }

	public String getAddress() {
		return address;
	}
    public Account getAccount() {
        return account;
    }
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
}
