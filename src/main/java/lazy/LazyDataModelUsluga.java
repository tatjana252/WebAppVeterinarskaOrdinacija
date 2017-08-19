/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazy;

import domen.Usluga;
import exceptions.RESTException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import kontroler.Kontroler;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hp
 */
@Named
@Stateless
public class LazyDataModelUsluga extends LazyDataModel<Usluga> {
    @Inject
    Kontroler kontroler;

    public LazyDataModelUsluga() {

    }
    
    @PostConstruct
    public void init(){
        try {
            this.setRowCount(kontroler.ucitajUsluge().size());
      } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            this.setRowCount(0);
       } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            this.setRowCount(0);
       }
    }
    
    @Override
    public Object getRowKey(Usluga usluga) {
        return usluga.getUslugaid();
    }    

    @Override
    public List<Usluga> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                filters.replace(key, String.valueOf(value));
            }
            List<Usluga> usluge = kontroler.pretrazi(first, pageSize, sortField, sortOrder, filters);
            return usluge;
       } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return new ArrayList<>();
    }

    @Override
    public Usluga getRowData(String rowKey) {
       List<Usluga> usluge = (List<Usluga>) getWrappedData();
        for (Usluga usluga : usluge) {
            if(usluga.getUslugaid() == Integer.parseInt(rowKey)){
                return usluga;
            }
        }
        return null;
    }
    
}
