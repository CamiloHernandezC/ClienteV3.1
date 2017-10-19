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
public class ConfigEntryPersonFormsBean extends AbstracConfigFormsBean<ConfigForm>{
    
    
    private Porterias selectedEntry;
    @EJB
    private ConfigFormFacade facade;
    
    // <editor-fold desc="Person Render booleans" defaultstate="collapsed">
    public boolean isShowARL() {
        ConfigForm entity = fieldsEntities.get("Arl");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Arl");
            fieldsEntities.put("Arl", entity);
        }
        return entity.getMostrar();
    }

    public void setShowARL(boolean show) {
        fieldsEntities.get("Arl").setMostrar(show);
        changed = true;
    }

    public boolean isShowCellphone() {
        ConfigForm entity = fieldsEntities.get("Celular");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Celular");
            fieldsEntities.put("Celular", entity);
        }
        return entity.getMostrar();
    }

    public void setShowCellphone(boolean show) {
        fieldsEntities.get("Celular").setMostrar(show);
        changed = true;
    }

    public boolean isShowDepartment() {
        ConfigForm entity = fieldsEntities.get("Departamento");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Departamento");
            fieldsEntities.put("Departamento", entity);
        }
        return entity.getMostrar();
    }

    public void setShowDepartment(boolean show) {
        fieldsEntities.get("Departamento").setMostrar(show);
        changed = true;
    }

    public boolean isShowAddress() {
        ConfigForm entity = fieldsEntities.get("Direccion");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Direccion");
            fieldsEntities.put("Direccion", entity);
        }
        return entity.getMostrar();
    }

    public void setShowAddress(boolean show) {
        fieldsEntities.get("Direccion").setMostrar(show);
        changed = true;
    }

    public boolean isShowMail() {
        ConfigForm entity = fieldsEntities.get("Email");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Email");
            fieldsEntities.put("Email", entity);
        }
        return entity.getMostrar();
    }

    public void setShowMail(boolean show) {
        fieldsEntities.get("Email").setMostrar(show);
        changed = true;
    }

    public boolean isShowEnterprise() {
        ConfigForm entity = fieldsEntities.get("Empresa");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Empresa");
            fieldsEntities.put("Empresa", entity);
        }
        return entity.getMostrar();
    }

    public void setShowEnterprise(boolean show) {
        fieldsEntities.get("Empresa").setMostrar(show);
        changed = true;
    }

    public boolean isShowEPS() {
        ConfigForm entity = fieldsEntities.get("Eps");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Eps");
            fieldsEntities.put("Eps", entity);
        }
        return entity.getMostrar();
    }

    public void setShowEPS(boolean show) {
        fieldsEntities.get("Eps").setMostrar(show);
        changed = true;
    }

    public boolean isShowBirthDay() {
        ConfigForm entity = fieldsEntities.get("Fecha nacimiento");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Fecha nacimiento");
            fieldsEntities.put("Fecha nacimiento", entity);
        }
        return entity.getMostrar();
    }

    public void setShowBirthDay(boolean show) {
        fieldsEntities.get("Fecha nacimiento").setMostrar(show);
        changed = true;
    }

    public boolean isShowFuncionario() {
        ConfigForm entity = fieldsEntities.get("Funcionario");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Funcionario");
            fieldsEntities.put("Funcionario", entity);
        }
        return entity.getMostrar();
    }

    public void setShowFuncionario(boolean show) {
        fieldsEntities.get("Funcionario").setMostrar(show);
        changed = true;
    }

    public boolean isShowOtherID() {
        ConfigForm entity = fieldsEntities.get("Id externo");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Id externo");
            fieldsEntities.put("Id externo", entity);
        }
        return entity.getMostrar();
    }

    public void setShowOtherID(boolean show) {
        fieldsEntities.get("Id externo").setMostrar(show);
        changed = true;
    }

    public boolean isShowMunicipaly() {
        ConfigForm entity = fieldsEntities.get("Municipio");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Municipio");
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
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Pais");
            fieldsEntities.put("Pais", entity);
        }
        return entity.getMostrar();
    }

    public void setShowCountry(boolean show) {
        fieldsEntities.get("Pais").setMostrar(show);
        changed = true;
    }

    public boolean isShowEmergencyContact() {
        ConfigForm entity = fieldsEntities.get("Persona contacto");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Persona contacto");
            fieldsEntities.put("Persona contacto", entity);
        }
        return entity.getMostrar();
    }

    public void setShowEmergencyContact(boolean show) {
        fieldsEntities.get("Persona contacto").setMostrar(show);
        changed = true;
    }

    public boolean isShowRH() {
        ConfigForm entity = fieldsEntities.get("Rh");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Rh");
            fieldsEntities.put("Rh", entity);
        }
        return entity.getMostrar();
    }

    public void setShowRH(boolean show) {
        fieldsEntities.get("Rh").setMostrar(show);
        changed = true;
    }

    public boolean isShowGender() {
        ConfigForm entity = fieldsEntities.get("Sexo");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Sexo");
            fieldsEntities.put("Sexo", entity);
        }
        return entity.getMostrar();
    }

    public void setShowGender(boolean show) {
        fieldsEntities.get("Sexo").setMostrar(show);
        changed = true;
    }

    public boolean isShowEmergencyPhone() {
        ConfigForm entity = fieldsEntities.get("Tel persona contacto");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Tel persona contacto");
            fieldsEntities.put("Tel persona contacto", entity);
        }
        return entity.getMostrar();
    }

    public void setShowEmergencyPhone(boolean show) {
        fieldsEntities.get("Tel persona contacto").setMostrar(show);
        changed = true;
    }

    public boolean isShowPhone() {
        ConfigForm entity = fieldsEntities.get("Telefono");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Telefono");
            fieldsEntities.put("Telefono", entity);
        }
        return entity.getMostrar();
    }

    public void setShowPhone(boolean show) {
        fieldsEntities.get("Telefono").setMostrar(show);
        changed = true;
    }

    public boolean isShowVigencySS() {
        ConfigForm entity = fieldsEntities.get("Vigencia SS");
        if(entity==null){
            entity = new ConfigForm();
            initEntity(entity,Constants.CONFIG_FORM_GERENCIA_PERSONA,"Vigencia SS");
            fieldsEntities.put("Vigencia SS", entity);
        }
        return entity.getMostrar();
    }

    public void setShowVigencySS(boolean show) {
        fieldsEntities.get("Vigencia SS").setMostrar(show);
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
        /*sb.append(" JOIN");
        sb.append(Querys.PORTERIA_SUCURSAL_CLI_TABLE_NAME);
        sb.append(" p on a.porteria = p.porteria");*/
        sb.append(" WHERE");
        sb.append(Querys.CONFIG_FORM_PORTERIA);
        sb.append(selectedEntry.getIdPorteria());
        sb.append("' AND");
        sb.append(Querys.CONFIG_FORM_FORMULARIO);
        sb.append("PERSONA'");
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
