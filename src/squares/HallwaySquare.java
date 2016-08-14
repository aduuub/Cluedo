package squares;

/**
 * Created by Adam on 25/07/16.
 */
public class HallwaySquare extends Square implements Walkable {

    @Override
    public String getKey() {
        return "_";
    }

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof DoorSquare|| fromSquare instanceof SpawnSquare || fromSquare instanceof HallwaySquare)
            return true;
        return false;
    }
}
