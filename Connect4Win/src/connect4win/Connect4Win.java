/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4win;

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.GradientPaint;

public class Connect4Win extends JFrame implements Runnable {
    static final int XBORDER = 20;
    static final int YBORDER = 20;
    static final int YTITLE = 25;
    static final int WINDOW_WIDTH = 840;
    static final int WINDOW_HEIGHT = 865;    
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;
	int yeahboi; 
    
    boolean player1Turn;
    final static int NUM_ROWS = 8;
    final static int NUM_COLUMNS = 8;  
    Piece board[][] = new Piece[NUM_ROWS][NUM_COLUMNS];
    int mostRecentRow;
    int mostRecentCol;
    boolean win;
    
    WinInfo winInfo;
    int NUM_CONNECT_WIN = 4;    

    public static void main(String[] args) {
        Connect4Win frame = new Connect4Win();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Connect4Win() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton() ) {
                    
                    int ydelta = getHeight2()/NUM_ROWS;
                    int xdelta = getWidth2()/NUM_COLUMNS;

                    int zcol = 0;
                    int zcolLoc = xdelta;
                    for (int i=0;i<NUM_COLUMNS;i++)
                    {
                        if (zcolLoc*i < e.getX()-getX(0))
                            zcol = i;
                    } 
                    
                    for (int i=NUM_ROWS-1;i>=0;i--)
                    {
                        if (board[i][zcol] == null)
                        {
                            if (player1Turn)
                                board[i][zcol] = new Piece(Color.red);            
                            else
                                board[i][zcol] = new Piece(Color.black);
                            player1Turn = !player1Turn;
                            mostRecentRow = i;
                            mostRecentCol = zcol;
                    
                            break;
                        }
                    }

                    
                }

                if (e.BUTTON3 == e.getButton()) {
                     reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }

        
         
        
        
//Calculate the width and height of each board square.
        int ydelta = getHeight2()/NUM_ROWS;
        int xdelta = getWidth2()/NUM_COLUMNS;
        
        
        
//Draw win.
        
//        if(winInfo.getWin())
//        {
//            if(null != winInfo.getWinDirection())
//            //Vertical
//            switch (winInfo.getWinDirection()) {
//                case VERTICAL:
//                    for (int zi = winInfo.getRow();zi<(winInfo.getRow()+NUM_CONNECT_WIN);zi++)
//                    {
//                        
// 
//                        if (board[zi][winInfo.getColumn()] != null)
//                        {
//                            g.setColor(Color.GRAY);
//                            g.fillRect(getX(winInfo.getColumn()*xdelta),getY(zi*ydelta),xdelta,ydelta);
//                        }
//                        
//                    }
//                    //Horizontal
//                    break;
//                case HORIZONTAL:
//                    for (int zx = winInfo.getColumn();zx<(winInfo.getColumn()+NUM_CONNECT_WIN);zx++)
//                    {
//                        
//                        
//                        if (board[winInfo.getRow()][zx] != null)
//                        {
//                            g.setColor(Color.GRAY);
//                            g.fillRect(getX(zx*xdelta),getY(winInfo.getRow()*ydelta),xdelta,ydelta);
//                        }
//                        
//                    }
//                    //Diag_UP
//                    break;
//            //Diag_DOWN
//                case DIAGONAL_UP:
//                    for (int i = winInfo.getColumn();i<(winInfo.getColumn()+NUM_CONNECT_WIN);i++) // int zi = winInfo.getRow();zi>(winInfo.getRow()-NUM_CONNECT_WIN);zi--)
//                    {
//                        
//                        
//                        
//                        g.setColor(Color.GRAY);
//                        g.fillRect(getX((winInfo.getColumn()+i)*xdelta),getY((winInfo.getRow()-i)*ydelta),xdelta,ydelta);
//                        
//            
//                    }
//                    break;
//                case DIAGONAL_DOWN:
//                    for (int i = winInfo.getColumn();i<(winInfo.getColumn()+NUM_CONNECT_WIN);i++) // int zi = winInfo.getRow();zi>(winInfo.getRow()-NUM_CONNECT_WIN);zi--)
//                    {
//                        
// 
//                        
//                        g.setColor(Color.GRAY);
//                        g.fillRect(getX((winInfo.getColumn()+i)*xdelta),getY((winInfo.getRow()+i)*ydelta),xdelta,ydelta);
//                        
//            
//                    }
//                    break;
//                default:
//                    break;
//        }        
        
//Draw win.
        
        if(winInfo.getWin())
        {
            //Vertical
            if(winInfo.getWinDirection() == WinInfo.WinDirection.VERTICAL)
            for (int zi = winInfo.getRow();zi<(winInfo.getRow()+NUM_CONNECT_WIN);zi++)
            {
            
            
                if (board[zi][winInfo.getColumn()] != null)
                {
                    g.setColor(Color.GRAY); 
                    g.fillRect(getX(winInfo.getColumn()*xdelta),getY(zi*ydelta),xdelta,ydelta);
                }
            
            }
            //Horizontal
            else if(winInfo.getWinDirection() == WinInfo.WinDirection.HORIZONTAL)
            for (int zx = winInfo.getColumn();zx<(winInfo.getColumn()+NUM_CONNECT_WIN);zx++)
            {
            
            
                if (board[winInfo.getRow()][zx] != null)
                {
                    g.setColor(Color.GRAY); 
                    g.fillRect(getX(zx*xdelta),getY(winInfo.getRow()*ydelta),xdelta,ydelta);
                }
            
            }
            //Diag_UP
            else if(winInfo.getWinDirection() == WinInfo.WinDirection.DIAGONAL_UP)
            { 
                
                     for (int i = winInfo.getColumn();i<(winInfo.getColumn()+NUM_CONNECT_WIN);i++) // int zi = winInfo.getRow();zi>(winInfo.getRow()-NUM_CONNECT_WIN);zi--)
                    {
            
            
                        
                        g.setColor(Color.GRAY); 
                        g.fillRect(getX((winInfo.getColumn()+i)*xdelta),getY((winInfo.getRow()-i)*ydelta),xdelta,ydelta);
                        
            
                    }

            }
             //Diag_DOWN
            else if(winInfo.getWinDirection() == WinInfo.WinDirection.DIAGONAL_DOWN)
            { 
                
                     for (int i = winInfo.getColumn();i<(winInfo.getColumn()+NUM_CONNECT_WIN);i++) // int zi = winInfo.getRow();zi>(winInfo.getRow()-NUM_CONNECT_WIN);zi--)
                    {
            
            
                        
                        g.setColor(Color.GRAY); 
                        g.fillRect(getX((winInfo.getColumn()+i)*xdelta),getY((winInfo.getRow()+i)*ydelta),xdelta,ydelta);
                        
            
                    }

            }
        }







        
        
        
        
        
 //draw grid
        g.setColor(Color.black);
        for (int zi = 1;zi<NUM_ROWS;zi++)
        {
            g.drawLine(getX(0),getY(zi*ydelta),
                    getX(getWidth2()),getY(zi*ydelta));
        }
        for (int zi = 1;zi<NUM_COLUMNS;zi++)
        {
            g.drawLine(getX(zi*xdelta),getY(0),
                    getX(zi*xdelta),getY(getHeight2()));
        }
        

//Draw the piece.        
        for (int zi = 0;zi<NUM_ROWS;zi++)
        {
            for (int zx = 0;zx<NUM_COLUMNS;zx++)
            {
                if (board[zi][zx] != null)
                {
                    g.setColor(board[zi][zx].getColor()); 
                
                    int[] xdim = {getX(zx*xdelta),(getX(zx*xdelta)+((getX(xdelta)/2)))-XBORDER/2,(getX(zx*xdelta)+getX(xdelta))-XBORDER,(getX(zx*xdelta)+((getX(xdelta)/2)))-XBORDER/2};
                    int[] ydim = {(getY(zi*ydelta)+getY(ydelta)/2)-YBORDER/2-YTITLE/2,getY(zi*ydelta),(getY(zi*ydelta)+getY(ydelta)/2)-YBORDER/2-YTITLE/2,(getY(zi*ydelta)+getY(ydelta)-YBORDER-YTITLE)};
                    g.fillPolygon(xdim,ydim,xdim.length);   
                    g.setFont(new Font("Broadway",Font.PLAIN,80));
                    g.setColor(Color.GRAY);
                    g.drawString("" + board[zi][zx].getValue(), (getX(zx*xdelta)+((getX(xdelta)/2)))-XBORDER/2-1,((getY(zi*ydelta)+getY(ydelta)/2)-YBORDER/2-YTITLE/2)-1);
                    if(board[zi][zx].getColor()==Color.red) 
                        {
                            g.setColor(Color.black);
                        }
                    else 
                        {
                            g.setColor(Color.white);
                        }
                    
                    g.drawString("" + board[zi][zx].getValue(), (getX(zx*xdelta)+((getX(xdelta)/2)))-XBORDER/2,(getY(zi*ydelta)+getY(ydelta)/2)-YBORDER/2-YTITLE/2);  
                            
//                    g.fillOval(getX(zx*xdelta),getY(zi*ydelta),xdelta,ydelta);
                }
            }
        }
 
        if (win)
        {
            g.setColor(Color.blue);
            g.setFont(new Font("Broadway",Font.PLAIN,80));
            g.drawString("WIN", 160, 200);        
            
        }
            
        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .1;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {


        player1Turn = true;
        for (int zi = 0;zi<NUM_ROWS;zi++)
        {
            for (int zx = 0;zx<NUM_COLUMNS;zx++)
            {
                board[zi][zx] = null;
            }
        }
      
        win = false;

        winInfo = new WinInfo();
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }

            reset();

        }

        win = checkWinWholeBoard();    
    }
    

    public boolean checkWinWholeBoard()
    {
        int numConsecutive = 0;
        Color colorMatch = null;

//Check for horizontal win.        
        for (int row=0;row<NUM_ROWS;row++)
        {
            for (int col=0;col<NUM_COLUMNS;col++)
            {

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, row, (col-(NUM_CONNECT_WIN-1)), WinInfo.WinDirection.HORIZONTAL);
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
             
            }            
            colorMatch = null;
            numConsecutive = 0;        
        }
        
//Check for vertical win.       
        colorMatch = null;
        numConsecutive = 0;        
        
        for (int col=0;col<NUM_COLUMNS;col++)
        {
            for (int row=0;row<NUM_ROWS;row++)
            {

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, (row-(NUM_CONNECT_WIN-1)), col, WinInfo.WinDirection.VERTICAL);
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
                             
                
     
            }            
            colorMatch = null;
            numConsecutive = 0;        
        }    
        
//Check for diagonal win to the right and up.
        colorMatch = null;
        numConsecutive = 0;        
        for (int zrow=0;zrow<NUM_ROWS;zrow++)
        {
            int row = zrow;
            for (int col=0;row>=0;col++)
            {

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, (row+(NUM_CONNECT_WIN-1)), (col-(NUM_CONNECT_WIN-1)), WinInfo.WinDirection.DIAGONAL_UP);
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
             

                row--;
            }
            colorMatch = null;
            numConsecutive = 0;        

        }
        
        colorMatch = null;
        numConsecutive = 0;        
        for (int zcol=1;zcol<NUM_COLUMNS;zcol++)
        {
            int col = zcol;
            for (int row=NUM_ROWS-1;col<NUM_COLUMNS;row--)
            {              

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, (row+(NUM_CONNECT_WIN-1)), (col-(NUM_CONNECT_WIN-1)), WinInfo.WinDirection.DIAGONAL_UP);
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
             
     
                col++;
            }
            colorMatch = null;
            numConsecutive = 0;        

        }
        
//Check for diagonal win to the right and down.
        colorMatch = null;
        numConsecutive = 0;        
        for (int zrow=NUM_ROWS-1;zrow>=0;zrow--)
        {
            int row = zrow;
            for (int col=0;row<NUM_ROWS;col++)
            {

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, (row-(NUM_CONNECT_WIN-1)), (col-(NUM_CONNECT_WIN-1)), WinInfo.WinDirection.DIAGONAL_DOWN);
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
              
     
                row++;
            }
            colorMatch = null;
            numConsecutive = 0;        
            
        }
        
        colorMatch = null;
        numConsecutive = 0;        
        for (int acol=1;acol<NUM_COLUMNS;acol++)
        {
            int col = acol;
            for (int row=0;col<NUM_COLUMNS;row++)
            {              

                if (board[row][col] == null)
                {        
                    numConsecutive = 0;
                    colorMatch = null;
                }
                else if (board[row][col].getColor() == colorMatch)
                {   
                    numConsecutive++;                    
                    
                    if (numConsecutive == NUM_CONNECT_WIN)
                    {
                        winInfo.setWinInfo(true, (row-(NUM_CONNECT_WIN-1)), (col-(NUM_CONNECT_WIN-1)), WinInfo.WinDirection.DIAGONAL_DOWN);                        
                        return (true);
                    }
                }
                else if (board[row][col].getColor() != colorMatch)
                {                                   
                    numConsecutive = 1;
                    colorMatch = board[row][col].getColor();
                }        
                   
                
                col++;
            }
            colorMatch = null;
            numConsecutive = 0;        
            
        }
             
        return (false);
    }    

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE);
    }
    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE+getHeight2());
    }

    public int getWidth2() {
        return (xsize - getX(0) - XBORDER);
    }

    public int getHeight2() {
        return (ysize - getY(0) - YBORDER);
    }
}
                            