package cluedo;

import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import util.CluedoError;
import util.PlayerMover;
import util.Point;
import view.Board;

import java.util.List;

/**
 * Created by Adam on 25/07/16.
 * The Game is an instance of a game of cluedo. It stores all the information of the game including the solution, players,
 * the board, and the various cards.
 */
public class Game {

    private boolean finished;
    private Board board;
    private List<Player> players;
    private List<Card> solution;
    private List<WeaponCard> weapons;
    private List<RoomCard> rooms;
    private List<CharacterCard> characters;
    private final String boardFile = "board.txt";

    /**
     * Construct a new game
     * @param players
     * @param solution
     * @param weapons
     * @param rooms
     * @param characters
     */
    public Game(List<Player> players, List<Card> solution, List<WeaponCard> weapons, List<RoomCard> rooms, List<CharacterCard> characters) {
        this.players = players;
        this.weapons = weapons;
        this.rooms = rooms;
        this.characters = characters;
        return;

//        setSolution(solution);
//
//        this.finished = false;
//        this.board = Board.parseBoard(boardFile);
//        board.setSpawnLocations(players);
    }

    /**
     * Moves the player on the board
     * @param player
     * @param dir
     * @param steps
     */
    public void movePlayer(Player player, Board.Direction dir, int steps) throws CluedoError {
        int x = player.x();
        int y = player.y();
        int startX = x;
        int startY = y;

        switch (dir) {
            case UP:
                y -= steps;
                break;

            case DOWN:
                y += steps;
                break;

            case LEFT:
                x -= steps;
                break;

            case RIGHT:
                x += steps;
                break;
        }

        if (x >= board.getWidth() || x < 0 || y >= board.getHeight() || y < 0)
            throw new CluedoError("You cannot move that many steps in that direction");

        Point start = new Point(startX, startY);
        Point end = new Point(x, y);
        PlayerMover.movePlayer(board, player, start, end);
    }

    /**
     * Accuse a player, character and weapon
     * @param cards
     * @return if the accusation is correct
     */
    public boolean accuse(List<Card> cards) {
        if (cards.size() == 0) // An error occoured
            return false;

        if (cards.size() != 3) {
            throw new CluedoError("Invalid number of cards in accusation. Must be equal to 3");
        }
        // check all cards match
        for (Card c : cards) {
            if (!solution.contains(c)) // oop one doesn't match
                return false;
        }
        return true;
    }


    /**
     * Set the solution. The length must be equal to 3, and contain the player, character and weapon.
     * @param cards
     */
    public void setSolution(List<Card> cards) {
        if (cards.size() != 3) {
            throw new CluedoError("Invalid number of cards. Must be equal to 3");
        }
        this.solution = cards;
    }

    /**
     * Has the game finished?
     * @return
     */
    public boolean isFinished() {
        return this.finished;
    }

    /**
     * Finish the game, called when a player wins
     */
    public void finish() {
        this.finished = true;
    }

    public Board getBoard() {
        return board;
    }
    public List<Player> getPlayers(){ return this.players;}
}
