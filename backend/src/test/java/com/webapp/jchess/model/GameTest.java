package com.webapp.jchess.model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameTest {

    @Test
    public void justAnExample() {
        //System.out.println("justAnExampleGameTest");
        assertTrue(true);
    }

    // To be mocked object
    @Mock
    private Game game;

    // BeforeEach means this happens before each Test so we do not have to intialize them in each Test
    @BeforeEach
    public void setup() {
        // Mockito initialisieren
        MockitoAnnotations.openMocks(this);
        game = new Game();
    }

    @Test
    public void testGameinit(){
        assertTrue(game.initGame());
    }
}