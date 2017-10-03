package com.clienteV31.converters;


import com.clienteV31.entities.VehiculosSucursal;
import com.clienteV31.entities.VehiculosSucursalPK;
import com.clienteV31.facades.VehiculosSucursalFacade;
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
public class VehiculosSucursalConverter{

    @EJB
    private VehiculosSucursalFacade ejbFacade;

    public VehiculosSucursalConverter() {
    }

    public VehiculosSucursal getVehiculosSucursal(VehiculosSucursalPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = VehiculosSucursal.class)
    public static class VehiculosSucursalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VehiculosSucursalConverter controller = (VehiculosSucursalConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "vehiculosSucursalConverter");
            return controller.getVehiculosSucursal(getKey(value));
        }

        VehiculosSucursalPK getKey(String value) {
            VehiculosSucursalPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new VehiculosSucursalPK();
            key.setPlaca(values[0]);
            key.setSucursal(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(VehiculosSucursalPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getPlaca());
            sb.append(SEPARATOR);
            sb.append(value.getSucursal());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof VehiculosSucursal) {
                VehiculosSucursal o = (VehiculosSucursal) object;
                return getStringKey(o.getVehiculosSucursalPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VehiculosSucursal.class.getName()});
                return null;
            }
        }

    }

}
