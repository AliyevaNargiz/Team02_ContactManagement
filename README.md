# Team02 Contact Management System

## Overview
A creative, modern Contact Management System simulating a phone contacts app. Features include:
- Add, list, search, edit, delete, sort, and filter contacts
- Multi-attribute filtering
- JavaFX GUI and CLI
- Data persistence with file I/O
- Modern design with custom CSS

## Team Members
- Nargiz Aliyeva
- Aykhan Ismayilzade
- Bashir Safarli

## Requirements
- Java 11 or higher
- JavaFX SDK (for GUI)

## Project Structure
- `Contact.java`, `Group.java`, `ContactManager.java`, `Main.java` — core logic and CLI
- `ContactManagerGUI.java` — JavaFX GUI
- `data/contacts.txt` — sample data file
- `style.css` — modern GUI stylesheet

## How to Run

### 1. **Running the CLI Version**

1. **Open a terminal** in the project folder (where the `.java` files are).
2. **Compile the CLI files:**
   ```sh
   javac Contact.java Group.java ContactManager.java Main.java
   ```
3. **Run the CLI app:**
   ```sh
   java Main
   ```
4. **Use the menu** to add, list, search, edit, delete, sort, and filter contacts.

---

### 2. **Running the JavaFX GUI Version**

#### **A. Download and Set Up JavaFX SDK**
1. Go to [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Download the JavaFX SDK for your operating system (e.g., Windows).
3. Extract the ZIP file to a location you can find, e.g., `C:\javafx-sdk-21.0.2`

#### **B. Compile All Java Files with JavaFX**
1. In your project folder, run:
   ```sh
   javac --module-path "C:\Users\hp\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml *.java
   ```
   - Replace the path with your actual JavaFX SDK location if different.

#### **C. Run the GUI Application**
1. In your project folder, run:
   ```sh
   java --module-path "C:\Users\hp\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.fxml ContactManagerGUI
   ```
2. The GUI window will open. You can now use all features visually.

#### **D. Modern Design**
- The GUI uses `style.css` for a modern look. Make sure `style.css` is in the same folder as your `.java` files.

---

## **Troubleshooting**
- If you see errors about `javafx` packages not found, double-check your JavaFX SDK path and use the `--module-path` and `--add-modules` options as shown above.
- If you get file errors, make sure the `data` folder and `contacts.txt` exist.
- For any other issues, check the error message and ensure all files are present.

---

## Data
- All contacts are stored in `data/contacts.txt`.
- The app loads and saves contacts automatically.

## Features
- Add, list, search, edit, delete, sort, and filter contacts
- Multi-attribute filtering (e.g., all "Friends" in Baku with birthday in May)
- Modern JavaFX GUI and creative CLI
- Stylish design with `style.css`

---
For more details, see the project report (Team02_REPORT.pdf). 