/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.entities.ConfigFormGerencia;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.JsfUtil;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author chernandez
 */
@Named
@ViewScoped
public class ConfigFormsBean implements Serializable{
    
    @Inject
    private BranchOfficeBean branchOfficeBean;
    private List<ConfigFormGerencia> fields;
    private HashMap<String,ConfigFormGerencia> fieldsEntities;
    
    // <editor-fold desc="Render booleans" defaultstate="collapsed">
    private boolean showARL;
    private boolean showCellphone;
    private boolean showDepartment;
    private boolean showAddress;
    private boolean showMail;
    private boolean showEnterprise;
    private boolean showEPS;
    private boolean showBirthDay;
    private boolean showFuncionario;
    private boolean showOtherID;
    private boolean showMunicipaly;
    private boolean showCountry;
    private boolean showEmergencyContact;
    private boolean showRH;
    private boolean showGender;
    private boolean showEmergencyPhone;
    private boolean showPhone;
    private boolean showVigencySS;

    public boolean isShowARL() {
        ConfigFormGerencia entity = fieldsEntities.get("Arl");
        return entity != null && entity.getMostrar();
    }

    public void setShowARL(boolean showARL) {
        this.showARL = showARL;
    }

    public boolean isShowCellphone() {
        return showCellphone;
    }

    public void setShowCellphone(boolean showCellphone) {
        this.showCellphone = showCellphone;
    }

    public boolean isShowDepartment() {
        return showDepartment;
    }

    public void setShowDepartment(boolean showDepartment) {
        this.showDepartment = showDepartment;
    }

    public boolean isShowAddress() {
        return showAddress;
    }

    public void setShowAddress(boolean showAddress) {
        this.showAddress = showAddress;
    }

    public boolean isShowMail() {
        return showMail;
    }

    public void setShowMail(boolean showMail) {
        this.showMail = showMail;
    }

    public boolean isShowEnterprise() {
        return showEnterprise;
    }

    public void setShowEnterprise(boolean showEnterprise) {
        this.showEnterprise = showEnterprise;
    }

    public boolean isShowEPS() {
        return showEPS;
    }

    public void setShowEPS(boolean showEPS) {
        this.showEPS = showEPS;
    }

    public boolean isShowBirthDay() {
        return showBirthDay;
    }

    public void setShowBirthDay(boolean showBirthDay) {
        this.showBirthDay = showBirthDay;
    }

    public boolean isShowFuncionario() {
        return showFuncionario;
    }

    public void setShowFuncionario(boolean showFuncionario) {
        this.showFuncionario = showFuncionario;
    }

    public boolean isShowOtherID() {
        return showOtherID;
    }

    public void setShowOtherID(boolean showOtherID) {
        this.showOtherID = showOtherID;
    }

    public boolean isShowMunicipaly() {
        return showMunicipaly;
    }

    public void setShowMunicipaly(boolean showMunicipaly) {
        this.showMunicipaly = showMunicipaly;
    }

    public boolean isShowCountry() {
        return showCountry;
    }

    public void setShowCountry(boolean showCountry) {
        this.showCountry = showCountry;
    }

    public boolean isShowEmergencyContact() {
        return showEmergencyContact;
    }

    public void setShowEmergencyContact(boolean showEmergencyContact) {
        this.showEmergencyContact = showEmergencyContact;
    }

    public boolean isShowRH() {
        return showRH;
    }

    public void setShowRH(boolean showRH) {
        this.showRH = showRH;
    }

    public boolean isShowGender() {
        return showGender;
    }

    public void setShowGender(boolean showGender) {
        this.showGender = showGender;
    }

    public boolean isShowEmergencyPhone() {
        return showEmergencyPhone;
    }

    public void setShowEmergencyPhone(boolean showEmergencyPhone) {
        this.showEmergencyPhone = showEmergencyPhone;
    }

    public boolean isShowPhone() {
        return showPhone;
    }

    public void setShowPhone(boolean showPhone) {
        this.showPhone = showPhone;
    }

    public boolean isShowVigencySS() {
        return showVigencySS;
    }

    public void setShowVigencySS(boolean showVigencySS) {
        this.showVigencySS = showVigencySS;
    }
    //</editor-fold>

    public void ConfigFormsBean(){}
    
    
    @PostConstruct
    public void loadFieldsPerson() {
        if(branchOfficeBean.getSelectedBranchOffice()==null){
            JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("EditPersonasCliRequiredMessage_idSucursal"));
            return;
        }
        fields = branchOfficeBean.getSelectedBranchOffice().getConfigFormGerenciaList();
        for (int i = 0; i < fields.size(); i++) {
            ConfigFormGerencia modelo = fields.get(i);
            fieldsEntities.put(modelo.getCampo(), modelo);
        }
    }
            /*String tmp = modelo.getCampo().trim();
            switch (tmp) {
                case "Arl":
                    showARL = modelo.getMostrar();
                    break;
                case "Celular":
                    showCellphone = modelo.getMostrar();
                    break;
                case "Departamento":
                    showDepartment = modelo.getMostrar();
                    break;
                case "Direccion":
                    showAddress = modelo.getMostrar();
                    break;
                case "Email":
                    showMail = modelo.getMostrar();
                    break;
                case "Empresa":
                    showEnterprise = modelo.getMostrar();
                    break;
                case "Eps":
                    showEPS = modelo.getMostrar();
                    break;
                case "Fechanacimiento":
                    showBirthDay = modelo.getMostrar();
                    break;
                case "Funcionario":
                    showFuncionario = modelo.getMostrar();
                    break;
                case "Idexterno":
                    showOtherID = modelo.getMostrar();
                    //limpiarIdExterno = showIdExterno;
                    break;
                case "Municipio":
                    showMunicipaly = modelo.getMostrar();
                    break;
                case "Pais":
                    showCountry = modelo.getMostrar();
                    break;
                case "Personacontacto":
                    showEmergencyContact = modelo.getMostrar();
                    break;
                case "Rh":
                    showRH = modelo.getMostrar();
                    break;
                case "Sexo":
                    showGender = modelo.getMostrar();
                    break;
                case "Telpersonacontacto":
                    showPhone = modelo.getMostrar();
                    break;
                case "Telefono":
                    showPhone = modelo.getMostrar();
                    break;
                case "VigenciaSS":
                    showVigencySS = modelo.getMostrar();
                    break;
            }
        }
    }*/
}
