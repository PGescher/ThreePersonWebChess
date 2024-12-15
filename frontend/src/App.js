import React, { useState} from "react";
import "./App.css"; // Add necessary CSS styles
import GenerateChessboards from './chessboard.js';

const App = () => {
  const [gameState, setGameState] = useState(null);
  const [message, setMessage] = useState("");
  const [players, setPlayers] = useState([{ id: 0, name: "" }, { id: 1, name: "" }, { id: 2, name: "" }]);

  const startNewGame = async () => {
    try {
      setMessage("Trying to start a new game...");
      await fetch("http://localhost:8080/api/start?playerAmount=" + 3, {
        method: "POST",
      });
      fetchGameState();
    } catch (error) {
      setMessage("Error starting a new game.");
    }
  };

  const fetchGameState = async () => {
    try {
      setMessage("Fetching game state...");
      const response = await fetch("http://localhost:8080/api/state");

      if (response.status === 204) {
        setMessage("No active game found.");
        setGameState({ boards: {} }); // Set empty boards to avoid undefined error
        return;
      }

      const json = await response.text();
      setMessage("Got JSON")
      const parsedState = JSON.parse(json);
      setGameState(parsedState);
      //setGameState(json);
      setMessage("Game state fetched successfully.");
    } catch (error) {
      setMessage("Error fetching game state.");
    }
  };

  const addPlayerField = () => {
    const newId = players.length > 0 ? players[players.length - 1].id + 1 : 1;
    setPlayers([...players, { id: newId, name: "" }]);
  };

  const handlePlayerNameChange = (id, newName) => {
    setPlayers(
      players.map((player) =>
        player.id === id ? { ...player, name: newName } : player
      )
    );
  };

  const startGameWithPlayers = async () => {
    const playerNames = players.map((player) => player.name.trim()).filter(Boolean);
    if (playerNames.length === 0) {
      setMessage("Please enter at least one player name.");
      return;
    }
    setMessage("Starting game with players...");
    await startNewGame();
  };

  if (!gameState) {
    return (
      <div className="board-container">
        <h1>Start a New Chess Game</h1>

        {players.map((player) => (
          <div key={player.id} style={{ marginBottom: "10px" }}>
            <input
              type="text"
              placeholder={`Player ${player.id} name`}
              value={player.name}
              onChange={(e) => handlePlayerNameChange(player.id, e.target.value)}
              style={{ marginRight: "10px" }}
            />
          </div>
        ))}
        <button 
          onClick={addPlayerField}
          disabled={players.length > 5}
          style={{ marginLeft: "10px", marginBottom: "10px" }}
          >
          Add Player
        </button>
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
    return GenerateChessboards({gameState, players});
  }
};

export default App;
