/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.AreasControl;
import com.clienteV31.bussiness.GeneralDataControl;
import com.clienteV31.bussiness.MunicipalityControl;
import com.clienteV31.entities.AreasEmpresa;
import com.clienteV31.entities.Arl;
import com.clienteV31.entities.Categorias;
import com.clienteV31.entities.Departamentos;
import com.clienteV31.entities.EmpresaOrigen;
import com.clienteV31.entities.Entidades;
import com.clienteV31.entities.Eps;
import com.clienteV31.entities.Municipios;
import com.clienteV31.entities.Paises;
import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.facades.EmpresaOrigenFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**s
 *
 * @author chernandez
 */
@Named
@ApplicationScoped
public class GeneralViewBean {
    @EJB
    private GeneralDataControl generalDataControl;  
    @EJB
    private AreasControl areasControl;
    @EJB
    private MunicipalityControl municipalityControl;
    @Inject
    private BranchOfficeBean branchOfficeBean;
    @EJB
    private EmpresaOrigenFacade empresaFacade;

    public List<TiposDocumento> getTiposDocumento() {
        return generalDataControl.getTiposDocumento();
    }
    
    public List<Entidades> getPersonTypes() {
        return generalDataControl.getPersonTypes();
    }
    
    public List<Entidades> getVehiclesTypes() {
        return generalDataControl.getVehiclesTypes();
    }
    
    public List<AreasEmpresa> getAreasBySelectedBranchOffice(){
        if(branchOfficeBean.getSelectedBranchOffice()!=null){
            return areasControl.getAreasByBranchOffice(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
        }
        return null;
    }
    
    public List<Paises> getCountries(){
        return generalDataControl.getCountries();
    }
    
    public List<Departamentos> getDepartments(){
        return generalDataControl.getDepartments();
    }
    
    public List<Municipios> getmunicipalitiesByDepartment(int idDepartment){
        return municipalityControl.getmunicipalitiesByDepartment(idDepartment);
    }
    
    public List<Eps> getEps(){
        return generalDataControl.getEps();
    }
    
    public List<Arl> getArl(){
        return generalDataControl.getArl();
    }
    
    public List<EmpresaOrigen> getEnterprises(){
        return empresaFacade.findAll();
    }
    
    public List<Categorias> getCategories(){
        return generalDataControl.getCategories();
    }
    
}
