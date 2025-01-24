package jchess.Java_Frontend;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jchess.JChessApp;
import jchess.backend.Game_twoPlayers;

import java.awt.event.ComponentListener;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class GUI_Game extends JPanel implements MouseListener, ComponentListener{

    private Game_twoPlayers game;
    public GUI_Chessboard gui_chessboard;

    public GUI_Game(){
        this.game = new Game_twoPlayers();
        this.setLayout(null);
        this.addComponentListener(this);
        this.setDoubleBuffered(true);

        //Setup Chessboard GUI.
        gui_chessboard = new GUI_Chessboard(game);
        gui_chessboard.setVisible(true);
        gui_chessboard.setSize(GUI_Chessboard.img_height, GUI_Chessboard.img_widht);
        gui_chessboard.addMouseListener(this);
        gui_chessboard.setLocation(new Point(0, 0));
        this.add(gui_chessboard);

        //Moves History Display
        if(game.moveHistoryDisplay!=null){
            JScrollPane movesHistory = game.moveHistoryDisplay.getScrollPane();
            movesHistory.setSize(new Dimension(180, 350));
            movesHistory.setLocation(new Point(500, 121));
            this.add(movesHistory);
        }
    }

    public void newGame(){
        //dirty hacks starts over here :) 
        //to fix rendering artefacts on first run
        GUI_Game activeGameGUI = JChessApp.jcv.getActiveTabGame();
        if( activeGameGUI != null )
        {
            activeGameGUI.gui_chessboard.resizeChessboard(gui_chessboard.get_height(false));
            activeGameGUI.gui_chessboard.repaint();
            repaint();
        }
        gui_chessboard.repaint();
        this.repaint();
        //dirty hacks ends over here :)
    }

    public boolean undo(){
        return this.game.undo() ;
    }

    public boolean redo(){
        return this.game.redo() != null ;
    }

    @Override
    public void componentHidden(ComponentEvent event) {
        
    }

    @Override
    public void componentMoved(ComponentEvent event) {
        
    }

    @Override
    public void componentResized(ComponentEvent event) {
        int height = this.getHeight() >= this.getWidth() ? this.getWidth() : this.getHeight();
        int chess_height = (int)Math.round( (height * 0.8)/8 )*8;
        gui_chessboard.resizeChessboard((int)chess_height);
        chess_height = gui_chessboard.getHeight();
        if(game.moveHistoryDisplay!=null){
            game.moveHistoryDisplay.getScrollPane().setLocation(new Point(chess_height + 5, 100));
            game.moveHistoryDisplay.getScrollPane().setSize(game.moveHistoryDisplay.getScrollPane().getWidth(), chess_height - 100);
        }
    }

    @Override
    public void componentShown(ComponentEvent event) {
        
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        
    }

    @Override
    public void mouseExited(MouseEvent event) {
        
    }

    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) //right button
        {
            game.undo();
            gui_chessboard.repaint();//repaint for sure
        }
        else if (event.getButton() == MouseEvent.BUTTON2 )
        {
            game.redo();
            gui_chessboard.repaint();;
        }
        else if (event.getButton() == MouseEvent.BUTTON1) //left button
        {
            int x = event.getX();//get X position of mouse
            int y = event.getY();//get Y position of mouse
            int[] localCoords = gui_chessboard.getCoordinates(x, y);
            int[] globalCoords = new int[]{0,localCoords[0],localCoords[1]};
            if(!game.validCoordinates(globalCoords))return;

            int status = game.selectField(localCoords);
            
            switch (status) {
                case 0: // Nothing happened
                    return;
                case 1: // Selected different Piece
                    gui_chessboard.repaint();
                    return;
                case 2: // Made a move
                    gui_chessboard.repaint();
                    return;
                default:
                    System.out.println("ERROR: This should not happen.");
                    return;
            }

            
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        
    }
    
}
