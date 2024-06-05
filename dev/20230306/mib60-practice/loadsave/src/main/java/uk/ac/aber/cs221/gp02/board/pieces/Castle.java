package uk.ac.aber.cs221.gp02.board.pieces;

import uk.ac.aber.cs221.gp02.board.Color;

import java.util.ArrayList;
import java.util.List;

public class Castle extends Piece {

    public Castle(Color colour) {
        super(colour);
        this.ID = "C";
    }

    public Castle(){

    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<int[]>();

        // Move right along the row
        for (int i = y + 1; i < 8; i++) {
            if (boardArray[x][i].getPiece() == null) {
                int[] move = {x, i};
                returnArray.add(move);
            } else if (boardArray[x][i].getPiece() != null && boardArray[x][i].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {x, i};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move left along the row
        for (int i = y - 1; i >= 0; i--) {
            if (boardArray[x][i].getPiece() == null) {
                int[] move = {x, i};
                returnArray.add(move);
            } else if (boardArray[x][i].getPiece() != null && boardArray[x][i].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {x, i};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move up along the column
        for (int i = x - 1; i >= 0; i--) {
            if (boardArray[i][y].getPiece() == null) {
                int[] move = {i, y};
                returnArray.add(move);
            } else if (boardArray[i][y].getPiece() != null && boardArray[i][y].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, y};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move down along the column
        for (int i = x + 1; i < 8; i++) {
            if (boardArray[i][y].getPiece() == null) {
                int[] move = {i, y};
                returnArray.add(move);
            } else if (boardArray[i][y].getPiece() != null && boardArray[i][y].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, y};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for Castle at " + x + "," + y);
    }
}
