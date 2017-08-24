/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Korisnik;
import domen.Ljubimac;
import domen.LjubimacSaPosetama;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import exceptions.RESTException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import mb.MBKorisnik;
import org.primefaces.model.SortOrder;
import services.KorisnikREST;
import services.LjubimacREST;
import services.PosetaREST;
import services.UslugaREST;
import services.VlasnikREST;
import services.VrstazivotinjeREST;

/**
 *
 * @author hp
 */
@Named("Kontroler")
@ApplicationScoped
public class Kontroler implements Serializable {

    @Inject
    MBKorisnik mbKorisnik;

    public Request getRequest(Object obj) {
        return new Request(mbKorisnik.getKorisnik(), obj, mbKorisnik.getLanguage());
    }

    public Korisnik login(Korisnik korisnik) throws Exception, RESTException {
        KorisnikREST korisnikREST = new KorisnikREST();
        Request request = getRequest(null);
        request.setKorisnik(korisnik);
        Response response = korisnikREST.login(request);
        return getObject(response, Korisnik.class);
    }

    public List<Tipusluge> ucitajTipoveUsluga() throws Exception, RESTException {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.ucitajTipoveUsluga(Response.class);
        GenericType<List<Tipusluge>> gt = new GenericType<List<Tipusluge>>() {
        };
        return getObject(response, gt);
    }

    public Tipusluge vratiTipUsluge(String value) throws Exception, RESTException {
        try {
            UslugaREST uslugaREST = new UslugaREST();
            Response response = uslugaREST.vratiTipUsluge(Response.class, value);
            return getObject(response, Tipusluge.class);

        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public String sacuvaj(Usluga u) throws Exception, RESTException {
        try {
            UslugaREST uslugaREST = new UslugaREST();
            Response response = uslugaREST.sacuvaj_XML(getRequest(u));
            return getObject(response, String.class);
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public List<Usluga> ucitajUsluge() throws Exception, RESTException {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.ucitajSve_XML(getRequest(null));
        GenericType<List<Usluga>> gt = new GenericType<List<Usluga>>() {
        };
        return (List<Usluga>) getObject(response, gt);
    }

    public Usluga prikaziUslugu(Usluga odabranaUsluga) throws Exception, RESTException {
        UslugaREST uslugaRest = new UslugaREST();
        System.out.println("saljem uslugu " +odabranaUsluga);
        Response response = uslugaRest.prikazi_XML(getRequest(odabranaUsluga));
        Usluga usluga = getObject(response, Usluga.class);
        return usluga;
    }

    private <T> T getObject(Response response, Class<T> type) throws Exception, RESTException {
        if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND) {
            String odg = response.readEntity(String.class);
            if(odg.startsWith("<!DOCTYPE html PUBLIC")){
                odg = "ERROR!";
            }
            throw new RESTException(odg);
        } else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK) {
            return response.readEntity(type);
        }
        throw new Exception(response.getStatusInfo().getStatusCode() + "");
    }

    private <T> T getObject(Response response, GenericType<T> type) throws Exception, RESTException {
        if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND) {
            String odg = response.readEntity(String.class);
            if(odg.startsWith("<!DOCTYPE html PUBLIC")){
                odg = "ERROR!";
            }
           throw new RESTException(odg);
        } else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK) {
            return response.readEntity(type);
        }
        throw new Exception("");
    }

    public List<Usluga> pretrazi(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws RESTException {
        try {
            UslugaREST uslugaREST = new UslugaREST();
            Response response = uslugaREST.pretrazi(getRequest(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters)));
            GenericType<List<Usluga>> gt = new GenericType<List<Usluga>>() {
            };
            List<Usluga> usluge = getObject(response, gt);
            return usluge;
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String izmeniUslugu(Usluga izmena) throws Exception, RESTException {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.izmeni_XML(getRequest(izmena));
        String odgovor = getObject(response, String.class);
        return odgovor;
    }

    public String obrisiUslugu(Usluga odabranaUsluga) throws Exception, RESTException {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.obrisi_XML(getRequest(odabranaUsluga));
        String odgovor = getObject(response, String.class);
        return odgovor;
    }

    public List<Vlasnik> ucitajVlasnike() throws Exception, RESTException {
        VlasnikREST vlasnikREST = new VlasnikREST();
        Response response = vlasnikREST.ucitajSve_XML(getRequest(null));
        GenericType<List<Vlasnik>> gt = new GenericType<List<Vlasnik>>() {
        };
        List<Vlasnik> vlasnici = getObject(response, gt);
        return vlasnici;
    }
    List<Vrstazivotinje> vrsteZivotinja;

    public List<Vrstazivotinje> ucitajVrsteZivotinje() throws Exception, RESTException {
        VrstazivotinjeREST ljubimacREST = new VrstazivotinjeREST();
        Response response = ljubimacREST.ucitajSve_XML(getRequest(null));
        GenericType<List<Vrstazivotinje>> gt = new GenericType<List<Vrstazivotinje>>() {
        };
        vrsteZivotinja = getObject(response, gt);
        return vrsteZivotinja;
    }

    public Object vratiVrstuZivotinje(String value) {
        if (vrsteZivotinja != null) {
            for (Vrstazivotinje vrstazivotinje : vrsteZivotinja) {
                if (vrstazivotinje.getNaziv().equals(value)) {
                    return vrstazivotinje;
                }
            }
        }
        return null;
    }

    public String sacuvaj(Ljubimac ljubimac) throws Exception, RESTException {

            LjubimacREST ljubimacREST = new LjubimacREST();
            Response response = ljubimacREST.sacuvaj_XML(getRequest(ljubimac));
            return getObject(response, String.class);
        
    }

    public List<Ljubimac> pretraziLjubimce(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception, RESTException {
        try {

            LjubimacREST ljubimacREST = new LjubimacREST();
            Response response = ljubimacREST.pretrazi(getRequest(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters)));
            GenericType<List<Ljubimac>> gt = new GenericType<List<Ljubimac>>() {
            };
            List<Ljubimac> ljubimac = getObject(response, gt);
            return ljubimac;
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public int ucitajLjubimce() throws Exception, RESTException {
        LjubimacREST ljubimacREST = new LjubimacREST();
        Response response = ljubimacREST.countAll_XML(getRequest(null));
        String odg = getObject(response, String.class);
        Integer poseta = Integer.parseInt(odg);
        return poseta;
    }

    public Ljubimac prikaziLjubimca(Ljubimac odabraniLjubimac) throws Exception, RESTException {
        LjubimacREST ljubimacREST = new LjubimacREST();
        Response response = ljubimacREST.prikazi_XML(getRequest(odabraniLjubimac));
        GenericType<LjubimacSaPosetama> gt = new GenericType<LjubimacSaPosetama>() {
        };
        LjubimacSaPosetama ljp = (LjubimacSaPosetama) getObject(response, gt);
        Ljubimac lj = ljp.vratiLjubimca();
        return lj;
    }

    public String izmeni(Ljubimac ljubimac) throws Exception, RESTException {
        LjubimacREST ljubimacREST = new LjubimacREST();
        Response response = ljubimacREST.izmeni_XML(getRequest(ljubimac));
        String odgovor = getObject(response, String.class);
        return odgovor;
    }

    public List<SelectItem> vratiKategorijeUsluga() throws Exception, RESTException {
        ArrayList<SelectItem> categories = new ArrayList<>();
        List<Tipusluge> tipoviUsluga = ucitajTipoveUsluga();
        List<Usluga> usluge = ucitajUsluge();
        for (Tipusluge tipusluge : tipoviUsluga) {
            SelectItemGroup grupa = new SelectItemGroup(tipusluge.getNaziv().toUpperCase());
            List<SelectItem> uslugel = new ArrayList<>();
            for (int i = 0; i < usluge.size(); i++) {
                if (usluge.get(i).getTipuslugeid().equals(tipusluge)) {
                    uslugel.add(new SelectItem(usluge.get(i), usluge.get(i).getNaziv()));
                }
            }
            SelectItem[] items = new SelectItem[uslugel.size()];
            for (int i = 0; i < items.length; i++) {
                items[i] = uslugel.get(i);
            }
            grupa.setSelectItems(items);
            categories.add(grupa);
        }
        return categories;
    }

    public Object vratiUslugu(String value) throws Exception, RESTException {
        List<Usluga> usluge = ucitajUsluge();
        for (Usluga usluga : usluge) {
            if (usluga.getNaziv().equals(value)) {
                return usluga;
            }
        }
        return null;
    }

    public String sacuvajPosetu(Poseta poseta) throws Exception, RESTException {
        PosetaREST posetaRest = new PosetaREST();
        Response response = posetaRest.sacuvaj_XML(getRequest(poseta));
        String odgovor = getObject(response, String.class);
        return odgovor;
    }

    public int ucitajPosete() throws RESTException {
        try {
            PosetaREST posetaREST = new PosetaREST();
            Response response = posetaREST.countAll_XML(getRequest(null));
            String odg = getObject(response, String.class);
            Integer poseta = Integer.parseInt(odg);
            return poseta;
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public List<Poseta> pretraziPosete(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception, RESTException {
        try {
            PosetaREST posetaREST = new PosetaREST();
            Response response = posetaREST.pretrazi_XML(getRequest(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters)));
            GenericType<List<Poseta>> gt = new GenericType<List<Poseta>>() {
            };
            List<Poseta> poseta = getObject(response, gt);
            return poseta;
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public Poseta prikaziPosetu(Poseta poseta) throws Exception, RESTException {
        PosetaREST posetaREST = new PosetaREST();
        Response response = posetaREST.prikazi_XML(getRequest(poseta));
        return (Poseta) getObject(response, Poseta.class);
    }

}
