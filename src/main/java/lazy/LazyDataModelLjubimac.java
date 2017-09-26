/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazy;

import domen.Ljubimac;
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
import mb.MBLjubimac;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author hp
 */
@Named
@Stateless
public class LazyDataModelLjubimac extends LazyDataModel<Ljubimac> {
    @Inject
    kontroler.Kontroler kontroler;

    public LazyDataModelLjubimac() {

    }
    
    @PostConstruct
    public void init(){
        try {
            this.setRowCount(kontroler.ucitajLjubimce());
       } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            this.setRowCount(0);
       } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            this.setRowCount(0);
       }
       
    }
    
    @Override
    public Object getRowKey(Ljubimac ljubimac) {
        return ljubimac.getLjubimacid();
    }    

    @Override
    public List<Ljubimac> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                filters.replace(key, String.valueOf(value));
            }
            List<Ljubimac> ljubimci = kontroler.pretraziLjubimce(first, pageSize, sortField, sortOrder, filters);
            this.setRowCount(kontroler.ucitajLjubimce());
            return ljubimci;
        } catch (Exception ex) {
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return new ArrayList<>();
    }

    @Override
    public Ljubimac getRowData(String rowKey) {
        try {
            return kontroler.prikaziLjubimca(new Ljubimac(Integer.parseInt(rowKey)));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return null;
    }
    
}
