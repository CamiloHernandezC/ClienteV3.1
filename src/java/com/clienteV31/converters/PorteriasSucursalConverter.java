package com.clienteV31.converters;

import com.clienteV31.entities.PorteriasSucursal;
import com.clienteV31.entities.PorteriasSucursalPK;
import com.clienteV31.facades.PorteriasSucursalFacade;
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
public class PorteriasSucursalConverter {

    @EJB
    private PorteriasSucursalFacade ejbFacade;

    public PorteriasSucursalConverter() {
    }

    private PorteriasSucursal getPorteriasSucursal(PorteriasSucursalPK key) {
        return ejbFacade.find(key);
    }
    
    @FacesConverter(forClass = PorteriasSucursal.class)
    public static class PorteriasSucursalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PorteriasSucursalConverter controller = (PorteriasSucursalConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "porteriasSucursalConverter");
            return controller.getPorteriasSucursal(getKey(value));
        }

        PorteriasSucursalPK getKey(String value) {
            PorteriasSucursalPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PorteriasSucursalPK();
            key.setPorteria(Integer.parseInt(values[0]));
            key.setSucursal(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(PorteriasSucursalPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPorteria());
            sb.append(SEPARATOR);
            sb.append(value.getSucursal());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PorteriasSucursal) {
                PorteriasSucursal o = (PorteriasSucursal) object;
                return getStringKey(o.getPorteriasSucursalPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PorteriasSucursal.class.getName()});
                return null;
            }
        }

    }
}
