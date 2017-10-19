/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Estados;
import com.clienteV31.entities.VehiculosSucursal;
import com.clienteV31.facades.VehiculosSucursalFacade;
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
public class VehiculosSucursalControl {
    
    @EJB
    private VehiculosSucursalFacade vehiculosSucursalFacade;
            
     public Result blockVehicle(VehiculosSucursal vehicle) {
        vehicle.setEstado(new Estados(Constants.STATUS_BLOCKED));
        return vehiculosSucursalFacade.update(vehicle);
    }

    public Result activeVehicle(VehiculosSucursal vehicle) {
        vehicle.setEstado(new Estados(Constants.STATUS_ACTIVE));
        return vehiculosSucursalFacade.update(vehicle);
    }

    public Result disableVehicle(VehiculosSucursal vehicle) {
        vehicle.setEstado(new Estados(Constants.STATUS_INACTIVE));
        return vehiculosSucursalFacade.update(vehicle);
    }
    
    public Result findSpecificVehicle(String plate, Integer branchOffice){
        StringBuilder squery = new StringBuilder();
        squery.append(Querys.VEHICULOS_SUCURSAL_CLI_ALL);
        squery.append(" WHERE");
        squery.append(Querys.VEHICULOS_SUCURSAL_CLI_PLACA);
        squery.append(plate);
        squery.append("' AND");
        squery.append(Querys.VEHICULOS_SUCURSAL_CLI_SUCURSAL);
        squery.append(branchOffice);
        squery.append("'");
        return vehiculosSucursalFacade.findByQuery(squery.toString(), true);
    }

}
