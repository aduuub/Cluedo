package util;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import view.Board;
import cluedo.Game;
import cluedo.Player;
import squares.RoomSquare;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Adam on 31/07/16.
 * Util to help Cluedo with making an accusation
 */
public class Accusation {

    /**
     * Accuse a player of the crime
     */
    public static void accuse(Player currentPlayer, Game currentGame) {
        Board board = currentGame.getBoard();
        List<Card> solution = getWeaponCharacterRoom(currentPlayer, board);

        // Check solution
        if (currentGame.accuse(solution)) { // They are correct
            System.out.println("Well done, " + currentPlayer.getCharacter().toString() + ", you have won the game!");
            currentGame.finish();

        } else { // Incorrect
            System.out.println("You made an invalid accusation, you are now eliminated.");
            currentPlayer.elimate();
        }
    }

    /**
     * Suggest a player of the crime
     */
    public static void suggest(Player currentPlayer, Game currentGame) {
        Board board = currentGame.getBoard();
        List<Card> solution = getWeaponCharacterRoom(currentPlayer, board);
        Card cardProvedWrong = proveSolutionWrong(currentPlayer, solution, currentGame.getPlayers());

        // Check solution
        if (cardProvedWrong == null) { // They are correct
            System.out.println("No one was able to disprove your suggestion.");

        } else  // Incorrect
            System.out.println("Incorrect suggestion. A player has card: " + cardProvedWrong.toString());
    }

    /**
     * Assists the Suggest and Accuse methods by getting the players room, and what weapon they believed killed another
     * character.
     *
     * @return A list of cards with length = 3, and contains the current room the player is in, the weapon they believe
     * killed the character, and the character that died
     */
    public static List<Card> getWeaponCharacterRoom(Player currentPlayer, Board board) {

        // Print characters to accuse
        System.out.println("Who would you like to accuse?");
        List<CharacterCard> characterCards = CharacterCard.generateObjects();
        System.out.println(characterCards.toString() + "\n(enter number between 1 and 6)\n");

        // Get characters
        int index = IO.getIntBetweenBounds("", 1, characterCards.size()) - 1;
        CharacterCard characterAccused = characterCards.get(index);

        // Print potential weapons
        System.out.println("What weapon killed the character?");
        List<WeaponCard> weaponsCards = WeaponCard.generateObjects();
        System.out.println(weaponsCards.toString() + "\n(enter number between 1 and 6)\n");

        // Get weapon
        index = IO.getIntBetweenBounds("", 1, weaponsCards.size()) - 1;
        WeaponCard weaponAccused = weaponsCards.get(index);

        // RoomSquare
        int playerX = currentPlayer.x();
        int playerY = currentPlayer.y();
        RoomCard roomAccused = null;
        try {
            RoomSquare roomSquare = board.getRoom(playerX, playerY);
            roomAccused = new RoomCard(roomSquare.getName());

        } catch (CluedoError e) {
            System.out.println(e.getMessage());
            return Arrays.asList(); // Can't accuse if not in the room
        }

        // Return the three cards
        return Arrays.asList(characterAccused, weaponAccused, roomAccused);
    }

    /**
     * Prove the solution the currentPlayer provided to be wrong.
     *
     * @param currentPlayer current player - used so they don't check there cards, and so we can iterate round the players
     *                      in the correct order trying to find a card to prove them wrong
     * @param solution      the three solution cards
     * @param players       list of players in the game
     * @return The card that proved the player wrong, or null if they are correct
     */
    public static Card proveSolutionWrong(Player currentPlayer, List<Card> solution, List<Player> players) {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        int playersIndex = currentPlayerIndex + 1;

        for (Player p : players) { // loop over the players excluding the currentPLayer
            List<Card> playersCards = p.getCards();
            for (Card c1 : playersCards) {
                for (Card c2 : solution)
                    if (c1.equals(c2))
                        return c1;
            }
        }
        return null;
    }
}
