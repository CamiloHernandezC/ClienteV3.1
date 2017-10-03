package com.clienteV31.converters;

import com.clienteV31.entities.PersonasSucursal;
import com.clienteV31.entities.PersonasSucursalPK;
import com.clienteV31.facades.PersonasSucursalFacade;
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
public class PersonasSucursalConverter{

    @EJB
    private PersonasSucursalFacade ejbFacade;
    
    public PersonasSucursalConverter(){
    }
    
    public PersonasSucursal getPersonasSucursal(PersonasSucursalPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = PersonasSucursal.class)
    public static class PersonasSucursalControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PersonasSucursalConverter controller = (PersonasSucursalConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "personasSucursalConverter");
            return controller.getPersonasSucursal(getKey(value));
        }

        PersonasSucursalPK getKey(String value) {
            PersonasSucursalPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new PersonasSucursalPK();
            key.setIdPersona(Integer.parseInt(values[0]));
            key.setSucursal(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(PersonasSucursalPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdPersona());
            sb.append(SEPARATOR);
            sb.append(value.getSucursal());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PersonasSucursal) {
                PersonasSucursal o = (PersonasSucursal) object;
                return getStringKey(o.getPersonasSucursalPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PersonasSucursal.class.getName()});
                return null;
            }
        }

    }

}
