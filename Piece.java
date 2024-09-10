
public class Piece {
    private char color;
    private int x;
    private int y;

    public Piece(char color, int x, int y) {
        
        this.color = color;
        this.x = x;
        this.y = y;
    }


    public char getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
