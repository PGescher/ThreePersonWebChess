package com.webapp.jchess.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.gamestate.SquareBoard;
import com.webapp.jchess.model.gamestate.TriangleBoard;
import com.webapp.jchess.model.gamestate.AbstractBoard;
import com.webapp.jchess.model.pieces.Bishop;
import com.webapp.jchess.model.pieces.King;
import com.webapp.jchess.model.pieces.Knight;
import com.webapp.jchess.model.pieces.Pawn;
import com.webapp.jchess.model.pieces.Queen;
import com.webapp.jchess.model.pieces.Rook;
import com.webapp.jchess.model.pieces.Piece;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

/*
 * Testing a Mock Game:
 * - Starting game with board, pieces and players
 * - Making a valid move on the squareboard, undo and redo.
 * - Moving from squareboard to triangleboard, undo and redo.
 * - Making a valid move on the triangleboard, undo and redo.
 * - Moving from triangleboard to squareboard
 */
@Execution(ExecutionMode.SAME_THREAD)

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@TestInstance(Lifecycle.PER_CLASS)
class GameThreePlayerGameIntegrationTest {

    private static Game_threePlayerGame game;

    @BeforeAll
    static void setUp() {
        game = new Game_threePlayerGame();
        // Only initialize once

        AbstractBoard[] boards = game.boards;

        //White Pawn to Test Movement into and out of Triangleboard
        Pawn pawnWhite0 = new Pawn(game, game.allPlayers.get(0));
        boards[0].getField(new int[] {1, 2}).setPiece(pawnWhite0);
        game.activePieces.add(pawnWhite0);

        //Rook, Bishop, Knight and Queen to test movement over Triangleboard
        Rook rookWhite1 = new Rook(game, game.allPlayers.get(0));
        boards[0].getField(new int[] {5, 3}).setPiece(rookWhite1);
        game.activePieces.add(rookWhite1);

        Bishop bishopWhite2 = new Bishop(game, game.allPlayers.get(0));
        boards[0].getField(new int[] {7, 3}).setPiece(bishopWhite2);
        game.activePieces.add(bishopWhite2);

        Knight knightWhite3 = new Knight(game, game.allPlayers.get(0));
        boards[0].getField(new int[] {0,3}).setPiece(knightWhite3);
        game.activePieces.add(knightWhite3);

        Queen queenWhite4 = new Queen(game, game.allPlayers.get(0));
        boards[0].getField(new int[] {4, 2}).setPiece(queenWhite4);
        game.activePieces.add(queenWhite4);

        //Black Pawn to be taken
        Pawn pawnBlack5 = new Pawn(game, game.allPlayers.get(1));
        boards[1].getField(new int[] {4,3}).setPiece(pawnBlack5);
        game.activePieces.add(pawnBlack5);

        Pawn pawnBlack6 = new Pawn(game, game.allPlayers.get(1));
        boards[1].getField(new int[] {7,3}).setPiece(pawnBlack6);
        game.activePieces.add(pawnBlack6);
    }

    @Test
    @Order(1)
    void testGameInitialization() {
        assertEquals(4, game.boards.length, "There should be exactly 4 boards.");
    }

    @Test
    @Order(2)
    void testPlayerInitialization() {
        assertEquals(3, game.allPlayers.size(), "There should be 3 players in a three-player game.");
        assertEquals(game.allPlayers.get(0).playerID, Player.player_IDS.PID1);
        assertEquals(game.allPlayers.get(1).playerID, Player.player_IDS.PID2);
        assertEquals(game.allPlayers.get(2).playerID, Player.player_IDS.PID3);

    }

    /*
     * Test if the to be clicked fields are validCoordinates to validFields, so we know if this causes failures.
     */
    @Test
    @Order(3)
    void validCoordinates_ofMovesInIntegrationTest(){
    }

    @Test
    @Order(4)
    void validFields_ofMovesInIntegrationTest(){
    }

    /*
     * Test making a move on the squareboard, undoing and redoing it. (White Pawn Movement)
     */
    @Test
    @Order(5)
    void testSquareboardMove() {
        assertArrayEquals(new int[] {0, 1, 2}, game.activePieces.get(47 +1).field.getGlobalCoords());
        game.selectField(new int[] {0, 1, 2});
        game.selectField(new int[] {0, 1, 3});
        assertArrayEquals(new int[] {0, 1, 3}, game.activePieces.get(47 +1).field.getGlobalCoords());
        game.undo();
        assertArrayEquals(new int[] {0, 1, 2}, game.activePieces.get(47 +1).field.getGlobalCoords());
        game.redo();
        assertArrayEquals(new int[] {0, 1, 3}, game.activePieces.get(47 +1).field.getGlobalCoords());
    }

    /*
     * Test movement from squareboard into triangleboard, undo and redo.
     */
    @Test
    @Order(6)
    void testSquareboardToTriangleboardMove() {
        assertArrayEquals(new int[] {1 , 7, 3}, game.activePieces.get(47+7).field.getGlobalCoords());
        game.selectField(new int[] {1, 7, 3});
        game.selectField(new int[] {3, 0, 0, 7});
        assertArrayEquals(new int[] {3, 0, 0, 7}, game.activePieces.get(47+7).field.getGlobalCoords());
        game.undo();
        assertArrayEquals(new int[] {1, 7, 3}, game.activePieces.get(47+7).field.getGlobalCoords());
        game.redo();
        assertArrayEquals(new int[] {3, 0, 0, 7}, game.activePieces.get(47+7).field.getGlobalCoords());
    }

    /*
     * Test making a move in triangleboard, undoing and redoing it.
     */
    @Test
    @Order(7)
    void testTriangleboardMove() {
        assertArrayEquals(new int[] {3, 0, 0, 7}, game.activePieces.get(47+7).field.getGlobalCoords());
        game.selectField(new int[] {3, 0, 0, 7});
        
    }

    /*
     * Test movement from triangleboard into squareboard
     */
    @Disabled
    @Test
    @Order(8)
    void testTriangleboardToSquareboardMove() {
        assertEquals(new int[] {3, 0, 0, 6}, game.activePieces.get(9).field.getGlobalCoords());
        game.selectField(new int[] {3, 0, 0, 7});
        game.selectField(new int[] {0, 0, 3});
        assertEquals(new int[] {0, 0, 3}, game.activePieces.get(9).field.getGlobalCoords());
        game.undo();
        assertEquals(new int[] {3, 0, 0, 7}, game.activePieces.get(9).field.getGlobalCoords());
        game.redo();
        assertEquals(new int[] {0, 0, 3}, game.activePieces.get(9).field.getGlobalCoords());
    }

    /*
     * Test moving from squareboard through triangleboard to another squareboard
     */
    //Rook
    @Disabled
    @Test
    @Order(9)
    void TestMoveThroughTriangleboardRook() {
        assertEquals(new int[] {0, 5, 1}, game.activePieces.get(4).field.getGlobalCoords());
        game.selectField(new int[] {0, 5, 1});
        game.selectField(new int[] {2, 2, 0});
        assertEquals(new int[] {2, 2, 0}, game.activePieces.get(4).field.getGlobalCoords());
    }
    //Bishop
    @Disabled
    @Test
    @Order(10)
    void TestMoveThroughTriangleboardBishop() {
        assertEquals(new int[] {0, 7, 1}, game.activePieces.get(5).field.getGlobalCoords());
        game.selectField(new int[] {0, 7, 1});
        game.selectField(new int[] {1, 0, 1});
        assertEquals(new int[] {1, 0, 1}, game.activePieces.get(5).field.getGlobalCoords());
        }
    //Knight
    @Disabled
    @Test
    @Order(11)
    void TestMoveThroughTriangleboardKnight() {
        assertEquals(new int[] {0, 0, 2}, game.activePieces.get(6).field.getGlobalCoords());
        game.selectField(new int[] {0, 0, 2});
        game.selectField(new int[] {1, 7, 3});
        assertEquals(new int[] {1, 7, 3}, game.activePieces.get(6).field.getGlobalCoords());
    }
    //Queen
    @Disabled
    @Test
    @Order(12)
    void TestMoveThroughTriangleboardQueen() {
        assertEquals(new int[] {0, 4, 1}, game.activePieces.get(7).field.getGlobalCoords());
        game.selectField(new int[] {0, 4, 1});
        game.selectField(new int[] {1, 3, 1});
        assertEquals(new int[] {1, 3, 1}, game.activePieces.get(7).field.getGlobalCoords());
    }

    /*
     * Test checkmate.
     */

}