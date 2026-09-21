package db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;


/**
 * BaseDao with parameters, that is used to extend other Dao Classes
 */
@Repository
public class BaseDao<T> implements Dao<T>{

    private static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    // Field injection is required here: @PersistenceContext is handled by
    // PersistenceAnnotationBeanPostProcessor and, unlike EntityManagerFactory,
    // EntityManager cannot be resolved through ordinary constructor autowiring.
    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public void saveOrUpdate(T t){
        try {
            entityManager.merge(t);
            log.info("saveOrUpdate(t):" + t);
            log.info("Save or update (commit):" + t);
         } catch (RuntimeException e) {
            log.error("Error save or update" + getPersistentClass() + "in Dao" + e);
        }

    }
    @Override
    public T get(Serializable id) {
        log.info("Get class by id:" + id);
        T t = null;
        try {
            t = (T) entityManager.find(getPersistentClass(), id);
            log.info("get clazz:" + t);
        } catch (RuntimeException e) {
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
        }
        return t;
    }
    @Override
    public T load(Serializable id) {
        log.info("Load class by id:" + id);
        T t = null;
        try {
            t = (T) entityManager.getReference(getPersistentClass(), id);
            log.info("load() clazz:" + t);
        } catch (RuntimeException e) {
            log.error("Error load() " + getPersistentClass() + " in Dao" + e);
        }
        return t;
    }
   @Override
    public void delete(T t) {
        try {
            entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
            log.info("Delete:" + t);
         } catch (RuntimeException e) {
            log.error("Error save or update" + getPersistentClass() + "in Dao" + e);
         }
    }
    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
