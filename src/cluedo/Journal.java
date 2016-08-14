package cluedo;

/**
 * Created by Adam on 25/07/16.
 * The Journal is used by players to store information in. It is all stored in one string that they can append more
 * too. Used to keep track of cards players contain and help them solve the game.
 */
public class Journal{
    /**
     * Message the journal stores
     */
    String message;

    /**
     * Create a new Journal
     */
    public Journal(){
        this.message = "";
    }

    /**
     * Get the message contained in the journal
     * @return
     */
    public String getContents(){
        return this.message;
    }

    /**
     * Adds the msg to the journal of the player
     * @param msg
     */
    public void addToJournal(String msg){
        message += msg + "\n";
    }
}
