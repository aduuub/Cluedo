package cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The room card stores rooms in the game. One room is represented by one card. Note this is different to RoomSquare
 * which is a square on the board
 */
public class RoomCard extends Card {
    public RoomCard(Room value) {
        super(value);
    }

    /**
     * The various types of rooms
     */
    public enum Room {
        KITCHEN,
        BALL_ROOM,
        CONSERVATORY,
        BILLIARD_ROOM,
        LIBRARY,
        STUDY,
        HALL,
        LOUNGE,
        DINING_ROOM;
    }

    /**
     * Generate a list of all RoomCard object each having one of the room enums
     * @return
     */
    public static List<RoomCard> generateObjects() {
        List<RoomCard> cards = new ArrayList<>();
        for(Room r: Room.values())
            cards.add(new RoomCard(r));
        return cards;
    }
}
