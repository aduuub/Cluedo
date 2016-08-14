package cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The weapon card is used to store the various weapons in the game
 */
public class WeaponCard extends Card {

    /**
     * Create a new weapon
     * @param value
     */
    public WeaponCard(Weapon value) {
        super(value);
    }

    /**
     * The various types of weapons in the game
     */
    public enum Weapon {
        CANDLESTICK,
        DAGGER,
        LEAD_PIPE,
        REVOLVER,
        ROPE,
        SPANNER;
    }

    /**
     * Generate a list of WeaponCards, each having one of the weapons
     * @return
     */
    public static List<WeaponCard> generateObjects() {
        List<WeaponCard> cards = new ArrayList<>();
        for(Weapon w : Weapon.values())
            cards.add(new WeaponCard(w));
        return cards;
    }
}

