package cluedo;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import squares.Square;
import util.Accusation;
import util.CluedoError;
import util.IO;
import view.Board;
import view.BoardFrame;

import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * Cluedo is the main class and is responsible for running of the game, and calls the appropriate methods in other classes.
 */
public class Cluedo {

    private Game currentGame;
    private List<Player> players;
    private Board board;
    private Player currentPlayer;

    /**
     * Construct a new game of Cluedo
     */
    public Cluedo() {

    }

    /**
     * Starts a new Game, gets the number of players, sets the characters, creates a random solution, distributes the
     * remaining cards to all the players.
     */
    public void setupGame(List<CharacterCard.Character> choosenCharacters) {
    	System.out.println(choosenCharacters);
    	// Create the Card objects and init the player list
        List<CharacterCard> characters = CharacterCard.generateObjects();
        List<WeaponCard> weapons = WeaponCard.generateObjects();
        List<RoomCard> rooms = RoomCard.generateObjects();

        // Set up players
        choosenCharacters.forEach(p -> characters.remove(p));
        this.players = new ArrayList<>();
        choosenCharacters.forEach(p -> players.add(new Player(p)));

        // Shuffle the stack
        Collections.shuffle(characters);
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);

        // Set up the solution
        List<Card> solution = new ArrayList<>();

        // Add random character
        int random = (int) (Math.random() * characters.size());
        solution.add(characters.get(random));

        // Add random room
        random = (int) (Math.random() * rooms.size());
        solution.add(rooms.remove(random));

        // Add random weapon
        random = (int) (Math.random() * weapons.size());
        solution.add(weapons.remove(random));

        // Deal Cards
        Card.dealCards(weapons, players);
        Card.dealCards(rooms, players);
        Card.dealCards(characters, players);

        // Create Game
        this.currentGame = new Game(players, solution, weapons, rooms, characters);
        this.board = currentGame.getBoard();

    }

    /**
     * Called when its the players turn to move, performs dice roll and physical movement of the player
     *
     * @param p
     */
    public void movePlayer(Player p) {
        int diceRoll = (int) (Math.random() * 6 + 1) + (int) (Math.random() * 6 + 1); // Assuming 2 x 6 sided die
        System.out.println("You rolled: " + diceRoll);
        while (diceRoll > 0) {
            diceRoll = movePlayerHelper(diceRoll, p);
        }
    }

    /**
     * Assists movePlayer() by asking and executing one movement e.g. 'Left 4' or 'Down 2'
     *
     * @param diceRoll
     * @param p
     * @return
     */
    public int movePlayerHelper(int diceRoll, Player p) {
        while (true) {
            System.out.println("You are at position " + p.getPos() + " and you have " + diceRoll + " steps to move");
            System.out.println("What direction would you like to move in and how many steps? E.g. Left 4");
            Scanner s = new Scanner(System.in);

            // Get direction
            Board.Direction directionEnum = null;
            int steps = -1;
            try {
                String dir = s.next().trim().toUpperCase();
                directionEnum = Board.Direction.valueOf(dir);
                steps = s.nextInt();

            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println("Invalid input");
                continue;
            }

            if (steps < 0) // check valid steps were entered
                continue;

            if (steps > diceRoll) {
                System.out.println("You can only move " + diceRoll + " steps.");
                continue;
            }

            try {
                currentGame.movePlayer(p, directionEnum, steps);
                Square square = board.getBoard()[p.x()][p.y()];
                System.out.println("\n");
                System.out.println(board.toString());
                System.out.println("You are now on a " + square.getClass().getName() + " square");
            } catch (CluedoError e) {
                System.out.println(e.getMessage());
                continue;
            }
            return diceRoll - steps;
        }
    }

    /**
     * Adds a message to the current players Journal. The current player should not be null when this is called.
     *
     * @param message
     */
    public void addToCurrentPlayersJournal(String message) {
        assert currentPlayer != null;
        currentPlayer.getJournal().addToJournal(message);
    }

    /**
     * Advance the currentPlayer to be the next player. This is the player after the the currentPlayer in players
     */
    public void nextPlayer(){
        if(currentPlayer == null) {
            currentPlayer = players.get(0);
        }else{
            int index = players.indexOf(currentPlayer);
            index = (index == players.size() - 1) ? 0 : index + 1;
            currentPlayer = players.get(index);
        }
        assert currentPlayer != null;
    }


    /**
     * Constructs a new Cluedo Game
     * @param args
     */
    public static void main(String args[]) {
        new Cluedo();
    }

    /* Getters and setters */

    public Player getCurrentPlayer(){ return this.currentPlayer;}
    public Game getGame(){return this.currentGame;}

}