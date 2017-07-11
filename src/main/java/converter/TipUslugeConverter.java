/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import domen.Tipusluge;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;
import kontroler.Kontroler;

/**
 *
 * @author hp
 */

@Named("tipuslugeCNV")
@RequestScoped
public class TipUslugeConverter implements Converter{

    @Inject
    Kontroler kontroler;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value==null || value.isEmpty()) return null;
            return kontroler.vratiTipUsluge(value);
        } catch (Exception ex) {
          Logger.getLogger(TipUslugeConverter.class.getName()).log(Level.SEVERE, null, ex);
          
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Tipusluge){
            Tipusluge tu = (Tipusluge) value;
            return tu.getNaziv()+"";
        }
        return "";
    }
    
}
