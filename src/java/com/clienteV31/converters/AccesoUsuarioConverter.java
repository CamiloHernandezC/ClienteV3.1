/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteV31.converters;

import com.clienteV31.entities.AccesoUsuario;
import com.clienteV31.facades.AccesoUsuarioFacade;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author chernandez
 */
@Named
@RequestScoped
public class AccesoUsuarioConverter {

    @EJB
    private AccesoUsuarioFacade ejbFacade;

    public AccesoUsuarioConverter() {
    }

    private AccesoUsuarioFacade getEjbFacade() {
        return ejbFacade;
    }
    
    @FacesConverter(forClass = AccesoUsuario.class)
    public static class SubAccesoUsuarioConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AccesoUsuarioConverter converter = (AccesoUsuarioConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "accesoUsuarioConverter");
            return converter.getEjbFacade().find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AccesoUsuario) {
                AccesoUsuario o = (AccesoUsuario) object;
                return getStringKey(o.getIDAccesoUsuario());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), AccesoUsuario.class.getName()});
                return null;
            }
        }
    }

}
