/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.JsfUtil;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.chart.BarChartModel;

/**
 *
 * @author chernandez
 */
@Named
@RequestScoped
public class BarSummaryReportBean {
    
    private BarChartModel barModel;
    private Date startDate;
    private Date endDate;
    @EJB
    private BranchOfficeBean branchOfficeBean;
    /*@EJB
    private mov*/

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public String generateReport() {
        if (branchOfficeBean.getSelectedBranchOffice() == null) {
            JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("EditPersonasCliRequiredMessage_idSucursal"));
            return null;
        }
        initializeVariables();
        /*getMovs();
        
        if(!items.isEmpty()){
            showBarModel = true;
            countWorkedHours();
            loadBarModel();
        }*/
        return null;
    }
    
    private void initializeVariables() {
        //items = new ArrayList<>();
        barModel = new BarChartModel();
        //showBarModel = false;
    }
    
    /*private getMovs(){
        
    }*/
    
}
