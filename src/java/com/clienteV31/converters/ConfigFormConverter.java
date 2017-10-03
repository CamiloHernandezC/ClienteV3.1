package com.clienteV31.converters;


import com.clienteV31.entities.ConfigForm;
import com.clienteV31.facades.ConfigFormFacade;
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
public class ConfigFormConverter{

    @EJB
    private ConfigFormFacade ejbFacade;
   
    public ConfigFormConverter() {
    }
    
    public ConfigForm getConfigForm(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ConfigForm.class)
    public static class ConfigFormControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ConfigFormConverter controller = (ConfigFormConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "configFormConverter");
            return controller.getConfigForm(getKey(value));
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
            if (object instanceof ConfigForm) {
                ConfigForm o = (ConfigForm) object;
                return getStringKey(o.getIdConfigForm());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ConfigForm.class.getName()});
                return null;
            }
        }

    }
}
