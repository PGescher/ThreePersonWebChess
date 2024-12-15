import React, { useState} from "react";
import "./chessboard.css"; // Add necessary CSS styles

const GenerateChessboards = ({ gameState, players }) => {
  return players.map((player) => (
    <div key={player.id} style={{ marginBottom: "20px" }}>
      <PlayerChessboard
      gameState={gameState}
      playerId={player.id}
      />
    </div>
  ));
};

const PlayerChessboard = ({ gameState, playerId }) => {
  const [validMoves, setValidMoves] = useState([]);
  const [lastClickedSquare, setLastClickedSquare] = useState("");
  const [message, setMessage] = useState("");
  const [message2, setMessage2] = useState("");
  const playerBoard = gameState.boards[playerId];

  if (!playerBoard) {
    console.error(`No board found for player ID: ${playerId}`);
    return <h2>No board available for Player {playerId}</h2>;
  }

  const { pieces } = playerBoard;

  const Square = ({ playerId, xIndex, yIndex, piece }) => {
    const squareId = `${playerId}-${xIndex}-${yIndex}`;

    const handleSquareClick = async (squareId) => {
      if (validMoves.includes(squareId)) {
        // Square is highlighted, move the piece
        try {
          const response = await fetch(`http://localhost:8080/api/move?fromsquare=${lastClickedSquare}&tosquare=${squareId}`);

          if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
          }
          await response.json();
          setMessage2(`Piece moved from ${lastClickedSquare} to ${squareId}`);
          setValidMoves([]); // Clear highlights after a move
          setLastClickedSquare(""); // Clear the last clicked square
        } catch (error) {
          console.error("API call failed:", error);
          setMessage("Failed to move the piece.");
        }
      } else {
        // Make API call to get valid moves for the clicked square
        try {
          const response = await fetch(`http://localhost:8080/api/validMoves?square=${squareId}`);
          if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
          }

          const moves = await response.json(); // Expect JSON array of valid moves
          //console.log(moves);
          //console.log(validMoves);
          setValidMoves(moves); // Update state with valid moves
          //console.log(validMoves);
          setLastClickedSquare(squareId); // Save the clicked square
          console.log(`Valid moves for ${squareId}: ${moves.join(", ")}`);
          setMessage(`Valid moves for ${squareId}: ${moves.join(", ")}`);
        } catch (error) {
          console.error("API call failed:", error);
          setMessage("Failed to fetch valid moves from the server.");
        }
      }
    };

    const isHighlighted = validMoves.includes(squareId);
    //console.log(`Valid moves for ${squareId}: ${validMoves}, ${isHighlighted}, ${squareId}`);

    return (
      <button className={`square ${isHighlighted ? "highlighted" : ""}`} onClick={() => handleSquareClick(squareId)}>
        {piece}
      </button>
    );
  };
  return (
    <div className="player-board">
      <h2>Player {playerId}</h2>
      <div className="chessboard">
        {pieces.map((x, xIndex) =>
          x.map((piece, yIndex) => (
            <Square
              key={`${playerId}-${xIndex}-${yIndex}`}
              playerId={playerId}
              xIndex={xIndex}
              yIndex={yIndex}
              piece={piece}
            />
          ))
        )}
      </div>
      <p>{message}</p>
      <p>{message2}</p>
    </div>
  );
};

export default GenerateChessboards;
