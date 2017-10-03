/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Personas;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.facades.PersonasFacade;
import com.clienteV31.facades.PersonasSucursalFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Result;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author chernandez
 */
@Stateless
public class PersonasControl {
    
    @EJB
    private PersonasFacade personasFacade;
    @EJB
    private PersonasSucursalFacade personasSucursalFacade;
    
    public Result findPersonByDocument(Personas person){
        StringBuilder squery = new StringBuilder();
        squery.append(Querys.PERSONA_CLI_ALL);
        squery.append("WHERE");
        squery.append(Querys.PERSONA_CLI_DOC_TYPE);
        squery.append(person.getTipoDocumento().getTipoDocumento());
        squery.append("' AND");
        squery.append(Querys.PERSONA_CLI_DOC_NUMBER);
        squery.append(person.getNumeroDocumento());
        squery.append("'");
        return personasFacade.findByQuery(squery.toString(), true);
    }
    
    public Result create(Personas person, PersonasSucursal specificPerson){
        Result result = null;
        if(person.getIdPersona()==null){//Record doesn't exit
             result = personasFacade.create(person);
        }
        if(result== null || result.errorCode==Constants.OK){//the statement result == null ||, avoid null pointer exception, because if it is null enters inmediatly in the method
            specificPerson.setPersonas(person);
            result = personasSucursalFacade.create(specificPerson);
        }
        return result;
    }

    public Result edit(Personas person, PersonasSucursal specificPerson) {
        Result result = personasFacade.update(person);
        if(result.errorCode==Constants.OK){
            result = personasSucursalFacade.update(specificPerson);
        }
        return result;
    }
}
