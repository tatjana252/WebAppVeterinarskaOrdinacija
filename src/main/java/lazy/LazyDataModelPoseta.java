package lazy;

import domen.Poseta;
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
public class LazyDataModelPoseta extends LazyDataModel<Poseta> {
    @Inject
    Kontroler kontroler;

    public LazyDataModelPoseta() {

    }
    
    @PostConstruct
    public void init(){
        try {
            this.setRowCount(kontroler.ucitajPosete());
      } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            this.setRowCount(0);
       } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            this.setRowCount(0);
       }
    }
    
    @Override
    public Object getRowKey(Poseta poseta) {
        
        return poseta.getPosetaid();
    }    

    @Override
    public List<Poseta> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                filters.replace(key, String.valueOf(value));
            }
            List<Poseta> posete = kontroler.pretraziPosete(first, pageSize, sortField, sortOrder, filters);
            this.setRowCount(kontroler.ucitajPosete());
            return posete;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
        return new ArrayList<>();
    }

    @Override
    public Poseta getRowData(String rowKey) {
       List<Poseta> posete = (List<Poseta>) getWrappedData();
        for (Poseta poseta : posete) {
            if(poseta.getPosetaid()== Integer.parseInt(rowKey)){
                return poseta;
            }
        }
        return null;
    }
    
}
