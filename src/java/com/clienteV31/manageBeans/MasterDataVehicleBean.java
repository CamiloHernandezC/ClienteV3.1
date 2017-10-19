/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.VehicleFileUploadHandler;
import com.clienteV31.bussiness.VehiculosControl;
import com.clienteV31.bussiness.VehiculosSucursalControl;
import com.clienteV31.entities.AreasEmpresa;
import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.entities.Vehiculos;
import com.clienteV31.entities.VehiculosSucursal;
import com.clienteV31.facades.VehiculosSucursalFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Navigation;
import com.clienteV31.utils.Result;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author chernandez
 */
@Named
@SessionScoped
public class MasterDataVehicleBean implements Serializable {

    @Inject
    private BranchOfficeBean branchOfficeBean;
    @EJB
    private VehiculosSucursalFacade vehiculosSucursalFacade;
    @EJB
    private VehiculosSucursalControl vehiculosSucursalControl;
    @EJB
    private VehiculosControl vehiculosControl;
    @EJB
    private VehicleFileUploadHandler fileUploadHandler;

    private ArrayList<VehiculosSucursal> uploadedFileErrorList;
    private Vehiculos vehicle;
    private VehiculosSucursal specificVehicle;

    public ArrayList<VehiculosSucursal> getUploadedFileErrorList() {
        return uploadedFileErrorList;
    }

    public VehiculosSucursal getSpecificVehicle() {
        if (specificVehicle == null) {
            specificVehicle = new VehiculosSucursal();
        }
        return specificVehicle;
    }

    public void setSpecificVehicle(VehiculosSucursal specificVehicle) {
        this.specificVehicle = specificVehicle;
    }

    public Vehiculos getVehicle() {
        if (vehicle == null) {
            vehicle = new Vehiculos();
        }
        return vehicle;
    }

    public void setVehicle(Vehiculos vehicle) {
        this.vehicle = vehicle;
    }

    public List<VehiculosSucursal> getVehiclesByBranchOffice() {
        if (branchOfficeBean.getSelectedBranchOffice() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.VEHICULOS_SUCURSAL_CLI_ALL);
        sb.append(" WHERE");
        sb.append(Querys.VEHICULOS_SUCURSAL_CLI_SUCURSAL);
        sb.append(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
        sb.append("' AND");
        sb.append(Querys.VEHICULOS_SUCURSAL_CLI_NO_ESTADO);
        sb.append(Constants.STATUS_INACTIVE);
        sb.append("'");
        return (List<VehiculosSucursal>) vehiculosSucursalFacade.findByQueryArray(sb.toString()).result;
    }

    public void disableVehicle(VehiculosSucursal vehicle) {
        Result result = vehiculosSucursalControl.disableVehicle(vehicle);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyDeleted"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE + "can't block vehicle " + result.result);
    }

    public void blockVehicle(VehiculosSucursal vehicle) {
        Result result = vehiculosSucursalControl.blockVehicle(vehicle);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("RecordBlocked"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE + "can't block vehicle " + result.result);
        //TODO SHOW DIALOG TO BLOCK PERSON FOR OTHER BRANCH OFICCES WHERE USER HAS ACCESS
    }

    /**
     * unlock vehicle from master data list
     *
     * @param vehicle
     */
    public void activeVehicle(VehiculosSucursal vehicle) {
        Result result = vehiculosSucursalControl.activeVehicle(vehicle);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("RecordUnblocked"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE + "can't active vehicle " + result.result);
    }

    /**
     * Unlock Person from dialog when trying to create ¡specificc Person has to
     * be loaded previously in searchToCreate method!
     *
     * @return
     */
    public String activeVehicle() {
        activeVehicle(specificVehicle);
        clean();
        return Navigation.PAGE_MASTER_DATA_VEHICLE;
    }

    public boolean isHasErrorLoadingFile() {
        return uploadedFileErrorList != null && uploadedFileErrorList.size() > 0;
    }

    public String searchToCreate() {
        Result result = vehiculosControl.findVehicle(vehicle.getPlaca());
        if (result.errorCode == Constants.OK) {//Vehicle already exist, update vehiclea, and create vehiclea sucursal
            vehicle = (Vehiculos) result.result;
            Result specificResult = vehiculosSucursalControl.findSpecificVehicle(vehicle.getPlaca(), branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
            if (specificResult.errorCode == Constants.OK) {
                specificVehicle = (VehiculosSucursal) specificResult.result;
                if (specificVehicle.getEstado().getIdEstado().equals(Constants.STATUS_INACTIVE)) {
                    JsfUtil.showModal("inactiveDialog");
                    return null;
                }
                clean();
                JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("RepeatedRecord"));
                return null;
            }
        }
        if (specificVehicle == null) {//TODO erase this when automatic entry is rendered in form
            specificVehicle = new VehiculosSucursal();
        }
        branchOfficeBean.setDisableBranchOffice(true);
        return Navigation.PAGE_VEHICLE_CREATE;
    }

    public String preEdit(VehiculosSucursal editableVehicle) {
        specificVehicle = editableVehicle;
        vehicle = specificVehicle.getVehiculos();
        branchOfficeBean.setSelectedBranchOffice(specificVehicle.getSucursales());
        branchOfficeBean.setDisableBranchOffice(true);
        return Navigation.PAGE_VEHICLES_EDIT;
    }
    
    public String edit(){
        Result result = vehiculosControl.edit(vehicle, specificVehicle);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyUpdatedRegistry"));
            clean();
            return Navigation.PAGE_MASTER_DATA_VEHICLE;
        }
        if(result.result!=null){//Here comes an error message
            JsfUtil.addErrorMessage((String) result.result);
            return "";//Reload page
        }
        JsfUtil.addTecnicalErrorMessage();
        return "";//Reload page
    }

    public String create() {
        specificVehicle.setSucursales(branchOfficeBean.getSelectedBranchOffice());//this should be doing here because when we create by excel file selected branch office is null
        Result result = vehiculosControl.create(vehicle, specificVehicle);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyCreatedRegistry"));
            clean();
            return Navigation.PAGE_MASTER_DATA_VEHICLE;
        }
        if (result.result != null) {//Here comes an error message
            JsfUtil.addErrorMessage((String) result.result);
            return "";//Reload page
        }
        JsfUtil.addTecnicalErrorMessage();
        return "";//Reload page
    }

    public void clean() {
        branchOfficeBean.setDisableBranchOffice(false);
        specificVehicle = null;
        vehicle = null;
    }

    /**
     * cancel create or edit person
     *
     * @return
     */
    public String cancel() {
        clean();
        return Navigation.PAGE_MASTER_DATA_VEHICLE;
    }
    
    public void downloadTemplate() {

        try {
            String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            XSSFWorkbook workbook;
            workbook = new XSSFWorkbook(new FileInputStream(realPath + "\\resources\\excelTemplate\\VehiclesLoadFile.xlsx"));

            workbook.getSheetAt(0).createRow(2).createCell(0).setCellValue(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());//Load branch office into template
            // write it as an excel attachment
            ByteArrayOutputStream outByteStream = new ByteArrayOutputStream();
            workbook.write(outByteStream);
            byte[] outArray = outByteStream.toByteArray();

            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            // configure response meta-data
            externalContext.setResponseContentType("xlsx");
            externalContext.setResponseContentLength(outArray.length);
            externalContext.setResponseHeader("Expires", "0");
            externalContext.setResponseHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            externalContext.setResponseHeader("Pragma", "public");
            /*
		 * filename* is the proper way to send non-ASCII filenames, but not all browsers support it (e.g. IE 8).
		 * So we also supply a normal filename encoded the same way, since that works on most browsers (but not Firefox).
		 * See RFC 6266 and http://greenbytes.de/tech/tc2231/
             */
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=Plantilla de carga vehiculos.xlsx");

            // write the response and signal JSF that we're done
            workbook.write(externalContext.getResponseOutputStream());
            workbook.close();
            facesContext.responseComplete();
            
            JsfUtil.hideModal("dialogDownloadTemplate");

            // write it as an excel attachment
        } catch (FileNotFoundException ex) {
            JsfUtil.addErrorMessage("NO SE HA ENCONTRADO LA PLANTILLA POR FAVOR CONTACTE AL SERVICIO TÉCNICO");//TODO CREATE PROPERTY HERE
        } catch (IOException ex) {
            JsfUtil.addErrorMessage("NO SE HA PODIDO DESCARGAR LA PLANTILLA, INTENTE DE NUEVO MÁS TARDE");//TODO CREATE PROPERTY HERE
        }
    }
    
    /**
     * If more than one branch office can be selected this method will shows a
     * dialog to choose one, otherwise will download an excel file
     */
    public void selectBranchOfficeToDownload() {
        if (branchOfficeBean.isShowBranchOffice()) {
            JsfUtil.showModal("dialogDownloadTemplate");
        } else {
            downloadTemplate();
        }
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        uploadedFileErrorList = null;
        
        Result result = fileUploadHandler.handleUploadedFile(event.getFile(),Constants.VEHICLE_EXCEL_COLUMNS);
        if(result.errorCode==Constants.PARTIAL_UPLOADED_FILE_ERROR){
            uploadedFileErrorList = (ArrayList<VehiculosSucursal>) result.result;
            JsfUtil.addSuccessMessage("PARTIAL LOAD FILE");//TODO CREATE BUNDLE PROPERTY HERE
            JsfUtil.hideModal("dialogLoad");
            return;
        }
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage("LOAD SUCCESSFULL");//TODO CREATE BUNDLE PROPERTY HERE
            JsfUtil.hideModal("dialogLoad");
            return;
        }
        JsfUtil.addErrorMessage((String) result.result);//TODO CREATE BUNDLE PROPERTY HERE
        JsfUtil.hideModal("dialogLoad");
        
    }

}
