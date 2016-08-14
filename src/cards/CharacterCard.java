package cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The character card stores characters in the game. One character is represented by one card.
 */
public class CharacterCard extends Card {
    /**
     * Create a new CharacterCard
     * @param character
     */
    public CharacterCard(Character character){
        super(character);
    }

    /**
     * Generates a list of all the CharacterCards (Various values that a character can contain are the Character enums)
     * @return
     */
    public static List<CharacterCard> generateObjects() {
        List<CharacterCard> cards = new ArrayList<>();
        for(Character c: Character.values())
            cards.add(new CharacterCard(c));
        return cards;
    }

    /**
     * The various characters in the game
     */
    public enum Character {
        MISS_SCARLETTE,
        COLENEL_MUSTARD,
        MRS_WHITE,
        THE_REVEREND_GREEN,
        MRS_PEACOCK,
        PROFESSOR_PLUM;
    }


}
