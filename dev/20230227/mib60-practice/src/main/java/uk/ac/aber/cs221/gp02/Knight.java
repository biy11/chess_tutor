package uk.ac.aber.cs221.gp02;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;


public class Knight extends Piece {

    public Knight(Color colour) {
        super(colour);
        this.ID = "Kt";
    }

    public Knight(){

    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();

        int[] moveX = {2, 2, 1, 1, -1, -1, -2, -2};
        int[] moveY = {1, -1, 2, -2, 2, -2, 1, -1};

        for (int i = 0; i < moveX.length; i++) {
            int newX = x + moveX[i];
            int newY = y + moveY[i];
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) { //check in board
                if (boardArray[newX][newY].getPiece() == null) {
                    int[] move = {newX, newY};
                    returnArray.add(move);
                } else if (boardArray[newX][newY].getPiece() != null && boardArray[newX][newY].getPiece().getPlayer() == piece.getOppositePlayer()) {
                    int[] move = {newX, newY};
                    returnArray.add(move);
                }
            }
        }

        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for Knight at " + x + "," + y);
    }
}
