package com.webapp.jchess.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.webapp.jchess.model.gamestate.AbstractField;

class GameThreePlayerGameTest {

    private Game_threePlayerGame game;

    @BeforeEach
    void setUp() {
        game = new Game_threePlayerGame();
    }
    /*
     * Game init is implicitly Tested.
     */

    /*
     * getField for invalid Squareboard Coordinates
    
    @Test
    void getField_forInvalidSquareboardCoordinates(){
        int [] invalidCoords = {1, 7, 5}; // Board is 8x * 4y
        AbstractField field = game.getField(invalidCoords);
        assertNotEquals(field,  "Field should be null for invalid Coordinates!");
    }
     */

    /*
     * getField for valid Squareboard Coordinates
     */
    @Test
    void getField_forValidSquareboardCoordinates(){
        int [] validCoords = {1, 7, 3}; // Board is 8x * 4y
        AbstractField field = game.getField(validCoords);
        assertNotNull(field, "Field should not be null for valid Coordinates!");
    }

    /*
     * getField for invalid Triangleboard Coordinates
    @Test
    void getField_forInvalidTriangleboardCoordinates(){
        int [] invalidCoords = {3, 0, 7, -1}; // {boardidx, x, y, z}; x, y, z -> sum = 7;
        AbstractField field = game.getField(invalidCoords);
        assertNull(field, "Field should be null for invalid Coordinates!");
    }
    */

    /*
     * getFied for valid Triangleboard Coordinates
     */
    @Test
    void getField_forValidTriangleboardCoordinates(){
        int [] validCoords = {3, 0, 0, 7}; // {boardidx, x, y, z}; x, y, z -> sum = 7;
        AbstractField field = game.getField(validCoords);
        assertNotNull(field, "Field should not be null for valid Coordinates!");
    }

    /*
     * getField for invalid Squareboard Coordinates
     */
    @Test
    void validCoordinates_forInvalidSquareboardCoordinates(){
        int [] invalidCoords = {1, 7, 5}; // Board is 8x * 4y
        assertFalse(game.validCoordinates(invalidCoords), "Coordinates should be invalid");
    }

    /*
     * getField for valid Squareboard Coordinates
     */
    @Test
    void validCoordinates_forValidSquareboardCoordinates(){
        int [] validCoords = {1, 7, 3}; // Board is 8x * 4y
        assertTrue(game.validCoordinates(validCoords), "Coordinates should be valid");
    }

    /*
     * getField for invalid Triangleboard Coordinates
     */
    @Test
    void validCoordinates_forInvalidTriangleboardCoordinates(){
        int [] invalidCoords = {3, 0, 7, -1}; // {boardidx, x, y, z}; x, y, z -> sum = 7;
        assertFalse(game.validCoordinates(invalidCoords), "Coordinates should be invalid");
    }

    /*
     * getFied for valid Triangleboard Coordinates
     */
    @Test
    void validCoordinates_forValidTriangleboardCoordinates(){
        int [] validCoords = {3, 0, 0, 7}; // {boardidx, x, y, z}; x, y, z -> sum = 7;
        assertTrue(game.validCoordinates(validCoords), "Coordinates should be valid");
    }

    /*
     * Test if Game end successful
     */
    //TODO: Test end Game

    /*
     * Test isPromotion Field
     */
    //TODO: Test isPromotionField
}