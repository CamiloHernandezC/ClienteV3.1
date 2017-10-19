/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.entities.AbstractConfigForm;
import com.clienteV31.entities.ConfigFormGerencia;
import com.clienteV31.facades.AbstractPersistenceFacade;
import com.clienteV31.facades.ConfigFormGerenciaFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author chernandez
 */
@Named
@ViewScoped
public class ConfigVehicleFormsAdminBean extends AbstracConfigFormsBean<ConfigFormGerencia>{
    
    @EJB
    private ConfigFormGerenciaFacade configFormGerenciaFacade;

    // <editor-fold desc="Vehicle Render booleans" defaultstate="collapsed">
    public boolean isShowColor(){
        ConfigFormGerencia entity = fieldsEntities.get("Color");
        if(entity==null){
            entity = new ConfigFormGerencia();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Color");
            fieldsEntities.put("Color", entity);
        }
        return entity.getMostrar();
    }
    
    public void setShowColor(boolean show) {
        fieldsEntities.get("Color").setMostrar(show);
        changed = true;
    }
    
    public boolean isShowDepartment() {
        ConfigFormGerencia entity = fieldsEntities.get("Departamento");
        if(entity==null){
            entity = new ConfigFormGerencia();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Departamento");
            fieldsEntities.put("Departamento", entity);
        }
        return entity.getMostrar();
    }

    public void setShowDepartment(boolean show) {
        fieldsEntities.get("Departamento").setMostrar(show);
        changed = true;
    }

    public boolean isShowMunicipaly() {
        ConfigFormGerencia entity = fieldsEntities.get("Municipio");
        if(entity==null){
            entity = new ConfigFormGerencia();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Municipio");
            fieldsEntities.put("Municipio", entity);
        }
        return entity.getMostrar();
    }

    public void setShowMunicipaly(boolean show) {
        fieldsEntities.get("Municipio").setMostrar(show);
        changed = true;
    }

    public boolean isShowCountry() {
        ConfigFormGerencia entity = fieldsEntities.get("Pais");
        if(entity==null){
            entity = new ConfigFormGerencia();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Pais");
            fieldsEntities.put("Pais", entity);
        }
        return entity.getMostrar();
    }

    public void setShowCountry(boolean show) {
        fieldsEntities.get("Pais").setMostrar(show);
        changed = true;
    }
    //</editor-fold>
    
    @Override
    public void loadFields() {
        fieldsEntities = new HashMap<>();
        if(branchOfficeBean.getSelectedBranchOffice()==null){
           return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.CONFIG_FORM_GERENCIA_ALL);
        sb.append(" WHERE");
        sb.append(Querys.CONFIG_FORM_GERENCIA_FORMULARIO);
        sb.append("VEHICULO' AND");
        sb.append(Querys.CONFIG_FORM_GERENCIA_SUCURSAL);
        sb.append(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
        sb.append("'");
        super.loadFields(sb.toString());
    }

    @Override
    protected AbstractPersistenceFacade getFacade() {
        return configFormGerenciaFacade;
    }
    
    @Override
    public void initEntity(AbstractConfigForm entity, String form, String field) {
        super.initEntity(entity, form, field); //To change body of generated methods, choose Tools | Templates.
        ((ConfigFormGerencia) entity).setSucursal(branchOfficeBean.getSelectedBranchOffice());
    }
    
}
