package helloChuck.chuckApi;


import com.fasterxml.jackson.databind.ObjectMapper;
import helloChuck.chuckApi.model.ChuckResponse;
import org.apache.http.HttpStatus;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class ChuckService {

    final private ClientConfig config;

    final private Client client;

    final private WebTarget target;

    public ChuckService() {
        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(getBaseURI());
    }

    public String getFact() throws IOException {
        client.property("accept", MediaType.APPLICATION_JSON);
        Invocation.Builder invocationBuilder = target.request().accept(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.get();

        if (response.getStatus() != HttpStatus.SC_OK) {
            throw new RuntimeException("Failed with Http error code: " + response.getStatus());
        }

        // get JSON for application
        String responseString = response.readEntity(String.class);

        // create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // map json string to object
        ChuckResponse chuckResponse = objectMapper.readValue(responseString.getBytes(), ChuckResponse.class);

        return chuckResponse.fact.joke;
    }

    // TODO add firstName lastName feature?
    private static URI getBaseURI() {
        final String baseApiUrl = "http://api.icndb.com/jokes/random";

        return UriBuilder.fromUri(baseApiUrl).build();
    }
}

