import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.Objects;
import java.util.function.Consumer;

// https://rosettacode.org/wiki/2048
public class G2048 {

    public static final int SIZE = 4;
    public static final int WIN_SCORE = 2048;

    private final int side;
    private final int winScore;
    private final int[][] board;
    private int score;
    private final Random gen;

    private final Consumer<Movement> mov = (m) -> {};

    public G2048(int side, int winScore) {
        if (side < 3) {
            throw new IllegalArgumentException("The length of the 2048's board cannot be less than 3!");
        }
        this.side = side;
        this.winScore = winScore;
        board = new int[side][side];
        score = 0;
        gen = new Random();

        newTile();
    }

    public G2048() {
        this(SIZE, WIN_SCORE);
    }

    // check each column from left to right
    // start from the top left, check downwards
    public void up() {
        up(mov);
    }

    public void up(Consumer<Movement> movement) {
        move(movement, true, 0, 0, 1);
    }

    // check each column from left to right
    // start from the bottom left, check upwards
    public void down() {
        down(mov);
    }

    public void down(Consumer<Movement> movement) {
        move(movement, true, side-1, 0, -1);
    }

    // check each row from top to bottom
    // start from the top left, check rightwards
    public void left() {
        left(mov);
    }

    public void left(Consumer<Movement> movement) {
        move( movement, false, 0, 1, 0);
    }

    // check each row from top to bottom
    // start from the top right, check leftwards
    public void right() {
        right(mov);
    }

    public void right(Consumer<Movement> movement) {
        move(movement, false, side-1, -1, 0);
    }

    // startIn = starting x or y index, x/y depending on isVertical
    // if startIn = 0, then x/y increment should be positive
    // if startIn = side-1, then x/y increment should be negative
    private void move(Consumer<Movement> movement, boolean isVertical, int startIn, int xIncr, int yIncr) {
        boolean hasChanged = false;
        // loop through rows or columns
        for (int i = 0; i < side; i++) {
            int row;
            int column;

            if (isVertical) {
                 row = startIn;
                 column = i;
            } else {
                row = i;
                column = startIn;
            }

            // loop within each row/column
            outer:
            while (row >= 0 && row < side && column >= 0 && column < side) {

                int tile = board[row][column];
                int y = row + yIncr, x = column + xIncr;

                // inner loop for checking numbered/empty cells
                while (y >= 0 && y < side && x >= 0 && x < side) {
                    int nxtTile = board[y][x];

                    // numbered tile
                    if (tile != 0 && nxtTile != 0) {
                        // different number
                        if (nxtTile != tile) break;
                        // same number
                        else {
                            board[row][column] *= 2;
                            board[y][x] = 0;
                            if (board[row][column] > score) score = board[row][column];
                            movement.accept(new Movement(true, y, x, row, column));
                            hasChanged = true;
                            break;
                        }
                    }

                    // empty tile
                    if (tile == 0 && nxtTile != 0) {
                        board[row][column] = nxtTile;
                        board[y][x] = 0;
                        movement.accept(new Movement(false, y, x, row, column));
                        hasChanged = true;
                        continue outer; // redo the outer loop to check if the newly placed number can be merged
                    }

                    x += xIncr;
                    y += yIncr;
                }
                row += yIncr;
                column += xIncr;
            }

        }
        if (hasChanged) newTile();
    }

    public boolean isWon() {
        return score >= winScore;
    }

    public boolean isLost() {
        if (score >= winScore)
            return false;

        // the board is not full of tiles
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (board[i][j] == 0) return false;
            }
        }

        // check if one can no longer make any move in any direction
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                // horizontal direction
                if (j <= side-2) {
                    if (board[i][j] == board[i][j+1])
                        return false;
                }

                // vertical direction
                if (i <= side-2) {
                    if (board[i][j] == board[i+1][j])
                        return false;
                }
            }
        }

        return true;
    }

    public int getSide() {
        return side;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    Pair<Integer> newTile() {
        int x, y;
        do {
            x = gen.nextInt(side);
            y = gen.nextInt(side);
        } while (board[y][x] != 0);

        if (gen.nextInt(100) >= 10) {
            // generates 2 tile
            board[y][x] = 2;
        } else {
            // generates 4 tile
            board[y][x] = 4;
        }
        return new Pair<>(y, x);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                res.append(board[i][j]).append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }

    static class Pair<K> {
        public final K k;
        public final K v;

        public Pair(K k, K v) {
            this.k = k;
            this.v = v;
        }

        @Override
        public String toString() {
            return "<" + k + "," + v + ">";
        }
    }

}
