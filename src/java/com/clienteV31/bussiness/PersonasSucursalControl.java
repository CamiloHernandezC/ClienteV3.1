/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Estados;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.facades.PersonasSucursalFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Result;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chernandez
 */
@Stateless
public class PersonasSucursalControl {
    
    @EJB
    private PersonasSucursalFacade personasSucursalFacade;
            
     public Result blockPerson(PersonasSucursal person) {
        person.setEstado(new Estados(Constants.STATUS_BLOCKED));
        return personasSucursalFacade.update(person);
    }

    public Result activePerson(PersonasSucursal person) {
        person.setEstado(new Estados(Constants.STATUS_ACTIVE));
        return personasSucursalFacade.update(person);
    }

    public Result disablePerson(PersonasSucursal person) {
        person.setEstado(new Estados(Constants.STATUS_INACTIVE));
        return personasSucursalFacade.update(person);
    }
    
    public Result findSpecificPerson(int idPerson, Integer idSucursal){
        StringBuilder squery = new StringBuilder();
        squery.append(Querys.PERSONAS_SUCURSAL_CLI_ALL);
        squery.append("WHERE");
        squery.append(Querys.PERSONAS_SUCURSAL_CLI_PERSONA);
        squery.append(idPerson);
        squery.append("' AND");
        squery.append(Querys.PERSONAS_SUCURSAL_CLI_SUCURSAL);
        squery.append(idSucursal);
        squery.append("'");
        return personasSucursalFacade.findByQuery(squery.toString(), true);
    }

}
