package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Set starting point body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetStartingPointBody {
    private int x;
    private int y;

    /**
     * Instantiates a new Set starting point body.
     */
    public SetStartingPointBody() {
    }

    /**
     * Instantiates a new Set starting point body.
     *
     * @param x the x
     * @param y the y
     */
    public SetStartingPointBody(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}
