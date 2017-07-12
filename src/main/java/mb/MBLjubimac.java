/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Ljubimac;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import kontroler.Kontroler;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author hp
 */
@Named(value = "mbLjubimac")
@SessionScoped
public class MBLjubimac implements Serializable {

    private String stranica;
    private Ljubimac ljubimac;
       private final ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());


    @Inject
    Kontroler kontroler;

    /**
     * Creates a new instance of MBLjubimac
     */
    public MBLjubimac() {
    }

    @PostConstruct
    public void init() {
        stranica = "WEB-INF/includes/ljubimac/ljubimacPregled.xhtml";
        ljubimac = new Ljubimac();
        ljubimac.setVlasnikid(new Vlasnik(-1));
    }

    public void promeniStranicu(int stranica) {
        switch (stranica) {
            case 1:
                this.stranica = "WEB-INF/includes/ljubimac/ljubimacPregled.xhtml";
                break;
            case 2:
                this.stranica = "WEB-INF/includes/ljubimac/ljubimacIzmena.xhtml";
                break;
            case 3:
                this.stranica = "WEB-INF/includes/ljubimac/ljubimacUnos.xhtml";
                break;
        }
    }

    public String getStranica() {
        return stranica;
    }

    public void setStranica(String stranica) {
        this.stranica = stranica;
    }

    public Ljubimac getLjubimac() {
        return ljubimac;
    }

    public void setLjubimac(Ljubimac ljubimac) {
        this.ljubimac = ljubimac;
    }

    public List<Vlasnik> ucitajVlasnike() {
        try {
            return kontroler.ucitajVlasnike();
        } catch (Exception ex) {
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public void otvoriDialog() {
        Map<String, Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("draggable", true);
        options.put("modal", true);
        RequestContext.getCurrentInstance().openDialog("vlasnik", options, null);
    }

    public void selectPetOwnerFromDialog(Vlasnik vlasnik) {
        ljubimac.setVlasnikid(vlasnik);
        RequestContext.getCurrentInstance().closeDialog(vlasnik);
    }

    public void vlasnikOdabran(SelectEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pet Owner Selected", "");

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void obrisiVlasnika() {
        ljubimac.setVlasnikid(new Vlasnik(-1));
    }

    public List<Vrstazivotinje> ucitajVrsteZivotinje() {
        try {
            return kontroler.ucitajVrsteZivotinje();
        } catch (Exception ex) {
            return null;
        }
    }

    public void sacuvaj() {
        try {
            kontroler.sacuvaj(ljubimac);
            ljubimac = new Ljubimac();
        ljubimac.setVlasnikid(new Vlasnik(-1));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac je sacuvan!"));
        } catch (Exception ex) {
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac nije sacuvan!"));
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void postavidatumrodjenja(SelectEvent e) {
        ljubimac.setDatumrodjenja((Date)e.getObject());

    }

}
