/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb.navigacija;

import constants.WebConstants;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ComponentSystemEvent;

/**
 *
 * @author hp
 */
@Named(value = "mbNavigacija")
@RequestScoped
public class MBNavigacija {

    private String stranica;

    /**
     * Creates a new instance of 
     */
    public MBNavigacija() {
    }

    public String getStranica() {
        System.out.println("stranica" +stranica);
        System.out.println("stranicaa");
        return stranica;
    }

    public void setStranica(String stranica) {
        System.out.println("stranica" +stranica);
        this.stranica = stranica;
    }
    
    public void retrieveData(ComponentSystemEvent event) {
    this.stranica = (String) event.getComponent().getAttributes().get("pocetna");
        System.out.println("poawta" + stranica);
}
    
    public boolean isStranicaNull(){
        if(stranica == null){
            System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
        
    }
    

    @PostConstruct
    public void init() {
    }
}
