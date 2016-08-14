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
        startGame();
        //runGame();
    }

    /**
     * Starts a new Game, gets the number of players, sets the characters, creates a random solution, distributes the
     * remaining cards to all the players.
     */
    public void startGame() {
        // Get number of players
        int numberOfPlayers = IO.getIntBetweenBounds("You can have between 3 and 6 players, how many would you like?", 3, 6);

        // Create the Card objects and init the player list
        List<CharacterCard> characters = CharacterCard.generateObjects();
        List<WeaponCard> weapons = WeaponCard.generateObjects();
        List<RoomCard> rooms = RoomCard.generateObjects();
        this.players = new ArrayList<>();

        // Shuffle the stack
        Collections.shuffle(characters);
        Collections.shuffle(weapons);
        Collections.shuffle(rooms);
        List<CharacterCard> characterDel = CharacterCard.generateObjects();

        // Ask for the players
        for (int i = 0; i < numberOfPlayers; i++) {
            String playersLeft = characterDel.toString();
            String msg = "\nPlayers left: \n" + playersLeft + "\n" +
                    "What character would you like? \n(enter number between 1 and " + characterDel.size() + ")\n";
            int wantedIndex = IO.getIntBetweenBounds(msg, 1, characterDel.size()) - 1;
            CharacterCard card = characterDel.remove(wantedIndex);
            Player newPlayer = new Player((CharacterCard.Character) card.getValue());
            players.add(newPlayer);
        }

        // create solution list
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
     * Continually runs the game until its completed
     */
    public void runGame() {
        int currentPlayerIndex = 0;

        while (!currentGame.isFinished()) {
            Player currentPlayer = players.get(currentPlayerIndex);

            boolean hasMoved = false; // has the player moved this turn?
            turn:
            while (true) {

                if (currentPlayer.isEliminated() || currentGame.isFinished()) { // The player cannot play if they are already eliminated
                    break;
                }

                switch (IO.getIntBetweenBounds("What would you like to do?", 1, 7)) {
                    case 1:
                        if (hasMoved) {
                            System.out.println("You can only move once per turn");
                            break;
                        }
                        hasMoved = true;
                        movePlayer(currentPlayer);
                        break;
                    case 2:
                        //Accusation.suggest(currentPlayer, currentGame, board);
                        break turn;
                    case 3:
                        //Accusation.accuse(currentPlayer, currentGame, board);
                        break;
                    case 4: // Show Hand
                        System.out.println("Your cards: \n" + currentPlayer.printCards());
                        break;
                    case 5:
                        addToJournal(currentPlayer);
                        break;
                    case 6: // Show Journal
                        System.out.println(currentPlayer.getJournal().getContents());
                        break;
                    case 7: // End turn
                        break turn;
                    default:
                        System.out.println("Invalid option, please enter a number between 1 and 7");
                }
            }
            // Move the index onto the next player
            currentPlayerIndex = (currentPlayerIndex == players.size() - 1) ? 0 : currentPlayerIndex + 1;
        }
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
     * Adds a message to the players Journal
     *
     * @param p
     */
    public void addToJournal(Player p) {
        System.out.println("Your Journal: \n" + p.getJournal().getContents() + "\n");
        String input = IO.getString("What would you like to add? \n");
        p.getJournal().addToJournal(input);
    }

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

    public Game getGame(){return this.currentGame;}

    /**
     * Constructs a new Cluedo Game
     *
     * @param args
     */
    public static void main(String args[]) {
        new Cluedo();
        //Runnable r = () -> new BoardFrame("Adam", new Cluedo().getGame(), new KeyListener[0]);
        //javax.swing.SwingUtilities.invokeLater(r);
    }

    public Player getCurrentPlayer(){ return this.currentPlayer;}
}