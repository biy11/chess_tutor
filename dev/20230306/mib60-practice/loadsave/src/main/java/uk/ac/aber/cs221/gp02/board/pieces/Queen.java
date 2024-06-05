package uk.ac.aber.cs221.gp02.board.pieces;

import uk.ac.aber.cs221.gp02.board.Color;

import java.util.ArrayList;
import java.util.List;


public class Queen extends Piece {

    public Queen(Color colour) {
        super(colour);
        this.ID = "Q";
    }

    public Queen(){}

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
