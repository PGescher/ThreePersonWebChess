package jchess.Java_Frontend;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JPanel;

import jchess.backend.Game_twoPlayers;

public class GUI_Chessboard extends JPanel{

    private static final Image orgImage = Image_Loader.getImage("chessboard.png");//image of chessboard
    private static Image image = orgImage;//image of chessboard
    private static final Image org_sel_square = Image_Loader.getImage("sel_square.png");//image of highlited square
    private static Image sel_square = org_sel_square;//image of highlited square
    private static final Image org_able_square = Image_Loader.getImage("able_square.png");//image of square where piece can go
    private static Image able_square = org_able_square;//image of square where piece can go
    public static final int img_x = 5;//image x position (used in JChessView class!)
    public static final int img_y = img_x;//image y position (used in JChessView class!)
    public static final int img_widht = 480;//image width
    public static final int img_height = img_widht;//image height

    private Image upDownLabel = null;
    private Image LeftRightLabel = null;
    private Point topLeft = new Point(0, 0);
    private float square_height;//height of square
    private Game_twoPlayers game;
    
    public GUI_Chessboard(Game_twoPlayers game){
        this.square_height = img_height / 8;//we need to devide to know height of field
        this.setDoubleBuffered(true);
        this.drawLabels((int) this.square_height);
        this.game = game;
    }

    /**
     * Method to draw Chessboard and their elements (pieces etc.)
     * @deprecated 
     */
    public void draw()
    {
        this.getGraphics().drawImage(image, this.getTopLeftPoint().x, this.getTopLeftPoint().y, null);//draw an Image of chessboard
        this.drawLabels();
        this.repaint();
    }/*--endOf-draw--*/


    /** method to get reference to square from given x and y integeres
     * @param x x position on chessboard
     * @param y y position on chessboard
     * @return reference to searched square
     */
    public int[] getCoordinates(int x, int y)
    { 
        if ((x > this.get_height()) || (y > this.get_widht())) //test if click is out of chessboard
        {
            System.out.println("click out of chessboard.");
            return null;
        }
        
        {
            x -= this.upDownLabel.getHeight(null);
            y -= this.upDownLabel.getHeight(null);
        }
        double square_x = x / square_height;//count which field in X was clicked
        double square_y = y / square_height;//count which field in Y was clicked

        if (square_x > (int) square_x) //if X is more than X parsed to Integer
        {
            square_x = (int) square_x + 1;//parse to integer and increment
        }
        if (square_y > (int) square_y) //if X is more than X parsed to Integer
        {
            square_y = (int) square_y + 1;//parse to integer and increment
        }
        
        int[] globalCoords = new int[]{0,(int) square_x - 1,(int) square_y - 1};
        System.out.println("square_x: " + square_x + " square_y: " + square_y + " \n"); //4tests
        if(this.game.validCoordinates(globalCoords)){
            return globalCoords;
        }else{
            return null;
        }
    }
    
    public int get_widht()
    {
        return this.get_widht(false);
    }
    
    public int get_height()
    {
        return this.get_height(false);
    }


    public int get_widht(boolean includeLables)
    {
        return this.getHeight();
    }/*--endOf-get_widht--*/


    public int get_height(boolean includeLabels)
    {
        
        {
            return image.getHeight(null) + upDownLabel.getHeight(null);
        }
        
    }/*--endOf-get_height--*/


    public int get_square_height()
    {
        int result = (int) this.square_height;
        return result;
    }

    /**
     * Annotations to superclass Game updateing and painting the crossboard
     */
    @Override
    public void update(Graphics g)
    {
        repaint();
    }

    public Point getTopLeftPoint()
    {
        
        {
            return new Point(this.topLeft.x + this.upDownLabel.getHeight(null), this.topLeft.y + this.upDownLabel.getHeight(null));
        }
        
    }

    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Point topLeftPoint = this.getTopLeftPoint();
        
        //Draw basic Chessboard
        {
            if(topLeftPoint.x <= 0 && topLeftPoint.y <= 0) 
            {
                this.drawLabels();
            }
            g2d.drawImage(this.upDownLabel, 0, 0, null);
            g2d.drawImage(this.upDownLabel, 0, image.getHeight(null) + topLeftPoint.y, null);
            g2d.drawImage(this.LeftRightLabel, 0, 0, null);
            g2d.drawImage(this.LeftRightLabel, image.getHeight(null) + topLeftPoint.x, 0, null);
        }
        g2d.drawImage(image, topLeftPoint.x, topLeftPoint.y, null);
        
        //Draw pieces
        {
            ArrayList<int[]> coordList = new ArrayList<>(); 
            ArrayList<String> pieceList = new ArrayList<>(); 
            ArrayList<String> playerList = new ArrayList<>();
            game.getDrawablePieces(coordList,pieceList,playerList);
            for(int i=0;i<coordList.size();i++){
                String imgName = pieceList.get(i);
                if("PID1".equals(playerList.get(i))) {imgName +="-W.png"; }
                else{imgName +="-B.png"; }
                int[] localCoords = Arrays.copyOfRange(coordList.get(i), 1, coordList.get(i).length);
                this.drawPiece(g,localCoords,imgName);//draw image of Piece
            }
        }
        
        //Draw Active Square and possible moves
        if(game.pieceIsSelected()){
            ArrayList<int[]> coordList = new ArrayList<>(); 
            ArrayList<String> fieldList = new ArrayList<>();
            game.getHighlightedFields(coordList, fieldList);

            for(int i=0;i<coordList.size();i++){
                int[] localCoords = Arrays.copyOfRange(coordList.get(i), 1, coordList.get(i).length);
                if(fieldList.get(i).equals("a")){
                    g2d.drawImage(sel_square, 
                    (localCoords[0] * (int) square_height) + topLeftPoint.x,
                    (localCoords[1] * (int) square_height) + topLeftPoint.y, null);//draw image of selected square
                }
                else if(fieldList.get(i).equals("p")){
                    g2d.drawImage(able_square, 
                    (localCoords[0] * (int) square_height) + topLeftPoint.x,
                    (localCoords[1] * (int) square_height) + topLeftPoint.y, null);
                }
            }
        }
    }/*--endOf-paint--*/


    public void resizeChessboard(int height)
    {
        BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics g = resized.createGraphics();
        g.drawImage(orgImage, 0, 0, height, height, null);
        g.dispose();
        image = resized.getScaledInstance(height, height, 0);
        this.square_height = (float) (height / 8);
        
        {
            height += 2 * (this.upDownLabel.getHeight(null));
        }
        this.setSize(height, height);

        resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(org_able_square, 0, 0, (int) square_height, (int) square_height, null);
        g.dispose();
        able_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);

        resized = new BufferedImage((int) square_height, (int) square_height, BufferedImage.TYPE_INT_ARGB_PRE);
        g = resized.createGraphics();
        g.drawImage(org_sel_square, 0, 0, (int) square_height, (int) square_height, null);
        g.dispose();
        sel_square = resized.getScaledInstance((int) square_height, (int) square_height, 0);
        this.drawLabels();
    }

    protected void drawLabels()
    {
        this.drawLabels((int) this.square_height);
    }

    protected final void drawLabels(int square_height)
    {
        //BufferedImage uDL = new BufferedImage(800, 800, BufferedImage.TYPE_3BYTE_BGR);
        int min_label_height = 20;
        int labelHeight = (int) Math.ceil(square_height / 4);
        labelHeight = (labelHeight < min_label_height) ? min_label_height : labelHeight;
        int labelWidth =  (int) Math.ceil(square_height * 8 + (2 * labelHeight)); 
        BufferedImage uDL = new BufferedImage(labelWidth + min_label_height, labelHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);

        uDL2D.fillRect(0, 0, labelWidth + min_label_height, labelHeight);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));
        int addX = (square_height / 2);
        
        {
            addX += labelHeight;
        }

        String[] letters =
        {
            "a", "b", "c", "d", "e", "f", "g", "h"
        };
        
        
        for (int i = 1; i <= letters.length; i++)
        {
            uDL2D.drawString(letters[i - 1], (square_height * (i - 1)) + addX, 10 + (labelHeight / 3));
        }
        
        uDL2D.dispose();
        this.upDownLabel = uDL;

        uDL = new BufferedImage(labelHeight, labelWidth + min_label_height, BufferedImage.TYPE_3BYTE_BGR);
        uDL2D = (Graphics2D) uDL.createGraphics();
        uDL2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        uDL2D.setColor(Color.white);
        //uDL2D.fillRect(0, 0, 800, 800);
        uDL2D.fillRect(0, 0, labelHeight, labelWidth + min_label_height);
        uDL2D.setColor(Color.black);
        uDL2D.setFont(new Font("Arial", Font.BOLD, 12));

        for (int i = 1; i <= 8; i++){
            uDL2D.drawString(new Integer(i).toString(), 3 + (labelHeight / 3), (square_height * (i - 1)) + addX);
        }
        uDL2D.dispose();
        this.LeftRightLabel = uDL;
    }

    /* Method to draw piece on chessboard
     * @graph : where to draw
     */
    public final void drawPiece(Graphics g, int[] localCoords, String imgName)
    {
        try
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Point topLeft = this.getTopLeftPoint();
            int height = this.get_square_height();
            int x = (localCoords[0] * height) + topLeft.x;
            int y = (localCoords[1] * height) + topLeft.y;
            
            Image tempImage = Image_Loader.getImage(imgName);
            if (tempImage != null && g != null)
            {
                
                float addX = (height - tempImage.getWidth(null)) / 2;
                float addY = (height - tempImage.getHeight(null)) / 2;
                BufferedImage resized = new BufferedImage(height, height, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D imageGr = (Graphics2D) resized.createGraphics();
                imageGr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                imageGr.drawImage(tempImage, 0, 0, height, height, null);
                imageGr.dispose();
                tempImage = resized.getScaledInstance(height, height, 0);
                g2d.drawImage(tempImage, x, y, null);
            }
            else
            {
                System.out.println("image is null!");
            }

        }
        catch (java.lang.NullPointerException exc)
        {
            System.out.println("Something wrong when painting piece: " + exc.getMessage());
        }
    }
}
