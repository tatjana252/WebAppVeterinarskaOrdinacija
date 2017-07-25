/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author hp
 */
@Named("WebConstants")
@ApplicationScoped
public class WebConstants implements Serializable{
    private final String INDEX = "index.xhtml";
    private final String USLUGA_PREGLED = "WEB-INF/includes/usluga/uslugaPregled.xhtml";
    private final String USLUGA_IZMENA = "WEB-INF/includes/usluga/uslugaIzmena.xhtml";
    private final String USLUGA_UNOS = "WEB-INF/includes/usluga/uslugaUnos.xhtml";
    private final String POSETA_PREGLED = "WEB-INF/includes/poseta/posetaPregled.xhtml";
    private final String POSETA_UNOS = "WEB-INF/includes/poseta/posetaUnos.xhtml";
    private final String LJUBIMAC_PREGLED = "WEB-INF/includes/ljubimac/ljubimacPregled.xhtml";
    private final String LJUBIMAC_UNOS = "WEB-INF/includes/ljubimac/ljubimacUnos.xhtml";   

    public String getINDEX() {
        return INDEX;
    }

    public String getUSLUGA_PREGLED() {
        return USLUGA_PREGLED;
    }

    public String getUSLUGA_IZMENA() {
        return USLUGA_IZMENA;
    }

    public String getUSLUGA_UNOS() {
        return USLUGA_UNOS;
    }

    public String getPOSETA_PREGLED() {
        return POSETA_PREGLED;
    }

    public String getPOSETA_UNOS() {
        return POSETA_UNOS;
    }

    public String getLJUBIMAC_PREGLED() {
        return LJUBIMAC_PREGLED;
    }

    public String getLJUBIMAC_UNOS() {
        return LJUBIMAC_UNOS;
    }
    
}
