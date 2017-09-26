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
 * Jersey REST client generated for REST resource:Kontrolor
 * [veterinarskaordinacija]<br>
 * USAGE:
 * <pre>
 *        Servis client = new Servis();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author hp
 */
public class Servis {

        private WebTarget webTarget;
        private Client client;
        private static final String BASE_URI = "http://localhost:8080/VeterinarskaOrdinacijaREST/api";

        public Servis() {
            client = javax.ws.rs.client.ClientBuilder.newClient();
            webTarget = client.target(BASE_URI).path("veterinarskaordinacija");
        }

        public Response izmeniLjubimca_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("izmeniLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response izmeniLjubimca_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("izmeniLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response sacuvajPosetu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajPosetu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response sacuvajPosetu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajPosetu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response pretraziLjubimce(Object requestEntity) throws ClientErrorException {
            return webTarget.path("pretraziLjubimce").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response sacuvajUslugu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response sacuvajUslugu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajTipoveUsluga_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajTipoveUsluga").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajTipoveUsluga_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajTipoveUsluga").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajLjubimce_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajLjubimce").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajLjubimce_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajLjubimce").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response login(Object requestEntity) throws ClientErrorException {
            return webTarget.path("login").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziUslugu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziUslugu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajVrsteZivotinja_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajVrsteZivotinja").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajVrsteZivotinja_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajVrsteZivotinja").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response prikaziPosetu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziPosetu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziPosetu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziPosetu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response obrisiUslugu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("obrisiUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response obrisiUslugu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("obrisiUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response izmeniUslugu_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("izmeniUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response izmeniUslugu_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("izmeniUslugu").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response sacuvajLjubimca_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response sacuvajLjubimca_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("sacuvajLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajUsluge_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajUsluge").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajUsluge_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajUsluge").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajPosete_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajPosete").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajPosete_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajPosete").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response ucitajVlasnike_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajVlasnike").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response ucitajVlasnike_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("ucitajVlasnike").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response prikaziLjubimca_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziLjubimca_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziLjubimca").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response pretraziPosete_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("pretraziPosete").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response pretraziPosete_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("pretraziPosete").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public Response pretraziUsluge(Object requestEntity) throws ClientErrorException {
            return webTarget.path("pretraziUsluge").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziTipUsluge_XML(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziTipUsluge").request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML), Response.class);
        }

        public Response prikaziTipUsluge_JSON(Object requestEntity) throws ClientErrorException {
            return webTarget.path("prikaziTipUsluge").request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), Response.class);
        }

        public void close() {
            client.close();
        }
}
