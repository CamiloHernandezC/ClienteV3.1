/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Result;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author MAURICIO
 */
public abstract class AbstractQueryFacade<T> implements EJBRemoteQuery<T>{
    
    protected String validationErrorObservation;
    private final Class<T> entityClass;

    public AbstractQueryFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected abstract EntityManager getEntityManager();

    @Override
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Result findByQuery(String squery, boolean maxResult) {
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery(squery);
            T rEntity;
            if (maxResult) {
                rEntity = (T) query.setMaxResults(1).getSingleResult();
            } else {
                rEntity = (T) query.getSingleResult();
            }
            return new Result(rEntity, Constants.OK);
        } catch (NoResultException nre) {
            return new Result(null, Constants.NO_RESULT_EXCEPTION);
        } catch (NonUniqueResultException nure) {
            return new Result(null, Constants.NO_UNIQUE_RESULT_EXCEPTION);
        }
        //TODO catch malformed query exception and other exceptions and prints with logger for trace errors
    }

    @Override
    public Result findByQueryArray(String squery) {

        EntityManager em = getEntityManager();
        Query query = em.createQuery(squery);
        List<T> list;
        list = (List<T>) query.getResultList();
        if (list.isEmpty()) {
            return new Result(list, Constants.NO_RESULT_EXCEPTION);
        }
        return new Result(list, Constants.OK);
    }

    /**
     * Execute update or delete query
     * @param squery 
     */
    @Override
    public void executeQuery(String squery) {
        EntityManager em = getEntityManager();
        Query query = em.createQuery(squery);
        query.executeUpdate();
    }

}
