/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webpageresolver;

import javax.faces.context.FacesContext;

/**
 *
 * @author hp
 */
public class Page {
    private String pageid;
    private String page;
    private String title;

    public Page() {
    }

    public Page(String pageid, String title, String page) {
        this.pageid = pageid;
        this.page = page;
        this.title = title;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
    
   
    
}
