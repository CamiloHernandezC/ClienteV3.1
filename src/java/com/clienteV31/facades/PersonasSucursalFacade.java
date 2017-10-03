/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.entities.Estados;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.entities.PersonasSucursalPK;
import com.clienteV31.utils.Constants;
import com.sun.corba.se.impl.orbutil.closure.Constant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author chernandez
 */
@Stateless
public class PersonasSucursalFacade extends AbstractPersistenceFacade<PersonasSucursal> {

    @PersistenceContext(unitName = "ClienteV3.1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonasSucursalFacade() {
        super(PersonasSucursal.class);
    }

    @Override
    public void setEmbeddableKeys() {
        entity.getPersonasSucursalPK().setSucursal(entity.getSucursales().getIdSucursal());
        entity.getPersonasSucursalPK().setIdPersona(entity.getPersonas().getIdPersona());
    }

    @Override
    public void initializeEmbeddableKey() {
        entity.setPersonasSucursalPK(new PersonasSucursalPK());
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
