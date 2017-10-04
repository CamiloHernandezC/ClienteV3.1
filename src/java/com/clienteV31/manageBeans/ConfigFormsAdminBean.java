/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.ConfigFormsAdminControl;
import com.clienteV31.entities.ConfigFormGerencia;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Navigation;
import com.clienteV31.utils.Result;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author chernandez
 */
@Named
@ViewScoped
public class ConfigFormsAdminBean implements Serializable, Observer{
    
    @Inject
    private BranchOfficeBean branchOfficeBean;
    private List<ConfigFormGerencia> fields;
    private HashMap<String,ConfigFormGerencia> fieldsEntities;
    private boolean changed;
    @EJB
    private ConfigFormsAdminControl configFormsAdminControl;
    
    // <editor-fold desc="Render booleans" defaultstate="collapsed">
    public boolean isShowARL() {
        ConfigFormGerencia entity = fieldsEntities.get("Arl");
        return entity != null && entity.getMostrar();
    }

    public void setShowARL(boolean show) {
        fieldsEntities.get("Arl").setMostrar(show);
        changed = true;
    }

    public boolean isShowCellphone() {
        ConfigFormGerencia entity = fieldsEntities.get("Celular");
        return entity != null && entity.getMostrar();
    }

    public void setShowCellphone(boolean show) {
        fieldsEntities.get("Celular").setMostrar(show);
        changed = true;
    }

    public boolean isShowDepartment() {
        ConfigFormGerencia entity = fieldsEntities.get("Departamento");
        return entity != null && entity.getMostrar();
    }

    public void setShowDepartment(boolean show) {
        fieldsEntities.get("Departamento").setMostrar(show);
        changed = true;
    }

    public boolean isShowAddress() {
        ConfigFormGerencia entity = fieldsEntities.get("Direccion");
        return entity != null && entity.getMostrar();
    }

    public void setShowAddress(boolean show) {
        fieldsEntities.get("Direccion").setMostrar(show);
        changed = true;
    }

    public boolean isShowMail() {
        ConfigFormGerencia entity = fieldsEntities.get("Email");
        return entity != null && entity.getMostrar();
    }

    public void setShowMail(boolean show) {
        fieldsEntities.get("Email").setMostrar(show);
        changed = true;
    }

    public boolean isShowEnterprise() {
        ConfigFormGerencia entity = fieldsEntities.get("Empresa");
        return entity != null && entity.getMostrar();
    }

    public void setShowEnterprise(boolean show) {
        fieldsEntities.get("Empresa").setMostrar(show);
        changed = true;
    }

    public boolean isShowEPS() {
        ConfigFormGerencia entity = fieldsEntities.get("Eps");
        return entity != null && entity.getMostrar();
    }

    public void setShowEPS(boolean show) {
        fieldsEntities.get("Eps").setMostrar(show);
        changed = true;
    }

    public boolean isShowBirthDay() {
        ConfigFormGerencia entity = fieldsEntities.get("Fecha nacimiento");
        return entity != null && entity.getMostrar();
    }

    public void setShowBirthDay(boolean show) {
        fieldsEntities.get("Fecha nacimiento").setMostrar(show);
        changed = true;
    }

    public boolean isShowFuncionario() {
        ConfigFormGerencia entity = fieldsEntities.get("Funcionario");
        return entity != null && entity.getMostrar();
    }

    public void setShowFuncionario(boolean show) {
        fieldsEntities.get("Funcionario").setMostrar(show);
        changed = true;
    }

    public boolean isShowOtherID() {
        ConfigFormGerencia entity = fieldsEntities.get("Id externo");
        return entity != null && entity.getMostrar();
    }

    public void setShowOtherID(boolean show) {
        fieldsEntities.get("Id externo").setMostrar(show);
        changed = true;
    }

    public boolean isShowMunicipaly() {
        ConfigFormGerencia entity = fieldsEntities.get("Municipio");
        return entity != null && entity.getMostrar();
    }

    public void setShowMunicipaly(boolean show) {
        fieldsEntities.get("Municipio").setMostrar(show);
        changed = true;
    }

    public boolean isShowCountry() {
        ConfigFormGerencia entity = fieldsEntities.get("Pais");
        return entity != null && entity.getMostrar();
    }

    public void setShowCountry(boolean show) {
        fieldsEntities.get("Pais").setMostrar(show);
        changed = true;
    }

    public boolean isShowEmergencyContact() {
        ConfigFormGerencia entity = fieldsEntities.get("Persona contacto");
        return entity != null && entity.getMostrar();
    }

    public void setShowEmergencyContact(boolean show) {
        fieldsEntities.get("Persona contacto").setMostrar(show);
        changed = true;
    }

    public boolean isShowRH() {
        ConfigFormGerencia entity = fieldsEntities.get("Rh");
        return entity != null && entity.getMostrar();
    }

    public void setShowRH(boolean show) {
        fieldsEntities.get("Rh").setMostrar(show);
        changed = true;
    }

    public boolean isShowGender() {
        ConfigFormGerencia entity = fieldsEntities.get("Sexo");
        return entity != null && entity.getMostrar();
    }

    public void setShowGender(boolean show) {
        fieldsEntities.get("Sexo").setMostrar(show);
        changed = true;
    }

    public boolean isShowEmergencyPhone() {
        ConfigFormGerencia entity = fieldsEntities.get("Tel persona contacto");
        return entity != null && entity.getMostrar();
    }

    public void setShowEmergencyPhone(boolean show) {
        fieldsEntities.get("Tel persona contacto").setMostrar(show);
        changed = true;
    }

    public boolean isShowPhone() {
        ConfigFormGerencia entity = fieldsEntities.get("Telefono");
        return entity != null && entity.getMostrar();
    }

    public void setShowPhone(boolean show) {
        fieldsEntities.get("Telefono").setMostrar(show);
        changed = true;
    }

    public boolean isShowVigencySS() {
        ConfigFormGerencia entity = fieldsEntities.get("Vigencia SS");
        return entity != null && entity.getMostrar();
    }

    public void setShowVigencySS(boolean show) {
        fieldsEntities.get("Vigencia SS").setMostrar(show);
        changed = true;
    }
    //</editor-fold>

    public void ConfigFormsBean(){}
    
    @PostConstruct
    public void init(){
        branchOfficeBean.addObserver(this);
        loadFieldsPerson();
    }
    
    public void loadFieldsPerson() {
        if(branchOfficeBean.getSelectedBranchOffice()==null){
           return;
        }
        fieldsEntities = new HashMap<>();
        fields = branchOfficeBean.getSelectedBranchOffice().getConfigFormGerenciaList();
        for (int i = 0; i < fields.size(); i++) {
            ConfigFormGerencia modelo = fields.get(i);
            fieldsEntities.put(modelo.getCampo(), modelo);
        }
    }

    @Override
    public void update(Observable o, Object arg) {//Observer interface method. Different from update in persistence
        loadFieldsPerson();
    }
    
    @PreDestroy
    public void destroy(){
        branchOfficeBean.deleteObserver(this);
    }
    
    public String save(){
        if(changed){
            Result result = configFormsAdminControl.update(fieldsEntities);
            if(result.errorCode!=Constants.OK){
                JsfUtil.addErrorMessage((String) result.result);
                return "";
            }
        }
        JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyConfigured"));
        return Navigation.PAGE_INDEX;
    }
    
    public String cancel(){
        return Navigation.PAGE_INDEX;
    }
}
