/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Arl;
import com.clienteV31.entities.Departamentos;
import com.clienteV31.entities.Entidades;
import com.clienteV31.entities.Eps;
import com.clienteV31.entities.Paises;
import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.facades.ArlFacade;
import com.clienteV31.facades.DepartamentosFacade;
import com.clienteV31.facades.EntidadesFacade;
import com.clienteV31.facades.EpsFacade;
import com.clienteV31.facades.PaisesFacade;
import com.clienteV31.facades.TiposDocumentoFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

/**
 *
 * @author chernandez
 */
@Singleton
//@Startup
public class GeneralDataControl {
    @EJB
    private TiposDocumentoFacade ejbTiposDocumento;
    private List<TiposDocumento> tiposDocumento;
    
    @EJB
    private EntidadesFacade ejbEntidadesFacade;
    private List<Entidades> personsTypes;
    private List<Entidades> vehiclesTypes;
    
    @EJB
    private PaisesFacade paisesFacade;
    private List<Paises> countries;
    
    @EJB
    private DepartamentosFacade departamentosFacade;
    private List<Departamentos> departments;
    
    @EJB
    private EpsFacade epsFacade;
    private List<Eps> eps;
    private int lastEpsID;
    
    @EJB
    private ArlFacade arlFacade;
    private List<Arl> arl;
    private int lastArlID;
    
    @PostConstruct
    public void init(){
        getTiposDocumento();
        getPersonTypes();
        getCountries();
        getDepartments();
    }

    @Lock(LockType.READ)
    public List<TiposDocumento> getTiposDocumento() {
        if(tiposDocumento==null){
            tiposDocumento = ejbTiposDocumento.findAll();
        }
        return tiposDocumento;
    }
    
    @Lock(LockType.READ)
    public List<Entidades> getPersonTypes() {
        if(personsTypes==null){
            StringBuilder sb = new StringBuilder();
            sb.append(Querys.ENTIDADES_ALL);
            sb.append(" WHERE");
            sb.append(Querys.ENTIDADES_CATEGORIA);
            sb.append(Constants.CATEGORY_PERSON);
            sb.append("'");
            personsTypes = (List<Entidades>) ejbEntidadesFacade.findByQueryArray(sb.toString()).result;
        }
        return personsTypes;
    }
    
    @Lock(LockType.READ)
    public List<Entidades> getVehiclesTypes() {
        if(vehiclesTypes==null){
            StringBuilder sb = new StringBuilder();
            sb.append(Querys.ENTIDADES_ALL);
            sb.append(" WHERE");
            sb.append(Querys.ENTIDADES_CATEGORIA);
            sb.append(Constants.CATEGORY_VEHICLE);
            sb.append("'");
            vehiclesTypes = (List<Entidades>) ejbEntidadesFacade.findByQueryArray(sb.toString()).result;
        }
        return vehiclesTypes;
    }
    
    @Lock(LockType.READ)
    public List<Paises> getCountries(){
        if(countries==null){
            countries = paisesFacade.findAll();
        }
        return countries;
    }

    @Lock(LockType.READ)
    public List<Departamentos> getDepartments() {
        if(departments==null){
            departments = departamentosFacade.findAll();
        }
        return departments;
    }
    
    @Lock(LockType.READ)
    public List<Eps> getEps(){
        if(eps==null){
            eps = epsFacade.findAll();
        }
        else{
            Eps lastEps = (Eps) (epsFacade.findByQuery(Querys.EPS_PRIMARY_KEY, true).result);
            if(lastEpsID != lastEps.getEps()){
                lastEpsID = lastEps.getEps();
                eps = epsFacade.findAll();
            }
        }
        return eps;
    }

    @Lock(LockType.READ)
    public List<Arl> getArl() {
        if(arl==null){
            arl = arlFacade.findAll();
        }
        else{
            Arl lastArl = (Arl) (arlFacade.findByQuery(Querys.ARL_PRIMARY_KEY, true).result);
            if(lastArlID != lastArl.getArl()){
                lastArlID = lastArl.getArl();
                arl = arlFacade.findAll();
            }
        }
        return arl;
    }

}
