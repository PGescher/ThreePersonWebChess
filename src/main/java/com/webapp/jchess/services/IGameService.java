package com.webapp.jchess.services;

import java.util.List;

public interface IGameService {

    /*
     * Stats the Game
     * Creates Chessboards for Players
     * And sets the Pieces
     * 
     * Return true if GameInit sucessfuly
     */
    public boolean initGame(int playerAmount);

    public default boolean initGame() {
        return initGame(3);
    }
    
    /*
     * Get the current Gamestate
     * Return: JSON String.
     * JSON String Example = """
            {
            "boards": {
                "0": {
                "pieces": {
                    ["R", "N", "B", "Q", "K", "B", "N", "R"],
                    ["P", "P", "P", "P", "P", "P", "P", "P"],
                    ["", "", "", "", "", "", "", ""],
                    ["", "", "", "", "", "", "", ""]
                ]
                },
                "1": {
                "pieces": [
                    ["R", "N", "B", "K", "Q", "B", "N", "R"],
                    ["P", "P", "P", "P", "P", "P", "P", "P"],
                    ["", "", "", "", "", "", "", ""],
                    ["", "", "", "", "", "", "", ""]
                ]
                }
            }
        }
        """;
     */
    public String getGamestate();
    

    /*
     * Square String Format checker
     */
    public default boolean squareFormatChecker(String Square){
        //Format - "id-X-Y"
        return Square.matches("\\d{1}-\\d{1}-\\d{1}");
    }

    /*
     * Get the Valid Moves
     * {id,x,y}
     */
    public List<String> getvaidMoves(String Square);
    
    /*
     * Square String = {id, x, y}
     * 
     * Return true, if successfull
     */
    public boolean Move(String startSquare, String endSquare);
}