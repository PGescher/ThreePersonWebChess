import React, { useState, useEffect } from "react";
import "./Chessboard.css";

const Chessboard = ({ pieces, highlightedFields, onFieldClick , handleUndo, handleRedo, handleRestart}) => {
  // Board initialization happens once
  const [boards, setBoards] = useState({
    squareBoardLeft: Array(8).fill(null).map(() => new Array(4).fill(null).map(() => ({}))),
    squareBoardTop: Array(8).fill(null).map(() => new Array(4).fill(null).map(() => ({}))),
    squareBoardRight: Array(8).fill(null).map(() => new Array(4).fill(null).map(() => ({}))),
    triangleBoard: Array(8).fill(null).map((_, rowIndex, arr) => {
      const maxIdx = arr.length - 1; // max index for `z` calculations
      const firstZ = maxIdx - rowIndex;
      const rowTriangles = [];
    
      // Add first triangle in the row
      rowTriangles.push({ x: rowIndex, y: 0, z: firstZ });
      //console.log(rowIndex, 0, firstZ);
    
      // Add subsequent triangles based on the row and extra squares
      for (let sq = 1; sq <= maxIdx - rowIndex; sq++) {
        const leftY = sq - 1;
        const rightY = sq;
        const sqZ = firstZ - sq;
    
        rowTriangles.push({ x: rowIndex, y: leftY, z: sqZ });
        rowTriangles.push({ x: rowIndex, y: rightY, z: sqZ });
      }
      //console.log(rowTriangles)
      //return rowTriangles.map(() => ({})); // Return cells initialized as empty objects
      return rowTriangles;
    })
    
  });

  const getTriangleIndex = (rowIndex, y, z) => {

    const row = boards.triangleBoard[rowIndex]
  
    const index = row.findIndex(triangle => triangle.y === y && triangle.z === z);

    if (index === -1) {
      console.error(`Invalid coordinates for triangle! row: ${rowIndex}, y: ${y}, z: ${z}`);
      return null;
    }
  
    
    return index;
  };


  // Create a mapping of boardids to board names
  const boardIdToName = {
    0: 'squareBoardLeft',
    1: 'squareBoardTop',
    2: 'squareBoardRight',
    3: 'triangleBoard',
  };

  //const players = ["White", "Black", "Green"];

  useEffect(() => {
    const updatedBoards = { ...boards };
  
  // Clear all boards, but preserve the coordinates
  Object.keys(updatedBoards).forEach((boardName) => {
    updatedBoards[boardName] = updatedBoards[boardName].map((row) => 
      row.map((cell) => {
        return {
          ...cell,
          value: undefined,
          active: false,
          possible: false,
        };
      })
    );
  });
  
    // Place pieces
    pieces.forEach(({ coordinates, piece, player }) => {
      const [boardid, x, y, z] = coordinates;
    
      if (boardid === 3) {
        const row = updatedBoards.triangleBoard[x];
        const index = getTriangleIndex(x, y, z);

        //console.log("triange Index: " + index);
    
        if (index !== null) {
          row[index].type = "piece";
          row[index].value = piece + player;
        }
      } else {
        const boardName = boardIdToName[boardid];
        if (boardName) updatedBoards[boardName][x][y] = { type: "piece", value: piece + player };
      }
    });
    
    
    highlightedFields.forEach(({ coordinates, fieldtype }) => {
      const [boardid, x, y, z] = coordinates;
    
      if (boardid === 3) {
        const row = updatedBoards.triangleBoard[x];
        const index = getTriangleIndex(x, y, z);
    
        if (index !== null) {
          row[index][fieldtype === "a" ? "active" : "possible"] = true;
        }
      } else {
        const boardName = boardIdToName[boardid];
        if (boardName) updatedBoards[boardName][x][y][fieldtype === "a" ? "active" : "possible"] = true;
      }
    });

    setBoards(updatedBoards);
  }, [pieces, highlightedFields]); // Re-run whenever pieces or highlights change

  // Map row and colindex to Triangle coords
  const handleTriangleFieldClick = ({boardid, coords}) => {

    const row = coords[0];
    const col = coords[1];

    const triangle = boards.triangleBoard[row][col]

    console.log(triangle)

    onFieldClick({boardid: boardid, coords: [triangle.x, triangle.y, triangle.z]})
  };
/*
  const TriangleBoard2 = ({ rows }) => (
      <div className="triangle-board">
        {rows.map((row, rowIndex) => (
          <div key={rowIndex} className="row" style={{ display: "flex", justifyContent: "center", gap: "1px" }}>
            {row.map((field, colIndex) => {
              <div
                key={colIndex}
                className={`cell ${colIndex % 2 === 1 ? "triangle" : "triangle-flipped"} ${field?.active ? "active" : ""} ${field?.possible ? "possible" : ""}`}
                onClick={() => {
                  handleTriangleFieldClick({coords: [rowIndex, colIndex] })
                }}
              >
                {field?.value && <img src={`/images/${field.value}.png`} alt={"Test"} />}
              </div>
            })}
          </div>
        ))}
      </div>
  );
  */

  const TriangleBoard = ({ rows, boardid }) => (
    <div className="triangle-board">
      {rows.map((row, rowIndex) => (
        <div
        key={rowIndex}
        className="row"
        style={{
          display: "flex",
          justifyContent: "center",
          gap: "1px",
        }}
      >
          {row.map((cell, colIndex) => (
            <div
              key={colIndex}
              className={`cell ${colIndex % 2 === 1 ? "triangle" : "triangle-flipped"} ${cell?.active ? "active" : ""} ${cell?.possible ? "possible" : ""} `}
              onClick={() =>
              handleTriangleFieldClick({ boardid: boardid, coords: [rowIndex, colIndex] })
            }>
            {cell?.value && <img src={`/images/${cell.value}.png`} alt={"Test"} />}
            </div>
          ))}
        </div>
      ))}
    </div>
  );


  // Square board rendering
  const SquareBoard = ({ rows, boardName, boardid }) => (
    <div className={`square-board ${boardName}`}>
      {rows.map((row, rowIndex) => (
        <div
        key={rowIndex}
        className="row"
        style={{
          display: "flex",
          justifyContent: "center",
          gap: "1px",
        }}
      >
          {row.map((cell, colIndex) => (
            <div
              key={colIndex}
              className={`cell square ${cell?.active ? "active" : ""} ${cell?.possible ? "possible" : ""} ${(rowIndex + colIndex) % 2 === 0 ? "light" : "dark"}`}
              onClick={() =>
              onFieldClick({ boardid: boardid, coords: [rowIndex, colIndex] })
            }>
            {cell?.value && <img src={`/images/${cell.value}.png`} alt={"Test"} />}
            </div>
          ))}
        </div>
      ))}
    </div>
  );
  return (
    <div className="app-container">
      <div className="menu">
        <button onClick={handleUndo} >Undo</button>
        <button onClick={handleRedo} >Redo</button>
        <button onClick={handleRestart} >Restart</button>
      </div>

      <div className="chessboard">
        <TriangleBoard rows={boards.triangleBoard} boardid={3} />
        <SquareBoard rows={boards.squareBoardLeft} boardName="square-board-left" boardid={0} />
        <SquareBoard rows={boards.squareBoardTop} boardName="square-board-top" boardid={1} />
        <SquareBoard rows={boards.squareBoardRight} boardName="square-board-right" boardid={2} />
      </div>
    </div>
  );
};
  
export default Chessboard;

/*
<div className="active-player-panel">
<p>Active Player: {players[activePlayerIndex]}</p>
</div>
*/