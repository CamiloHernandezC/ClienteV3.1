/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.bussiness;

import com.clienteV31.entities.Notificaciones;
import com.clienteV31.facades.NotificacionesFacade;
import com.clienteV31.utils.Result;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author chernandez
 */
@Stateless
public class NotificationsControl {
    
    @EJB
    private NotificacionesFacade facade;

    public Result create(Notificaciones notification) {
        return facade.create(notification);
    }
    
    public Result edit(Notificaciones notification) {
        return facade.update(notification);
    }
    
    public Result delete(Notificaciones notification) {
        return facade.delete(notification);
    }
    
}
