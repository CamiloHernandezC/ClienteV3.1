/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Result;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author chernandez
 * @param <T> entity type
 */
public interface EJBRemoteQuery<T> {
    
   public T find(Object id);
   public List<T> findAll();
   public Result findByQuery(String squery, boolean maxResult);
   public Result findByQueryArray(String squery);
   public void executeQuery(String squery);
}
