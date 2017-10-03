package com.clienteV31.converters;


import com.clienteV31.entities.VisitasEsperadas;
import com.clienteV31.entities.VisitasEsperadasPK;
import com.clienteV31.facades.VisitasEsperadasFacade;
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
public class VisitasEsperadasConverter{

    @EJB
    private VisitasEsperadasFacade ejbFacade;
    
    public VisitasEsperadasConverter() {
    }

    public VisitasEsperadas getVisitasEsperadas(VisitasEsperadasPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = VisitasEsperadas.class)
    public static class VisitasEsperadasControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VisitasEsperadasConverter controller = (VisitasEsperadasConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "visitasEsperadasConverter");
            return controller.getVisitasEsperadas(getKey(value));
        }

        VisitasEsperadasPK getKey(String value) {
            VisitasEsperadasPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new VisitasEsperadasPK();
            key.setIdPersona(Integer.parseInt(values[0]));
            key.setSucursal(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(VisitasEsperadasPK value) {
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
            if (object instanceof VisitasEsperadas) {
                VisitasEsperadas o = (VisitasEsperadas) object;
                return getStringKey(o.getVisitasEsperadasPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), VisitasEsperadas.class.getName()});
                return null;
            }
        }

    }

}
