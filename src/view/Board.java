package view;

import cards.RoomCard;
import cluedo.Player;
import squares.*;
import util.CluedoError;
import util.Point;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Adam on 25/07/16.
 */
public class Board {

    /**
     * Direction enum. Used for navigating the board and direction the door faces in
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private int width;
    private int height;
    private Square[][] board;
    private Map<Character, RoomSquare> rooms;
    private List<SpawnSquare> spawnLocations;
    private List<StairSquare> stairLocations;

    /**
     * Construct a new Board object
     * @param width
     * @param height
     * @param rooms maps the key character (in the board.txt file) to the room square
     */
    public Board(int width, int height, Map<Character, RoomSquare> rooms) {
        this.width = width;
        this.height = height;
        this.board = new Square[width][height];
        this.rooms = rooms;
        this.spawnLocations = new ArrayList<>();
        this.stairLocations = new ArrayList<>();
    }

    /**
     * Sets the list of players initial location, should be called at the start of the game.
     * Gives the players a random location from the field spawnLocations.
     * @param players
     */
    public void setSpawnLocations(List<Player> players) {
        Collections.shuffle(spawnLocations);
        for (Player p : players) {
            SpawnSquare square = spawnLocations.remove(0);
            p.setPos(square.getX(), square.getY());
        }
    }

    public RoomSquare getRoom(int x, int y) throws CluedoError {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new CluedoError("The position " + x + "," + y + " isn't on the board");
        }
        Square square = board[x][y];
        if (square == null || square.getClass() != RoomSquare.class)
            throw new CluedoError("You must be in a room to make an accusation");
        return (RoomSquare) square;
    }


    /**
     * Gets the Point on the board that the stair square teleports to
     * @param start
     * @return
     */
    public Point getStairDestination(StairSquare start) {
        int type = start.getType();

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] instanceof StairSquare) {
                    StairSquare sq = (StairSquare) board[x][y];

                    if (sq.getType() == type && sq != start)
                        return new Point(x, y);
                }
            }
        }
        return null;
    }

        /* Board parser methods */

    /**
     * Parses the board from the text file using a scanner. Calls parseRooms and parseBoardLayout, both of which assist
     * this method.
     * @param filename
     * @return
     */

    public static Board parseBoard(String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new RuntimeException("Could not parse: File not found");
        }
        sc.useDelimiter("[;\r\n]+");
        Map<Character, RoomSquare> rooms = parseRooms(sc);

        // Parse the board
        int width;
        int height;
        Pattern intRegex = Pattern.compile("[0-9]+");
        try {
            width = Integer.parseInt(sc.next(intRegex));
            height = Integer.parseInt(sc.next(intRegex));
        } catch (NumberFormatException e) {
            throw new CluedoError("Could not parse: Board dimensions not declared");
        }
        Board board = new Board(width, height, rooms);
        parseBoardLayout(sc, board);
        sc.close();
        return board;
    }


    /**
     * Parse the room declarations
     * @param sc
     */
    private static Map<Character, RoomSquare> parseRooms(Scanner sc) {
        Pattern roomReg = Pattern.compile("[A-Z]:.+");
        Map<Character, RoomSquare> rooms = new HashMap<>();

        while (sc.hasNext(roomReg)) {
            String[] line = sc.next(roomReg).split(":"); // Char : Name
            char key = line[0].charAt(0);
            RoomCard.Room roomEnum = RoomCard.Room.valueOf(line[1].trim().toUpperCase());
            RoomSquare roomSquare = new RoomSquare(roomEnum);
            if (rooms.containsKey(key)) {
                throw new CluedoError(
                        "Parse Error: RoomSquare already exists.");
            }
            rooms.put(key, roomSquare);
        }
        return rooms;
    }


    /**
     * Parses each square of the map
     */
    private static void parseBoardLayout(Scanner sc, Board board) {
        for (int y = 0; y < board.height; y++) {
            String line = sc.next();

            for (int x = 0; x < board.width; x++) {
                char token = line.charAt(x);

                if (board.rooms.containsKey(token)) { // If
                    board.board[x][y] = board.rooms.get(token);

                } else {
                    switch (token) {
                        case '#': // Blank
                            board.board[x][y] = new BlankSquare();
                            break;

                        case '_': // HallwaySquare
                            board.board[x][y] = new HallwaySquare();
                            break;

                        case '?': // Spawn Token
                            board.board[x][y] = new SpawnSquare(x, y);
                            board.spawnLocations.add((SpawnSquare) board.board[x][y]); // Add to list of spawn locations
                            break;

                        // Door tokens
                        case 'u':
                            board.board[x][y] = new DoorSquare(Direction.UP);
                            break;

                        case 'd':
                            board.board[x][y] = new DoorSquare(Direction.DOWN);
                            break;

                        case 'l':
                            board.board[x][y] = new DoorSquare(Direction.LEFT);
                            break;

                        case 'r':
                            board.board[x][y] = new DoorSquare(Direction.RIGHT);
                            break;

                        case '1':
                            board.board[x][y] = new StairSquare(1);
                            board.stairLocations.add((StairSquare) board.board[x][y]);
                            break;

                        case '2':
                            board.board[x][y] = new StairSquare(2);
                            board.stairLocations.add((StairSquare) board.board[x][y]);
                            break;

                        default:
                            System.out.println("Unknown token " + token);
                            board.board[x][y] = new BlankSquare();
                            break;
                    }
                }
            }
        }
    }

    /* Getters and Setters */

    public int getWidth() {
        return this.width;
    }
    public int getHeight() {
        return this.height;
    }
    public Square[][] getBoard(){ return this.board;}

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board[x][y] == null) {
                    str.append("z");
                } else {
                    String key = board[x][y].getKey();
                    str.append(key);
                }
            }
            str.append("\n");
        }
        return str.toString();
    }
}
