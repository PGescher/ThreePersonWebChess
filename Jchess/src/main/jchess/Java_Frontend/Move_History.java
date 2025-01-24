
package jchess.Java_Frontend;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.*;

import jchess.backend.Game_twoPlayers;

import java.awt.Dimension;
import java.awt.Rectangle;

/** Class representing the players moves, it's also checking
 * that the moves taken by player are correct.
 * All moves which was taken by current player are saving as List of Strings
 * The history of moves is printing in a table
 * @param game The current game
 */
public class Move_History extends AbstractTableModel
{

    private ArrayList<String> move = new ArrayList<String>();
    private int columnsNum = 3;
    private int rowsNum = 0;
    private String[] names = new String[]
    {
        "White", "Black"
    };
    private MyDefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTable table;
    private boolean enterBlack = false;
    private Game_twoPlayers game;
    

    public Move_History(Game_twoPlayers game)
    {
        super();
        this.tableModel = new MyDefaultTableModel();
        this.table = new JTable(this.tableModel);
        this.scrollPane = new JScrollPane(this.table);
        this.scrollPane.setMaximumSize(new Dimension(100, 100));
        this.table.setMinimumSize(new Dimension(100, 100));
        this.game = game;

        this.tableModel.addColumn(this.names[0]);
        this.tableModel.addColumn(this.names[1]);
        this.addTableModelListener(null);
        this.tableModel.addTableModelListener(null);
        this.scrollPane.setAutoscrolls(true);
    }

    public void draw()
    {
    }

    @Override
    public String getValueAt(int x, int y)
    {
        return this.move.get((y * 2) - 1 + (x - 1));
    }

    @Override
    public int getRowCount()
    {
        return this.rowsNum;
    }

    @Override
    public int getColumnCount()
    {
        return this.columnsNum;
    }

    protected void addRow()
    {
        this.tableModel.addRow(new String[2]);
    }

    protected void addCastling(String move)
    {
        this.move.remove(this.move.size() - 1);//remove last element (move of Rook)
        if (!this.enterBlack)
        {
            this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 1);//replace last value
        }
        else
        {
            this.tableModel.setValueAt(move, this.tableModel.getRowCount() - 1, 0);//replace last value
        }
        this.move.add(move);//add new move (O-O or O-O-O)
    }

    @Override
    public boolean isCellEditable(int a, int b)
    {
        return false;
    }

    /** Method of adding new moves to the table
     * @param str String which in is saved player move
     */
    protected void addMove2Table(String str)
    {
        try
        {
            if (!this.enterBlack)
            {
                this.addRow();
                this.rowsNum = this.tableModel.getRowCount() - 1;
                this.tableModel.setValueAt(str, rowsNum, 0);
            }
            else
            {
                this.tableModel.setValueAt(str, rowsNum, 1);
                this.rowsNum = this.tableModel.getRowCount() - 1;
            }
            this.enterBlack = !this.enterBlack;
            this.table.scrollRectToVisible(table.getCellRect(table.getRowCount() - 1, 0, true));//scroll to down

        }
        catch (java.lang.ArrayIndexOutOfBoundsException exc)
        {
            if (this.rowsNum > 0)
            {
                this.rowsNum--;
                addMove2Table(str);
            }
        }
    }



    public void addMove(String locMove)
    {
        
        this.move.add(locMove);
        this.addMove2Table(locMove);
        this.scrollPane.scrollRectToVisible(new Rectangle(0, this.scrollPane.getHeight() - 2, 1, 1));
    }

    public JScrollPane getScrollPane()
    {
        return this.scrollPane;
    }

    public synchronized void removeLastMove()
    {
        if (this.enterBlack)
        {
            this.tableModel.setValueAt("", this.tableModel.getRowCount() - 1, 0);
            this.tableModel.removeRow(this.tableModel.getRowCount() - 1);

            if (this.rowsNum > 0)
            {
                this.rowsNum--;
            }
        }
        else
        {
            if (this.tableModel.getRowCount() > 0)
            {
                this.tableModel.setValueAt("", this.tableModel.getRowCount() - 1, 1);
            }
        }
        this.move.remove(this.move.size() - 1);
        this.enterBlack = !this.enterBlack;
        
            
    }

}
/*
 * Overriding DefaultTableModel and  isCellEditable method
 * (history cannot be edited by player)
 */

class MyDefaultTableModel extends DefaultTableModel
{

    MyDefaultTableModel()
    {
        super();
    }

    @Override
    public boolean isCellEditable(int a, int b)
    {
        return false;
    }
}