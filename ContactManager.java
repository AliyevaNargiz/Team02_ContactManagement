import java.util.*;
import java.io.*;

public class ContactManager {
    private ArrayList<Contact> contacts;
    private final String DATA_FILE = "data/contacts.txt";

    public ContactManager() {
        contacts = new ArrayList<>();
        loadContacts();
    }

    // Load contacts from file
    public void loadContacts() {
        contacts.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8) {
                    Contact c = new Contact(
                        parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                        Group.valueOf(parts[6]), parts[7]
                    );
                    contacts.add(c);
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Could not load contacts: " + e.getMessage());
        }
    }

    // Save contacts to file
    public void saveContacts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Contact c : contacts) {
                pw.println(String.join("|",
                    c.getFirstName(), c.getLastName(), c.getPhoneNumber(), c.getEmail(),
                    c.getAddress(), c.getBirthday(), c.getGroup().toString(), c.getNote()
                ));
            }
        } catch (IOException e) {
            System.out.println("[!] Could not save contacts: " + e.getMessage());
        }
    }

    // CRUD operations
    public void addContact(Contact c) { contacts.add(c); }
    public ArrayList<Contact> getContacts() { return contacts; }
    public int getContactCount() { return contacts.size(); }
    public void removeContact(Contact c) { contacts.remove(c); }

    // Search by any attribute
    public List<Contact> search(String query, String attribute) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            switch (attribute.toLowerCase()) {
                case "firstname": if (c.getFirstName().equalsIgnoreCase(query)) result.add(c); break;
                case "lastname": if (c.getLastName().equalsIgnoreCase(query)) result.add(c); break;
                case "phone": if (c.getPhoneNumber().equalsIgnoreCase(query)) result.add(c); break;
                case "email": if (c.getEmail().equalsIgnoreCase(query)) result.add(c); break;
                case "address": if (c.getAddress().toLowerCase().contains(query.toLowerCase())) result.add(c); break;
                case "birthday": if (c.getBirthday().equals(query)) result.add(c); break;
                case "group": if (c.getGroup().toString().equalsIgnoreCase(query)) result.add(c); break;
                case "note": if (c.getNote().toLowerCase().contains(query.toLowerCase())) result.add(c); break;
            }
        }
        return result;
    }

    // Multi-attribute filter (bonus)
    public List<Contact> filter(Map<String, String> filters) {
        List<Contact> result = new ArrayList<>();
        for (Contact c : contacts) {
            boolean match = true;
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String attr = entry.getKey().toLowerCase();
                String val = entry.getValue();
                switch (attr) {
                    case "firstname": if (!c.getFirstName().equalsIgnoreCase(val)) match = false; break;
                    case "lastname": if (!c.getLastName().equalsIgnoreCase(val)) match = false; break;
                    case "phone": if (!c.getPhoneNumber().equalsIgnoreCase(val)) match = false; break;
                    case "email": if (!c.getEmail().equalsIgnoreCase(val)) match = false; break;
                    case "address": if (!c.getAddress().toLowerCase().contains(val.toLowerCase())) match = false; break;
                    case "birthday": if (!c.getBirthday().equals(val)) match = false; break;
                    case "group": if (!c.getGroup().toString().equalsIgnoreCase(val)) match = false; break;
                    case "note": if (!c.getNote().toLowerCase().contains(val.toLowerCase())) match = false; break;
                }
                if (!match) break;
            }
            if (match) result.add(c);
        }
        return result;
    }

    // Edit contact (update fields)
    public void updateContact(Contact c, Contact updated) {
        int idx = contacts.indexOf(c);
        if (idx != -1) contacts.set(idx, updated);
    }

    // Sort by any attribute
    public void sortBy(String attribute, boolean ascending) {
        Comparator<Contact> comp = null;
        switch (attribute.toLowerCase()) {
            case "firstname": comp = Comparator.comparing(Contact::getFirstName, String.CASE_INSENSITIVE_ORDER); break;
            case "lastname": comp = Comparator.comparing(Contact::getLastName, String.CASE_INSENSITIVE_ORDER); break;
            case "phone": comp = Comparator.comparing(Contact::getPhoneNumber); break;
            case "email": comp = Comparator.comparing(Contact::getEmail, String.CASE_INSENSITIVE_ORDER); break;
            case "address": comp = Comparator.comparing(Contact::getAddress, String.CASE_INSENSITIVE_ORDER); break;
            case "birthday": comp = Comparator.comparing(Contact::getBirthday); break;
            case "group": comp = Comparator.comparing(c -> c.getGroup().toString(), String.CASE_INSENSITIVE_ORDER); break;
            case "note": comp = Comparator.comparing(Contact::getNote, String.CASE_INSENSITIVE_ORDER); break;
        }
        if (comp != null) {
            if (!ascending) comp = comp.reversed();
            contacts.sort(comp);
        }
    }
} 