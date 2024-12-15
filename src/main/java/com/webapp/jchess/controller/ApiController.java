package com.webapp.jchess.controller;

import java.util.ArrayList;
import java.util.List;

import com.webapp.jchess.model.Game;
import com.webapp.jchess.model.Player.playerTypes;
import com.webapp.jchess.services.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // For ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping; // For @GetMapping
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; // For @RequestMapping
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; // For @RestController
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<String> createGame(@RequestParam int playerAmount) {
        try {
            this.gameService = new GameService();
            
            if (gameService.newGame(playerAmount)) {
                return ResponseEntity.ok("Game started successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to start the game.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    // Get valid moves for a square
    @GetMapping("/validMoves")
    public List<String> getValidMoves(@RequestParam String square) {

        //Test
        List<String> validMoves = new ArrayList<>();
        validMoves.add("0-3-7");
        return validMoves;

        //return gameService.getvalidMoves(square);
    }

    @GetMapping("/state")
    public ResponseEntity<String> getGameState() {
        // Returning the JSON string as the response
        String json = gameService.getGamestate();

        return ResponseEntity.ok(json); 
    }
    

    @PostMapping("/move")
    public ResponseEntity<Void> Move(@RequestBody String fromSquare, String toSquare) {
        
        if(gameService.Move(fromSquare, toSquare))
        {
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.internalServerError().build();
        }
    }
    

}

