/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.facades;

import com.clienteV31.entities.EmpresaOrigen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author chernandez
 */
@Stateless
public class EmpresaOrigenFacade extends AbstractQueryFacade<EmpresaOrigen> {

    @PersistenceContext(unitName = "ClienteV3.1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaOrigenFacade() {
        super(EmpresaOrigen.class);
    }
    
}
