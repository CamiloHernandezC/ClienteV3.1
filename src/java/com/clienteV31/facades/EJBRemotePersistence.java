/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Result;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author chernandez
 * @param <T> entity type
 */
public interface EJBRemotePersistence<T> {
    
    public abstract void setEmbeddableKeys();
    public abstract void initializeEmbeddableKey();
    public abstract void prepareCreate();
    public abstract void prepareUpdate();
    public void prepareDisable();
    public void calculatePrimaryKey(String squery);
    public void assignParametersToUpdate();
    public Result create(T pEntity);
    public Result update(T pEntity);
    /**
     * Set status to disable
     * @param pEntity
     * @return 
     */
    public Result disable(T pEntity);
    /**
     * Delete from database, ¡confirm if you should use this method or disable!
     * @param pEntity
     * @return 
     */
    public Result delete(T pEntity);
    public Result persist(JsfUtil.PersistAction persistAction);
}
