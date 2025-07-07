import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactManager manager = new ContactManager();

    public static void main(String[] args) {
        System.out.println("\n==============================");
        System.out.println("   Welcome to ContactPro!  ");
        System.out.println("==============================\n");
        boolean running = true;
        while (running) {
            System.out.println("\u001B[36m" + "Total contacts: " + manager.getContactCount() + "\u001B[0m");
            System.out.println("1. List all contacts");
            System.out.println("2. Create new contact");
            System.out.println("3. Search contact");
            System.out.println("4. Filter contacts (multi-attribute)");
            System.out.println("5. Sort contacts");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1": listContacts(); break;
                case "2": createContact(); break;
                case "3": searchContact(); break;
                case "4": filterContacts(); break;
                case "5": sortContacts(); break;
                case "6": manager.saveContacts(); running = false; break;
                default: System.out.println("[!] Invalid option. Try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void listContacts() {
        System.out.println("\n--- All Contacts ---");
        int i = 1;
        for (Contact c : manager.getContacts()) {
            System.out.println(i + ". " + c);
            i++;
        }
        System.out.println();
    }

    private static void createContact() {
        System.out.println("\n--- Create New Contact ---");
        try {
            System.out.print("First Name: "); String first = scanner.nextLine();
            System.out.print("Last Name: "); String last = scanner.nextLine();
            System.out.print("Phone Number: "); String phone = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            System.out.print("Address (Street, City): "); String address = scanner.nextLine();
            System.out.print("Birthday (YYYY-MM-DD): "); String birthday = scanner.nextLine();
            System.out.print("Group (FAMILY, FRIENDS, WORK, OTHER): ");
            Group group = Group.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Note: "); String note = scanner.nextLine();
            Contact c = new Contact(first, last, phone, email, address, birthday, group, note);
            manager.addContact(c);
            System.out.println("[+] Contact added!");
        } catch (Exception e) {
            System.out.println("[!] Invalid input. Contact not added.");
        }
    }

    private static void searchContact() {
        System.out.println("\n--- Search Contact ---");
        System.out.print("Search by (firstname, lastname, phone, email, address, birthday, group, note): ");
        String attr = scanner.nextLine();
        System.out.print("Enter value: ");
        String value = scanner.nextLine();
        List<Contact> found = manager.search(value, attr);
        if (found.isEmpty()) {
            System.out.println("[!] No contact found.");
        } else {
            for (int i = 0; i < found.size(); i++) {
                System.out.println((i+1) + ". " + found.get(i));
            }
            System.out.print("Select contact to edit/delete (number, or 0 to cancel): ");
            try {
                int sel = Integer.parseInt(scanner.nextLine());
                if (sel > 0 && sel <= found.size()) {
                    Contact c = found.get(sel-1);
                    System.out.print("Edit (e) or Delete (d) or Cancel (c)? ");
                    String action = scanner.nextLine();
                    if (action.equalsIgnoreCase("e")) {
                        editContact(c);
                    } else if (action.equalsIgnoreCase("d")) {
                        manager.removeContact(c);
                        System.out.println("[+] Contact deleted.");
                    }
                }
            } catch (Exception e) {
                System.out.println("[!] Invalid selection.");
            }
        }
    }

    private static void editContact(Contact c) {
        System.out.println("\n--- Edit Contact ---");
        try {
            System.out.print("First Name [" + c.getFirstName() + "]: "); String first = scanner.nextLine();
            System.out.print("Last Name [" + c.getLastName() + "]: "); String last = scanner.nextLine();
            System.out.print("Phone Number [" + c.getPhoneNumber() + "]: "); String phone = scanner.nextLine();
            System.out.print("Email [" + c.getEmail() + "]: "); String email = scanner.nextLine();
            System.out.print("Address [" + c.getAddress() + "]: "); String address = scanner.nextLine();
            System.out.print("Birthday [" + c.getBirthday() + "]: "); String birthday = scanner.nextLine();
            System.out.print("Group [" + c.getGroup() + "]: "); String groupStr = scanner.nextLine();
            System.out.print("Note [" + c.getNote() + "]: "); String note = scanner.nextLine();
            Contact updated = new Contact(
                first.isEmpty() ? c.getFirstName() : first,
                last.isEmpty() ? c.getLastName() : last,
                phone.isEmpty() ? c.getPhoneNumber() : phone,
                email.isEmpty() ? c.getEmail() : email,
                address.isEmpty() ? c.getAddress() : address,
                birthday.isEmpty() ? c.getBirthday() : birthday,
                groupStr.isEmpty() ? c.getGroup() : Group.valueOf(groupStr.toUpperCase()),
                note.isEmpty() ? c.getNote() : note
            );
            manager.updateContact(c, updated);
            System.out.println("[+] Contact updated!");
        } catch (Exception e) {
            System.out.println("[!] Invalid input. Contact not updated.");
        }
    }

    private static void filterContacts() {
        System.out.println("\n--- Filter Contacts (Multi-Attribute) ---");
        Map<String, String> filters = new HashMap<>();
        String[] attrs = {"firstname", "lastname", "phone", "email", "address", "birthday", "group", "note"};
        for (String attr : attrs) {
            System.out.print("Filter by " + attr + " (leave blank to skip): ");
            String val = scanner.nextLine();
            if (!val.isEmpty()) filters.put(attr, val);
        }
        List<Contact> found = manager.filter(filters);
        if (found.isEmpty()) {
            System.out.println("[!] No contacts found.");
        } else {
            int i = 1;
            for (Contact c : found) {
                System.out.println(i + ". " + c);
                i++;
            }
        }
    }

    private static void sortContacts() {
        System.out.println("\n--- Sort Contacts ---");
        System.out.print("Sort by (firstname, lastname, phone, email, address, birthday, group, note): ");
        String attr = scanner.nextLine();
        System.out.print("Ascending (a) or Descending (d)? ");
        String order = scanner.nextLine();
        manager.sortBy(attr, order.equalsIgnoreCase("a"));
        System.out.println("[+] Contacts sorted by " + attr + ".");
    }
} 