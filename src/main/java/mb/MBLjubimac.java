package mb;

import domen.Ljubimac;
import domen.Poseta;
import domen.Stavkaposete;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import exceptions.RESTException;
import java.io.Serializable;
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
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
                izmena = false;
                ljubimac = new Ljubimac();
                obrisiVlasnika();
                break;
            case 2:
                this.stranica = "WEB-INF/includes/ljubimac/ljubimacIzmena.xhtml";
                
                break;
            case 3:
                this.stranica = "WEB-INF/includes/ljubimac/ljubimacUnos.xhtml";
                izmena = false;
                ljubimac = new Ljubimac();
                obrisiVlasnika();
        }
    }



    public List<Vlasnik> ucitajVlasnike() {
        try {
            return kontroler.ucitajVlasnike();
        } catch (Exception ex) {
            Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RESTException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ""));
        }
        return new ArrayList();
    }

    public void otvoriDialog() {
        Map<String, Object> options = new HashMap();
        options.put("resizable", true);
        options.put("draggable", true);
        options.put("modal", true);
        
        options.put("width", "800px");
        options.put("height", "500px");
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("position", "center top");
        RequestContext.getCurrentInstance().openDialog("vlasnik", options, null);
    }

    public void selectPetOwnerFromDialog(Vlasnik vlasnik) {
        ljubimac.setVlasnikid(vlasnik);
        vlasnikOdabran = true;
        RequestContext.getCurrentInstance().closeDialog(vlasnik);
    }

    public boolean isVlasnikOdabran() {
        return vlasnikOdabran;
    }

    boolean vlasnikOdabran = false;
    public void vlasnikOdabran(SelectEvent event) {
    }

    public void obrisiVlasnika() {
        ljubimac.setVlasnikid(new Vlasnik(-1));
        vlasnikOdabran = false;
    }

    public List<Vrstazivotinje> ucitajVrsteZivotinje() {
        try {
            return kontroler.ucitajVrsteZivotinje();
        } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    public void sacuvaj() {
//        if(ljubimac.getVlasnikid() == null || ljubimac.getVlasnikid().getVlasnikid()==-1){
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ljubimac mora imati vlasnika!"));
//            return;
//        }
        if (!izmena) {
            try {
                String odgovor = kontroler.sacuvaj(ljubimac);
                ljubimac = new Ljubimac();
                ljubimac.setVlasnikid(new Vlasnik(-1));
                obrisiVlasnika();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(odgovor));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ""));
            }
        } else if (izmena) {
            try {
                String odgovor = kontroler.izmeni(ljubimac);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(odgovor));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String prikaziPoseteLjubimca(Ljubimac ljubimac){
        try {
        this.ljubimac = kontroler.prikaziLjubimca(ljubimac);
        prikaziLjubimca();
        return "pretty:ljubimac";
          } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
    }

    public void postavidatumrodjenja(SelectEvent e) {
        ljubimac.setDatumrodjenja((Date) e.getObject());
    }

    public void prikaziLjubimca() {
        try {
            ljubimac = kontroler.prikaziLjubimca(ljubimac);
            izmena = true;
            stranica = "WEB-INF/includes/ljubimac/ljubimacDetalji.xhtml";
       } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", ""));
                Logger.getLogger(MBLjubimac.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RESTException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
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
        System.out.println(opis);
        return opis;
    }
    
    public String getCurrentDate(){
        return new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    }
    
    public boolean isIzmena() {
        return izmena;
    }

    public void setIzmena(boolean izmena) {
        this.izmena = izmena;
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
}
