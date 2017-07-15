package mb;

import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import domen.Vlasnik;
import domen.Vrstazivotinje;
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
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import kontroler.Kontroler;
import lazy.LazyDataModelLjubimac;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named("mbLjubimac")
@SessionScoped
public class MBLjubimac implements Serializable {

    private String stranica;
    private Ljubimac ljubimac;
    private boolean izmena = false;
    private boolean stariVlasnik = true;

    public boolean isStariVlasnik() {
        return stariVlasnik;
    }

    public void setStariVlasnik(boolean stariVlasnik) {
        this.stariVlasnik = stariVlasnik;
    }

    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    @Inject
    private LazyDataModelLjubimac lazydmLjubimac;
    @Inject
    Kontroler kontroler;

    public LazyDataModelLjubimac getLazydmLjubimac() {
        return lazydmLjubimac;
    }

    public void setLazydmLjubimac(LazyDataModelLjubimac lazydmLjubimac) {
        this.lazydmLjubimac = lazydmLjubimac;
    }

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
                izmena = false;
                stariVlasnik = false;
                ljubimac = new Ljubimac();
                ljubimac.setVlasnikid(new Vlasnik(-1));
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
        return new ArrayList();
    }

    public void otvoriDialog() {
        Map<String, Object> options = new HashMap();
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
        stariVlasnik = false;
    }

    public List<Vrstazivotinje> ucitajVrsteZivotinje() {
        try {
            return kontroler.ucitajVrsteZivotinje();
        } catch (Exception ex) {
        }
        return null;
    }

    public void sacuvaj() {
        if (!izmena) {
            try {
                kontroler.sacuvaj(ljubimac);
                ljubimac = new Ljubimac();
                ljubimac.setVlasnikid(new Vlasnik(-1));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac je sacuvan!"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac nije sacuvan!"));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (izmena) {
            try {
                kontroler.izmeni(ljubimac);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac je izmenjen!"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac nije izmenjen!"));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void postavidatumrodjenja(SelectEvent e) {
        ljubimac.setDatumrodjenja((Date) e.getObject());
    }

    public void prikaziLjubimca() {
        try {
            ljubimac.setPosetaList(kontroler.prikaziLjubimca(ljubimac));
            izmena = true;
            stranica = "WEB-INF/includes/ljubimac/ljubimacDetalji.xhtml";
        } catch (Exception ex) {
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String vratiOpis(Poseta poseta) {
        String opis = "";
        if (poseta.getStavkaposeteList() != null) {
            for (Stavkaposete sp : poseta.getStavkaposeteList()) {
                opis += sp.getUsluga().getNaziv() + "; ";
            }
        }
        return opis;
    }
}
