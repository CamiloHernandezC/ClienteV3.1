/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.ConfigFormGerencia;
import com.clienteV31.facades.ConfigFormGerenciaFacade;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Result;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chernandez
 */
@Stateless
public class ConfigFormsAdminControl {
    
    @EJB
    private ConfigFormGerenciaFacade ejbFacade;
    
    public Result update(HashMap<String, ConfigFormGerencia> fieldsEntities) {
        for (ConfigFormGerencia entity : fieldsEntities.values()) {
            Result result = ejbFacade.update(entity);
            if(result.errorCode!=Constants.OK){
                return result;
            }
        }
        return new Result(null, Constants.OK);
    }
}
