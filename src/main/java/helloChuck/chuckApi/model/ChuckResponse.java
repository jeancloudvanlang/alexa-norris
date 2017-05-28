package helloChuck.chuckApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChuckResponse {

    public String type;

    @JsonProperty("value")
    public ChuckFact fact;
}
