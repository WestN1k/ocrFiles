package yamanov.database.DAO;

import java.io.Serializable;

public interface DaoInterface<T, Id extends Serializable> {
    public void insert(T entity);
    public void update(T entity);
    public T findById(Id id);
}
