package server.game;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

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

}
