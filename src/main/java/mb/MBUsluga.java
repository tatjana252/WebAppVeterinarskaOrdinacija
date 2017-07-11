/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Tipusluge;
import domen.Usluga;
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
    private final ResourceBundle bundle = ResourceBundle.getBundle("resources.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @Inject
    private LazyDataModelUsluga lazydmUsluga;

    @Inject
    private LazyDataModelUsluga lazydmUsluga1;

    public LazyDataModelUsluga getLazydmUsluga1() {
        return lazydmUsluga1;
    }

    public void setLazydmUsluga1(LazyDataModelUsluga lazydmUsluga1) {
        this.lazydmUsluga1 = lazydmUsluga1;
    }

    @Inject
    Kontroler kontroler;

    public MBUsluga() {
        u = new Usluga();
    }

    public List<Tipusluge> ucitajTipoveUsluga() {
        try {
            return kontroler.ucitajTipoveUsluga();
        } catch (Exception ex) {
        }
        return new ArrayList<>();
    }

    public String sacuvaj() {
        try {
            kontroler.sacuvaj(u);
            u = new Usluga();
        //    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("service_saved")));
        } catch (Exception ex) {
          //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("service_not_saved")));
        }
        return null;
    }

    public void prikaziUslugu() {
        try {
            odabranaUsluga = kontroler.prikaziUslugu(odabranaUsluga);
        } catch (Exception ex) {
            Logger.getLogger(MBUsluga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            Usluga izmena = (Usluga) event.getObject();
            kontroler.izmeniUslugu(izmena);
            FacesMessage msg = new FacesMessage(bundle.getString("service_changed"));
         //   FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(bundle.getString("service_not_changed"));
          //  FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void obrisiUslugu() {
        try {
            kontroler.obrisiUslugu(odabranaUsluga);
            setPoruka(bundle.getString("service_deleted"));
        } catch (Exception e) {
            setPoruka(bundle.getString("service_not_deleted"));
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

    @PostConstruct
    public void init() {
        stranica = "WEB-INF/includes/usluga/uslugaPregled.xhtml";

    }
}
