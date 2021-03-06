class Movement {

    public static final int incrScale = 70; // need to be less than TILE_GAP + TILE_SIDE

    final boolean isMerging;
    final int fromY;
    final int fromX;
    final int toY;
    final int toX;

    final int yIncr;
    final int xIncr;

    Movement(boolean isMerging, int fromY, int fromX, int toY, int toX) {
        this.isMerging = isMerging;
        this.fromY = fromY;
        this.fromX = fromX;
        this.toY = toY;
        this.toX = toX;
        this.yIncr = incrScale * Integer.compare((toY - fromY), 0);
        this.xIncr = incrScale * Integer.compare((toX - fromX), 0);
    }

    // returns true if the tile has been moved to or over the intended place on the x axis
    boolean isMovedOnX(GUIClient client) {
        Tile from = client.getBoard()[fromY][fromX];
        float fromSX = from.screenX();
        float toSX = Tile.screenX(client, toX);

        int difX = toX - fromX; // difference of x in tile count

        if (difX >= 0) return fromSX >= toSX;
        else return fromSX <= toSX;
    }

    // returns true if the tile has been moved to or over the intended place on the y axis
    boolean isMovedOnY(GUIClient client) {
        Tile from = client.getBoard()[fromY][fromX];
        float fromSY = from.screenY();
        float toSY = Tile.screenY(client, toY);

        int difY = toY - fromY;

        if (difY >= 0) return fromSY >= toSY;
        else return fromSY <= toSY;
    }

}
