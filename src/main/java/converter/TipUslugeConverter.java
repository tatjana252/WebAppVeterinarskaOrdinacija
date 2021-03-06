/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import domen.Tipusluge;
import exceptions.RESTException;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
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
@Named("tipuslugeCNV")
@RequestScoped
public class TipUslugeConverter implements Converter {

    @Inject
    Kontroler kontroler;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            if (value == null || value.isEmpty()) {
                return null;
            }
            return kontroler.vratiTipUsluge(value);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
       return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Tipusluge) {
            Tipusluge tu = (Tipusluge) value;
            return tu.getNaziv() + "";
        }
        return "";
    }

}
