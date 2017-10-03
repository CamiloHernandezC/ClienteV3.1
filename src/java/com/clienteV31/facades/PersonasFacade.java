/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.entities.Personas;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author chernandez
 */
@Stateless
public class PersonasFacade extends AbstractPersistenceFacade<Personas> {

    @PersistenceContext(unitName = "ClienteV3.1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonasFacade() {
        super(Personas.class);
    }

    @Override
    public void setEmbeddableKeys() {
        //Nothing to do here
    }

    @Override
    public void initializeEmbeddableKey() {
        //Nothing to do here
    }

    @Override
    public void prepareCreate() {
        entity.setStatus(Constants.STATUS_ACTIVE);
        calculatePrimaryKey(Querys.PERSONA_CLI_LAST_PRIMARY_KEY);
        prepareUpdate();
    }

    @Override
    public void prepareUpdate() {
        assignParametersToUpdate();
    }
    
}
