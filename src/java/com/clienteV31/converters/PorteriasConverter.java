package com.clienteV31.converters;


import com.clienteV31.entities.Porterias;
import com.clienteV31.entities.Porterias;
import com.clienteV31.facades.PorteriasFacade;
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
public class PorteriasConverter{

    @EJB
    private PorteriasFacade ejbFacade;
    
    public PorteriasConverter() {
    }

    public Porterias getPorterias(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Porterias.class)
    public static class PorteriasControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PorteriasConverter controller = (PorteriasConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "porteriasConverter");
            return controller.getPorterias(getKey(value));
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
            if (object instanceof Porterias) {
                Porterias o = (Porterias) object;
                return getStringKey(o.getIdPorteria());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Porterias.class.getName()});
                return null;
            }
        }

    }

}
