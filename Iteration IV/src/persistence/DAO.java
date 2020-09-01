package persistence;

import java.util.List;

public interface DAO<T> {

    List<T> get(String ID);
    List<T> getAll();
    void save(T t);
    void update(T t, String[] params);
    void delete(T t);
}
