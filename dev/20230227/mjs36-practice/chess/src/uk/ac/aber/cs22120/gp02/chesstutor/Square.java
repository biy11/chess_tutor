package uk.ac.aber.cs22120.gp02.chesstutor;

import uk.ac.aber.cs22120.gp02.chesstutor.Pieces.Piece;

public class Square {

    private Piece piece;
    private boolean hasPiece;
    private int x;
    private int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        hasPiece = false;
    }

    public Square(int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        hasPiece = true;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHasPiece() {
        return hasPiece;
    }

}
