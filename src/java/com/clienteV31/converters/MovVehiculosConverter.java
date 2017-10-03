package com.clienteV31.converters;


import com.clienteV31.entities.MovVehiculos;
import com.clienteV31.facades.MovVehiculosFacade;
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
public class MovVehiculosConverter{

    @EJB
    private MovVehiculosFacade ejbFacade;
    
    public MovVehiculosConverter() {
    }

    public MovVehiculos getMovVehiculos(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = MovVehiculos.class)
    public static class MovVehiculosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovVehiculosConverter controller = (MovVehiculosConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movVehiculosConverter");
            return controller.getMovVehiculos(getKey(value));
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
            if (object instanceof MovVehiculos) {
                MovVehiculos o = (MovVehiculos) object;
                return getStringKey(o.getIdMovVehiculo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MovVehiculos.class.getName()});
                return null;
            }
        }

    }

}
