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
import java.util.Observable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author MAURICIO
 */
@Named
@SessionScoped
public class BranchOfficeBean extends Observable implements Serializable{
    
    @EJB
    private AccesoUsuarioFacade ejbFacade;
    private ArrayList<Sucursales> branchOffices = null;
    private Sucursales selectedBranchOffice = null;
    private boolean disableBranchOffice;
    /*THIS FIELD IS DISABLED WHEN:
    disable select one menu for branch office when is creating or editing to avoid verify if person exist for selected branch office again
    Is setting person form configuration
    */
    
    /**
     * Creates a new instance of MenuController
     */
    public BranchOfficeBean() {
    }
    
    public boolean isDisableBranchOffice() {
        return disableBranchOffice;
    }

    public void setDisableBranchOffice(boolean disableBranchOffice) {
        this.disableBranchOffice = disableBranchOffice;
    }

    public boolean isShowBranchOffice() {
        loadBranchOffices();
        if(branchOffices==null || branchOffices.isEmpty()){
            //selectedBranchOffice = new Sucursales(0);//query whit brachOffice = 0 will return no result
            return false;
        }
        if(branchOffices.size()==1){
            selectedBranchOffice = branchOffices.get(0);
            return false;
        }
        return true;
    }

    public Sucursales getSelectedBranchOffice() {
        return selectedBranchOffice;//Don't put initialization because you can crash logic procedures if you do
    }

    public void setSelectedBranchOffice(Sucursales selectedBranchOffice) {
        this.selectedBranchOffice = selectedBranchOffice;
        setChanged();
        notifyObservers();
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
