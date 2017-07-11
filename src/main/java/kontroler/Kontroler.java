/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Korisnik;
import domen.Ljubimac;
import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import mb.MBKorisnik;
import mb.MBUsluga;
import org.primefaces.model.SortOrder;
import services.KorisnikREST;
import services.LjubimacREST;
import services.UslugaREST;

/**
 *
 * @author hp
 */
@Named("Kontroler")
@ApplicationScoped
public class Kontroler implements Serializable{

    public Korisnik login(Korisnik korisnik) throws Exception {
        KorisnikREST korisnikREST = new KorisnikREST();
        Response response = korisnikREST.login(korisnik);
        return getObject(response, Korisnik.class);
    }
    
    
    
    public List<Tipusluge> ucitajTipoveUsluga() throws Exception {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.ucitajTipoveUsluga(Response.class);
        GenericType<List<Tipusluge>> gt = new GenericType<List<Tipusluge>>(){};
        return getObject(response, gt);
    }

    public Tipusluge vratiTipUsluge(String value) throws Exception {
        try{
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.vratiTipUsluge(Response.class, value);
        return getObject(response, Tipusluge.class);
        
        }catch(Exception e){
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
        }return null;
    }

    public String sacuvaj(Usluga u) throws Exception {
        try{
            System.out.println("POZIVAM SERVIS");
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.sacuvajUslugu_XML(u);
            System.out.println("vracam odg");
        return getObject(response, String.class);
        }catch(Exception e){
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public List<Usluga> ucitajUsluge() throws Exception {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.ucitajUsluge_XML(Response.class);
        GenericType<List<Usluga>> gt = new GenericType<List<Usluga>>(){};
        return (List<Usluga>) getObject(response, gt);
    }

    public Usluga prikaziUslugu(Usluga odabranaUsluga) throws Exception {
        UslugaREST uslugaRest = new UslugaREST();
        Response response = uslugaRest.prikaziUslugu_XML(odabranaUsluga);
        Usluga usluga = getObject(response, Usluga.class);
        return usluga;
    }
    
    private <T>T getObject(Response response,  Class<T> type) throws Exception{
        if(Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND){
            String odg = response.readEntity(String.class);
            throw new Exception(odg);
        }else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK){
            return  response.readEntity(type);
        }
        throw new Exception("");
    }
    
    private <T>T getObject(Response response,  GenericType<T> type) throws Exception{
        if(Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND){
            String odg = response.readEntity(String.class);
            throw new Exception(odg);
        }else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK){
            return  response.readEntity(type);
        }
        throw new Exception("");
    }

    public List<Usluga> pretrazi(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            System.out.println("Pozivam servis!");
           UslugaREST uslugaREST = new UslugaREST();
           Response response = uslugaREST.pretrazi(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters));
           GenericType<List<Usluga>> gt = new GenericType<List<Usluga>>(){};
           List<Usluga> usluge = getObject(response, gt);
           return usluge;
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }return null;
    }

    public void izmeniUslugu(Usluga izmena) {
           UslugaREST uslugaREST = new UslugaREST();
           uslugaREST.promeni_XML(izmena, izmena.getUslugaid()+"");

    }

    public void obrisiUslugu(Usluga odabranaUsluga) {
        UslugaREST uslugaREST = new UslugaREST();
           uslugaREST.remove(odabranaUsluga.getUslugaid()+"");
    }

    public List<Vlasnik> ucitajVlasnike() throws Exception {
        LjubimacREST vlasnikREST = new LjubimacREST();
        Response response = vlasnikREST.ucitajVlasnike_XML(Response.class);
        GenericType<List<Vlasnik>> gt = new GenericType<List<Vlasnik>>(){};
        List<Vlasnik> vlasnici = getObject(response, gt);
        return vlasnici;
    }
    
}
