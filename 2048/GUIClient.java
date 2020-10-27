import processing.core.PApplet;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class GUIClient extends PApplet {

    public static final int WINDOW_X = 1000;
    public static final int WINDOW_Y = 800;

    public static final int CORNER_RADIUS = 8;
    public static final int TILE_SIDE = 110;
    public static final int TILE_GAP = 15;

    public static final Color COLOR_BACKGROUND = new Color(250, 249, 239);
    public static final Color COLOR_TEXT = new Color(120, 110, 101);
    public static final Color COLOR_BOARD_BACKGROUND = new Color(189, 173, 159);
    public static final Color COLOR_0 = new Color(238, 228, 218, 130); // empty tile
    public static final Color COLOR_2_4_TEXT = COLOR_TEXT;
    public static final Color COLOR_8_2048_TEXT = Color.WHITE;
    public static final Color COLOR_2 = new Color(238, 228, 218);
    public static final Color COLOR_4 = new Color(237, 224, 200);
    public static final Color COLOR_8 = new Color(252, 175, 115);
    public static final Color COLOR_16 = new Color(255, 142, 89);
    public static final Color COLOR_32 = new Color(255, 108, 83);
    public static final Color COLOR_64 = new Color(255, 66, 29);
    public static final Color COLOR_128 = new Color(239, 211, 107);
    public static final Color COLOR_256 = new Color(237, 204, 97);
    public static final Color COLOR_512 = new Color(237, 200, 80);
    public static final Color COLOR_1024 = new Color(237, 197, 63);
    public static final Color COLOR_2048 = new Color(237, 194, 46);
    public static final Color COLOR_ENDING = new Color(250, 249, 239, 80);

    G2048 game;
    final int side;
    Tile[][] board;
    Queue<Movement> moves;
    Queue<Tile> newTiles;

    public GUIClient() {
        GUIClient dis = this;
        side = G2048.SIZE;
        board = new Tile[side][side];
        moves = new LinkedList<>();
        newTiles = new LinkedList<>();

        game = new G2048(side, G2048.WIN_SCORE) {
            @Override
            Pair<Integer> newTile() {
                Pair<Integer> p = super.newTile();
                newTiles.add(new Tile(dis, getBoard()[p.k][p.v], p.k, p.v));
                return p;
            }
        };
    }

    @Override
    public void draw() {
        // There are movements to be drawn, still
        if (!moves.isEmpty()) {
            Movement nxt = moves.peek();
            int fY = nxt.fromY;
            int tY = nxt.toY;
            int fX = nxt.fromX;
            int tX = nxt.toX;

            Tile from = board[fY][fX];
            if (nxt.isMovedOnX(this) && nxt.isMovedOnY(this)) {
                if (nxt.isMerging) {
                    from.setValue(from.value() * 2);
                }

                from.setScreenXY(tY, tX);
                board[fY][fX] = null;
                board[tY][tX] = from;

                moves.poll();
            } else {
                from.moveScreenXY(nxt.yIncr, nxt.xIncr);
            }
            drawBoard();
            drawTiles();
        } else {
            // If there are new tiles not yet drawn
            if (!newTiles.isEmpty()) {
                Tile nt = newTiles.poll();
                board[nt.y()][nt.x()] = nt;
                System.out.println(game);
                drawBoard();
                drawTiles();
            } else {
                // If the game is over
                if (game.isWon() || game.isLost()) {
                    drawEnding();
                    noLoop();
                }
                // no change (no input)
            }
        }
    }

    private void drawTiles() {
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                Tile tile = board[i][j];
                if (tile == null) continue;

                // tile
                rectMode(CORNER);
                fill(tile.color().getRGB(), tile.color().getAlpha());
                noStroke();
                rect(tile.screenX(), tile.screenY(), TILE_SIDE, TILE_SIDE, CORNER_RADIUS);

                // tile text
                fill(tile.txtColor().getRGB());
                textSize(tile.textSize());
                textAlign(CENTER, CENTER);
                text(tile.value(), tile.screenX() + (float) (TILE_SIDE / 2), tile.screenY() + (float) (TILE_SIDE / 2));
            }
        }
    }

    private void drawBoard() {
        // Draw background, board, and empty tiles
        background(COLOR_BACKGROUND.getRGB());
        fill(COLOR_BOARD_BACKGROUND.getRGB());
        int boardSide = TILE_SIDE * game.getSide() + TILE_GAP * (side + 1);
        noStroke();
        rectMode(CORNER);
        rect(boardX(), boardY(), boardSide, boardSide, CORNER_RADIUS);
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                fill(COLOR_0.getRGB(), COLOR_0.getAlpha());
                int y = boardY() + i * GUIClient.TILE_SIDE + GUIClient.TILE_GAP * (i+1);
                int x = boardX() + j * GUIClient.TILE_SIDE + GUIClient.TILE_GAP * (j+1);
                rect(x, y, TILE_SIDE, TILE_SIDE);
            }
        }

        // Draw surrounding texts
        fill(COLOR_TEXT.getRGB());
        noStroke();
        textSize(50);
        textAlign(CENTER, CENTER);
        text("2048", (float) (WINDOW_X / 2), (float) (boardY() / 2));

        textSize(25);
        int y = boardY() + (TILE_GAP+1) * side + TILE_SIDE * side;
        text("Score: " + game.getScore(), (float) (WINDOW_X / 2), (float) ((WINDOW_Y - y)/2) + y);
    }

    private void drawEnding() {
        boolean isWon = game.isWon();
        fill(COLOR_ENDING.getRGB(), COLOR_ENDING.getAlpha());
        noStroke();
        rectMode(CORNER);
        rect(0, 0, WINDOW_X, WINDOW_Y);

        fill(COLOR_TEXT.getRGB());
        noStroke();
        textSize(40);
        textAlign(CENTER, CENTER);
        String text = isWon ? "You won!" : "You lost!";
        text(text, (float) (WINDOW_X / 2), (float) (WINDOW_Y / 2));
    }

    // Used to pass to G2048's movement methods
    private final Consumer<Movement> queueIt = (m) -> moves.add(m);

    @Override
    public void keyReleased() {
        if (key != CODED) return;
        if (!moves.isEmpty() || !newTiles.isEmpty()) return;
        switch (keyCode) {
            case UP:
                game.up(queueIt);
                break;
            case DOWN:
                game.down(queueIt);
                break;
            case LEFT:
                game.left(queueIt);
                break;
            case RIGHT:
                game.right(queueIt);
                break;
        }
    }

    @Override
    public void setup() {
        frameRate(100);
    }

    @Override
    public void settings() {
        size(WINDOW_X, WINDOW_Y);
    }

    public Tile[][] getBoard() {
        return board;
    }

    public int boardX() {
        double half = ((double) side)/2;
        return (int) (WINDOW_X / 2 - TILE_SIDE * half - TILE_GAP * (half + 0.5));
    }

    public int boardY() {
        double half = ((double) side)/2;
        return (int) (WINDOW_Y / 2 - TILE_SIDE * half - TILE_GAP * (half + 0.5));
    }

    public static void main(String[] args) {
        PApplet.main("GUIClient", args);
    }

}
