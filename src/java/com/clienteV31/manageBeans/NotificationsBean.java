/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.manageBeans;

import com.clienteV31.bussiness.NotificationsControl;
import com.clienteV31.entities.Notificaciones;
import com.clienteV31.facades.NotificacionesFacade;
import com.clienteV31.querys.Querys;
import com.clienteV31.utils.BundleUtils;
import com.clienteV31.utils.Constants;
import com.clienteV31.utils.JsfUtil;
import com.clienteV31.utils.Navigation;
import com.clienteV31.utils.Result;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author chernandez
 */
@Named
@SessionScoped
public class NotificationsBean implements Serializable {

    private int activeStep;
    @Inject
    private BranchOfficeBean branchOfficeBean;
    @EJB
    private NotificacionesFacade facade;
    private Notificaciones notification;
    private List<String> availableMessages;
    private String exampleMessage;

    @EJB
    private NotificationsControl notificationsControl;

    public List<String> getAvailableMessages() {
        return availableMessages;
    }

    public boolean isEmptyDropArea() {
        return !(notification.getMostrarEmpresaOrigen() || notification.getMostrarEnte()
                || notification.getMostrarEntidad() || notification.getMostrarPorteria() || notification.getMostrarSucursal());
    }

    public String getExampleMessage() {
        return exampleMessage;
    }

    public Notificaciones getNotification() {
        if (notification == null) {
            notification = new Notificaciones();
        }
        return notification;
    }

    public void setNotification(Notificaciones notification) {
        this.notification = notification;
    }

    public int getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(int activeStep) {
        this.activeStep = activeStep;
    }

    @PostConstruct
    public void init() {
        availableMessages = new ArrayList<>();
        notification = new Notificaciones();
        if (!notification.getMostrarSucursal()) {
            availableMessages.add("Sucursal");
        }
        if (!notification.getMostrarPorteria()) {
            availableMessages.add("Porteria");
        }
        if (!notification.getMostrarEnte()) {
            availableMessages.add("Tipo de Objeto");
        }
        if (!notification.getMostrarEmpresaOrigen()) {
            availableMessages.add("Empresa");
        }
    }

    public void nextStep() {
        if (activeStep < 1) {//Different from last step
            activeStep += 1;
        }
    }

    public void onDrop(DragDropEvent ddEvent) {
        String messageToAdd = ((String) ddEvent.getData());
        //String newMessage=
        if (exampleMessage == null) {
            exampleMessage = "Acaba de llegar/salir";
        }
        switch (messageToAdd) {
            case "Sucursal":
                notification.setMostrarSucursal(true);
                exampleMessage += " a la sucursal: sucursal de ejemplo";
                break;
            case "Porteria":
                notification.setMostrarPorteria(true);
                exampleMessage += " por la porteria: porteria de ejemplo";
                break;
            case "Tipo de Objeto":
                notification.setMostrarEnte(true);
                exampleMessage += " la persona/ el vehículo";
                break;
            case "Empresa":
                notification.setMostrarEmpresaOrigen(true);
                exampleMessage += " de la empresa: empresa de ejemplo";
                break;
        }
        availableMessages.remove(messageToAdd);
    }

    public List<Notificaciones> getItems() {
        if (branchOfficeBean.getSelectedBranchOffice() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Querys.NOTIFICACIONES_ALL);
        sb.append("  WHERE");
        sb.append(Querys.NOTIFICACIONES_SUCURSAL);
        sb.append(branchOfficeBean.getSelectedBranchOffice().getIdSucursal());
        sb.append("'");
        return (List<Notificaciones>) facade.findByQueryArray(sb.toString()).result;
    }

    public String save() {
        notification.setSucursal(branchOfficeBean.getSelectedBranchOffice());
        Result result = notificationsControl.create(notification);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyCreatedRegistry"));
            return Navigation.PAGE_MASTER_DATA_NOTIFICATION;
        }
        JsfUtil.addErrorMessage((String) result.result);
        return null;
    }

    public String edit() {
        Result result = notificationsControl.edit(notification);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyUpdatedRegistry"));
            return Navigation.PAGE_MASTER_DATA_NOTIFICATION;
        }
        JsfUtil.addErrorMessage((String) result.result);
        return null;
    }

    public String delete() {
        Result result = notificationsControl.delete(notification);
        if (result.errorCode == Constants.OK) {
            JsfUtil.addSuccessMessage(BundleUtils.getBundleProperty("SuccessfullyDeletedRegistry"));
            return Navigation.PAGE_MASTER_DATA_NOTIFICATION;
        }
        JsfUtil.addErrorMessage((String) result.result);
        return null;
    }

    public String preEdit(Notificaciones selected) {
        notification = selected;
        exampleMessage = "Acaba de llegar/salir";
        if (notification.getMostrarEmpresaOrigen()) {
            availableMessages.remove("Empresa");
            exampleMessage += " de la empresa: empresa de ejemplo";
        }
        if (notification.getMostrarEnte()) {
            availableMessages.remove("Tipo de Objeto");
            exampleMessage += " la persona/ el vehículo";
        }
        if (notification.getMostrarPorteria()) {
            availableMessages.remove("Porteria");
            exampleMessage += " por la porteria: porteria de ejemplo";
        }
        if (notification.getMostrarSucursal()) {
            availableMessages.remove("Sucursal");
            exampleMessage += " a la sucursal: sucursal de ejemplo";
        }
        return Navigation.PAGE_NOTIFICATION_EDIT;
    }

    public void preDelete(Notificaciones selected) {
        notification = selected;
        JsfUtil.showModal("dialogConfirmDelete");
    }

    public String goToCreate() {
        if (branchOfficeBean.getSelectedBranchOffice() == null) {
            JsfUtil.addErrorMessage("SELECCIONE UNA SUCURSAL");//TODO create bundle propertie
            return null;
        }        
        return Navigation.PAGE_NOTIFICATION_CREATE;
    }

    public String cancel() {
        return Navigation.PAGE_INDEX;
    }
}
