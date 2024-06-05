package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

import uk.ac.aber.cs22120.gp02.chesstutor.Color;
import uk.ac.aber.cs22120.gp02.chesstutor.Square;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(Color colour) {
        super(colour);
        this.ID = "Q";
    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();
        Castle tempCastle = new Castle(piece.getPlayer());
        Bishop tempBishop = new Bishop(piece.getPlayer());
        returnArray.addAll(tempCastle.getValidMoves(boardArray,piece,x,y));
        returnArray.addAll(tempBishop.getValidMoves(boardArray,piece,x,y));
        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for Queen at " + x + "," + y);
    }
}
