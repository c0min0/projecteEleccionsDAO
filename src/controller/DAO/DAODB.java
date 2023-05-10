package controller.DAO;

import java.util.List;

public interface DAODB <T> {

    // CRUD
    boolean create(T t);

    boolean read(T t);

    T readById (long id); // read by key (id)

    boolean update (T t);

    boolean update(T t, String ... camps);

    boolean delete(T t);

    List<T> search(String camp, Object valor);


    // ALTRES
    boolean exists(T t);

    long count();

    List<T> all();
}
