package com.clienteV31.converters;

import com.clienteV31.entities.Entidades;
import com.clienteV31.facades.EntidadesFacade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@RequestScoped
public class EntidadesConverter {

    @EJB
    private  EntidadesFacade ejbFacade;
    
    public EntidadesConverter() {
    }
    
    public Entidades getEntidades(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    @FacesConverter(forClass = Entidades.class)
    public static class EntidadesControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EntidadesConverter controller = (EntidadesConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "entidadesConverter");
            return controller.getEntidades(getKey(value));
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
            if (object instanceof Entidades) {
                Entidades o = (Entidades) object;
                return getStringKey(o.getIdEntidad());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Entidades.class.getName()});
                return null;
            }
        }

    }
    
}
