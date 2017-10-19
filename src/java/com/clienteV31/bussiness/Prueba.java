/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.AbstractConfigForm;
import com.clienteV31.entities.ConfigFormGerencia;
import com.clienteV31.utils.Result;
import java.util.HashMap;

/**
 *
 * @author chernandez
 */
public interface Prueba {
    public Result update(HashMap<String, AbstractConfigForm> fieldsEntities);
}
