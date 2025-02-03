package com.webapp.jchess.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final int[][][] TriangleBoardMap = {
    {{7, 0, 7}},
    {{7, 1, 6}, {7, 1, 7}, {6, 1, 7}},
    {{7, 2, 5}, {7, 2, 6}, {6, 2, 6}, {6, 2, 7}, {5, 2, 7}},
    {{7, 3, 4}, {7, 3, 5}, {6, 3, 5}, {6, 3, 6}, {5, 3, 6}, {5, 3, 7}, {4, 3, 7}},
    {{7, 4, 3}, {7, 4, 4}, {6, 4, 4}, {6, 4, 5}, {5, 4, 5}, {5, 4, 6}, {4, 4, 6}, {4, 4, 7}, {3, 4, 7}},
    {},
    {},
    {}
    };

    private GameService gameService;

    /*
     * Start the Game with amount of Players
     */
    /*
     * Check if active Game
     */
    @GetMapping("/Game")
    public ResponseEntity<String> isRunning() {
        if(gameService != null){
          return ResponseEntity.ok().body("GameService exists.");
        }
        else {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GameService does not exits.");
        }
    }

    /*
     * Start the Game with amount of Players
     */
    @PostMapping("/start")
    public ResponseEntity<String> createGame(@RequestParam int playerAmount) {
        try {
            this.gameService = new GameService();
            int responseCode = gameService.newGame(playerAmount);
            if (responseCode == 3) {
              return ResponseEntity.ok().body("Three Player Game started successfully");
            } else if (responseCode == 2) {
              return ResponseEntity.ok().body("Two Player Game started successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to start the game.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }
    
    /*
     * Get Drawable Pieces
     * The frontend generates an Array based in the specifications passes
     */
    @GetMapping("/pieces")
    public ResponseEntity<String> getPieces() throws JsonProcessingException {
        ArrayList<int[]> coordList = new ArrayList<>();
        ArrayList<String> pieceList = new ArrayList<>();
        ArrayList<String> playerList = new ArrayList<>();
        gameService.getPieces(coordList, pieceList, playerList);

        String json = piecesToJson(coordList, pieceList, playerList);

        return ResponseEntity.ok(json);
    }

    public String piecesToJson(ArrayList<int[]> coordList, ArrayList<String> pieceList, ArrayList<String> playerList) throws JsonProcessingException {
    // Create a list of maps for structured JSON
    ArrayList<Map<String, Object>> list = new ArrayList<>();

    for (int i = 0; i < coordList.size(); i++) {
        int[] coord = coordList.get(i);
        //gameService.getField(coord);

        // Convert coordinates to a list or array for JSON compatibility
        List<Integer> coordAsList = Arrays.stream(coord).boxed().toList();

        // Create a structured map for each piece
        Map<String, Object> entry = new HashMap<>();
        entry.put("coordinates", coordAsList);
        entry.put("piece", pieceList.get(i));
        entry.put("player", playerList.get(i));

        list.add(entry);
      }

    // Use ObjectMapper to serialize the structured list to JSON
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(list);
    }

    
    /*
     * Get the highlighted Fields
     */
    @GetMapping("/highlighted-fields")
    public String getHighlightedFields() throws JsonProcessingException {
      ArrayList<int[]> coordList = new ArrayList<>();
      ArrayList<String> fieldType = new ArrayList<>();
      gameService.getHighlightedFields(coordList, fieldType);

      // Create a list of maps for structured JSON
      ArrayList<Map<String, Object>> list = new ArrayList<>();

      for (int i = 0; i < coordList.size(); i++) {
          int[] coord = coordList.get(i);
          //gameService.getField(coord);

          // Convert coordinates to a list or array for JSON compatibility
          List<Integer> coordAsList = Arrays.stream(coord).boxed().toList();

          // Create a structured map for each piece
          Map<String, Object> entry = new HashMap<>();
          entry.put("coordinates", coordAsList);
          entry.put("fieldtype", fieldType.get(i));

          list.add(entry);
        }

      // Use ObjectMapper to serialize the structured list to JSON
      ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(list);
    }
    

    /*
     * Selected a Square
     */
    @PostMapping("/select-field")
    public ResponseEntity<String> selectField(@RequestBody int[] coords) {
        // Debugging: print the coordinates to verify
        System.out.println("Received coordinates in Controller: " + Arrays.toString(coords));

        // Assuming your game service has a method to calculate possible moves based on the field coordinates
        try {
          // Call the game service (or whatever logic you need) to fetch the possible moves
          int responsecode = gameService.selectField(coords);
          System.out.println("Return Code: " + responsecode);
          switch(responsecode){
            case(-1): return ResponseEntity.badRequest().body("{\"code\": 0, \"message\": \"No code in game.selectField was executed!\"}");
            case(0): return ResponseEntity.ok().body("{\"code\": 0, \"message\": \"Selected Field does not contain a Piece or Enemy Piece\"}");
            case(1): return ResponseEntity.ok().body("{\"code\": 1, \"message\": \"Field selection successful\"}");//Successfully passed the selected Field.
            case(2): return ResponseEntity.ok().body("{\"code\": 2, \"message\": \"Move has been made\"}");//Move has been Made.
            default: return ResponseEntity.badRequest().body("{\"code\": -1, \"message\": \"Game selectField returned an unknown code\"}");
          }
        } catch (Exception e) {
            // Handle any exceptions that may arise
            return ResponseEntity.status(500).body("{\"code\": 500, \"message\": \"An error occurred while processing the field.\", \"error\": \"" + e.getMessage() + "\"}");
        }
    }


    @GetMapping("/undo")
    public ResponseEntity<String> undo_move() {
        System.out.println("Undo received in Controller");
        if(gameService.undo_move()) {
          return ResponseEntity.ok().body("Undid move Successfully!");
        } else {
          return ResponseEntity.badRequest().body("Could not Undo Move!");
        }
    }
    @GetMapping("/redo")
    public ResponseEntity<String> redo_move() {
      System.out.println("Redo received in Controller");
      if(gameService.redo_move()) {
        return ResponseEntity.ok().body("Undid move Successfully!");
      } else {
        return ResponseEntity.badRequest().body("Could not Undo Move!");
      }
    }
    
  }


