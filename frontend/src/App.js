import React, { useState, useEffect } from "react";
import Chessboard from "./components/Chessboard";
import "./App.css";

const App = () => {
  // Debug Message
  const [message, setMessage] = useState("");
  // Player: id and name
  const [players, setPlayers] = useState([{ id: 0, name: "" }, { id: 1, name: "" }, { id: 2, name: "" }]);
  // Game status
  const [gameStatus, setGameStatus] = useState(0);
  // Holds piece data (coordinates, owner, piece type)
  const [pieces, setPieces] = useState([]);
  // Holds coordinates for highlighted fields
  const [highlightedFields, setHighlightedFields] = useState([]);

  // Fetch initial piece locations when the app loads
  useEffect(() => {
    fetchGame();
  }, []);

  // Check if a Game exists
  async function fetchGame() {
    try {
      const response = await fetch("http://localhost:8080/api/Game");
      if (!response.ok) {
        setGameStatus(0);
        throw new Error("No active game");
      }
      else{
        setGameStatus(1);
        await getPieces();
      }
    } catch (error) {
      // No Game running
      
      //startNewGame();
    }
  }

  // Start a new game: Posting the player names and fetching the game state
  const startNewGame = async () => {
    try {
      setMessage("Trying to start a new game...");
      // Collect player names (if available)
      const playerNames = players.map((player) => player.name).join(',');
      //const playerAmount = players.filter(player => player.name.trim()).length;
      const playerAmount = 3;
      const response = await fetch(`http://localhost:8080/api/start?playerAmount=${playerAmount}`, {
        method: "POST",
      });
      if (response.ok) {
        setGameStatus(1); // Update game status to running
        await getPieces(); // Fetch pieces after game starts
      } else {
        setMessage("Error starting a new game.");
      }
    } catch (error) {
      setMessage("Error starting a new game.");
    }
  };

  // Fetch all pieces
  const getPieces = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/pieces");
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      const data = await response.json();
      setPieces(data); // Update pieces state
      //setHighlightedFields([]);
    } catch (error) {
      console.error("Error fetching pieces:", error);
    }
  };

    // Fetch all pieces
    const getHighlightedFields = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/highlighted-fields");
        console.log("getHighlightedFields Response: " + response);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        setHighlightedFields(data); // Update pieces state
      } catch (error) {
        console.error("Error fetching pieces:", error);
      }
    };

  // Pass a selectedField
  const selectField = async (boardid, fieldCoords) => {
    //setHighlightedFields([]);
    console.log("boardid:", boardid, "fieldCoords:", fieldCoords);
    try {
      const dataToSend = [boardid, ...fieldCoords]; // Flatten the array to [boardid, fieldCoords[0], fieldCoords[1]]
      
      const response = await fetch(`http://localhost:8080/api/select-field`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',  // Ensure content type is JSON
        },
        body: JSON.stringify(dataToSend),  // Send the flattened array as JSON
      });
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      } else {
        const responseText = await response.text();
        const data = JSON.parse(responseText);
        if(data.code === 2) {
          // Move was made
          await Promise.all([getPieces(), getHighlightedFields()]);
          //togglePlayer(1);
        }
        if(data.code === 1) {
          // Field was selected
          await Promise.all([getPieces(), getHighlightedFields()]);
        }
      //getPieces();
      //getHighlightedFields();
      
      //const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

      //await delay(600);

      //await getPieces();
      }
    } catch (error) {
      console.error("Error fetching possible moves:", error);
    }
  };

  // Handling the player name change
  const handlePlayerNameChange = (id, newName) => {
    setPlayers(
      players.map((player) =>
        player.id === id ? { ...player, name: newName } : player
      )
    );
  };

  // Start the game if all player names are filled
  const startGameWithPlayers = async () => {
    const playerNames = players.map((player) => player.name.trim()).filter(Boolean);
    if (playerNames.length === 0) {
      setMessage("Please enter at least one player name.");
      return;
    }
    setMessage("Starting game with players...");
    await startNewGame();
  };

  // Field click handler passed to Chessboard component
  const handleFieldClick = async ({ boardid, coords }) => {
    console.log(`Field clicked: Board - ${boardid}, Coordinates - ${coords}`);
    await selectField(boardid, coords); // Fetch possible moves for the clicked field
  };

  //Handle Undo Button clicked
  const handleUndoClick = async () => {
    console.log(`Undo clicked!`);
    const response = await fetch("http://localhost:8080/api/undo");
    if(response.ok){
      console.log(`Undo successful!`);
      await getPieces();
      //togglePlayer(-1);
    } else {
      console.log("Undo could not be completed!")
    }
  };

    //Handle Undo Button clicked
    const handleRedoClick = async () => {
      console.log(`Redo clicked!`);
      const response = await fetch("http://localhost:8080/api/redo");
      if(response.ok){
        console.log(`Redo successful!`);
        await getPieces();
        //togglePlayer(+1);
      } else {
        console.log("Redo could not be completed!")
      }
    };

    /*
    const togglePlayer = (toggle) => {
      setActivePlayerIndex((activePlayerIndex) => (activePlayerIndex + toggle) % players.length);
    };
    const [activePlayerIndex, setActivePlayerIndex] = useState(0);
  */
  // Rendering return
  if (gameStatus === 0) {
    return (
      <div className="board-container">
        <h1>Start a New Chess Game</h1>
        {players.map((player) => (
          <div key={player.id} style={{ marginBottom: "10px" }}>
            <input
              type="text"
              placeholder={`Player ${player.id + 1} name`}
              value={player.name}
              onChange={(e) => handlePlayerNameChange(player.id, e.target.value)}
              style={{ marginRight: "10px" }}
            />
          </div>
        ))}
        <button
          onClick={startGameWithPlayers}
          disabled={players.some((player) => !player.name.trim())}
          style={{ marginLeft: "10px" }}
        >
          Start Game
        </button>
        <p>{message}</p>
      </div>
    );
  } else {
    return (
      <div className="board-container">
        <Chessboard
          pieces={pieces}
          highlightedFields={highlightedFields}
          onFieldClick={handleFieldClick} // Pass the click handler to Chessboard
          handleUndo={handleUndoClick}
          handleRedo={handleRedoClick}
          handleRestart={startNewGame}
          //activePlayerIndex={activePlayerIndex}
        />
      </div>
    );
  }
};

export default App;
