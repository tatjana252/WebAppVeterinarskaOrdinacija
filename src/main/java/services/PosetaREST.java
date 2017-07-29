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
 * Jersey REST client generated for REST resource:PosetaFacadeREST [poseta]<br>
 * USAGE:
 * <pre>
 *        PosetaREST client = new PosetaREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author hp
 */
public class PosetaREST {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/VeterinarskaOrdinacijaREST/api";

        public PosetaREST() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("poseta");
        }

        public Response sacuvaj_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvaj").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response sacuvaj_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvaj").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajSve_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("vratisve").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajSve_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("vratisve").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response pretrazi(Object requestEntity) throws ClientErrorException {
            return webTarget.path("pretraga").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public void close() {
            client.close();
        }
    }