import java.sql.Connection;

public class Manager<T extends IManaged> {

    private DatabaseTable databaseTable;
    private Class<T> managedClass;

    public Manager(DatabaseTable databaseTable, Class<T> managedClass) {
        this.databaseTable = databaseTable;
        this.managedClass = managedClass;
    }

    // CRUD: Create - Read - Update - Delete
    public void create(T managed, Connection connection) {

    }

}
