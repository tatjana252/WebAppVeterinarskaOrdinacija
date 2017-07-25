/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import domen.Korisnik;
import javax.inject.Named;

import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import kontroler.Kontroler;

/**
 *
 * @author hp
 */
@Named(value = "mbKorisnik")
@SessionScoped
public class MBKorisnik implements Serializable {

    private Korisnik korisnik;
    private boolean loggedIn;
    private Locale locale;
    @Inject
    Kontroler kontroler;

    public MBKorisnik() {
        korisnik = new Korisnik();
    }

    @PostConstruct
    public void init() {
        try{
        locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        }catch(Exception we){
            locale = new Locale("sr", "RS");
        }
    }

    public String login() throws Exception {
        try {
            korisnik = kontroler.login(korisnik);
            loggedIn = true;
            return "pretty:usluga";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
            return null;
        }

    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void changeLocale(int i) {
        switch (i) {
            case 1:
                locale= Locale.ENGLISH;
                break;
            case 2:
                locale = new Locale("sr", "RS");
                break;
        }
          FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
              
    }

    /**
     * ****** GETERI I SETERI ****
     */
    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Locale getLocale() {
        return locale;
    }
    
     public String getLanguage() {
        return locale.getLanguage();
    }
     
     public String getLanguageString(){
        if(getLanguage().equals("en")){
            return "English";
        }
        if(getLanguage().equals("sr")){
            return "Srpski";
        }return "";
     }

}
