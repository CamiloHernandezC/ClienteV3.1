/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.AreasControl;
import com.clienteV31.bussiness.PersonFileUploadHandler;
import com.clienteV31.bussiness.PersonasControl;
import com.clienteV31.bussiness.PersonasSucursalControl;
import com.clienteV31.entities.AreasEmpresa;
import com.clienteV31.facades.PersonasSucursalFacade;
import com.clienteV31.entities.Personas;
import com.clienteV31.entities.PersonasSucursal;
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
@SessionScoped//Session bean because it has to handle different views (master data person list, edit and create)
public class MasterDataPersonBean implements Serializable {

    private Personas person;
    @Inject
    private BranchOfficeBean branchOfficeBean;
    @EJB
    private PersonasSucursalFacade personaSucurcalFacade;
    @EJB
    private AreasControl areasControl;
    @EJB
    private PersonasSucursalControl personasSucursalControl;
    @EJB
    private PersonasControl personasControl;
    private PersonasSucursal specificPerson;
    
    @EJB
    private PersonFileUploadHandler fileUploadHandler;
    
    private String otherEnterpriseName;
    private ArrayList<PersonasSucursal> uploadedFileErrorList;

    public ArrayList<PersonasSucursal> getUploadedFileErrorList() {
        return uploadedFileErrorList;
    }

    public String getOtherEnterpriseName() {
        return otherEnterpriseName;
    }

    public void setOtherEnterpriseName(String otherEnterpriseName) {
        this.otherEnterpriseName = otherEnterpriseName;
    }

    public boolean isHasErrorLoadingFile() {
        return uploadedFileErrorList!=null && uploadedFileErrorList.size()>0;
    }

    public Personas getPerson() {
        if (person == null) {
            person = new Personas();
        }
        return person;
    }

    public void setPerson(Personas person) {
        this.person = person;
    }

    public PersonasSucursalControl getPersonasSucursalControl() {
        return personasSucursalControl;
    }

    public void setPersonasSucursalControl(PersonasSucursalControl personasSucursalControl) {
        this.personasSucursalControl = personasSucursalControl;
    }

    public PersonasSucursal getSpecificPerson() {
        if(specificPerson==null){
            specificPerson = new PersonasSucursal();
        }
        return specificPerson;
    }

    public void setSpecificPerson(PersonasSucursal specificPerson) {
        this.specificPerson = specificPerson;
    }

    public void handleFileUpload(FileUploadEvent event) {
        uploadedFileErrorList = null;
        Result result = fileUploadHandler.handleUploadedFile(event.getFile(),Constants.PERSON_EXCEL_COLUMNS);
        if(result.errorCode==Constants.PARTIAL_UPLOADED_FILE_ERROR){
            uploadedFileErrorList = (ArrayList<PersonasSucursal>) result.result;
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

    public String searchToCreate(){
        Result result = personasControl.findPersonByDocument(person);
        if(result.errorCode==Constants.OK){//Person already exist, update persona, and create persona sucursal
            person = (Personas) result.result;
            Result specificResult = personasSucursalControl.findSpecificPerson(person.getIdPersona(), branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
            if(specificResult.errorCode==Constants.OK){
                specificPerson = (PersonasSucursal) specificResult.result;
                if(specificPerson.getEstado().getIdEstado().equals(Constants.STATUS_INACTIVE)){
                    JsfUtil.showModal("inactiveDialog");
                    return null;
                }
                clean();
                JsfUtil.addErrorMessage(BundleUtils.getBundleProperty("RepeatedRecord"));
                return null;
            }
        }
        //branchOfficeBean.setDisableBranchOffice(true);
        return Navigation.PAGE_PERSONAS_CREATE;
    }

    public void downloadTemplate() {

        try {
            String realPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            XSSFWorkbook workbook;
            workbook = new XSSFWorkbook(new FileInputStream(realPath + "\\resources\\excelTemplate\\PersonsLoadFile.xlsx"));

            List<AreasEmpresa> areas = areasControl.getAreasByBranchOffice(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());

            Sheet areasSheet = workbook.getSheetAt(10);//WE HAVE TO GET SHEET BECAUSE IF WE CREATE SHEET THE REFERENCE FROM DROP DOWN LIST WILL LOST
            for (int i = 0; i < areas.size(); i++) {//Start in 1 because cell A1 in excel file is sheet's name
                Row row = areasSheet.createRow(i);
                Cell cell = row.createCell(0);
                cell.setCellValue(areas.get(i).getDescripcion());
            }
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
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=Plantilla de carga personas.xlsx");

            // write the response and signal JSF that we're done
            workbook.write(externalContext.getResponseOutputStream());
            workbook.close();
            facesContext.responseComplete();
            
            JsfUtil.hideModal("dialogDownloadTemplate");

            // write it as an excel attachment
        } catch (FileNotFoundException ex) {
            if (ex.getMessage().equals("C:\\ACTIV\\Plantilla de carga personas.xlsx (El proceso no tiene acceso al archivo porque está siendo utilizado por otro proceso)")) {
                JsfUtil.addErrorMessage("OTRO PROGRAMA ESTÁ UTILIZANDO EL ARCHIVO POR FAVOR CIERRELO Y DESCARGUE DE NUEVO");//TODO CREATE PROPERTY HERE
            } else {
                JsfUtil.addErrorMessage("NO SE HA ENCONTRADO LA PLANTILLA POR FAVOR CONTACTE AL SERVICIO TÉCNICO");//TODO CREATE PROPERTY HERE
            }
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

    /**
     * Load person where branch office is equal to selected and status is
     * different of inactive
     *
     * @return List of people belonging to selected branch office
     *
     */
    public List<PersonasSucursal> getPersonsByBranchOffice() {
        if (branchOfficeBean.getSelectedBranchOffice() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.PERSONAS_SUCURSAL_CLI_ALL);sb.append("WHERE");sb.append(Querys.PERSONAS_SUCURSAL_CLI_SUCURSAL);
        sb.append(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());sb.append("' AND");
        sb.append(Querys.PERSONAS_SUCURSAL_CLI_NO_ESTADO);sb.append(Constants.STATUS_INACTIVE);sb.append("'");
        return (List<PersonasSucursal>) personaSucurcalFacade.findByQueryArray(sb.toString()).result;
    }

    public String preEdit(PersonasSucursal editablePerson) {
        specificPerson = editablePerson;
        person = specificPerson.getPersonas();
        branchOfficeBean.setSelectedBranchOffice(specificPerson.getSucursales());
        //branchOfficeBean.setDisableBranchOffice(true);
        return Navigation.PAGE_PERSONAS_EDIT;
    }

    public void disablePerson(PersonasSucursal person) {
        Result result = personasSucursalControl.disablePerson(person);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyDeleted"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE+"can't block person "+result.result);
    }
    
    public void blockPerson(PersonasSucursal person) {
        Result result = personasSucursalControl.blockPerson(person);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("RecordBlocked"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE+"can't block person "+result.result);
        //TODO SHOW DIALOG TO BLOCK PERSON FOR OTHER BRANCH OFICCES WHERE USER HAS ACCESS
    }

    /**
     * unlock person from master data list
     * @param person
     */
    public void activePerson(PersonasSucursal person) {
        Result result = personasSucursalControl.activePerson(person);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("RecordUnblocked"));
            return;
        }
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, Constants.LOGGER_ERROR_MESSAGE+"can't active person "+result.result);
    }

    /**    
     * Unlock Person from dialog when trying to create
     * ¡specificc Person has to be loaded previously in searchToCreate method!
     * @return
     */
    public String activePerson() {
        activePerson(specificPerson);
        clean();
        return Navigation.PAGE_MASTER_DATA_PERSON;
    }
    
    /**
     * cancel create or edit person
     * @return 
     */
    public String cancel(){
        clean();
        JsfUtil.hideModal("dialogConfirmCancel");
        return Navigation.PAGE_MASTER_DATA_PERSON;
    }
    
    public void clean(){
        person = null;
        specificPerson = null;
    }
    
    public String create(){
        specificPerson.setSucursales(branchOfficeBean.getSelectedBranchOffice());
        Result result = personasControl.create(person, specificPerson);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyCreatedRegistry"));
            clean();
            return Navigation.PAGE_MASTER_DATA_PERSON;
        }
        if(result.result!=null){//Here comes an error message
            JsfUtil.addErrorMessage((String) result.result);
            return "";//Reload page
        }
        JsfUtil.addTecnicalErrorMessage();
        return "";//Reload page
    }
    
    public String edit(){
        Result result = personasControl.edit(person, specificPerson);
        if(result.errorCode==Constants.OK){
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyUpdatedRegistry"));
            clean();
            return Navigation.PAGE_MASTER_DATA_PERSON;
        }
        if(result.result!=null){//Here comes an error message
            JsfUtil.addErrorMessage((String) result.result);
            return "";//Reload page
        }
        JsfUtil.addTecnicalErrorMessage();
        return "";//Reload page
    }

}
