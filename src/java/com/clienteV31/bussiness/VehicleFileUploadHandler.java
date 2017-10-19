/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Departamentos;
import com.clienteV31.entities.Entidades;
import com.clienteV31.entities.Municipios;
import com.clienteV31.entities.Paises;
import com.clienteV31.entities.Vehiculos;
import com.clienteV31.entities.VehiculosSucursal;
import com.clienteV31.entities.Sucursales;
import com.clienteV31.facades.VehiculosFacade;
import com.clienteV31.facades.VehiculosSucursalFacade;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.FileUploadHandler;
import com.clienteV31.utils.Result;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author chernandez
 */
@Stateless
public class VehicleFileUploadHandler extends FileUploadHandler {

    @EJB
    private VehiculosControl vehiculosControl;
    @EJB
    private VehiculosSucursalControl vehiculosSucursalControl;

    @EJB
    private VehiculosSucursalFacade vehiculosSucursalFacade;
    @EJB
    private VehiculosFacade vehiculosFacade;

    private VehiculosSucursal vehiculosSucursal;
    private Vehiculos vehicle;

    @Override
    public Result handleUploadedFile(UploadedFile uploadedFile, int numberOfColums) {
        Result result = super.handleUploadedFile(uploadedFile, numberOfColums); //To change body of generated methods, choose Tools | Templates.
        if (result.errorCode != Constants.OK) {
            return result;
        }
        if (!verifyStructure((String[][]) result.result)) {
            result.errorCode = Constants.WRONG_STRUCTURE_ERROR;
            result.result = "THE STRUCTURE DOESN'T COINCIDE WHIT TEMPLATE, PLEASE DOWNLOAD TEMPLATE AND TRY AGAIN";//TODO CREATE BUNDLE PROPERTY HERE
            return result;
        }
        ArrayList<VehiculosSucursal> vehiclesToSave = castFileToEntity((String[][]) result.result);
        return saveFile(vehiclesToSave);
    }

    /**
     * This method checks primary keys are not null, in order to create vehicle
     * and vehiclea sucursal entities to be more efficient, only use it in update
     * from file
     *
     * @param t
     * @return
     */
    private boolean checkNotNullFieldsToUpdate(VehiculosSucursal t) {
        return t.getSucursales() == null || t.getVehiculos().getPlaca() == null;
    }

    private Result saveFile(ArrayList<VehiculosSucursal> array) {

        ArrayList<VehiculosSucursal> arrayError = new ArrayList<>();
        for (VehiculosSucursal t : array) {
            if (checkNotNullFieldsToUpdate(t)) { //NOT NULL FIELDS ARE CHECKED IN CREATE, BUT TO BE MORE EFFICIENT WE CHECK THEM HERE
                t.setErrorObservation("Los campos obligatorios no están completos");//TODO CREATE PROPERTY HERE                
                arrayError.add(t);
                continue;
            }

            Result existVehicle = vehiculosControl.findVehicle(t.getVehiculos().getPlaca());
            if (existVehicle.errorCode == Constants.NO_RESULT_EXCEPTION) {
                if (checkNotNullFieldsToCreate(t)) {
                    t.setErrorObservation("Los campos obligatorios no están completos");//TODO CREATE PROPERTY HERE                
                    arrayError.add(t);
                    continue;

                }
                Result createResult = vehiculosControl.create(t.getVehiculos(), t);
                if (createResult.errorCode != 0) {
                    t.setErrorObservation((String) createResult.result);
                    arrayError.add(t);
                }
                continue;
            }
            if (existVehicle.errorCode != Constants.OK) {
                //LOGGER
                continue;
            }
            Result existSpecificVehicle = vehiculosSucursalControl.findSpecificVehicle(t.getVehiculos().getPlaca(), t.getSucursales().getIdSucursal());
            vehicle = t.getVehiculos();
            vehiculosSucursal = t;
            if (existSpecificVehicle.errorCode == Constants.OK) {
                updateFromFile((VehiculosSucursal) existSpecificVehicle.result);
            } else {
                vehiculosSucursalFacade.create(t);
            }
            updateFromFile(((Vehiculos) existVehicle.result));
        }
        if (arrayError.isEmpty()) {
            return new Result(null, Constants.OK);
        } else {
            return new Result(arrayError, Constants.PARTIAL_UPLOADED_FILE_ERROR);
        }
    }

    /**
     * Update vehicle's properties from file
     *
     * @param existingVehicle
     * @return
     */
    public Result updateFromFile(Vehiculos existingVehicle) {
        //<editor-fold desc="update properties if added" defaultstate="collapsed">
        if (vehicle.getColor1() == null && existingVehicle.getColor1() !=null){
            vehicle.setColor1(existingVehicle.getColor1());
        }
        if (vehicle.getColor2() == null && existingVehicle.getColor2() !=null){
            vehicle.setColor2(existingVehicle.getColor2());
        }
        if (vehicle.getDepartamento() == null && existingVehicle.getDepartamento() !=null){
            vehicle.setDepartamento(existingVehicle.getDepartamento());
        }
        if (vehicle.getDescripcion() == null && existingVehicle.getDescripcion() !=null){
            vehicle.setDescripcion(existingVehicle.getDescripcion());
        }
        if (vehicle.getEntidad() == null && existingVehicle.getEntidad() !=null){
            vehicle.setEntidad(existingVehicle.getEntidad());
        }
        if (vehicle.getEstado() == null && existingVehicle.getEstado() !=null){
            vehicle.setEstado(existingVehicle.getEstado());
        }
        if (vehicle.getMarca() == null && existingVehicle.getMarca() !=null){
            vehicle.setMarca(existingVehicle.getMarca());
        }
        if (vehicle.getMunicipio() == null && existingVehicle.getMunicipio() !=null){
            vehicle.setMunicipio(existingVehicle.getMunicipio());
        }
        if (vehicle.getPais() == null && existingVehicle.getPais() !=null){
            vehicle.setPais(existingVehicle.getPais());
        }
        
        return vehiculosFacade.update(vehicle);
        //</editor-fold>

    }

    private Result updateFromFile(VehiculosSucursal existingVehicle) {
        vehiculosSucursal.setVehiculosSucursalPK(existingVehicle.getVehiculosSucursalPK());
        vehiculosSucursal.setVehiculos(existingVehicle.getVehiculos());
        vehiculosSucursal.setSucursales(existingVehicle.getSucursales());
        if (vehiculosSucursal.getEstado() == null) {
            vehiculosSucursal.setEstado(existingVehicle.getEstado());
        }
        if (vehiculosSucursal.getIdExterno()== null) {
            vehiculosSucursal.setIdExterno(existingVehicle.getIdExterno());
        }

        return vehiculosSucursalFacade.update(vehiculosSucursal);
    }

    /**
     * This method check not null to create vehicle and vehiclea sucursal entities
     * to be more efficient, only use it in create from file
     *
     * @param t
     * @return
     */
    private boolean checkNotNullFieldsToCreate(VehiculosSucursal t) {
        return t.getVehiculos().getEntidad() == null;
    }

    //<editor-fold desc="castFileToEntity" defaultstate="collapsed">
    private ArrayList<VehiculosSucursal> castFileToEntity(String[][] values) {
        ArrayList<VehiculosSucursal> vehiclesToSave = new ArrayList<>();

        //loading areas lists
        for (int x = 1; x < values.length; x++) {//x=1 is where data begin, x=0 fields name are defined
            VehiculosSucursal specificEntity = new VehiculosSucursal();
            //<editor-fold desc="master data vehicle" defaultstate="collapsed">
            Vehiculos entity = new Vehiculos();

            if (values[x][0] != null) {
                specificEntity.setSucursales(new Sucursales(Integer.valueOf(values[x][0])));
            }

            if (values[x][1] != null) {
                entity.setPlaca(values[x][1]);
            }

            if (values[x][2] != null) {
                switch (values[x][2]) {
                    case "AUTOMOVIL":
                        entity.setEntidad(new Entidades(6));
                        break;
                }
            }
            if (values[x][3] != null) {
                entity.setColor1(values[x][3]);
            }
            if (values[x][4] != null) {
                switch (values[x][4]) {
                    case "COLOMBIA"://TODO CREATE CONSTANT PROPERTY HERE
                        entity.setPais(new Paises(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][5] != null) {
                switch (values[x][5]) {
                    case "BOGOTA"://TODO CREATE CONSTANT PROPERTY HERE
                        entity.setDepartamento(new Departamentos(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            if (values[x][6] != null) {
                switch (values[x][6]) {
                    case "BOGOTA"://TODO CREATE CONSTANT PROPERTY HERE
                        entity.setMunicipio(new Municipios(1));//TODO ASSIGN REAL CASE HERE
                        break;
                }
            }
            
            specificEntity.setVehiculos(entity);
            //</editor-fold>

            vehiclesToSave.add(specificEntity);
        }
        return vehiclesToSave;
    }
    //</editor-fold>

    private boolean verifyStructure(String[][] values) {
        try {
            if ("Sucursal".equals(values[0][0]) && "Placa".equals(values[0][1]) && "Tipo Vehiculo".equals(values[0][2])
                && "Color".equals(values[0][3]) && "Pais".equals(values[0][4]) && "Departamento".equals(values[0][5]) && "Municipio".equals(values[0][6])) {
                return true;
            }
        } catch (Exception e) {
            //Nothing to do here
        }
        return false;
    }
}
