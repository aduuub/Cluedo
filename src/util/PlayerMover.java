package util;

import view.Board;
import cluedo.Player;
import squares.DoorSquare;
import squares.Square;
import squares.StairSquare;

/**
 * Created by Adam on 2/08/16.
 * Some util methods to help the Game and Cluedo move the player on the board.
 */
public class PlayerMover {

    /**
     * Move a player from the start point to the end point on the board
     * @param board
     * @param player
     * @param startPoint
     * @param endPoint
     * @return if the move was successful or not
     */
    public static void movePlayer(Board board, Player player, Point startPoint, Point endPoint) throws CluedoError {
        // Co-ordinates
        int startX = startPoint.getX();
        int startY = startPoint.getY();
        int endX = endPoint.getX();
        int endY = endPoint.getY();

        // Check the move
        if (startX != endX && startY == endY) { // Horizontal
            movePlayerHorizontal(board, player, startX, endX, endY);

        } else if (startX == endX && startY != endY) { // Vertical
            movePlayerVertical(board, player, startY, endY, endX);

        } else {
            throw new CluedoError("You cannot move in that direction, ensure you are only moving" +
                    "horizontally or vertically at one time");
        }
    }

    /**
     * Move the player on the board along a horizontal path from startX to endX along y
     * @param gameBoard
     * @param player
     * @param startX
     * @param endX
     * @param y
     * @return
     */
    public static void movePlayerHorizontal(Board gameBoard, Player player, int startX, int endX, int y) throws CluedoError {
        int add = startX < endX ? 1 : -1;
        int min = startX;
        Square[][] board = gameBoard.getBoard();

        for (; startX != endX; startX += add) {
            Square fromSquare = board[startX][y];
            Square endSquare = board[startX+add][y];

            // Check that type of transition is allowed
            if (!endSquare.isTransitionAllowed(fromSquare))
                throw new CluedoError("You cannot move from a " + fromSquare.getClass().getName() + ", to " +
                        endSquare.getClass().getName());

            // If its a door check correct direction
            if (endSquare instanceof DoorSquare) {
                DoorSquare door = (DoorSquare) endSquare;
                if (!door.isHorizontalDoor())
                    throw new CluedoError("You cannot walk through the door in that direction");
            }

            // Teleport if its stair square
            if (endSquare instanceof StairSquare) {
                Point point = gameBoard.getStairDestination((StairSquare) endSquare);
                Square destination = board[point.getX()][point.getY()];
                destination.enterSquare(player);
                player.setPos(point.getX(), point.getY());
                return;
            }
        }

        // Must be a valid path, lets move the player
        board[min][y].leaveSquare();
        board[endX][y].enterSquare(player);
        player.setPos(endX, y);
    }

    /**
     * Move the player on the board along a vertical path from startY to endY along x
     * @param gameBoard
     * @param player
     * @param startY
     * @param endY
     * @param x
     * @return
     */
    public static void movePlayerVertical(Board gameBoard, Player player, int startY, int endY, int x) throws CluedoError {
        int add = startY < endY ? 1 : -1;
        int min = startY;
        Square[][] board = gameBoard.getBoard();

        for (; startY != endY; startY += add) {
            Square fromSquare = board[x][startY];
            Square endSquare = board[x][startY+add];

            // Check that type of transition is allowed
            if (!endSquare.isTransitionAllowed(fromSquare))
                throw new CluedoError("You cannot move from a " + fromSquare.getClass().getName() + ", to " +
                        endSquare.getClass().getName());

            // If its a door check correct direction
            if (endSquare instanceof DoorSquare) {
                DoorSquare door = (DoorSquare) endSquare;
                if (!door.isVerticalDoor())
                    throw new CluedoError("You cannot walk through the door in that direction");
            }

            // Teleport if its stair square
            if (endSquare instanceof StairSquare) {
                Point point = gameBoard.getStairDestination((StairSquare) endSquare);
                Square destination = board[point.getX()][point.getY()];
                destination.enterSquare(player);
                return;
            }
        }

        // Must be a valid path, lets move the player
        board[x][min].leaveSquare();
        board[x][endY].enterSquare(player);
        player.setPos(x, endY);
    }
}
