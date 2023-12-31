// IG 10/18
// Subclass with overriden methods for ArrayList of Transactions

import java.util.ArrayList;

public class TransactionDatabase extends Table<Transaction> {

    // Constructor; initializes database ArrayList
    public TransactionDatabase() {
        database = new ArrayList<Transaction>();
    }

    // Overriden method to add an entry with the parent method and generate an ID
    @Override
    public void addEntry(Transaction entry) {
        super.addEntry(entry);
        entry.setPrimaryKey(generateID());
        /// update external database here
    }

    // Implements the ability to edit transaction entries
    public void setEntry(Transaction transaction, User user, Book book, String dateBorrowed, String dateDue,
            String dateReturned, String primaryKey) {
        if (user != null) {
            transaction.setUser(user);
        }
        if (book != null) {
            transaction.setBook(book);
        }
        if (dateBorrowed != null) {
            transaction.setDateBorrowed(dateBorrowed);
        }
        if (dateDue != null) {
            transaction.setDateDue(dateDue);
        }
        if (dateReturned != null) {
            transaction.setDateReturned(dateReturned);
        }
        if (primaryKey != null) {
            transaction.setPrimaryKey(primaryKey);
        }
    }

    // Returns the primaryKey at a given index
    // Overriden so that the parent class can access the primaryKey
    @Override
    public String getID(int index) {
        return (database.get(index).primaryKey);
    }

    // Displays each primary key (for testing purposes)
    public void display() {
        for (int i = 0; i < database.size(); i++) {
            System.out.println("Transaction ID " + i + ": " + database.get(i).primaryKey);
        }
    }

    // Returns a list of transactions affiliated with a given book
    public ArrayList<Transaction> getBookTransactions(Book book) {
        ArrayList<Transaction> bookTransactions = new ArrayList<Transaction>();
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).getBook() == book) {
                bookTransactions.add(database.get(i));
            }
        }
        return (bookTransactions);
    }

    // Returns a list of transactions affiliated with a given user
    public ArrayList<Transaction> getUserTransactions(User user) {
        ArrayList<Transaction> userTransactions = new ArrayList<Transaction>();
        for (int i = 0; i < database.size(); i++) {
            if (database.get(i).getUser() == user) {
                userTransactions.add(database.get(i));
            }
        }
        return (userTransactions);
    }
}