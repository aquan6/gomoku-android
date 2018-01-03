package andy_stefan.gomoku_android;

/**
 * Available Game boards. Also stores drawable id of image, and price in store
 */

public enum BoardType {
    CLASSIC(R.drawable.classic_board, 0),
    DRAGON_1(R.drawable.dragon_board, 100);

    private int drawableId, price;

    public int getDrawableId() {
        return drawableId;
    }

    public int getPrice() {
        return price;
    }

    BoardType(int drawableId, int price) {
        this.drawableId = drawableId;
        this.price = price;
    }
}
