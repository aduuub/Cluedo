package cards;

import cluedo.Player;

import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The card is used to store Weapons, Characters and Rooms. This is an abstract class that they all extend.
 */
public abstract class Card {
    Enum value;

    public Card(Enum value) {
        this.value = value;
    }

    /**
     * Deal the list of cards to the list of players
     * Doesn't even distribute the cards, instead it iterates the list and hands out card by card until there are no
     * cards left to deal
     *
     * @param cardsToDeal
     * @param players
     */
    public static void dealCards(List<? extends Card> cardsToDeal, List<Player> players) {
        int count = 0;
        while (!cardsToDeal.isEmpty()) {
            Card c = cardsToDeal.remove(0);
            players.get(count).addCard(c);
            count = count == players.size() - 1 ? 0 : count + 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return value != null ? value.equals(card.value) : card.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    public Enum getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
