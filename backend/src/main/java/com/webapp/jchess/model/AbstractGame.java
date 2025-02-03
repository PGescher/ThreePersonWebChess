package com.webapp.jchess.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import com.webapp.jchess.model.gamestate.AbstractField;
import com.webapp.jchess.model.moves.AbstractMove;
import com.webapp.jchess.model.moves.StandardMove;
import com.webapp.jchess.model.pieces.King;
import com.webapp.jchess.model.pieces.Piece;

public abstract class AbstractGame {
    public static boolean debugOutput = false;
    
    public ArrayList<Player> allPlayers;
    private int activePlayerIdx = 0;

    private Piece selectedPiece;
    public ArrayList<Piece> activePieces;
    public HashMap<Player.player_IDS,King> kings;

    protected Stack<AbstractMove> moveBackStack = new Stack<AbstractMove>();
    protected Stack<AbstractMove> moveForwardStack = new Stack<AbstractMove>();

    AbstractGame(int playerNum){
        //TODO: Make this more variable and dependant on playerNum?
        allPlayers=new ArrayList<Player>();
        allPlayers.add(new Player(Player.player_IDS.PID1));
        allPlayers.add(new Player(Player.player_IDS.PID2));
        if(playerNum == 3){
            allPlayers.add(new Player(Player.player_IDS.PID3));
        }
        
        kings = new HashMap<>();
        activePieces = new ArrayList<Piece>();
        selectedPiece = null;
    }

    abstract public AbstractField getField(int []coords);
    abstract public void endGame(String str);
    abstract public boolean validCoordinates(int [] coords);
    abstract public boolean isPromotionField(int[] coords);

    public boolean pieceIsSelected(){
        return selectedPiece != null;
    }

    public void removePiece(Piece piece){
        AbstractField fieldRef = piece.field;
        piece.field = null;
        fieldRef.piece = null;
        activePieces.remove(piece);
    }

    public void getDrawablePieces(ArrayList<int[]> coordList, 
    ArrayList<String> pieceList, ArrayList<String> playerList){
        for(Piece p: activePieces){
            coordList.add(p.getGlobalCoords());
            pieceList.add(p.pieceType.name());
            playerList.add(p.player.playerID.name());
        }
    }

    public void getHighlightedFields(ArrayList<int[]> coordList, ArrayList<String> fieldType){
        if(!pieceIsSelected()){ return;}
        coordList.add(selectedPiece.getGlobalCoords());
        fieldType.add("a");
        for(AbstractField field: selectedPiece.validMoves()){
            //Changes coordList to getGlobalCoordinates
            coordList.add(field.getGlobalCoords());
            fieldType.add("p");
        }
    }



    public AbstractMove move(AbstractMove move){
        return this.move(move,true);
    }

    public int selectField(int[] globalCoords){
        //{id, x, y} or {id, x, y, z}
        //0-2 Square, 3 Triangle
        if(!validCoordinates(globalCoords)){return 0;}
        AbstractField field = getField(globalCoords);
        if(field==null){
            System.out.print(("ERROR: no field at coords"));
            System.out.println(globalCoords);
            return 0;} //No Square at coords
        
        if(!pieceIsSelected()){ //We havent selected a piece yet
            //Selected field doesnt contain a piece
            if(field.piece==null){ return 0;}
            //Selected field contains enemy piece
            if(field.piece.player != getActivePlayer()){return 0;}

            if(field.piece.player == getActivePlayer()){
                selectedPiece = field.piece;
                //TODO: Calculate validMoves and save them somewhere
                return 1;
            }
        }else{  //A piece has been previously selected
            //If the field is among our valid moves
            
            if(selectedPiece.validMoves().indexOf(field)!=-1){
                StandardMove stMove = new StandardMove(selectedPiece, field);
                move(stMove);
                selectedPiece=null;

                //switch player
                nextMove();

                //checkmate or stalemate
                switch (activePlayerCheckmate())
                {
                    case 1:
                        endGame("Checkmate for player " + getActivePlayer() );
                        break;
                    case 2:
                        endGame("Stalemate! Draw!");
                        break;
                }
                return 2;
            }
            //We select a new piece
            else if(field.piece != null 
                && field.piece.player == getActivePlayer() 
                && field.piece != selectedPiece){
                selectedPiece = field.piece;
                //TODO: Calculate validMoves and save them somewhere
                return 1;
            }
            return 0;
        }
        
                return -1;
    }

    public boolean undo()
    {
        if(moveBackStack.empty()){return false;}

        AbstractMove lastMove = this.moveBackStack.pop();

        if( lastMove !=null )
        {
            moveForwardStack.push(lastMove);
            lastMove.undo_move();
            
            // if(this.moveHistoryDisplay!=null){
            //     this.moveHistoryDisplay.removeLastMove();
            // }
            this.switchActivePlayerBack();

            
        }
 
        return lastMove !=null;
    }

    public AbstractMove redo()
    {
        if (moveForwardStack.empty()){ return null; }
        AbstractMove move = this.moveForwardStack.pop();

        if ( move!=null )
        {
            move.execute_move();
            this.moveBackStack.push(move);
            // this.addMovetoHistory(move);
            this.nextMove();
        }


        return move;
    }

    ///////////////////////////////////////////////////////////////
    /// // Private methods
    private Player getActivePlayer(){
        return allPlayers.get(activePlayerIdx);
    }

    private void switchActivePlayer()
    {
        activePlayerIdx = (activePlayerIdx+1)%allPlayers.size();
    }

    private void switchActivePlayerBack(){
        activePlayerIdx = (activePlayerIdx-1)%allPlayers.size();
    }

    private void nextMove()
    {
        switchActivePlayer();

        System.out.println("next move, active playerID: " + getActivePlayer().playerID.name() );
    }

    private int activePlayerCheckmate(){
        King king = kings.get(getActivePlayer().playerID);
        return king.isCheckmatedOrStalemated();
    }

    private AbstractMove move(AbstractMove move, boolean clearForwardHistory){
        move.execute_move();
        if (clearForwardHistory)
        {   
            this.moveForwardStack.clear();
            this.moveBackStack.add(move);
        }
        return move;
    }

    

}
