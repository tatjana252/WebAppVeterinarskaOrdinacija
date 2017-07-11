/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import webpageresolver.Page;

/**
 *
 * @author hp
 */
@Named(value = "mbUtil")
@RequestScoped
public class MBUtil{

    List<Page> pages;

    public List<Page> getPages() {
        System.out.println(FacesContext.getCurrentInstance().getViewRoot().getViewId());
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public MBUtil() {
        pages = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
       
        ResourceBundle bundle = ResourceBundle.getBundle("internationalization.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String usluga = bundle.getString("pet_services");
        String ljubimac = bundle.getString("pet");
        String poseta = bundle.getString("appointments");
        pages.add(new Page("usluga", usluga, "pretty:usluga"));
        pages.add(new Page("ljubimac", ljubimac, "pretty:ljubimac"));
        pages.add(new Page("poseta", poseta, "pretty:poseta"));

    }



}
