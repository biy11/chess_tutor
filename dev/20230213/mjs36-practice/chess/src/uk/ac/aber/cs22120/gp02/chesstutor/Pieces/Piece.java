package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

import uk.ac.aber.cs22120.gp02.chesstutor.Square;

import java.util.List;

public abstract class Piece { //abstract as nothing should be initialised of this class

    public enum Player {
        WHITE,
        BLACK
    }

    public Player colour;
    public String ID;

    public Piece(Player colour) {
        this.colour = colour;
    }

    public Player getPlayer() {
        return colour;
    }

    public Player getOppositePlayer() {
        if (colour == Player.BLACK) {
            return Player.WHITE;
        } else if (colour == Player.WHITE) {
            return Player.BLACK;
        }
        return null;
    }

    public String getID() {
        return ID;
    }

    public abstract List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y);
    public abstract void noValidMoves(int x, int y);
}
