import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Manage data with interface IManaged.
 *
 * @param <T> class has implemented interface IManaged.
 */
public class Manager<T extends IManaged> {

    private final DatabaseTable databaseTable;
    private final Class<T> managedClass;

    public Manager(DatabaseTable databaseTable, Class<T> managedClass) {
        this.databaseTable = databaseTable;
        this.managedClass = managedClass;
    }

    // CRUD: Create - Read - Update - Delete

    /**
     * Create method.
     *
     * @param managed T, add this object to database.
     * @param connection Connection, use this Connection to interact with database.
     */
    public void create(T managed, Connection connection) {
        // INSERT INTO books (id, title, author) VALUES ('12', 'title', 'author')
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ").append(databaseTable.toString()).append(" (");

        // reflection
        Field[] fields = managedClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true); // access private fields
            query.append(fields[i].getName());
            if (i < fields.length - 1) {
                query.append(", ");
            }
        }
        query.append(") VALUES (");

        for (int i = 0; i < fields.length; i++) {
            try {
                Object value = fields[i].get(managed);
                query.append("'").append(value).append("'");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (i < fields.length - 1) {
                query.append(", ");
            }
        }
        query.append(")");

        System.out.println(query.toString());
        DatabaseUtil.execute(connection, query.toString());
    }

    /**
     * Read All method.
     * Read all record in database and return a List<T>
     *
     * @param connection Connection.
     * @return List<T>
     */
    public List<T> readAll(Connection connection) {
        List<T> managedList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", databaseTable.toString());
        ResultSet resultSet = DatabaseUtil.executeQuery(connection, query);
        try {
            while (resultSet.next()) {
                T instance = managedClass.getDeclaredConstructor().newInstance();
                for (Field field : managedClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object value = resultSet.getObject(field.getName());
                    field.set(instance, value);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return managedList;
    }

    /**
     * Update method.
     * Update the managed has same id with the param managed Object.
     *
     * @param managed T
     * @param connection Connection
     */
    public void update(T managed, Connection connection) {
        StringBuilder query = new StringBuilder();
        query.append("UPDATE ").append(databaseTable).append(" SET ");

        // reflection
        Field[] fields = managedClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object value = fields[i].get(managed);
                query.append(fields[i].getName()).append("='").append(value).append("'");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if (i < fields.length - 1) {
                query.append(", ");
            }
        }

        query.append(" WHERE id=").append(managed.getId());
        System.out.println(query.toString());  // Debug output
        DatabaseUtil.execute(connection, query.toString());
    }

    /**
     * Delete method.
     *
     * @param managed T
     * @param connection Connection
     */
    public void delete(T managed, Connection connection) {
        String query = String.format("DELETE FROM %s WHERE id=%d", databaseTable, managed.getId());
        DatabaseUtil.execute(connection, query);
    }

}
