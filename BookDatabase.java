// JH 10/26
// TODO: Finish this class
import java.util.ArrayList;

public class BookDatabase extends Table<Book> {

    public BookDatabase() {
        this.database = new ArrayList<Book>();
    }

    public void setEntry() {

    }

    @Override
    public String getID(int index) {
        return(database.get(index).primaryKey);
    }

    public void display() {
        for (int i=0;i<database.size();i++) {
            System.out.println("Book ID " + i + ": " + database.get(i).primaryKey);
        }
    }

}
