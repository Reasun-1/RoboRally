package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Player added body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAddedBody {
    private int clientID;
    private String name;
    private int figure;

    /**
     * Instantiates a new Player added body.
     */
    public PlayerAddedBody() {
    }

    /**
     * Instantiates a new Player added body.
     *
     * @param clientID the client id
     * @param name     the name
     * @param figure   the figure
     */
    public PlayerAddedBody(int clientID, String name, int figure) {
        this.clientID = clientID;
        this.name = name;
        this.figure = figure;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets figure.
     *
     * @return the figure
     */
    public int getFigure() {
        return figure;
    }
}
