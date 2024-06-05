package uk.ac.aber.cs22120.gp02.chesstutor.Pieces;

import uk.ac.aber.cs22120.gp02.chesstutor.Square;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Player colour) {
        super(colour);
        this.ID = "P";
    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();

        int direction = (piece.getPlayer() == Piece.Player.WHITE) ? -1 : 1; //ternary operator - learnt in C module - ? TRUE : FALSE

        int newX = x + direction;
        if (newX >= 0 && newX < 8 && boardArray[newX][y].getPiece() == null) {
            int[] move = {newX, y};
            returnArray.add(move);
        }

        int attackNeg = y - 1;
        int attackPos = y + 1;

        if (newX >= 0 && newX < 8) {
            if (attackNeg >= 0 && attackNeg < 8) {
                if (boardArray[newX][attackNeg].getPiece() != null && boardArray[newX][attackNeg].getPiece().getPlayer() == piece.getOppositePlayer()) {
                    int[] move = {newX, attackNeg};
                    returnArray.add(move);
                }
            } else if (attackPos >= 0 && attackPos < 8) {
                if (boardArray[newX][attackPos].getPiece() != null && boardArray[newX][attackPos].getPiece().getPlayer() == piece.getOppositePlayer()) {
                    int[] move = {newX, attackPos};
                    returnArray.add(move);
                }
            }
        }

        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for Pawn at " + x + "," + y);
    }
}
