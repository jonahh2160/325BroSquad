// IG 11/13
// GUI page allowing librarians to create new objects in records

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.io.IOException;

public class CreateInstanceGui extends DateManager {
    private BookDatabase bookDatabase;
    private UserDatabase userDatabase;
    private SearchWindow searchWindow;
    private TransactionDatabase transactionDatabase;
    private SearchPageGui searchPage;
    private DatabaseManager databaseManager;
    private JFrame frame, fr;
    private JPanel panel, pa;
    private JButton bookButton, userButton, transactionButton, submitButton, bookSearchButton, userSearchButton;
    private JComboBox permissionBox;
    private JLabel label1, label2, label3, label4;
    private JTextField te1, te2, te3, te4, te5, te6;
    private int frameWidth = 640;
    private int frameHeight = 480;
    private int componentX = (frameWidth / 2) - 100;
    private int[] componentY = { 50, 100, 150, 200, 250 };
    private int buttonWidth = 300;
    private int buttonHeight = 40;
    private int buttonSep = 20;
    private int buttonX = (frameWidth / 2) - (buttonWidth / 2);
    private int[] buttonY = { (frameHeight / 2) - (buttonHeight / 2) - buttonSep - 30 - buttonHeight,
            (frameHeight / 2) - (buttonHeight / 2) - 30, (frameHeight / 2) + (buttonHeight / 2) + buttonSep - 30 };
    private int currentType = 0;
    private InfoPageGui infoPage;
    // Colors
    private Color darkNavyColor = new Color(24, 23, 43);

    public CreateInstanceGui(BookDatabase bookDatabase, UserDatabase userDatabase,
            TransactionDatabase transactionDatabase,
            SearchPageGui searchPage, DatabaseManager databaseManager) {
        this.searchPage = searchPage;
        this.bookDatabase = bookDatabase;
        this.userDatabase = userDatabase;
        this.transactionDatabase = transactionDatabase;
        this.databaseManager = databaseManager;
        this.searchWindow = new SearchWindow(this, bookDatabase, userDatabase);

        // Create the frame
        frame = new JFrame("Manage");
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr = new JFrame("Manage");
        fr.setSize(frameWidth, frameHeight);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the panel
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(darkNavyColor);
        pa = new JPanel();
        pa.setLayout(null);
        pa.setBackground(darkNavyColor);

        // Book button
        bookButton = new JButton("NEW BOOK");
        bookButton.setSize(buttonWidth, buttonHeight);
        bookButton.setLocation(buttonX, buttonY[0]);

        // User button
        userButton = new JButton("NEW USER");
        userButton.setSize(buttonWidth, buttonHeight);
        userButton.setLocation(buttonX, buttonY[1]);

        // Transaction button
        transactionButton = new JButton("NEW TRANSACTION");
        transactionButton.setSize(buttonWidth, buttonHeight);
        transactionButton.setLocation(buttonX, buttonY[2]);

        // Submit button
        submitButton = new JButton("SUBMIT");
        submitButton.setSize(buttonWidth, buttonHeight);
        submitButton.setLocation((frameWidth / 2) - (buttonWidth / 2), frameHeight - 100);

        // search buttons
        bookSearchButton = new JButton("🔍");
        bookSearchButton.setSize(50, 30);
        bookSearchButton.setLocation(componentX + 150, componentY[1] + 30);
        userSearchButton = new JButton("🔍");
        userSearchButton.setSize(50, 30);
        userSearchButton.setLocation(componentX + 150, componentY[0] + 30);

        // Combo boxes
        String[] permissions = { "Member", "Administrator" };
        permissionBox = new JComboBox(permissions);
        permissionBox.setSize(150, 30);
        permissionBox.setLocation(componentX, componentY[3] + 30);
        permissionBox.setBackground(Color.WHITE);

        // Labels
        label1 = new JLabel();
        label1.setSize(200, 40);
        label1.setLocation(componentX, componentY[0]);
        label1.setForeground(Color.WHITE);
        label2 = new JLabel();
        label2.setSize(200, 40);
        label2.setLocation(componentX, componentY[1]);
        label2.setForeground(Color.WHITE);
        label3 = new JLabel();
        label3.setSize(200, 40);
        label3.setLocation(componentX, componentY[2]);
        label3.setForeground(Color.WHITE);
        label4 = new JLabel();
        label4.setSize(200, 40);
        label4.setLocation(componentX, componentY[3]);
        label4.setForeground(Color.WHITE);

        // Textfields
        te1 = new JTextField();
        te1.setSize(200, 30);
        // te1.set
        te1.setLocation(componentX, componentY[0] + 30);
        te2 = new JTextField();
        te2.setSize(200, 30);
        te2.setLocation(componentX, componentY[1] + 30);
        te3 = new JTextField();
        te3.setSize(200, 30);
        te3.setLocation(componentX, componentY[2] + 30);
        te4 = new JTextField();
        te4.setSize(200, 30);
        te4.setLocation(componentX, componentY[3] + 30);
        te5 = new JTextField();
        te5.setSize(200, 30);
        te5.setLocation(componentX, componentY[2] + 30);
        te6 = new JTextField();
        te6.setSize(200, 30);
        te6.setLocation(componentX, componentY[3] + 30);

        // Button logic
        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createBookPage();
            }
        });
        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createUserPage();
            }
        });
        transactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTransactionPage();
            }
        });
        bookSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchWindow.searchWindow(0);
            }
        });
        userSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchWindow.searchWindow(1);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String result;
                if (currentType == 0) {
                    result = createBook();
                } else if (currentType == 1) {
                    result = createUser();
                } else {
                    result = createTransaction();
                }
                JOptionPane.showMessageDialog(fr, result);
            }
        });

        // restricting string length for date boxes
        te5.addKeyListener(new KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (te5.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
        te6.addKeyListener(new KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (te6.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
    }

    // This opens up the menu (choose user,book,transaction)
    public void createInstancePage() {
        panel.removeAll();
        te1.setText("");
        te2.setText("");
        te3.setText("");
        te4.setText("");
        permissionBox.setSelectedIndex(0);

        panel.add(bookButton);
        panel.add(userButton);
        panel.add(transactionButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    // Page to create a book
    private void createBookPage() {
        this.infoPage = null;
        currentType = 0;
        fr.setTitle("New Book");
        pa.removeAll();
        label1.setText("Title:");
        label2.setText("Author:");
        label3.setText("Genre:");
        label4.setText("Year:");
        pa.add(label1);
        pa.add(label2);
        pa.add(label3);
        pa.add(label4);
        te1.setText("");
        te2.setText("");
        te3.setText("");
        te4.setText("");
        te1.setSize(200, 30);
        te2.setSize(200, 30);
        pa.add(te1);
        pa.add(te2);
        pa.add(te3);
        pa.add(te4);
        pa.add(submitButton);

        fr.add(pa);
        fr.setVisible(true);
    }

    // Page to create a new user
    private void createUserPage() {
        this.infoPage = null;
        currentType = 1;
        fr.setTitle("New User");
        pa.removeAll();
        label1.setText("Full Name:");
        label2.setText("Username:");
        label3.setText("Password:");
        label4.setText("Permissions:");
        pa.add(label1);
        pa.add(label2);
        pa.add(label3);
        pa.add(label4);
        te1.setText("");
        te2.setText("");
        te3.setText("");
        te4.setText("");
        te1.setSize(200, 30);
        te2.setSize(200, 30);
        permissionBox.setSelectedIndex(0);
        pa.add(te1);
        pa.add(te2);
        pa.add(te3);
        pa.add(permissionBox);
        pa.add(submitButton);

        fr.add(pa);
        fr.setVisible(true);
    }

    // Page to create a new transaction
    private void createTransactionPage() {
        this.infoPage = null;
        currentType = 2;
        fr.setTitle("New Transaction");
        pa.removeAll();
        label1.setText("User ID:");
        label2.setText("Book ID:");
        label3.setText("Date Borrowed (MM/DD/YYYY):");
        label4.setText("Date Due (MM/DD/YYYY):");
        pa.add(label1);
        pa.add(label2);
        pa.add(label3);
        pa.add(label4);
        te1.setText("");
        te2.setText("");
        te5.setText("");
        te6.setText("");
        te1.setSize(200 - 50, 30);
        te2.setSize(200 - 50, 30);
        pa.add(te1);
        pa.add(te2);
        pa.add(te5);
        pa.add(te6);
        pa.add(submitButton);
        pa.add(bookSearchButton);
        pa.add(userSearchButton);

        fr.add(pa);
        fr.setVisible(true);
    }

    // A transaction page that starts with the user id already put in
    public void createTransactionPage(User user, InfoPageGui infoPage) {
        this.infoPage = infoPage;
        currentType = 2;
        fr.setTitle("New Transaction");
        pa.removeAll();
        label1.setText("User ID:");
        label2.setText("Book ID:");
        label3.setText("Date Borrowed (MM/DD/YYYY):");
        label4.setText("Date Due (MM/DD/YYYY):");
        pa.add(label1);
        pa.add(label2);
        pa.add(label3);
        pa.add(label4);
        te1.setSize(200 - 50, 30);
        te2.setSize(200 - 50, 30);
        te1.setText(user.getPrimaryKey());
        pa.add(te1);
        te2.setText("");
        pa.add(te2);
        te5.setText("");
        pa.add(te5);
        te6.setText("");
        pa.add(te6);
        pa.add(submitButton);
        pa.add(bookSearchButton);
        pa.add(userSearchButton);

        fr.add(pa);
        fr.setVisible(true);
    }

    // A transaction page that starts with the book id already put in
    public void createTransactionPage(Book book, InfoPageGui infoPage) {
        this.infoPage = infoPage;
        currentType = 2;
        fr.setTitle("New Transaction");
        pa.removeAll();
        label1.setText("User ID:");
        label2.setText("Book ID:");
        label3.setText("Date Borrowed (MM/DD/YYYY):");
        label4.setText("Date Due (MM/DD/YYYY):");
        pa.add(label1);
        pa.add(label2);
        pa.add(label3);
        pa.add(label4);
        te1.setSize(200 - 50, 30);
        te2.setSize(200 - 50, 30);
        te1.setText("");
        pa.add(te1);
        te2.setText(book.getPrimaryKey());
        pa.add(te2);
        te5.setText("");
        pa.add(te5);
        te6.setText("");
        pa.add(te6);
        pa.add(submitButton);
        pa.add(bookSearchButton);
        pa.add(userSearchButton);

        fr.add(pa);
        fr.setVisible(true);
    }

    // This goes through the input and tries to create book or shows error
    private String createBook() {
        try {
            String title, author, genre;
            title = te1.getText();
            author = te2.getText();
            genre = te3.getText();
            if (title.length() < 1 || author.length() < 1 || genre.length() < 1) {
                return ("Invalid Input!");
            }
            int year = Integer.parseInt(te4.getText());
            Book book = new Book(title, author, genre, year);
            bookDatabase.addEntry(book);
        } catch (Exception e) {
            return ("Invalid Input!");
        }
        searchPage.refreshPage();
        fr.dispose();
        // Attempt to save to records file
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(fr, "ERROR: Could not save to database!");
        }
        return ("Book Added!");
    }

    // This goes through the input and tries to create user or shows error
    private String createUser() {
        String fullName, username, password;
        fullName = te1.getText();
        username = te2.getText();
        password = te3.getText();
        if (fullName.length() < 1 || username.length() < 1 || password.length() < 1) {
            return ("Invalid Input!");
        }
        int perms = permissionBox.getSelectedIndex();
        User user = new User(fullName, username, password, perms);
        userDatabase.addEntry(user);
        searchPage.refreshPage();
        fr.dispose();
        // Attempt to save to records file
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(fr, "ERROR: Could not save to database!");
        }
        return ("User Added!");
    }

    // This goes through the input and tries to create transaction or shows error
    private String createTransaction() {
        try {
            String userID, bookID, dateBorrowed, dateDue;
            userID = te1.getText();
            bookID = te2.getText();
            dateBorrowed = te5.getText();
            dateDue = te6.getText();
            User user = userDatabase.getEntry(userID);
            if (user == null) {
                return ("Invalid User!");
            }
            if (user.getActive() == false) {
                return ("User is inactive!");
            }
            Book book = bookDatabase.getEntry(bookID);
            if (book == null) {
                return ("Invalid Book!");
            }
            if (book.getAvailability() == false) {
                return ("Book is unavailable!");
            }
            if (checkDate(dateBorrowed) == 0 || checkDate(dateDue) == 0) {
                return ("Invalid Date(s)!");
            }
            if (getDateDifference(dateBorrowed, dateDue) < 0) {
                return ("Invalid Date(s)!");
            }
            Transaction transaction = new Transaction(user, book, dateBorrowed, dateDue);
            transactionDatabase.addEntry(transaction);
        } catch (Exception e) {
            return ("Invalid Input!");
        }
        searchPage.refreshPage();
        if (infoPage != null) {
            if (infoPage.getType() == 0) {
                infoPage.bookInfoPage();
            } else {
                infoPage.userInfoPage();
            }
        }
        fr.dispose();
        // Attempt to save to records file
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(fr, "ERROR: Could not save to database!");
        }
        return ("Transaction Added!");
    }

    public void setTe1(String text) {
        te1.setText(text);
    }

    public void setTe2(String text) {
        te2.setText(text);
    }

}