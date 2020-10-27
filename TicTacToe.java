package tictactoe;

public class TicTacToe {

    private final Cell[][] board;
    private int turn;
    
    public TicTacToe() {
        board = new Cell[3][3];
        
        for (Cell[] row : board) {
            for (int i = 0; i < row.length; i++) {
                row[i] = new Cell();
            }
        }
        turn = 1;
    }
    
    public void fillCell(int y, int x) {
        board[y][x].setState(turn);
        turn *= -1;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Cell[] row : board) {
            s.append("-------\n");
            s.append('|');
            
            for (Cell t : row) {
                s.append(t);
                s.append('|');
            }
            s.append('\n');
        }
        s.append("-------\n");
        return s.toString();
    }
    
    public boolean isFilled() {
        for (Cell[] row : board) {
            for (Cell t : row) {
                if (!t.isFilled())
                    return false;
            }
        }
        return true;
    }   
    
    // return true if there is a winner, return false if otherwise
    public boolean isFinished() {
        for (Cell[] row : board) {
            int one = row[0].getState();
            int two = row[1].getState();
            int three = row[2].getState();
            
            if (one != 0 && one == two && two == three)
                return true;
        }
        
        for (int i = 0; i < board.length; i++) {
            int one = board[0][i].getState();
            int two = board[1][i].getState();
            int three = board[2][i].getState();
            
            if (one != 0 && one == two && two == three)
                return true;
        }
        
        if (board[0][0].getState() != 0 
                && board[0][0].getState() == board[1][1].getState() 
                && board[1][1].getState() == board[2][2].getState())
            return true;
        
        if (board[0][2].getState() != 0 
                && board[0][2].getState() == board[1][1].getState()
                && board[1][1].getState() == board[2][0].getState())
            return true;
        
        return false;
    }
    
    public Cell getCell(int y, int x) {
        return board[y][x];
    }
    
}
