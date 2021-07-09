package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Player values body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerValuesBody {
    private String name;
    private int figure;

    /**
     * Instantiates a new Player values body.
     */
    public PlayerValuesBody() {
    }

    /**
     * Instantiates a new Player values body.
     *
     * @param name   the name
     * @param figure the figure
     */
    public PlayerValuesBody(String name, int figure) {
        this.name = name;
        this.figure = figure;
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
