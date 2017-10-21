/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.ConfigFormsControl;
import com.clienteV31.entities.AbstractConfigForm;
import com.clienteV31.entities.Porterias;
import com.clienteV31.facades.AbstractPersistenceFacade;
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
import javax.inject.Inject;

/**
 *
 * @author chernandez
 */
public abstract class AbstracConfigFormsBean<T> implements Serializable, Observer{
    
    @Inject
    protected BranchOfficeBean branchOfficeBean;
    protected List<AbstractConfigForm> fields;
    protected HashMap<String,T> fieldsEntities;
    protected boolean changed;
    protected Porterias selectedEntry;
    protected abstract AbstractPersistenceFacade getFacade();
    @EJB
    private ConfigFormsControl configFormsControl;

    public void ConfigFormsBean(){}
    
     public Porterias getSelectedEntry() {
        return selectedEntry;
    }

    public void setSelectedEntry(Porterias selectedEntry) {
        this.selectedEntry = selectedEntry;
    }
    
    @PostConstruct
    public void init(){
        branchOfficeBean.addObserver(this);
        loadFields();//We need to load field even if branch office is null to avoid null pointer exeptions for example at persons list view
    }
    
    public abstract void loadFields();
    
    public void loadFields(String squery) {
        fields = (List<AbstractConfigForm>) getFacade().findByQueryArray(squery).result;
        for (int i = 0; i < fields.size(); i++) {
            AbstractConfigForm modelo = fields.get(i);
            fieldsEntities.put(modelo.getCampo(), (T) modelo);
        }
    }

    @Override
    public void update(Observable o, Object arg) {//Observer interface method. Different from update in persistence
        selectedEntry = null;//Making selectedEntry null will show the modal in order to user select an entry
        //loadFields();
    }
    
    @PreDestroy
    public void destroy(){
        branchOfficeBean.deleteObserver(this);
    }
    
    public String save(){
        if(changed){
            Result result = configFormsControl.update((HashMap<String, AbstractConfigForm>) fieldsEntities, getFacade());
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
    
    /**
     * Called when the database hasn't the field defined
     * @param entity
     * @param form
     * @param field 
     */
    public void initEntity(AbstractConfigForm entity, String form, String field){
        entity.setFormulario(form);
        entity.setCampo(field);
    }
}
