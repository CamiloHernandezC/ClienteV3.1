/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.facades.AccesoUsuarioFacade;
import com.clienteV31.entities.AccesoUsuario;
import com.clienteV31.entities.Sucursales;
import com.clienteV31.entities.Usuarios;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Result;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author MAURICIO
 */
@Named
@SessionScoped
public class BranchOfficeBean implements Serializable{
    
    @EJB
    private AccesoUsuarioFacade ejbFacade;
    private ArrayList<Sucursales> branchOffices = null;
    private Sucursales selectedBranchOffice = null;

    /**
     * Creates a new instance of MenuController
     */
    public BranchOfficeBean() {
    }

    public boolean isShowBranchOffice() {
        loadBranchOffices();
        if(branchOffices==null || branchOffices.isEmpty()){
            selectedBranchOffice = new Sucursales(0);//query whit brachOffice = 0 will return no result
            return false;
        }
        if(branchOffices.size()==1){
            selectedBranchOffice = branchOffices.get(0);
            return false;
        }
        return true;
    }

    public Sucursales getSelectedBranchOffice() {
        return selectedBranchOffice;
    }

    public void setSelectedBranchOffice(Sucursales selectedBranchOffice) {
        this.selectedBranchOffice = selectedBranchOffice;
    }

    public ArrayList<Sucursales> getBranchOffices() {
        return branchOffices;
    }

    public void setBranchOffices(ArrayList<Sucursales> branchOffices) {
        this.branchOffices = branchOffices;
    }
    
    public void loadBranchOffices(){
        branchOffices = new ArrayList<>();
        Usuarios user = JsfUtil.getSessionUser();
        String squery = Querys.ACCESO_USUARIO_ALL+" WHERE"+Querys.ACCESO_USUARIO_USUARIO+user.getIdUsuario()+"'";
        Result result = ejbFacade.findByQueryArray(squery);
        if(result.errorCode==Constants.OK){
            List<AccesoUsuario> list = (List<AccesoUsuario>) result.result;
            list.stream().forEach((a) -> {
                branchOffices.add(a.getSucursal());
            });
        }
    }

    
}
