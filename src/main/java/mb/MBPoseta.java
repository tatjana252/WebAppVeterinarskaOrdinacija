/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import constants.WebConstants;
import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import domen.StavkaposetePK;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import kontroler.Kontroler;
import lazy.LazyDataModelLjubimac;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author hp
 */
@Named(value = "mbPoseta")
@SessionScoped
public class MBPoseta implements Serializable {

    private String stranica;
    private Poseta poseta;
    private Stavkaposete novastavkaposete;
    
    @Inject
    private LazyDataModelLjubimac lazydmLjubimac;
    
    @Inject
    private Kontroler kontroler;
    
//    @EJB(mappedName="WebConstants")
//    WebConstants webConstants;

    public LazyDataModelLjubimac getLazydmLjubimac() {
        return lazydmLjubimac;
    }

    public void setLazydmLjubimac(LazyDataModelLjubimac lazydmLjubimac) {
        this.lazydmLjubimac = lazydmLjubimac;
    }
    
    public MBPoseta() {
    }

    @PostConstruct
    public void init() {
        poseta = new Poseta();
        poseta.setStavkaposeteList(new ArrayList<Stavkaposete>());
        poseta.setDatum(new Date());
        novastavkaposete = new Stavkaposete();
        stranica = "WEB-INF/includes/poseta/posetaPregled.xhtml";
    }

    public void promeniStranicu(int stranica) {
        switch (stranica) {
            case 1:
                this.stranica = "WEB-INF/includes/poseta/posetaPregled.xhtml";
                break;
            case 2:
                this.stranica = "WEB-INF/includes/poseta/posetaUnos.xhtml";
                break;
        }
    }

    public String getStranica() {
        return stranica;
    }

    public void setStranica(String stranica) {
        this.stranica = stranica;
    }
    
        public void otvoriDialog() {
        Map<String, Object> options = new HashMap();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("ljubimac", options, null);
    }
        
        public void ljubimacOdabran(SelectEvent event) {
         Ljubimac ljubimac = (Ljubimac) event.getObject();
         poseta.setLjubimacid(ljubimac);
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet Owner Selected", "");

        FacesContext.getCurrentInstance().addMessage(null, message);
    }


    public Poseta getPoseta() {
        return poseta;
    }

    public void setPoseta(Poseta poseta) {
        this.poseta = poseta;
    }
    
    public void postaviDatum(SelectEvent e){
         poseta.setDatum((Date) e.getObject());
    }

    public Stavkaposete getNovastavkaposete() {
        return novastavkaposete;
    }

    public void setNovastavkaposete(Stavkaposete novastavkaposete) {
        this.novastavkaposete = novastavkaposete;
        
    }
    
    public List<SelectItem> vratiKategorijeUsluga(){
        try {
            return kontroler.vratiKategorijeUsluga();
        } catch (Exception ex) {
            Logger.getLogger(MBPoseta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
    
    public void obrisiLjubimca(){
        poseta.setLjubimacid(new Ljubimac());
    }
    
    public void dodajStavku(){
        if(novastavkaposete.getUsluga() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate da odaberete uslugu!", null));
            return;
        }
        if(!izmena){
        novastavkaposete.setStavkaposetePK(new StavkaposetePK(0, 0, (poseta.getStavkaposeteList().size()+1)));
        poseta.getStavkaposeteList().add(novastavkaposete);
        }
        novastavkaposete = new Stavkaposete();
    }
    
    public void izmeniStavku(Stavkaposete sp){
        if(sp.getUsluga() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Morate da odaberete uslugu!", null));
            return;
        }
        novastavkaposete = sp;
        izmena = true;
    }
    
    public void obrisiStavku(Stavkaposete sp){
        poseta.getStavkaposeteList().remove(sp);
        for (int i = 0; i < poseta.getStavkaposeteList().size(); i++) {
            poseta.getStavkaposeteList().get(i).getStavkaposetePK().setStavkaposeteid((i+1));
        }
    }
    
    boolean izmena = false;
    
    public void sacuvajPosetu(){
        String error_details = "";
        if(poseta.getDatum() == null){
            error_details += "Morate uneti datum!\n";
        }
        if(poseta.getStavkaposeteList().isEmpty()){
            error_details += "Poseta mora imati stavke!\n";
        }
        if(poseta.getLjubimacid()== null){
            error_details += "Morate odabrati ljubimca!\n";
        }
        if(!error_details.equals("")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Greška prilikom čuvanja posete!", error_details));
            return;
        }
        kontroler.sacuvajPosetu(poseta);
        poseta = new Poseta();
        poseta.setStavkaposeteList(new ArrayList<Stavkaposete>());
        poseta.setDatum(new Date());
        novastavkaposete = new Stavkaposete();
    }
    
     
}
