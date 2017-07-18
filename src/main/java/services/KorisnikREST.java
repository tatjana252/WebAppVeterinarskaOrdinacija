/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * Jersey REST client generated for REST resource:KorisnikFacadeREST
 * [korisnik]<br>
 * USAGE:
 * <pre>
 *        KorisnikREST client = new KorisnikREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author hp
 */
public class KorisnikREST {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/VeterinarskaOrdinacijaREST/api";

    public KorisnikREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("korisnik");
    }

    public Response login(Object requestEntity) throws ClientErrorException {
        return webTarget.path("login").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
    }

    public void close() {
        client.close();
    }
    
}
