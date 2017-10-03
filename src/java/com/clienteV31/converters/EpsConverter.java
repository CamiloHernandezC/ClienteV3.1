package com.clienteV31.converters;


import com.clienteV31.entities.Eps;
import com.clienteV31.facades.EpsFacade;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named
@RequestScoped
public class EpsConverter implements Serializable {

    @EJB
    private EpsFacade ejbFacade;

    public EpsConverter() {
    }
   
    public Eps getEps(java.lang.Integer id) {
        return ejbFacade.find(id);
    }
    
    @FacesConverter(forClass = Eps.class)
    public static class EpsControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            EpsConverter controller = (EpsConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "epsConverter");
            return controller.getEps(getKey(value));
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
            if (object instanceof Eps) {
                Eps o = (Eps) object;
                return getStringKey(o.getEps());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Eps.class.getName()});
                return null;
            }
        }

    }

}
