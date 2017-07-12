/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import domen.Vrstazivotinje;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import kontroler.Kontroler;

/**
 *
 * @author hp
 */
@Named("vrstazivotinjeCNV")
@RequestScoped
public class VrstazivotinjeConverter implements Converter {

    @Inject
    Kontroler kontroler;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null || value.isEmpty()) {
                return null;
            }
            return kontroler.vratiVrstuZivotinje(value);
        } catch (Exception ex) {
            Logger.getLogger(VrstazivotinjeConverter.class.getName()).log(Level.SEVERE, null, ex);

            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Vrstazivotinje) {
            Vrstazivotinje tu = (Vrstazivotinje) value;
            return tu.getNaziv() + "";
        }
        return "";
    }

   

}
