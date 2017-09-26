/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import domen.StavkaposetePK;
import exceptions.RESTException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import kontroler.Kontroler;

import lazy.LazyDataModelLjubimac;
import lazy.LazyDataModelPoseta;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named(value = "mbPoseta")
@SessionScoped
public class MBPoseta implements Serializable {

    private String stranica;
    private Poseta poseta;
    private Stavkaposete novastavkaposete;
    private boolean prikazDetalja = false;
    

    @Inject
    private LazyDataModelPoseta lazydmPoseta;

    @Inject
    private LazyDataModelLjubimac lazydmLjubimac;

    @Inject
    private Kontroler kontroler;

    public MBPoseta() {
    }

    @PostConstruct
    public void init() {
        initNovaPoseta();
        stranica = "WEB-INF/includes/poseta/posetaPregled.xhtml";
    }

    public void promeniStranicu(int stranica) {
        switch (stranica) {
            case 1:
                this.stranica = "WEB-INF/includes/poseta/posetaPregled.xhtml";
                break;
            case 2:
                this.stranica = "WEB-INF/includes/poseta/posetaUnos.xhtml";
                initNovaPoseta();
                break;
        }
    }

    private void initNovaPoseta() {
        poseta = new Poseta();
        poseta.setStavkaposeteList(new ArrayList<Stavkaposete>());
        poseta.setDatum(new Date());
        novastavkaposete = new Stavkaposete();
        izmenaStavke = false;
        prikazDetalja=false;
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
//        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet Owner Selected", "");
//        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void postaviDatum(SelectEvent e) {
        poseta.setDatum((Date) e.getObject());
    }

    public List<SelectItem> vratiKategorijeUsluga() {
        try {
            return kontroler.vratiKategorijeUsluga();
       } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        return new ArrayList<>();
    }

    public void obrisiLjubimca() {
        poseta.setLjubimacid(new Ljubimac());
    }

    public void dodajStavku() {
        if (novastavkaposete.getUsluga() == null) {
            ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("must_choose_pet_service"), null));
            return;
        }
        if (!izmenaStavke) {
            novastavkaposete.setStavkaposetePK(new StavkaposetePK(0, 0, (poseta.getStavkaposeteList().size() + 1)));
            poseta.getStavkaposeteList().add(novastavkaposete);
        }
        novastavkaposete = new Stavkaposete();
        izmenaStavke = false;
    }

    public void izmeniStavku(Stavkaposete sp) {
        novastavkaposete = sp;
        izmenaStavke = true;
    }

    public void obrisiStavku(Stavkaposete sp) {
        poseta.getStavkaposeteList().remove(sp);
        sredirednebrojeve();
    }

    private void sredirednebrojeve() {
        if (!poseta.getStavkaposeteList().isEmpty()) {
            for (int i = 0; i < poseta.getStavkaposeteList().size(); i++) {
                poseta.getStavkaposeteList().get(i).getStavkaposetePK().setStavkaposeteid((i + 1));
            }
        }
    }

    boolean izmenaStavke = false;

    public void sacuvajPosetu() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String error_details = "";
            if (poseta.getDatum() == null) {
                error_details += bundle.getString("choose_date")+"!\n";
            }
            if (poseta.getStavkaposeteList().isEmpty()) {
                error_details += bundle.getString("no_items")+"!\n";
            }
            if (poseta.getLjubimacid() == null) {
                error_details += bundle.getString("choose_pet")+"!\n";
            }
            if (!error_details.equals("")) {
                
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error_saving_visit"), error_details));
                return;
            }
            String odgovor = kontroler.sacuvajPosetu(poseta);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, odgovor, ""));
            initNovaPoseta();
       } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void prikaziPosetu() {
        try {
            poseta = kontroler.prikaziPosetu(poseta);
            stranica = "WEB-INF/includes/poseta/posetaUnos.xhtml";
            sredirednebrojeve();
            prikazDetalja=true;
        } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public String unesiPosetu(Ljubimac ljubimac){
        promeniStranicu(2);
        poseta.setLjubimacid(ljubimac);
        return "pretty:poseta";
    }

    public LazyDataModelLjubimac getLazydmLjubimac() {
        return lazydmLjubimac;
    }

    public void setLazydmLjubimac(LazyDataModelLjubimac lazydmLjubimac) {
        this.lazydmLjubimac = lazydmLjubimac;
    }

    public LazyDataModelPoseta getLazydmPoseta() {
        return lazydmPoseta;
    }

    public void setLazydmPoseta(LazyDataModelPoseta lazydmPoseta) {
        this.lazydmPoseta = lazydmPoseta;
    }

    public String getStranica() {
        return stranica;
    }

    public void setStranica(String stranica) {
        this.stranica = stranica;
    }

    public Poseta getPoseta() {
        return poseta;
    }

    public void setPoseta(Poseta poseta) {
        this.poseta = poseta;
    }

    public Stavkaposete getNovastavkaposete() {
        return novastavkaposete;
    }

    public void setNovastavkaposete(Stavkaposete novastavkaposete) {

        this.novastavkaposete = novastavkaposete;
    }

    public boolean isIzmenaStavke() {
        return izmenaStavke;
    }
    
    public boolean isPrikazDetalja() {
        return prikazDetalja;
    }
}
