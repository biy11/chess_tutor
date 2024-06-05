package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

import uk.ac.aber.cs22120.gp02.chesstutor.Square;

public abstract class Piece { //abstract as nothing should be initialised of this class

    public enum Player {
        WHITE,
        BLACK
    }

    public Player colour;

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
}
