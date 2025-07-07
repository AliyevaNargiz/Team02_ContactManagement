import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.collections.*;
import java.util.*;

public class ContactManagerGUI extends Application {
    private ContactManager manager = new ContactManager();
    private TableView<Contact> table = new TableView<>();
    private ObservableList<Contact> data;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("ContactPro - Contact Management");
        data = FXCollections.observableArrayList(manager.getContacts());
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Contact, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Contact, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Contact, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn<Contact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Contact, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn<Contact, String> birthdayCol = new TableColumn<>("Birthday");
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        TableColumn<Contact, String> groupCol = new TableColumn<>("Group");
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        TableColumn<Contact, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        table.getColumns().addAll(firstNameCol, lastNameCol, phoneCol, emailCol, addressCol, birthdayCol, groupCol, noteCol);

        // Top controls
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(10));
        Button addBtn = new Button("Add Contact");
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");
        Button refreshBtn = new Button("Refresh");
        topBar.getChildren().addAll(addBtn, editBtn, deleteBtn, refreshBtn);

        // Search and filter controls
        HBox searchBar = new HBox(10);
        searchBar.setPadding(new Insets(10));
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        ComboBox<String> searchAttr = new ComboBox<>();
        searchAttr.getItems().addAll("firstname", "lastname", "phone", "email", "address", "birthday", "group", "note");
        searchAttr.setValue("firstname");
        Button searchBtn = new Button("Search");
        Button filterBtn = new Button("Filter");
        Button sortBtn = new Button("Sort");
        searchBar.getChildren().addAll(new Label("Search by:"), searchAttr, searchField, searchBtn, filterBtn, sortBtn);

        VBox root = new VBox(10, topBar, searchBar, table);
        root.setPadding(new Insets(10));

        // Add modern CSS
        Scene scene = new Scene(root, 1100, 500);
        scene.getStylesheets().add("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button actions
        addBtn.setOnAction(e -> showContactDialog(null));
        editBtn.setOnAction(e -> {
            Contact selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) showContactDialog(selected);
        });
        deleteBtn.setOnAction(e -> {
            Contact selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                manager.removeContact(selected);
                data.setAll(manager.getContacts());
                manager.saveContacts();
            }
        });
        refreshBtn.setOnAction(e -> data.setAll(manager.getContacts()));
        searchBtn.setOnAction(e -> {
            String attr = searchAttr.getValue();
            String val = searchField.getText();
            if (!val.isEmpty()) {
                List<Contact> found = manager.search(val, attr);
                data.setAll(found);
            }
        });
        filterBtn.setOnAction(e -> showFilterDialog());
        sortBtn.setOnAction(e -> showSortDialog());
    }

    private void showContactDialog(Contact contact) {
        Dialog<Contact> dialog = new Dialog<>();
        dialog.setTitle(contact == null ? "Add Contact" : "Edit Contact");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));
        TextField first = new TextField(); first.setPromptText("First Name");
        TextField last = new TextField(); last.setPromptText("Last Name");
        TextField phone = new TextField(); phone.setPromptText("Phone");
        TextField email = new TextField(); email.setPromptText("Email");
        TextField address = new TextField(); address.setPromptText("Address");
        TextField birthday = new TextField(); birthday.setPromptText("YYYY-MM-DD");
        ComboBox<Group> group = new ComboBox<>(); group.getItems().addAll(Group.values());
        TextField note = new TextField(); note.setPromptText("Note");
        if (contact != null) {
            first.setText(contact.getFirstName());
            last.setText(contact.getLastName());
            phone.setText(contact.getPhoneNumber());
            email.setText(contact.getEmail());
            address.setText(contact.getAddress());
            birthday.setText(contact.getBirthday());
            group.setValue(contact.getGroup());
            note.setText(contact.getNote());
        }
        grid.add(new Label("First Name:"), 0, 0); grid.add(first, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1); grid.add(last, 1, 1);
        grid.add(new Label("Phone:"), 0, 2); grid.add(phone, 1, 2);
        grid.add(new Label("Email:"), 0, 3); grid.add(email, 1, 3);
        grid.add(new Label("Address:"), 0, 4); grid.add(address, 1, 4);
        grid.add(new Label("Birthday:"), 0, 5); grid.add(birthday, 1, 5);
        grid.add(new Label("Group:"), 0, 6); grid.add(group, 1, 6);
        grid.add(new Label("Note:"), 0, 7); grid.add(note, 1, 7);
        dialog.getDialogPane().setContent(grid);
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okBtn) {
                try {
                    Contact c = new Contact(
                        first.getText(), last.getText(), phone.getText(), email.getText(),
                        address.getText(), birthday.getText(), group.getValue(), note.getText()
                    );
                    return c;
                } catch (Exception e) { return null; }
            }
            return null;
        });
        Optional<Contact> result = dialog.showAndWait();
        result.ifPresent(c -> {
            if (contact == null) manager.addContact(c);
            else manager.updateContact(contact, c);
            data.setAll(manager.getContacts());
            manager.saveContacts();
        });
    }

    private void showFilterDialog() {
        Dialog<Map<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Filter Contacts");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));
        String[] attrs = {"firstname", "lastname", "phone", "email", "address", "birthday", "group", "note"};
        Map<String, TextField> fields = new HashMap<>();
        for (int i = 0; i < attrs.length; i++) {
            TextField tf = new TextField();
            tf.setPromptText(attrs[i]);
            grid.add(new Label(attrs[i] + ":"), 0, i);
            grid.add(tf, 1, i);
            fields.put(attrs[i], tf);
        }
        dialog.getDialogPane().setContent(grid);
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okBtn) {
                Map<String, String> filters = new HashMap<>();
                for (String attr : attrs) {
                    String val = fields.get(attr).getText();
                    if (!val.isEmpty()) filters.put(attr, val);
                }
                return filters;
            }
            return null;
        });
        Optional<Map<String, String>> result = dialog.showAndWait();
        result.ifPresent(filters -> {
            List<Contact> found = manager.filter(filters);
            data.setAll(found);
        });
    }

    private void showSortDialog() {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Sort Contacts");
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10); grid.setPadding(new Insets(20, 150, 10, 10));
        ComboBox<String> attrBox = new ComboBox<>();
        attrBox.getItems().addAll("firstname", "lastname", "phone", "email", "address", "birthday", "group", "note");
        attrBox.setValue("firstname");
        ComboBox<String> orderBox = new ComboBox<>();
        orderBox.getItems().addAll("Ascending", "Descending");
        orderBox.setValue("Ascending");
        grid.add(new Label("Attribute:"), 0, 0); grid.add(attrBox, 1, 0);
        grid.add(new Label("Order:"), 0, 1); grid.add(orderBox, 1, 1);
        dialog.getDialogPane().setContent(grid);
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okBtn) {
                return new String[]{attrBox.getValue(), orderBox.getValue()};
            }
            return null;
        });
        Optional<String[]> result = dialog.showAndWait();
        result.ifPresent(arr -> {
            manager.sortBy(arr[0], arr[1].equals("Ascending"));
            data.setAll(manager.getContacts());
        });
    }
} 