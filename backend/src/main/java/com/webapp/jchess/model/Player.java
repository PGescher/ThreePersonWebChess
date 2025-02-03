
package com.webapp.jchess.model;

public class Player 
{
    public enum player_IDS{
        PID1, PID2, PID3, NOT_SET
    }
    public player_IDS playerID;
    
    public Player()
    {
        // this.name="Default Name";
        this.playerID = player_IDS.NOT_SET;
    }

    public Player(player_IDS ID)
    {
        this.playerID = ID;
    }

    public String toString(){
        return playerID.toString();
    }

}
