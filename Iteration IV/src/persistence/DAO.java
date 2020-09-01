package persistence;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    List<T> get(String ID) throws SQLException;
    List<T> getAll() throws SQLException;
    void save(T t) throws SQLException;
    void update(T t, String[] params);
    void delete(T t);
}
