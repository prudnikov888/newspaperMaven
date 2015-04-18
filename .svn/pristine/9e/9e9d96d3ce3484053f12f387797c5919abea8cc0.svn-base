package db;

import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface Dao<T> {

    void saveOrUpdate(T t);

    T get(Serializable id);

    T load(Serializable id);

    void delete(T t);
}




