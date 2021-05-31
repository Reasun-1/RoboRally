package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pit extends FeldObject{

    String type;

    public Pit() {
    }

    public Pit(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

}
