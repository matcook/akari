/**
 * Akari represents a single puzzle of the game Akari.
 *
 * @author Lyndon While 
 * @version 2021
 */
import java.util.ArrayList;

public class Akari
{
    private String filename; // the name of the puzzle file
    private int size;        // the board is size x size
    private Space[][] board; // the board is a square grid of spaces of various types

    /**
     * Constructor for objects of class Akari. 
     * Creates and initialises the fields with the contents of filename. 
     * The layout of a puzzle file is described on the LMS page; 
     * you may assume the layout is always correct. 
     */
    public Akari(String filename)
    {
        // TODO 3
        FileIO puzzle = new FileIO(filename);
        ArrayList<String> lines = puzzle.getLines();
        this.filename = puzzle.getName();
        size = Integer.parseInt(lines.get(0));
        initialiseBoard();
        for(int i = 1; i < lines.size(); i++){
            fillBlackSquares(lines.get(i), lines.indexOf(lines.get(i)));
        }
    }
    
    private void initialiseBoard() {
        board = new Space[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                board[i][j] = Space.EMPTY;
            }
        }
    }
    
    private void fillBlackSquares(String line, int index) {
        String[] squares = line.split(" ");
        for(String square: squares) {
            if(square.length() > 0){
                int row = parseIndex(square.charAt(0));
                for(int i = 1; i < square.length(); i++) {
                    int column = parseIndex(square.charAt(i));
                    if(index == 1) {this.board[row][column] = Space.BLACK;}
                    if(index == 2) {this.board[row][column] = Space.ZERO;}
                    if(index == 3) {this.board[row][column] = Space.ONE;}
                    if(index == 4) {this.board[row][column] = Space.TWO;}
                    if(index == 5) {this.board[row][column] = Space.THREE;}
                    if(index == 6) {this.board[row][column] = Space.FOUR;}
                }
            }
        }   
    }
    
    /**
     * Uses the example file from the LMS page.
     */
    public Akari()
    {
        this("Puzzles/p7-e7.txt");
    }
    
    /**
     * Returns the name of the puzzle file.
     */
    public String getFilename()
    {
        // TODO 1a
        return filename;
    }
    
    /**
     * Returns the size of the puzzle.
     */
    public int getSize()
    {
        // TODO 1b
        return size;
    }
    
    /**
     * Returns true iff k is a legal index into the board. 
     */
    public boolean isLegal(int k)
    {
        // TODO 5
        if(k >= 0 && k < size){
            return true;
        }
        return false; 
    }
    
    /**
     * Returns true iff r and c are both legal indices into the board. 
     */
    public boolean isLegal(int r, int c)
    {
        // TODO 6
        if(isLegal(r) && isLegal(c)) {
            return true;
        }
        return false;  
    }
    
    /**
     * Returns the contents of the square at r,c if the indices are legal, 
     * o/w throws an illegal argument exception. 
     */
    public Space getBoard(int r, int c)
    {
        // TODO 7
        if(isLegal(r,c)) {
            return board[r][c];
        } else {
            throw new IllegalArgumentException("Invalid board index");
        }
    }
    
    /**
     * Returns the int equivalent of x. 
     * If x is a digit, returns 0 -> 0, 1 -> 1, etc; 
     * if x is an upper-case letter, returns A -> 10, B -> 11, etc; 
     * o/w throws an illegal argument exception. 
     */
    public static int parseIndex(char x)
    {
        // TODO 2
        if(Character.isDigit(x) || Character.isUpperCase(x)) {
            return Character.getNumericValue(x);
        } else {
            throw new IllegalArgumentException("Invalid character.");
        }
    }
    
    /**
     * Performs a left click on the square at r,c if the indices are legal, o/w does nothing. 
     * If r,c is empty, a bulb is placed; if it has a bulb, that bulb is removed.
     */
    public void leftClick(int r, int c)
    {
        // TODO 8
        if(isLegal(r,c)){
            if(getBoard(r,c) == Space.EMPTY) {
                board[r][c] = Space.BULB;
            } else if(getBoard(r,c) == Space.BULB) {
                board[r][c] = Space.EMPTY;
            }
        }
    }
    
    /**
     * Sets all mutable squares on the board to empty.
     */
    public void clear()
    {
        // TODO 4
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if(board[i][j] == Space.BULB) {
                    board[i][j] = Space.EMPTY;
                }
            }
        }
    }
    
    /**
     * Returns the number of bulbs adjacent to the square at r,c. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public int numberOfBulbs(int r, int c)
    {
        // TODO 14
        if(this.isLegal(r,c)) {
            int numBulbs = 0;
            if(this.isLegal(r-1,c) && this.getBoard(r-1,c) == Space.BULB){ numBulbs++; }
            if(this.isLegal(r+1,c) && this.getBoard(r+1,c) == Space.BULB){ numBulbs++; }
            if(this.isLegal(r,c-1) && this.getBoard(r,c-1) == Space.BULB){ numBulbs++; }
            if(this.isLegal(r,c+1) && this.getBoard(r,c+1) == Space.BULB){ numBulbs++; }
            return numBulbs;
        } else {
            throw new IllegalArgumentException("Invalid coordinates");
        }
    }
    
    /**
     * Returns true iff the square at r,c is lit by a bulb elsewhere. 
     * Throws an illegal argument exception if r,c are illegal coordinates.
     */
    public boolean canSeeBulb(int r, int c)
    {
        // TODO 15
        if(!isLegal(r,c)){
            throw new IllegalArgumentException("Invalid coordinates");
        }
   
        for(int i = 1; i < size; i++){
            if(!isLegal(r+i,c)) { break; }
            if(getBoard(r+i,c) == Space.BULB){ 
                return true;
            }
            if(getBoard(r+i,c) != Space.EMPTY && getBoard(r+i,c) != Space.BULB) {
                break;
            }
        }
        for(int i = 1; i < size; i++){
            if(!isLegal(r-i,c)) { break; }
            if(getBoard(r-i,c) == Space.BULB){ 
                return true;
            }
            if(getBoard(r-i,c) != Space.EMPTY && getBoard(r-i,c) != Space.BULB) {
                break;
            }
        }
        for(int i = 1; i < size; i++){
            if(!isLegal(r,c+i)) { break; }
            if(getBoard(r,c+i) == Space.BULB){ 
                return true;
            }
            if(getBoard(r,c+i) != Space.EMPTY && getBoard(r,c+i) != Space.BULB) {
                break;
            }
        }
        for(int i = 1; i < size; i++){
            if(!isLegal(r,c-i)) { break; }
            if(getBoard(r,c-i) == Space.BULB){ 
                return true;
            }
            if(getBoard(r,c-i) != Space.EMPTY && getBoard(r,c-i) != Space.BULB) {
                break;
            }
        }

       return false;
    }
    
    /**
     * Returns an assessment of the state of the puzzle, either 
     * "Clashing bulb at r,c", 
     * "Unlit square at r,c", 
     * "Broken number at r,c", or
     * three ticks, as on the LMS page. 
     * r,c must be the coordinates of a square that has that kind of error. 
     * If there are multiple errors on the board, you may return any one of them. 
     */
    public String isSolution()
    {
        // TODO 16
        String message = "\u2713\u2713\u2713";
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if(!canSeeBulb(i,j) && getBoard(i,j) == Space.EMPTY){ message = "Unlit square at " + i + "," + j ; }
                if(numberOfBulbs(i,j) != 0 && getBoard(i,j) == Space.ZERO){ message = "Broken number at " + i + "," + j;}
                if(numberOfBulbs(i,j) != 1 && getBoard(i,j) == Space.ONE){ message = "Broken number at " + i + "," + j;}
                if(numberOfBulbs(i,j) != 2 && getBoard(i,j) == Space.TWO){ message = "Broken number at " + i + "," + j;}
                if(numberOfBulbs(i,j) != 3 && getBoard(i,j) == Space.THREE){ message = "Broken number at " + i + "," + j;}
                if(numberOfBulbs(i,j) != 4 && getBoard(i,j) == Space.FOUR){ message = "Broken number at " + i + "," + j;}
                if(getBoard(i,j) == Space.BULB && canSeeBulb(i,j)){ message = "Clashing bulb at " + i + "," + j;}
            }
        }
        
        return message;
    }
}