package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

import uk.ac.aber.cs22120.gp02.chesstutor.Color;
import uk.ac.aber.cs22120.gp02.chesstutor.Player;
import uk.ac.aber.cs22120.gp02.chesstutor.Square;

import java.util.List;

public abstract class Piece { //abstract as nothing should be initialised of this class

    public Color colour;
    public String ID;

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
