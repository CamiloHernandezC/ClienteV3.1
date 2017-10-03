/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.AreasEmpresa;
import com.clienteV31.entities.Municipios;
import com.clienteV31.facades.AreasEmpresaFacade;
import com.clienteV31.facades.MunicipiosFacade;
import com.clienteV31.querys.Querys;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chernandez
 */
@Stateless
public class MunicipalityControl {
    
    @EJB
    private MunicipiosFacade municipiosFacade;
    
    public List<Municipios> getmunicipalitiesByDepartment(int idDepartment){
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.MUNICIPIOS_CLI_DEPARTAMENTO);
        sb.append(idDepartment);
        sb.append("'");
        return (List<Municipios>) municipiosFacade.findByQueryArray(sb.toString()).result;
    }
}
