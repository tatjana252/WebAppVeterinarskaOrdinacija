/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Korisnik;
import domen.Ljubimac;
import domen.Poseta;
import domen.Request;
import domen.Search;
import domen.Tipusluge;
import domen.Usluga;
import domen.Vlasnik;
import domen.Vrstazivotinje;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
import mb.MBUsluga;
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
    
    public Request getRequest(Object obj){
        return new Request(mbKorisnik.getKorisnik(), obj);
    }

    public Korisnik login(Korisnik korisnik) throws Exception {
        KorisnikREST korisnikREST = new KorisnikREST();
        Response response = korisnikREST.login(korisnik);
        return getObject(response, Korisnik.class);
    }

    public List<Tipusluge> ucitajTipoveUsluga() throws Exception {
        UslugaREST uslugaREST = new UslugaREST();
        Response response = uslugaREST.ucitajTipoveUsluga(Response.class);
        GenericType<List<Tipusluge>> gt = new GenericType<List<Tipusluge>>() {
        };
        return getObject(response, gt);
    }

    public Tipusluge vratiTipUsluge(String value) throws Exception {
        try {
            UslugaREST uslugaREST = new UslugaREST();
            Response response = uslugaREST.vratiTipUsluge(Response.class, value);
            return getObject(response, Tipusluge.class);

        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public String sacuvaj(Usluga u) throws Exception {
        try {
            UslugaREST uslugaREST = new UslugaREST();
            Response response = uslugaREST.sacuvaj_XML(getRequest(u));
            return getObject(response, String.class);
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public List<Usluga> ucitajUsluge() throws Exception {
        UslugaREST uslugaREST = new UslugaREST();
        System.out.println("pozivam servis");
        Response response = uslugaREST.ucitajSve_XML(getRequest(null));
        System.out.println("pozvan servis primeljen odgovor");
        GenericType<List<Usluga>> gt = new GenericType<List<Usluga>>() {
        };
        return (List<Usluga>) getObject(response, gt);
    }

    public Usluga prikaziUslugu(Usluga odabranaUsluga) throws Exception {
        UslugaREST uslugaRest = new UslugaREST();
        Response response = uslugaRest.prikazi_XML(getRequest(odabranaUsluga));
        Usluga usluga = getObject(response, Usluga.class);
        return usluga;
    }

    private <T> T getObject(Response response, Class<T> type) throws Exception {
        if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND) {
            String odg = response.readEntity(String.class);
            throw new Exception(odg);
        } else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK) {
            return response.readEntity(type);
        }
        throw new Exception("");
    }

    private <T> T getObject(Response response, GenericType<T> type) throws Exception {
         if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.NOT_FOUND) {
            String odg = response.readEntity(String.class);
            throw new Exception(odg);
        } else if (Response.Status.fromStatusCode(response.getStatus()) == Response.Status.OK) {
            return response.readEntity(type);
        }
        throw new Exception("");
    }

    public List<Usluga> pretrazi(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
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

    public void izmeniUslugu(Usluga izmena) {
        UslugaREST uslugaREST = new UslugaREST();
        uslugaREST.izmeni_XML(getRequest(izmena));

    }

    public void obrisiUslugu(Usluga odabranaUsluga) {
        UslugaREST uslugaREST = new UslugaREST();
        uslugaREST.obrisi_XML(getRequest(odabranaUsluga));
    }

    public List<Vlasnik> ucitajVlasnike() throws Exception {
        VlasnikREST vlasnikREST = new VlasnikREST();
        Response response = vlasnikREST.ucitajSve_XML(getRequest(null));
        GenericType<List<Vlasnik>> gt = new GenericType<List<Vlasnik>>() {
        };
        List<Vlasnik> vlasnici = getObject(response, gt);
        return vlasnici;
    }
    List<Vrstazivotinje> vrsteZivotinja;

    public List<Vrstazivotinje> ucitajVrsteZivotinje() throws Exception {
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

    public String sacuvaj(Ljubimac ljubimac) throws Exception {
        try {
            LjubimacREST ljubimacREST = new LjubimacREST();
            Response response = ljubimacREST.sacuvaj_XML(getRequest(ljubimac));
            return getObject(response, String.class);
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public List<Ljubimac> pretraziLjubimce(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
        try {

            LjubimacREST ljubimacREST = new LjubimacREST();
            Response response = ljubimacREST.pretrazi(getRequest(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters)) );
            GenericType<List<Ljubimac>> gt = new GenericType<List<Ljubimac>>() {
            };
            List<Ljubimac> ljubimac = getObject(response, gt);
            return ljubimac;
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public List<Ljubimac> ucitajLjubimce() throws Exception {
        LjubimacREST ljubimacREST = new LjubimacREST();
        System.out.println("pokusavam da ucitam ljubimce");
        Response response = ljubimacREST.ucitajSve_XML(getRequest(null));
        System.out.println("odgovor je primljen");
        GenericType<List<Ljubimac>> gt = new GenericType<List<Ljubimac>>() {
        };
        return (List<Ljubimac>) getObject(response, gt);
    }

    public Ljubimac prikaziLjubimca(Ljubimac odabraniLjubimac) throws Exception {
        LjubimacREST ljubimacREST = new LjubimacREST();
        Response response = ljubimacREST.prikazi_XML(getRequest(odabraniLjubimac));
        GenericType<List<Poseta>> gt = new GenericType<List<Poseta>>() {
        };
        List<Poseta> posete = (List<Poseta>) getObject(response, gt);
        Ljubimac lj = posete.get(0).getLjubimacid();
        lj.setPosetaList(posete);
        return lj;
    }

    public String izmeni(Ljubimac ljubimac) {
        LjubimacREST ljubimacREST = new LjubimacREST();
        ljubimacREST.izmeni_XML(getRequest(ljubimac));
        return "";
    }

    public List<SelectItem> vratiKategorijeUsluga() throws Exception {
        ArrayList<SelectItem> categories = new ArrayList<>();
        List<Tipusluge> tipoviUsluga = ucitajTipoveUsluga();
        List<Usluga> usluge = ucitajUsluge();
        System.out.println(tipoviUsluga);
        for (Tipusluge tipusluge : tipoviUsluga) {
            SelectItemGroup grupa = new SelectItemGroup(tipusluge.getNaziv().toUpperCase());
            List<SelectItem> uslugel = new ArrayList<>();
            for (int i = 0; i < usluge.size(); i++) {
                if (usluge.get(i).getTipuslugeid().equals(tipusluge)) {
                    uslugel.add(new SelectItem(usluge.get(i), usluge.get(i).getNaziv()));
//                    usluge.remove(usluge.get(i));
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

    public Object vratiUslugu(String value) throws Exception {
        List<Usluga> usluge = ucitajUsluge();
        System.out.println("trazim uslugu " + value);
        for (Usluga usluga : usluge) {
            if(usluga.getNaziv().equals(value)) return usluga;
        }
        return null;
    }

    public void sacuvajPosetu(Poseta poseta) {
        PosetaREST posetaRest = new PosetaREST();
        Response response = posetaRest.sacuvaj_XML(getRequest(poseta));
    }

    public List<Poseta> ucitajPosete() {
        try {
            PosetaREST posetaREST = new PosetaREST();
            Response response = posetaREST.ucitajSve_XML(getRequest(null));
            System.out.println(response);
            GenericType<List<Poseta>> gt = new GenericType<List<Poseta>>() {
            };
            System.out.println("UCITAJ POSETE");
            return (List<Poseta>) getObject(response, gt);
        } catch (Exception ex) {
            return null;
        }
    }

    public List<Poseta> pretraziPosete(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
         try {
            PosetaREST posetaREST = new PosetaREST();
            Response response = posetaREST.pretrazi_XML(getRequest(new Search(first, pageSize, sortField, javax.swing.SortOrder.valueOf(sortOrder.name()), filters)) );
            GenericType<List<Poseta>> gt = new GenericType<List<Poseta>>() {
            };
            List<Poseta> poseta = getObject(response, gt);
            return poseta;
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            throw e;
        }
    }

    public Poseta prikaziPosetu(Poseta poseta) throws Exception {
        PosetaREST posetaREST = new PosetaREST();
        Response response = posetaREST.prikazi_XML(getRequest(poseta));
        return (Poseta) getObject(response, Poseta.class);
    }

    

}
