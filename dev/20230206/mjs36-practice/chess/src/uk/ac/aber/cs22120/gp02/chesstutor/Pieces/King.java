package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

public class King extends Piece {
    private boolean castled = false;

    public King(Player colour) {
        super(colour);
    }

    public boolean hasCastled() {
        return castled;
    }

    public void setCastled() {
        castled = true;
    }

    //add move functions
}
