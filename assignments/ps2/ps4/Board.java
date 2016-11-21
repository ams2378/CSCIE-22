/*
 * Board.java
 * 
 * Computer Science E-22, Harvard University
 */

/**
 * A class that is used to represent a state of an Eight-Puzzle
 * problem -- i.e., a given configuration of the tiles on the board.
 */
public class Board {
    // dimension of the board
    public static final int DIM = 3;
    
    // constants related to the operators -- the four directions in
    // which we "move" the blank -- which corresponds to moving the
    // appropriate tile in the opposite direction.
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final String[] moveString = {"up", "down", "left", "right"};
    
    private byte[][] tiles;               // the locations of the tiles
    private int blankRow, blankCol;       // the location of the blank
    
    /**
     * create a Board object that is a copy of the specified object
     */
    public Board(Board other) {
        tiles = new byte[DIM][DIM];
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                tiles[r][c] = other.tiles[r][c];
                if (other.tiles[r][c] == 0) {
                    blankRow = r; 
                    blankCol = c;
                }
            }
        }
    }
    
    /**
     * create a Board from the specified string.  For example, the
     * goal state would correspond to the string "012345678".
     */
    public Board(String boardString) {
        if (boardString.length() != DIM * DIM)
            throw new IllegalArgumentException("string must have " +
                                               DIM * DIM + " characters");
        
        tiles = new byte[DIM][DIM];
        for (int i = 0; i < DIM * DIM; i++) {
            if (boardString.charAt(i) >= 'a')
                tiles[i / DIM][i % DIM] =
                (byte)(10 + (boardString.charAt(i) - 'a'));
            else
                tiles[i / DIM][i % DIM] = 
                (byte)(boardString.charAt(i) - '0');
            
            if (tiles[i / DIM][i % DIM] == 0) {
                blankRow = i / DIM;
                blankCol = i % DIM;
            }
        }
    }
    
    /**
     * getTile - get the tile at the specified row and column
     */
    public int getTile(int row, int col) {
        return tiles[row][col];
    }
    
    /**
     * moveBlank - used to implement the four operators, moving the
     * blank in one of four directions.  Returns true if the operator
     * could be applied, and false otherwise.
     */
    public boolean moveBlank(int direction) {
        int newRow = blankRow;
        int newCol = blankCol;
        
        switch (direction) {
            case UP:
                newRow = blankRow - 1;
                break;
            case DOWN:
                newRow = blankRow + 1;
                break;
            case LEFT:
                newCol = blankCol - 1;
                break;
            case RIGHT:
                newCol = blankCol + 1;
                break;
            default:
                throw new IllegalArgumentException("direction: " + direction);
        }
        
        // Return false if the move is not possible.
        if (newRow < 0 || newRow >= DIM || newCol < 0 || newCol >= DIM)
            return false;
        
        // Make the move.
        tiles[blankRow][blankCol] = tiles[newRow][newCol];
        tiles[newRow][newCol] = 0;
        blankRow = newRow;
        blankCol = newCol;
        
        return true;
    }
    
    /**
     * stepsToGoal - the heuristic function that is used to estimate
     * the cost-to-goal of the current configuration of the board --
     * i.e., the number of moves that would be needed to get from this
     * board to the goal state.  It returns the sum of the Manhattan
     * distances of the tiles from their positions in the goal state.
     */
    public int stepsToGoal() {
        int estimate = 0;
        
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                int tile = tiles[r][c];
                if (tile != 0) {
                    int rowInGoal = tile / 3;
                    estimate += Math.abs(r - rowInGoal);
                    int colInGoal = tile % 3;
                    estimate += Math.abs(c - colInGoal);
                }
            }
        }
        
        return estimate;
    }
    
    /**
     * equals - is the calling object equivalent to the specified
     * other object?  Two boards are equivalent if all of their tiles
     * are in the same position.
     */
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass())
            return false;
        
        Board otherBoard = (Board)other;
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                if (tiles[r][c] != otherBoard.tiles[r][c])
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * toString - returns a String representation of the board.  For
     * example, the goal state would be represented by the string
     * "    _ 1 2 \n    3 4 5 \n    6 7 8 ".
     */
    public String toString() {
        String str = "";
        
        for (int r = 0; r < DIM; r++) {
            str += "    ";
            for (int c = 0; c < DIM; c++) {
                if (tiles[r][c] == 0)
                    str += "_ ";
                else if (tiles[r][c] >= 10)
                    str += ((char)('a' + (tiles[r][c] - 10)) + " ");
                else
                    str += (tiles[r][c] + " ");
            }
            if (r < DIM - 1)
                str += "\n";
        }
        
        return str;
    }
}