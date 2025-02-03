package com.webapp.jchess.model;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.pieces.Piece;

public class AbstractGameTest {


    // To be mocked object
    @Mock
    private AbstractGame game;

    // BeforeEach means this happens before each Test so we do not have to intialize them in each Test
    @BeforeEach
    public void setup() {
        // Mockito initialisieren
        MockitoAnnotations.openMocks(this);
        game = new Game_threePlayerGame();
    }

    /*
     * Test Game Start with Correct amount of Players
     */
    @Test
    public void AbtractGame_forThreePlayers(){
        assertEquals(3, game.allPlayers.size());
    }

    /*
     * Test removing of Piece
     */
    //TODO: Should this be tested in a seperate test for the relevant class piece.java?
    @Test
    void removePiece_isPieceRemovedCorrectly() {
        Piece mockPiece = mock(Piece.class);
        AbstractField mockField = mock(AbstractField.class);
        
        mockPiece.field = mockField;
        game.activePieces.add(mockPiece);
        
        game.removePiece(mockPiece);
        
        assertNull(mockPiece.field, "Piece field should be null after removal.");
        assertFalse(game.activePieces.contains(mockPiece), "Piece should be removed from activePieces.");
    }

    /*
     * Test Field Selection
     */

    /*
     * Test getting of Highlighted Fields
     * Edge Cases: Getting Highlighted fields across boards
     */

    /*
     * Test Making a valid Move
     * Edge Cases: Moving between boards
     */

    /*
     * Test Undo
     * EdgeCase: Stack is empty
     */
    @Test
    void undo_WhenStackIsEmpty() {
        assertFalse(game.undo(), "Undo should return false when there are no moves to undo.");
    }

    /*
     * Test Redo
     * Edgecase: Stack is empty
     */
    @Test
    void redo_WhenStackIsEmpty() {
        assertNull(game.redo(), "Redo should return null when there are no moves to redo.");
    }
}