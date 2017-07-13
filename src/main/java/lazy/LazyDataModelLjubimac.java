/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lazy;

import domen.Ljubimac;
import domen.Usluga;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
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
public class LazyDataModelLjubimac extends LazyDataModel<Ljubimac> {
    @Inject
    Kontroler kontroler;

    public LazyDataModelLjubimac() {

    }
    
    @PostConstruct
    public void init(){
        try {
            this.setRowCount(kontroler.ucitajLjubimce().size());
        } catch (Exception ex) {
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
            return ljubimci;
        } catch (Exception ex) {
            Logger.getLogger(LazyDataModelLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Ljubimac getRowData(String rowKey) {
       List<Ljubimac> ljubimci = (List<Ljubimac>) getWrappedData();
        for (Ljubimac ljubimac : ljubimci) {
            if(ljubimac.getLjubimacid()== Integer.parseInt(rowKey)){
                return ljubimac;
            }
        }
        return null;
    }
    
}
