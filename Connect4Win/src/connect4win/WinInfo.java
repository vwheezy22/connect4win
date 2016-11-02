/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4win;

/**
 *
 * @author jyee
 */
public class WinInfo {
    public enum WinDirection {
        HORIZONTAL,VERTICAL,DIAGONAL_DOWN,DIAGONAL_UP
    }
    
    private boolean win;
    private int row;
    private int column;
    private WinDirection direction;
    WinInfo()
    {
        win = false;
    }
    public void setWinInfo(boolean _win,int _row,int _column,WinDirection _direction)
    {
        win = _win;
        row = _row;
        column = _column;
        direction = _direction;
    }
    public boolean getWin()
    {
        return(win);
    }
    public WinDirection getWinDirection()
    {
        return(direction);
    }
    public int getRow()
    {
        return(row);
    }
    public int getColumn()
    {
        return(column);
    }
}
