package com.webapp.jchess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.jchess.services.GameService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThreePlayerGameControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameService gameService;

    //Ensure no game is running, for correct Test flow.
    @BeforeAll
    void setup() {
        assertFalse(gameService.isRunning(), "No game should be running at the beginning.");
    }

    /*
     * Test the starting of a ThreePlayerGame. Correct Response Code and initialization.
     */
    @Test
    @Order(1)
    void testStartGameThreePlayers() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/start?playerAmount=3", null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, gameService.newGame(3), "A three-player game should have been initialized.");
        assertTrue(gameService.isRunning(), "Game should be running after initialization.");
    }

    /*
     * Make sure state persists throughout the Test Cases for proper Integration testing.
     */
    @Test
    @Order(2)
    void testCheckGameRunningAfterFirstTest() {
        assertTrue(gameService.isRunning(), "Game state should persist between tests.");
    }

    /*
     * Test retrieval of piece positions.
     */
    @Test
    @Order(3)
    void testGetPieces() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/pieces", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Pieces retrieval should return status 200.");
        assertNotNull(response.getBody(), "Response should contain piece information.");
    
    }

    /*
     * Test field selection
     */
    @Test
    @Order(4)
    void testCheckSelectField() {
        //White Pawn should be at this position.
        int[] coordinates = {0, 1, 1};
        ResponseEntity<String> response = restTemplate.postForEntity("/api/select-field", coordinates, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Field selection should return status 200.");
        assertTrue(response.getBody().contains("\"code\": 1"), "Response should indicate successful field selection. But was: " + response.getBody());
    }

    /*
     * Test getting of the correct highlighted Fields
     */
    @Test
    @Order(5)
    void testGetHighlightedFields() throws JsonProcessingException {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/highlighted-fields", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Highlight retrieval should return status 200.");
        assertNotNull(response.getBody(), "Response should contain highlighted field information.");

        String body = response.getBody();
        assertTrue(body.contains("\"coordinates\":[0,1,2]"), "Highlighted fields should include expected coordinates");

    }

    /*
     * Test making a move a retrieving the pieces
     */
    @Test
    @Order(6)
    void testMakingAValidMove() {
        //This should be a valid move of the previous pawn. Based on previous test.
        int[] coordinates = {0, 1, 2};
        ResponseEntity<String> response = restTemplate.postForEntity("/api/select-field", coordinates, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Valid move selection should return status 200.");
        assertTrue(response.getBody().contains("\"code\": 2"), "Response should indicate that a move has been made.");
    }

    /*
     * Test undoing a move
     */
    @Test
    @Order(7)
    void testUndoMove() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/undo", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Undo move should return status 200.");
        assertTrue(response.getBody().contains("Undid move Successfully"), "Response should confirm successful undo.");
    }

    /*
     * Test redoing a move
     */
    @Test
    @Order(8)
    void testRedoMove() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/redo", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Redo move should return status 200.");
        assertTrue(response.getBody().contains("Undid move Successfully"), "Response should confirm successful redo.");
    }
}
