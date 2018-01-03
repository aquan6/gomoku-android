package andy_stefan.gomoku_android;

/**
 * Available game pieces. Also stores drawable id and price in store
 */

public enum PieceType {
    CLASSIC_WHITE(R.drawable.white_piece, 0),
    CLASSIC_BLACK(R.drawable.black_piece, 0);

    private int drawableId, price;

    public int getDrawableId() {
        return drawableId;
    }

    public int getPrice() {
        return price;
    }

    PieceType(int drawableId, int price) {
        this.drawableId = drawableId;
        this.price = price;
    }
}
