// MT 11/12
// Class that handles management/editing page

import javax.swing.*;
import java.io.IOException;

public class ManagementPageGui extends DateManager {
    private InfoPageGui infoPage;
    private SearchPageGui searchPage;
    private BookDatabase bookDatabase;
    private UserDatabase userDatabase;
    private TransactionDatabase transactionDatabase;
    private DatabaseManager databaseManager;

    // Constructor
    public ManagementPageGui(BookDatabase bookDatabase, UserDatabase userDatabase,
            TransactionDatabase transactionDatabase,
            InfoPageGui infoPage, SearchPageGui searchPage, DatabaseManager databaseManager) {
        this.infoPage = infoPage;
        this.searchPage = searchPage;
        this.bookDatabase = bookDatabase;
        this.userDatabase = userDatabase;
        this.databaseManager = databaseManager;
        this.transactionDatabase = transactionDatabase;
    }

    // Method to assign action event for the edit button in the case of editing the
    // variables of a book;
    public void changeBookInfo(Book book) {

        String title = book.getTitle();
        String author = book.getAuthor();
        String genre = book.getGenre();
        String year = Integer.toString(book.getYear());
        int availability = 0;
        String[] available = { "Available", "Unavailable", "Cancel" };

        String[] responses = { "Title", "Author", "Genre", "Year", "Availability", "Delete", "Cancel" };
        String[] deleteResponse = { "Yes", "Cancel" };
        int deleteResult = 1;
        int answer = JOptionPane.showOptionDialog(
                null,
                "Which field would you like to edit?",
                "Book Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,
                responses[0]);
        if (answer == 0) {
            title = JOptionPane.showInputDialog("What would you like to change the title to? ", title);
            if (title != null) {
                book.setTitle(title);
            }
        } else if (answer == 1) {
            author = JOptionPane.showInputDialog("What would you like to change the author to? ", author);
            if (author != null) {
                book.setAuthor(author);
            }
        } else if (answer == 2) {
            genre = JOptionPane.showInputDialog("What would you like to change the genre to? ", genre);
            if (genre != null) {
                book.setGenre(genre);
            }
        } else if (answer == 3) {
            year = JOptionPane.showInputDialog("What would you like to change the year to?", year);
            if (year != null) {
                book.setYear(Integer.parseInt(year));
            }
        } else if (answer == 4) {
            availability = JOptionPane.showOptionDialog(
                    null,
                    "Is this book available?",
                    "Book Availability", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    available, available[0]);
            if (availability == 0) {
                book.setAvailability(true);
            } else if (availability == 1) {
                book.setAvailability(false);
            }
        } else if (answer == 5) {
            deleteResult = JOptionPane.showOptionDialog(null, "Are your sure you want to delete this Book?",
                    "Delete Book", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, deleteResponse,
                    responses[1]);
            if (deleteResult == 0) {
                bookDatabase.removeEntry(book.getPrimaryKey());
                infoPage.getFrame().dispose();
            }
        }

        if (deleteResult != 0) {
            infoPage.bookInfoPage(book);
        }
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not save to database!");
        }
        searchPage.refreshPage();
    }

    // Method to assign action event for the edit button in the case of editing the
    // variables of a user;
    public void changeUserInfo(User user) {

        String fullName = user.getFullName();
        String username = user.getUsername();
        String password = user.getPassword();
        int perms = user.getPerms();
        String balance = ("" + user.getAccountBalance());
        int active;
        String[] memberLevel = { "Member", "Librarian", "Cancel" };
        String[] deleteResponse = { "Yes", "Cancel" };
        String[] activeOptions = { "Active", "Inactive", "Cancel" };
        int deleteResult = 1;
        String[] responses = { "Name", "Username", "Password", "Perms", "Balance", "Status", "Delete", "Cancel" };
        int answer = JOptionPane.showOptionDialog( // TODO need to fix this bug by instantiating an istance to refer for
                                                   // closing methods
                null,
                "Which field would you like to edit?",
                "User Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,
                responses[0]);
        if (answer == 0) {
            fullName = JOptionPane.showInputDialog("What would you like to change the name to? ", fullName);
            if (fullName != null) {
                user.setFullName(fullName);
            }
        } else if (answer == 1) {
            username = JOptionPane.showInputDialog("What would you like to change the username to? ", username);
            if (username != null) {
                user.setUsername(username);
            }
        } else if (answer == 2) {
            password = JOptionPane.showInputDialog("What would you like to change the password to? ", password);
            if (password != null) {
                user.setPassword(password);
            }
        } else if (answer == 3) {
            perms = JOptionPane.showOptionDialog(
                    null,
                    "Which permission level would you like to assign?",
                    "Permission Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    memberLevel, memberLevel[0]);
            if (perms < 2) {
                if (searchPage.getUser() != user) {
                    user.setPerms(perms);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't change permissions of the current user!");
                }
            }
        } else if (answer == 4) {
            balance = JOptionPane.showInputDialog("What would you like to change the password to? ", balance);
            try {
                user.setAccountBalance(Double.parseDouble(balance));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid balance!");
            }
        } else if (answer == 5) {
            active = JOptionPane.showOptionDialog(
                    null,
                    "Select user status.",
                    "User Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    activeOptions, activeOptions[2]);
            if (active == 0) {
                user.setActive(true);
            } else if (active == 1) {
                user.setActive(false);
            }
        } else if (answer == 6) {
            deleteResult = JOptionPane.showOptionDialog(null, "Are your sure you want to delete this User?",
                    "Delete User", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, deleteResponse,
                    responses[1]);
            if (deleteResult == 0) {
                if (searchPage.getUser() != user) {
                    userDatabase.removeEntry(user.getPrimaryKey());
                    infoPage.getFrame().dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Can't delete the current user!");
                }
            }
        }

        // Refresh the info page
        if (deleteResult != 0) {
            infoPage.userInfoPage(user);
        }
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not save to database!");
        }
        searchPage.refreshPage();
    }

    // Method to assign action event for the edit button in the case of editing the
    // variables of a transaction;
    public void changeTransactionInfo(Transaction transaction) {

        String dateBorrowed = transaction.getDateBorrowed();
        String dateReturned = transaction.getDateReturned();
        String dateDue = transaction.getDateDue();

        String[] responses1 = { "Date Borrowed", "Date Due", "Complete Transaction", "Delete", "Cancel" };
        String[] responses2 = { "Date Borrowed", "Date Due", "Date Returned", "Delete", "Cancel" };
        String[] responses;
        String[] deleteResponse = { "Yes", "Cancel" };
        int deleteResult = 1;
        if (transaction.getStatus() == false) {
            responses = responses1;
        } else {
            responses = responses2;
        }
        int answer = JOptionPane.showOptionDialog( // TODO need to fix this bug by instantiating an istance to refer for
                                                   // closing methods
                null,
                "Which field would you like to edit?",
                "User Management", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, responses,
                responses[0]);
        if (answer == 0) {
            dateBorrowed = JOptionPane.showInputDialog("What would you like to change the borrowed date to?",
                    dateBorrowed);
            if (checkDate(dateBorrowed) != 0) {
                transaction.setDateBorrowed(dateBorrowed);
            } else {
                JOptionPane.showMessageDialog(infoPage.getFrame(), "Invalid Date!");
            }
        } else if (answer == 1) {
            dateDue = JOptionPane.showInputDialog("What would you like to change the due date to?", dateDue);
            if (checkDate(dateDue) != 0) {
                transaction.setDateDue(dateDue);
            } else {
                JOptionPane.showMessageDialog(infoPage.getFrame(), "Invalid Date!");
            }
        } else if (answer == 2) {
            if (responses == responses2) {
                dateReturned = JOptionPane.showInputDialog("What would you like to change the returned date to?",
                        dateReturned);
                if (checkDate(dateReturned) != 0) {
                    transaction.setDateReturned(dateReturned);
                } else {
                    JOptionPane.showMessageDialog(infoPage.getFrame(), "Invalid Date!");
                }
            } else {
                dateReturned = JOptionPane.showInputDialog("Input the returned date.", "MM/DD/YYYY");
                if (checkDate(dateReturned) != 0) {
                    transaction.completeTransaction(dateReturned);
                } else {
                    JOptionPane.showMessageDialog(infoPage.getFrame(), "Invalid Date!");
                }
            }
        } else if (answer == 3) {
            deleteResult = JOptionPane.showOptionDialog(null, "Are your sure you want to delete this Transaction?",
                    "Delete Transaction", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    deleteResponse,
                    responses[1]);
            if (deleteResult == 0) {
                transactionDatabase.removeEntry(transaction.getPrimaryKey());
                infoPage.getFrame().dispose();
            }
        }

        // refresh the info and search page with new information
        infoPage.refreshTransactions();
        if (infoPage.getType() == 0) {
            infoPage.bookInfoPage();
        } else {
            infoPage.userInfoPage();
        }
        try {
            databaseManager.saveRecords();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "ERROR: Could not save to database!");
        }
        searchPage.refreshPage();
    }

}