/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Personas;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.entities.Vehiculos;
import com.clienteV31.entities.VehiculosSucursal;
import com.clienteV31.facades.VehiculosFacade;
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
public class VehiculosControl {
    
    @EJB
    private VehiculosFacade vehiculosFacade;
    @EJB
    private VehiculosSucursalFacade vehiculosSucursalFacade;
    
    public Result findVehicle(String plate){
        StringBuilder squery = new StringBuilder();
        squery.append(Querys.VEHICULOS_ALL);
        squery.append(" WHERE");
        squery.append(Querys.VEHICULOS_PLACA);
        squery.append(plate);
        squery.append("'");
        return vehiculosFacade.findByQuery(squery.toString(), true);
    }
    

    /**
     * Create both (vehicle and vehicle sucursal). if vehicle already exits, it'll be updated
     * @param vehicle
     * @param specificVehicle
     * @return 
     */
    public Result create(Vehiculos vehicle, VehiculosSucursal specificVehicle) {
        Result result;
        if(vehicle.getFecha()==null){//record doesn't exits
            result = vehiculosFacade.create(vehicle);
        }else{
            result = vehiculosFacade.update(vehicle);
        }
        if(result.errorCode==Constants.OK){
            specificVehicle.setVehiculos(vehicle);
            result = vehiculosSucursalFacade.create(specificVehicle);
        }
        return result;
    }
    
    public Result edit(Vehiculos vehicle, VehiculosSucursal specificVehicle) {
        Result result = vehiculosFacade.update(vehicle);
        if(result.errorCode==Constants.OK){
            result = vehiculosSucursalFacade.update(specificVehicle);
        }
        return result;
    }
}
