/**
 * AkariViewer represents an interface for a player of Akari.
 *
 * @author Lyndon While
 * @version 2021
 */
import java.awt.*;
import java.awt.event.*; 

public class AkariViewer implements MouseListener
{    
    private Akari puzzle;    // the internal representation of the puzzle
    private SimpleCanvas sc; // the display window
    private int tilesize = 50;
    private int offset = tilesize / 25;


    /**
     * Constructor for objects of class AkariViewer.
     * Sets all fields and displays the initial puzzle.
     */
    public AkariViewer(Akari puzzle)
    {
        // TODO 10
        this.puzzle = puzzle;
        sc = new SimpleCanvas("Akari", puzzle.getSize() * tilesize, puzzle.getSize() * tilesize + tilesize * 2, Color.black);
        sc.addMouseListener(this);
        displayPuzzle();
    }
    
    /**
     * Selects from among the provided files in folder Puzzles. 
     * The number xyz selects pxy-ez.txt. 
     */
    public AkariViewer(int n)
    {
        this(new Akari("Puzzles/p" + n / 10 + "-e" + n % 10 + ".txt"));
    }
    
    /**
     * Uses the provided example file on the LMS page.
     */
    public AkariViewer()
    {
        this(77);
    }
    
    /**
     * Returns the internal state of the puzzle.
     */
    public Akari getPuzzle()
    {
        // TODO 9a
        return puzzle;
    }
    
    /**
     * Returns the canvas.
     */
    public SimpleCanvas getCanvas()
    {
        // TODO 9b
        return sc;
    }
    
    /**
     * Displays the initial puzzle; see the LMS page for a suggested layout. 
     */
    private void displayPuzzle()
    {
        // TODO 11
        sc.setFont(new Font("Times", 20, tilesize / 2));
        for (int i = 0; i < puzzle.getSize(); i++){
            for (int j = 0; j < puzzle.getSize(); j++) {
                if(puzzle.getBoard(i,j) == Space.EMPTY){
                    sc.drawRectangle(j * tilesize+offset,i * tilesize+offset, (j+1) * tilesize - offset,(i+1) * tilesize - offset, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.ZERO){
                    sc.drawString(0, j * tilesize + tilesize/3, i * tilesize + tilesize/3 * 2, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.ONE){
                    sc.drawString(1, j * tilesize + tilesize/3, i * tilesize + tilesize/3 * 2, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.TWO){
                    sc.drawString(2, j * tilesize + tilesize/3, i * tilesize + tilesize/3 * 2, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.THREE){
                    sc.drawString(3, j * tilesize + tilesize/3, i * tilesize + tilesize/3 * 2, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.FOUR){
                    sc.drawString(4, j * tilesize + tilesize/3, i * tilesize + tilesize/3 * 2, Color.white);
                }
                if(puzzle.getBoard(i,j) == Space.BULB){
                    sc.drawRectangle(j * tilesize + offset, i * tilesize + offset, (j+1) * tilesize - offset,(i+1) * tilesize - offset, Color.yellow);
                    sc.drawString("\uD83D\uDCA1", j * tilesize + tilesize/4, i * tilesize + tilesize/3 * 2, Color.black);
                }
                if(puzzle.canSeeBulb(i,j) && puzzle.getBoard(i,j) == Space.EMPTY){
                    sc.drawRectangle(j * tilesize+offset,i * tilesize+offset, (j+1) * tilesize - offset,(i+1) * tilesize - offset, Color.yellow);
                }
                
            }
        }
        sc.drawRectangle(offset,puzzle.getSize() * tilesize + offset, puzzle.getSize() * tilesize / 2 - offset ,puzzle.getSize() * tilesize + tilesize * 2 - offset, Color.white);
        sc.drawString("Solved?", puzzle.getSize() * tilesize / 8, (puzzle.getSize() + 1) * tilesize - tilesize / 4, Color.black);
        sc.drawString("Clear", puzzle.getSize() * tilesize / 6 * 4, (puzzle.getSize() + 1) * tilesize - tilesize / 4, Color.white);
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * Updates both puzzle and the display. 
     */
    public void leftClick(int r, int c)
    {
        // TODO 12
        if(puzzle.isLegal(r,c) && puzzle.getBoard(r,c) == Space.EMPTY){
            puzzle.leftClick(r,c);
            displayPuzzle();
        } else if(puzzle.isLegal(r,c) && puzzle.getBoard(r,c) == Space.BULB){
            puzzle.leftClick(r,c);
            displayPuzzle();
        }
    }
    
    // TODO 13
    public void mousePressed (MouseEvent e) {}
    public void mouseClicked (MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
        this.leftClick(e.getY() / tilesize, e.getX() / tilesize);
        
        if(e.getY() >= puzzle.getSize() * tilesize && e.getX()<= puzzle.getSize() * tilesize / 2) {
            sc.setFont(new Font("Times", 20, tilesize / 3));
            sc.drawString(puzzle.isSolution(), tilesize / 3, (puzzle.getSize() + 1) * tilesize - tilesize / 3 + tilesize / 2, Color.black);
        }
        
        if(e.getY() >= puzzle.getSize() * tilesize && e.getX() > puzzle.getSize() * tilesize / 2) {
            puzzle.clear();
            displayPuzzle();
        }
        
    }
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited  (MouseEvent e) {}
}
