package squares;

import cards.RoomCard;
import cluedo.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 */
public class RoomSquare extends Square implements Walkable{
    private RoomCard.Room name;
    private List<Player> playersInRoom;
    private List<DoorSquare> doors;

    public RoomSquare(RoomCard.Room name){
        this.name = name;
        playersInRoom = new ArrayList<>();
    }

    @Override
    public String getKey() {
        return "R";
    }

    public RoomCard.Room getName(){ return this.name;}

    @Override
    public boolean isTransitionAllowed(Square fromSquare) {
        if(fromSquare instanceof StairSquare || fromSquare instanceof DoorSquare || fromSquare instanceof RoomSquare)
            return true;
        return false;
    }
}
