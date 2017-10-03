package com.clienteV31.converters;


import com.clienteV31.entities.MaterialesSucursal;
import com.clienteV31.entities.MaterialesSucursalPK;
import com.clienteV31.facades.MaterialesSucursalFacade;
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
public class MaterialesSucursalConverter{

    @EJB
    private MaterialesSucursalFacade ejbFacade;
    
    public MaterialesSucursalConverter() {
    }

    public MaterialesSucursal getMaterialesSucursal(MaterialesSucursalPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = MaterialesSucursal.class)
    public static class MaterialesSucursalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaterialesSucursalConverter controller = (MaterialesSucursalConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materialesSucursalConverter");
            return controller.getMaterialesSucursal(getKey(value));
        }

        MaterialesSucursalPK getKey(String value) {
            MaterialesSucursalPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new MaterialesSucursalPK();
            key.setIdMaterial(Integer.parseInt(values[0]));
            key.setIdSucursal(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(MaterialesSucursalPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdMaterial());
            sb.append(SEPARATOR);
            sb.append(value.getIdSucursal());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof MaterialesSucursal) {
                MaterialesSucursal o = (MaterialesSucursal) object;
                return getStringKey(o.getMaterialesSucursalPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), MaterialesSucursal.class.getName()});
                return null;
            }
        }

    }

}
