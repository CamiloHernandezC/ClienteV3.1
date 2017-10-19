/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.entities.AbstractConfigForm;
import com.clienteV31.entities.ConfigForm;
import com.clienteV31.entities.Porterias;
import com.clienteV31.facades.AbstractPersistenceFacade;
import com.clienteV31.facades.ConfigFormFacade;
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
public class ConfigEntryVehicleFormsBean extends AbstracConfigFormsBean<ConfigForm>{
    
    private Porterias selectedEntry;
    @EJB
    private ConfigFormFacade facade;
    
    // <editor-fold desc="Vehicle Render booleans" defaultstate="collapsed">
    public boolean isShowType(){
        ConfigForm entity = fieldsEntities.get("Tipo Vehiculo");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Tipo Vehiculo");
            fieldsEntities.put("Tipo Vehiculo", entity);
        }
        return entity.getMostrar();
    }
    
    public void setShowType(boolean show) {
        fieldsEntities.get("Tipo Vehiculo").setMostrar(show);
        changed = true;
    }
    
    public boolean isShowPhoto(){
        ConfigForm entity = fieldsEntities.get("Foto");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Foto");
            fieldsEntities.put("Foto", entity);
        }
        return entity.getMostrar();
    }
    
    public void setShowPhoto(boolean show) {
        fieldsEntities.get("Foto").setMostrar(show);
        changed = true;
    }
    
    public boolean isShowObservation(){
        ConfigForm entity = fieldsEntities.get("Observacion");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_VEHICLE,"Observacion");
            fieldsEntities.put("Observacion", entity);
        }
        return entity.getMostrar();
    }
    
    public void setShowObservation(boolean show) {
        fieldsEntities.get("Observacion").setMostrar(show);
        changed = true;
    }
    
    public boolean isShowColor(){
        ConfigForm entity = fieldsEntities.get("Color");
        if(entity==null){
            entity = new ConfigForm();
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
        ConfigForm entity = fieldsEntities.get("Departamento");
        if(entity==null){
            entity = new ConfigForm();
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
        ConfigForm entity = fieldsEntities.get("Municipio");
        if(entity==null){
            entity = new ConfigForm();
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
        ConfigForm entity = fieldsEntities.get("Pais");
        if(entity==null){
            entity = new ConfigForm();
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

    public Porterias getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(Porterias selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
    @Override
    public void loadFields() {
        fieldsEntities = new HashMap<>();
        if(branchOfficeBean.getSelectedBranchOffice()==null || selectedEntry == null){
           return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.CONFIG_FORM_ALL);
        sb.append(" WHERE");
        sb.append(Querys.CONFIG_FORM_PORTERIA);
        sb.append(selectedEntry.getIdPorteria());
        sb.append("' AND");
        sb.append(Querys.CONFIG_FORM_FORMULARIO);
        sb.append("VEHICULO'");
        super.loadFields(sb.toString());
    }

    @Override
    protected AbstractPersistenceFacade getFacade() {
        return facade;
    }
    
    @Override
    public void initEntity(AbstractConfigForm entity, String form, String field) {
        super.initEntity(entity, form, field); //To change body of generated methods, choose Tools | Templates.
        ((ConfigForm) entity).setPorteria(selectedEntry);
    }
    
}
