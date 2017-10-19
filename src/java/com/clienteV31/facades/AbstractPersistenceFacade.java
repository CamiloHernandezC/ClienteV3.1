/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.entities.AbstractEntity;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Result;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author chernandez
 */
public abstract class AbstractPersistenceFacade<T> extends AbstractQueryFacade<T> implements EJBRemotePersistence<T>{
    
    protected T entity;
    
    public AbstractPersistenceFacade(Class<T> entityClass) {
        super(entityClass);
    }
    
    @Override
    public void calculatePrimaryKey(String squery) {
        Result result = findByQuery(squery, true);//Only need the first resul
        AbstractEntity superEntity = (AbstractEntity) entity;
        if (result.errorCode == Constants.NO_RESULT_EXCEPTION) {
            superEntity.setPrimaryKey(1);
            entity = (T) superEntity;
        } else {
            superEntity.setPrimaryKey(((AbstractEntity) result.result).getPrimaryKey() + 1);
            entity = (T) superEntity;
        }
    }

    @Override
    public void assignParametersToUpdate() {
        AbstractEntity aEntity = (AbstractEntity) entity;
        aEntity.setUser(JsfUtil.getSessionUser().getPersona());
        //aEntity.setUser(new Personas(1));//This line is used only for junit test
        aEntity.setDate(new Date());
        entity = (T) aEntity;
    }

    // <editor-fold desc="CRUD" defaultstate="collapsed">
    @Override
    public Result create(T pEntity) {
        entity = pEntity;
        prepareCreate();
        return persist(JsfUtil.PersistAction.CREATE);
    }

    @Override
    public Result update(T pEntity) {
        entity = pEntity;
        prepareUpdate();
        return persist(JsfUtil.PersistAction.UPDATE);
    }

    @Override
    public Result delete(T pEntity) {
        entity = pEntity;
        return persist(JsfUtil.PersistAction.DELETE);
    }
    //</editor-fold>

    @Override
    public Result persist(JsfUtil.PersistAction persistAction) {
        if (entity != null) {
            
            //Fields validation for entity
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
            if (constraintViolations.size() > 0) {
                validationErrorObservation = "";
                Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
                while (iterator.hasNext()) {
                    ConstraintViolation<T> cv = iterator.next();
                    validationErrorObservation += cv.getPropertyPath() + " " + cv.getMessage();
                }
                return new Result(validationErrorObservation, Constants.VALIDATION_ERROR);
            }
            try {
                if (persistAction == JsfUtil.PersistAction.UPDATE) {
                    getEntityManager().merge(entity);
                }
                if (persistAction == JsfUtil.PersistAction.CREATE) {
                    initializeEmbeddableKey();
                    setEmbeddableKeys();
                    getEntityManager().persist(entity);
                }
                if (persistAction == JsfUtil.PersistAction.DELETE){
                    getEntityManager().remove(getEntityManager().merge(entity));
                }
                return new Result(null, Constants.OK);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    return new Result(msg, Constants.PERSISTANCE_EXCEPTION);
                } else {
                    return new Result(ex, Constants.PERSISTANCE_EXCEPTION);
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                return new Result(ex, Constants.PERSISTANCE_EXCEPTION);
            }
        }
        return new Result(null, Constants.UNKNOWN_EXCEPTION);//This should never happen
    }
    
}
