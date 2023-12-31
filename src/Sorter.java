// MT 11/31
// Sorts a database of books, users, or transactions to later be displayed

import java.util.ArrayList;
import java.util.Comparator;
import java.util.*;

public class Sorter {

   // Book comparator objects using lambda expressions to compare object properties
   Comparator<Book> titleComparator = (t1, t2) -> t1.getTitle().toLowerCase().compareTo(t2.getTitle().toLowerCase());
   Comparator<Book> authorComparator = (a1, a2) -> a1.getAuthor().toLowerCase().compareTo(a2.getAuthor().toLowerCase());
   Comparator<Book> genreComparator = (g1, g2) -> g1.getGenre().toLowerCase().compareTo(g2.getGenre().toLowerCase());
   Comparator<Book> primaryKeyBookComparator = (k1, k2) -> k1.getPrimaryKey().toLowerCase().compareTo(k2.getPrimaryKey().toLowerCase());
   Comparator<Book> yearComparator = (y1,y2) -> Integer.compare(y1.getYear(),y2.getYear());
   Comparator<Book> availabilityComparator = (a1, a2) -> Boolean.compare(a1.getAvailability(),a2.getAvailability());

   // User comparator objects using lambda expressions to compare object properties
   Comparator<User> fullNameComparator = (f1, f2) -> f1.getFullName().toLowerCase()
         .compareTo(f2.getFullName().toLowerCase());
   Comparator<User> usernameComparator = (n1, n2) -> n1.getUsername().toLowerCase()
         .compareTo(n2.getUsername().toLowerCase());
   Comparator<User> primaryKeyUserComparator = (p1, p2) -> p1.getPrimaryKey().toLowerCase()
         .compareTo(p2.getPrimaryKey().toLowerCase());
   Comparator<User> statusComparator = (a1, a2) -> Boolean.compare(a1.getActive(),a2.getActive());

   // Transaction comparator objects using lambda expression to compare objects properties
   Comparator<Transaction> dateDueComparator = (d1, d2) -> d1.getDateDue().toLowerCase()
         .compareTo(d2.getDateDue().toLowerCase());
   Comparator<Transaction> primaryKeyTransactionComparator = (v1, v2) -> v1.getPrimaryKey().toLowerCase()
         .compareTo(v2.getPrimaryKey().toLowerCase());

   // Empty constructor
   public Sorter() {
   }

   // Method to order a list of books alphabetically by Title
   public ArrayList<Book> titleSorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(titleComparator);

      return listOfBooks;
   }

   // Method to order a list of books alphabetically by Author
   public ArrayList<Book> authorSorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(authorComparator);

      return listOfBooks;
   }

   // Method to order a list of books alphabetically by Genre
   public ArrayList<Book> genreSorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(genreComparator);

      return listOfBooks;
   }

   // Method to order a list of books by Primary Key
   public ArrayList<Book> bookPrimaryKeySorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(primaryKeyBookComparator);

      return listOfBooks;
   }

   // Method to order a list of books by year
   public ArrayList<Book> yearSorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(yearComparator);
      Collections.reverse(listOfBooks);
      return listOfBooks;
   }

   // Method to order a list of books by availability
   public ArrayList<Book> availabilitySorter(ArrayList<Book> listOfBooks) {

      listOfBooks.sort(availabilityComparator);
      Collections.reverse(listOfBooks);
      return listOfBooks;
   }

   // Method to order a list of users alphabetically by their Full Name
   public ArrayList<User> nameSorter(ArrayList<User> listOfBooks) {

      listOfBooks.sort(fullNameComparator);

      return listOfBooks;
   }

   // Method to order a list of users alphabetically by their Username
   public ArrayList<User> usernameSorter(ArrayList<User> listOfBooks) {

      listOfBooks.sort(usernameComparator);

      return listOfBooks;
   }

   // Method to order a list of users alphabetically by their Primary Key
   public ArrayList<User> primaryKeyUserSorter(ArrayList<User> listOfBooks) {

      listOfBooks.sort(primaryKeyUserComparator);

      return listOfBooks;
   }

   // Method to order a list of users by status
   public ArrayList<User> statusSorter(ArrayList<User> listOfBooks) {

      listOfBooks.sort(statusComparator);
      Collections.reverse(listOfBooks);
      return listOfBooks;
   }

   // Method to order a list of Transactions numerically by their Date Due
   public ArrayList<Transaction> dateDueSorter(ArrayList<Transaction> listOfBooks) {

      listOfBooks.sort(dateDueComparator);

      return listOfBooks;
   }

   // Method to order a list of Transactions numerically by their Primary Key
   public ArrayList<Transaction> primaryKeyTransactionSorter(ArrayList<Transaction> listOfBooks) {

      listOfBooks.sort(primaryKeyTransactionComparator);

      return listOfBooks;
   }
}