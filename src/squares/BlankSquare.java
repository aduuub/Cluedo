package squares;

/**
 * Created by Adam on 25/07/16.
 * The blank square is an empty square that players are not allowed to walk in.
 */
public class BlankSquare extends Square {

    @Override
    public String getKey() {
        return "#";
    }

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        return false;
    }
}
