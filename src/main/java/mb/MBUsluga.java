/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Tipusluge;
import domen.Usluga;
import exceptions.RESTException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import kontroler.Kontroler;
import lazy.LazyDataModelUsluga;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author hp
 */
@Named(value = "mbUsluga")
@SessionScoped
public class MBUsluga implements Serializable {

    private Usluga u;
    private Usluga odabranaUsluga;
    private String poruka;

    @Inject
    private LazyDataModelUsluga lazydmUsluga;

    @Inject
    private LazyDataModelUsluga lazydmUsluga1;

    @Inject
    Kontroler kontroler;

    public MBUsluga() {
        u = new Usluga();
    }

    @PostConstruct
    public void init() {
        stranica = "WEB-INF/includes/usluga/uslugaPregled.xhtml";

    }

    public List<Tipusluge> ucitajTipoveUsluga() {
        try {
            return kontroler.ucitajTipoveUsluga();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public String sacuvaj() {
        try {
            String msg = kontroler.sacuvaj(u);
            u = new Usluga();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean prikaziUslugu() {
        try {
            odabranaUsluga = kontroler.prikaziUslugu(odabranaUsluga);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('detaljiUsluge').show();");
            return true;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            odabranaUsluga = null;
        } catch (RESTException ex) {
            poruka = ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            odabranaUsluga = null;
        }
        return false;
    }

    public void izmeniUslugu() {
        try {
            String odg = kontroler.izmeniUslugu(odabranaUsluga);
            FacesMessage msg = new FacesMessage(odg);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiUslugu() {
        try {
            String odgovor = kontroler.obrisiUslugu(odabranaUsluga);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(odgovor));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getPoruka() {
        return poruka;
    }

    public void setPoruka(String poruka) {
        this.poruka = poruka;
    }

    public LazyDataModel getLazydmUsluga() {
        return lazydmUsluga;
    }

    public Usluga getOdabranaUsluga() {
        return odabranaUsluga;
    }

    public void setOdabranaUsluga(Usluga odabranaUsluga) {
        this.odabranaUsluga = odabranaUsluga;
    }

    public Usluga getU() {
        return u;
    }

    public void setU(Usluga u) {
        this.u = u;
    }

    public void promeniStranicu(int stranica) {
        switch (stranica) {
            case 1:
                this.stranica = "WEB-INF/includes/usluga/uslugaPregled.xhtml";
                break;
            case 2:
                this.stranica = "WEB-INF/includes/usluga/uslugaIzmena.xhtml";
                break;
            case 3:
                this.stranica = "WEB-INF/includes/usluga/uslugaUnos.xhtml";
                break;
        }
    }

    private String stranica;

    public String getStranica() {
        return stranica;
    }

    public void setStranica(String stranica) {
        this.stranica = stranica;
    }

    public LazyDataModelUsluga getLazydmUsluga1() {
        return lazydmUsluga1;
    }

    public void setLazydmUsluga1(LazyDataModelUsluga lazydmUsluga1) {
        this.lazydmUsluga1 = lazydmUsluga1;
    }

}
