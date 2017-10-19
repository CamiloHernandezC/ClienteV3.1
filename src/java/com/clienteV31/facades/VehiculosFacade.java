/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.entities.Estados;
import com.clienteV31.entities.Vehiculos;
import com.clienteV31.utils.Constants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author chernandez
 */
@Stateless
public class VehiculosFacade extends AbstractPersistenceFacade<Vehiculos> {

    @PersistenceContext(unitName = "ClienteV3.1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculosFacade() {
        super(Vehiculos.class);
    }

    @Override
    public void setEmbeddableKeys() {
        //Nothign to do here
    }

    @Override
    public void initializeEmbeddableKey() {
        //Nothign to do here
    }

    @Override
    public void prepareCreate() {
        entity.setEstado(new Estados(Constants.STATUS_ACTIVE));
        prepareUpdate();
    }

    @Override
    public void prepareUpdate() {
        assignParametersToUpdate();
    }
    
}
