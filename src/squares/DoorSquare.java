package squares;

import view.Board;

/**
 * Created by Adam on 25/07/16.
 * This is a representation of a door to a room, it must contain a Board.Direction which is the way the door opens.
 * Players can freely walk through door squares as long as they are moving in the correct direction. E.g. if direction is
 * 'Left' the player can walk through the door either from the left or right, but not from above or below.
 */
public class DoorSquare extends Square implements Walkable {
    /**
     * The direction the door opens
     */
    Board.Direction direction;

    /**
     * Create a new door object.
     * @param direction
     */
    public DoorSquare(Board.Direction direction){
        this.direction = direction;
    }

    @Override
    public String getKey() {
        return "D";
    }

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof RoomSquare || fromSquare instanceof HallwaySquare)
            return true;
        return false;
    }

    /**
     * Can the player walk through the door horizontally?
     * @return
     */
    public boolean isHorizontalDoor(){
        return direction == Board.Direction.RIGHT ||
                direction == Board.Direction.LEFT;
    }

    /**
     * Can the player walk through the door vertically?
     * @return
     */
    public boolean isVerticalDoor(){
        return direction == Board.Direction.UP ||
                direction == Board.Direction.DOWN;
    }
}
