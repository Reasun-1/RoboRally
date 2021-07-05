package server.game;

/**
 * The enum Direction.
 */
public enum Direction {
    /**
     * Up direction.
     */
    UP,
    /**
     * Down direction.
     */
    DOWN,
    /**
     * Left direction.
     */
    LEFT,
    /**
     * Right direction.
     */
    RIGHT;

    /**
     * Convert string to direction direction.
     *
     * @param str the str
     * @return the direction
     */
    public static Direction convertStringToDirection(String str){

        Direction direction = null;
        switch (str){
            case "right":
                direction = RIGHT;
                break;
            case "left":
                direction = LEFT;
                break;
            case "up":
                direction = UP;
                break;
            case "down":
                direction = DOWN;
                break;


        }
        return direction;
    }

    /**
     * Turn clock direction.
     *
     * @param curDir the cur dir
     * @return the direction
     */
    public static Direction turnClock(Direction curDir){
        Direction newDir = null;
        switch (curDir){
            case RIGHT:
                newDir = DOWN;
                break;
            case DOWN:
                newDir = LEFT;
                break;
            case LEFT:
                newDir = UP;
                break;
            case UP:
                newDir = RIGHT;
                break;
        }
        return newDir;
    }

    /**
     * Turn counter clock direction.
     *
     * @param curDir the cur dir
     * @return the direction
     */
    public static Direction turnCounterClock(Direction curDir){
        Direction newDir = null;
        switch (curDir){
            case RIGHT:
                newDir = UP;
                break;
            case DOWN:
                newDir = RIGHT;
                break;
            case LEFT:
                newDir = DOWN;
                break;
            case UP:
                newDir = LEFT;
                break;
        }
        return newDir;
    }



}
