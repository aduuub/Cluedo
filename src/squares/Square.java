package squares;

import util.CluedoError;
import cluedo.Player;

/**
 * Created by Adam on 25/07/16.
 */
public abstract class Square {
    private Player player;

    public abstract String getKey();

    /**
     * Set the player in the square
     *
     * @param p
     */
    public void enterSquare(Player p) throws CluedoError {
        if (player != null)
            throw new CluedoError("You cannot enter that square as there is already a player in it");
        player = p;
    }

    /**
     * Remove the player from the square
     *
     */
    public void leaveSquare() {
        player = null;
    }

    public abstract boolean isTransitionAllowed(Square fromSquare);


}
