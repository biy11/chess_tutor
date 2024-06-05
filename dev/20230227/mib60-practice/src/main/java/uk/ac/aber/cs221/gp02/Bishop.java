package uk.ac.aber.cs221.gp02;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


public class Bishop extends Piece {

    public Bishop(Color colour) {
        super(colour);
        this.ID = "B";
    }

    public Bishop(){

    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();

        // Move up and right along the diagonal
        for (int i = x - 1, j = y + 1; i >= 0 && j < 8; i--, j++) {
            if (boardArray[i][j].getPiece() == null) {
                int[] move = {i, j};
                returnArray.add(move);
            } else if (boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, j};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move up and left along the diagonal
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (boardArray[i][j].getPiece() == null) {
                int[] move = {i, j};
                returnArray.add(move);
            } else if (boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, j};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move down and right along the diagonal
        for (int i = x + 1, j = y + 1; i < 8 && j < 8; i++, j++) {
            if (boardArray[i][j].getPiece() == null) {
                int[] move = {i, j};
                returnArray.add(move);
            } else if (boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, j};
                returnArray.add(move);
                break;
            } else {
                break;
            }
        }

        // Move down and left along the diagonal
        for (int i = x + 1, j = y - 1; i < 8 && j >= 0; i++, j--) {
            if (boardArray[i][j].getPiece() == null) {
                int[] move = {i, j};
                returnArray.add(move);
            } else if (boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getPlayer() == piece.getOppositePlayer()) {
                int[] move = {i, j};
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
        System.err.println("No Valid Moves for Bishop at " + x + "," + y);
    }

}
