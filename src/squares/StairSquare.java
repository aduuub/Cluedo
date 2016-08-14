package squares;

/**
 * Created by Adam on 25/07/16.
 * The stair square is only contained in rooms and teleports players across the map to the corrisponding stair square in
 * the oppisite room. There should only be one stair square per room, and must have a matching stair square somewhere on
 * the board. Using the default board.txt file the player will be teleported to the oppisite corner room. E.g. if they
 * walk on the stair square in the top left they would teleport to the bottom right.
 */
public class StairSquare extends Square implements Walkable{

    /**
     * Identifier for the stair square. Should be exactly two stair squares with the same type. Type 1 teleports to the
     * other type 1. Type 2 teleports to the other type 2.
     */
    private int type;

    public StairSquare(int type) {
        this.type = type;
    }

    @Override
    public String getKey() {
        return "S";
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof RoomSquare)
            return true;
        return false;
    }
}
