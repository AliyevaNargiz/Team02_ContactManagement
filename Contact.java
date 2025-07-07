public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address; // Street + City
    private String birthday; // Format: YYYY-MM-DD
    private Group group;
    private String note;

    public Contact(String firstName, String lastName, String phoneNumber, String email, String address, String birthday, Group group, String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.group = group;
        this.note = note;
    }

    // Getters and setters for all fields
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | " + phoneNumber + " | " + email + " | " + address + " | " + birthday + " | " + group + " | " + note;
    }
} 