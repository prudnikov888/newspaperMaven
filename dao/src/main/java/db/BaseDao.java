package db;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;


/**
 * BaseDao with parameters, that is used to extend other Dao Classes
 */
@Repository
public class BaseDao<T> implements Dao<T>{

    private static Logger log = Logger.getLogger(BaseDao.class);

    private SessionFactory sessionFactory;

    public BaseDao(){
    }

    @Autowired
    public BaseDao (SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }

    public Session currentSession(){
        return sessionFactory.getCurrentSession();
    }
    @Override
    public void saveOrUpdate(T t){
        try {
            Session session = currentSession();
            session.saveOrUpdate(t);
            log.info("saveOrUpdate(t):" + t);
            log.info("Save or update (commit):" + t);
         } catch (HibernateException e) {
            log.error("Error save or update" + getPersistentClass() + "in Dao" + e);
        }

    }
    @Override
    public T get(Serializable id) {
        log.info("Get class by id:" + id);
        T t = null;
        try {
            Session session = currentSession();
            t = (T) session.get(getPersistentClass(), id);
            log.info("get clazz:" + t);
        } catch (HibernateException e) {
            log.error("Error get " + getPersistentClass() + " in Dao" + e);
        }
        return t;
    }
    @Override
    public T load(Serializable id) {
        log.info("Load class by id:" + id);
        T t = null;
        try {
            Session session = currentSession();
            t = (T) session.load(getPersistentClass(), id);
            log.info("load() clazz:" + t);
            session.isDirty();
        } catch (HibernateException e) {
            log.error("Error load() " + getPersistentClass() + " in Dao" + e);
        }
        return t;
    }
   @Override
    public void delete(T t) {
        try {
            Session session = currentSession();
            session.delete(t);
            log.info("Delete:" + t);
         } catch (HibernateException e) {
            log.error("Error save or update" + getPersistentClass() + "in Dao" + e);
         }
    }
    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
