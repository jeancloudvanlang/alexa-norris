package helloChuck.chuckApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChuckFact {
    public int id;
    public String joke;
}
