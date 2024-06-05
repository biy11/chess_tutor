package uk.ac.aber.cs221.gp02;


import jakarta.xml.bind.annotation.*;

import java.util.List;


/**
 * Piece is given the XMLTransient tag - this way
 * the annotations in Board.java will dictate how
 * the Piece.java subclasses are saved and loaded
 */

@XmlTransient
public abstract class Piece { //abstract as nothing should be initialised of this class

    public Color colour;

    public String ID;

    /**
     * Added an empty constructor to avoid needing a
     * factory method or adapter, as that is more
     * work and very ugly to me. The piece subclasses
     * also have an empty constructor.
     */
    public Piece(){

    }

    public Piece(Color colour) {
        this.colour = colour;
    }

    public Color getPlayer() {
        return colour;
    }

    public Color getOppositePlayer() {
        if (colour == Color.BLACK) {
            return Color.WHITE;
        } else if (colour == Color.WHITE) {
            return Color.BLACK;
        }
        return null;
    }

    public String getID() {
        return ID;
    }

    public abstract List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y);

    public abstract void noValidMoves(int x, int y);
}
