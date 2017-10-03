package com.clienteV31.converters;


import com.clienteV31.entities.TiposDocumento;
import com.clienteV31.facades.TiposDocumentoFacade;
import java.util.List;
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
public class TiposDocumentoConverter{

    @EJB
    private TiposDocumentoFacade ejbFacade;
    
    public TiposDocumentoConverter() {
    }
    
    public TiposDocumento getTiposDocumento(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = TiposDocumento.class)
    public static class TiposDocumentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TiposDocumentoConverter controller = (TiposDocumentoConverter) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tiposDocumentoConverter");
            return controller.getTiposDocumento(getKey(value));
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
            if (object instanceof TiposDocumento) {
                TiposDocumento o = (TiposDocumento) object;
                return getStringKey(o.getTipoDocumento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TiposDocumento.class.getName()});
                return null;
            }
        }

    }

}