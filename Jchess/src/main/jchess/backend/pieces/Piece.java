package jchess.backend.pieces;
import java.util.ArrayList;

import jchess.backend.AbstractGame;
import jchess.backend.Player;
import jchess.backend.Player.player_IDS;
import jchess.backend.gamestate.AbstractField;
import jchess.backend.gamestate.Square;


public abstract class Piece
{
    public enum typesOfPieces{
        Pawn, Rook, Knight, Bishop, King, Queen
    }

    public boolean wasMoved = false;
    public AbstractGame game; // <-- this relations isn't in class diagram, but it's necessary :/
    public AbstractField field;
    public Player player;
    public typesOfPieces pieceType;
    public String symbol;
    // public String imageName;
    protected ArrayList<movementVector> movementOptions;

    public Piece(AbstractGame game, Player player, typesOfPieces _pieceType)
    {
        this.game = game;
        this.player = player;
        this.pieceType = _pieceType;

        //Set .image
        // imageName = _pieceType.name();
        // if(player.playerID==player_IDS.PID1) {imageName +="-W.png"; }
        // else{imageName +="-B.png"; }

        movementOptions  = new ArrayList<>();

    }

    // public void specialMoves(ArrayList<Square> validMoves){};

    public ArrayList<Square> possibleMoves(){
        ArrayList<Square> allMoves = new ArrayList<Square>();
        
        for(movementVector moveVec: movementOptions){
            recursiveDirectionCheck(allMoves, this,(Square) this.field, moveVec,0);
        }

        // this.specialMoves(allMoves);

        return allMoves;
    }

    public int [] getGlobalCoords(){
        return this.field.getGlobalCoords();
    }
    
    public ArrayList<Square> validMoves()
    {
        ArrayList<Square> allMoves = possibleMoves();

        //Find relevant king
        King relevantKing = game.kings.get(this.player.playerID);

        //Collect valid moves
        ArrayList<Square> validMoves = new ArrayList<Square>();
        for(Square move: allMoves){
            if(relevantKing.willBeSafeAfterMove((Square)this.field, move)){
                validMoves.add(move);
            }
        }
        return validMoves;
    }

    public typesOfPieces getType()
    {
        return this.pieceType;
    }

    public static void recursiveDirectionCheck(ArrayList<Square> validMoves,Piece piece,
    AbstractField _lastSquare,movementVector moveVec,int stepCounter) {
        if(!(_lastSquare instanceof Square))return;
        Square lastSquare = (Square)_lastSquare;

        if(lastSquare==null){
            System.err.println("ERROR: Square should not be null!");
            return;
        }
        //if no steps are left or figure would leave the gameboard
        int boardIdx = lastSquare.getGlobalCoords()[0];
        int newX = lastSquare.getLocalCoords()[0]+moveVec.movementVector_X;
        int newY = lastSquare.getLocalCoords()[1] +moveVec.movementVector_Y;
        int[] globalCoords = new int[]{boardIdx, newX, newY};
        if(moveVec.maxSteps<=stepCounter || !piece.game.validCoordinates(globalCoords)){return;}
        AbstractField targetField = piece.game.getField(globalCoords);

        //Collision with own figure
        if (targetField.piece != null && targetField.piece.player == piece.player){return;}

        //If Square is empty or contains enemy piece
        if(targetField.piece == null){
            if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.REQUIRED){        
                validMoves.add((Square)targetField);
            }
            recursiveDirectionCheck(validMoves, piece, targetField,moveVec,stepCounter+1);
        }else if (targetField.piece.player != piece.player) {
            // check if enemy piece can be taken
            if(moveVec.takeEnemy!=movementVector.takeEnemyPiece.IMPOSSIBLE){        
                validMoves.add((Square)targetField);
            }
        }
    }
}
