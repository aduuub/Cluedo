package util;

/**
 * Created by Adam on 25/07/16.
 * Cluedo Error is the exception thrown when something goes wrong. It is thrown in multiple methods, and should be
 * caught by the method that called it, rather then creating an exception that disrupts play of the game.
 */
public class CluedoError extends RuntimeException{

    /**
     * Throw a new Cluedo Error
     * @param msg
     */
    public CluedoError(String msg){
        super(msg);
    }

}
