import java.awt.*;

// Wrapper class for each tile in the GUIClient
class Tile {

    private final GUIClient client;
    private int value;
    private int y;
    private int x;
    private float screenY;
    private float screenX;
    private Color color;
    private Color txtColor;
    private int textSize;

    Tile(GUIClient client, int value, int y, int x) {
        this.client = client;
        setValue(value);
        setScreenXY(y, x);
    }

    void setScreenXY(int y, int x) {
        this.y = y;
        this.x = x;
        screenY = screenY(client, y);
        screenX = screenX(client, x);
    }

    void moveScreenXY(float YIncr, float XIncr) {
        this.screenY += YIncr;
        this.screenX += XIncr;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }

    float screenY() {
        return screenY;
    }

    float screenX() {
        return screenX;
    }

    void setValue(int value) {
        this.value = value;
        this.color = tileColor(value);
        this.txtColor = tileTextColor(value);
        this.textSize = textSize(value);
    }

    int value() {
        return value;
    }

    Color color() {
        return color;
    }

    Color txtColor() {
        return txtColor;
    }

    public int textSize() {
        return textSize;
    }

    static float screenX(GUIClient client, int x) {
        return client.boardX() + x * GUIClient.TILE_SIDE + GUIClient.TILE_GAP * (x+1);
    }

    static float screenY(GUIClient client, int y) {
        return client.boardY() + y * GUIClient.TILE_SIDE + GUIClient.TILE_GAP * (y+1);
    }

    static Color tileColor(int value) {
        switch (value) {
            case 0: return GUIClient.COLOR_0;
            case 2: return GUIClient.COLOR_2;
            case 4: return GUIClient.COLOR_4;
            case 8: return GUIClient.COLOR_8;
            case 16: return GUIClient.COLOR_16;
            case 32: return GUIClient.COLOR_32;
            case 64: return GUIClient.COLOR_64;
            case 128: return GUIClient.COLOR_128;
            case 256: return GUIClient.COLOR_256;
            case 512: return GUIClient.COLOR_512;
            case 1024: return GUIClient.COLOR_1024;
            default: // 2048 or above
                return GUIClient.COLOR_2048;
        }
    }

    static Color tileTextColor(int value) {
        switch (value) {
            case 0: return GUIClient.COLOR_0;
            case 2:
            case 4: return GUIClient.COLOR_2_4_TEXT;
            default: // 8~2048 or above
                return GUIClient.COLOR_8_2048_TEXT;
        }
    }

    static int textSize(int value) {
        switch (value) {
            case 0: return 0;
            case 2:
            case 4:
            case 8:
            case 16:
            case 32:
            case 64:
                return 50;
            case 128:
            case 256:
            case 512:
                return 40;
            case 1024:
            case 2048:
                return 30;
        }
        if (value >= 10000)
            return 20;
        else return 30;
    }

}
