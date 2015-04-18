package services;

import db.Dao;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

@Transactional
@Service
public class BaseService<T> implements IService<T> {

    private static Logger log = Logger.getLogger(BaseService.class);

    @Autowired
    private Dao<T> baseDao;

    public BaseService(){}


    public BaseService(Dao<T> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public void saveOrUpdate(T t) {
        try {
            baseDao.saveOrUpdate(t);
        } catch (HibernateException e) {
            log.error("Error save or update " + getPersistentClass() + "in BaseService" + e);
        }

    }
    @Override
    public T get(Serializable id) {
        try {
            return baseDao.get(id);
        } catch (HibernateException e) {
            log.error("Error get " + getPersistentClass() + "in BaseService" + e);
        }
        return null;

    }

    public T load(Serializable id) {
        try {
            return baseDao.load(id);
        } catch (HibernateException e) {
            log.error("Error load " + getPersistentClass() + "in BaseService" + e);
        }
        return null;
    }
    @Override
    public void delete(T t) {
        try {
            baseDao.delete(t);
        } catch (HibernateException e) {
            log.error("Error delete " + getPersistentClass() + "in BaseService" + e);
        }
    }
    private Class getPersistentClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
