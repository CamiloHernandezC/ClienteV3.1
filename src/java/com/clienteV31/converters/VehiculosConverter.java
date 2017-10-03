package com.clienteV31.converters;


import com.clienteV31.entities.Vehiculos;
import com.clienteV31.facades.VehiculosFacade;
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
public class VehiculosConverter {

    @EJB
    private VehiculosFacade ejbFacade;
    
    public VehiculosConverter() {
    }

    public Vehiculos getVehiculos(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Vehiculos.class)
    public static class VehiculosControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VehiculosConverter controller = (VehiculosConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vehiculosConverter");
            return controller.getVehiculos(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Vehiculos) {
                Vehiculos o = (Vehiculos) object;
                return getStringKey(o.getPlaca());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Vehiculos.class.getName()});
                return null;
            }
        }

    }

}
