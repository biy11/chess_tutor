package uk.ac.aber.cs221.gp02.board.pieces;

import jakarta.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Square {

    @XmlTransient
    private boolean hasPiece = false;

    @XmlElement(name = "x-position")
    private int x;

    @XmlElement(name = "y-position")
    private int y;

    @XmlElement(name = "piece")
    private Piece piece;

    public Square(){
        hasPiece = false;
    }

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        hasPiece = false;
    }

    public Square(int x, int y, Piece piece) {
        this.piece = piece;
        this.x = x;
        this.y = y;
        if(piece != null) hasPiece = true;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        hasPiece = (this.piece) != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHasPiece() {
        return hasPiece;
    }

}
