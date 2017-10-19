/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.AbstractConfigForm;
import com.clienteV31.facades.AbstractPersistenceFacade;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.Result;
import java.util.HashMap;
import javax.ejb.Stateless;

/**
 *
 * @author chernandez
 */
@Stateless
public class ConfigFormsControl {
    
    public Result update(HashMap<String, AbstractConfigForm> fieldsEntities, AbstractPersistenceFacade facade) {
        for (AbstractConfigForm entity : fieldsEntities.values()) {
            Result result = facade.update(entity);
            if(result.errorCode!=Constants.OK){
                return result;
            }
        }
        return new Result(null, Constants.OK);
    }
}
