package cluedo;

import cards.Card;
import cards.CharacterCard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The player is a character in the game, it represents a real player playing the game.
 */
public class Player {

    private List<Card> cards;
    private Journal journal;
    private CharacterCard.Character character;
    private boolean eliminated;
    private int x;
    private int y;

    /**
     * Create a new Player
     * @param character
     */
    public Player(CharacterCard.Character character) {
        this.character = character;
        this.eliminated = false;
        this.cards = new ArrayList<>();
        this.journal = new Journal();
    }

    /**
     * Set the position of the player
     * @param x
     * @param y
     */
    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns a string containing the cards the player has in a nicer order then using .toString() method
     * @return
     */
    public String printCards() {
        String str = "";
        for (Card c : cards) {
            str += c.getValue().toString() + ", ";
        }
        return str.substring(0, Math.max(0, str.length() - 2)); // Remove last comma
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public String getPos() {
        return "(" + x + "," + y + ")";
    }

    public CharacterCard.Character getCharacter() {
        return this.character;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void elimate() {
        this.eliminated = true;
    }

    public boolean isEliminated() {
        return this.eliminated;
    }

    public void addCard(Card c) {
        cards.add(c);
    }

    public Journal getJournal() {
        return this.journal;
    }
}
