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
 * Jersey REST client generated for REST resource:TipUslugeFacadeREST
 * [tipusluge]<br>
 * USAGE:
 * <pre>
 *        TipUslugeREST client = new TipUslugeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author hp
 */
public class TipUslugeREST {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/VeterinarskaOrdinacijaREST/api";

    public TipUslugeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("tipusluge");
    }

    public Response prikazi() throws ClientErrorException {
        return webTarget.path("prikazi").request().post(null, Response.class);
    }

    public <T> T ucitajSve(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("ucitajsve");
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
