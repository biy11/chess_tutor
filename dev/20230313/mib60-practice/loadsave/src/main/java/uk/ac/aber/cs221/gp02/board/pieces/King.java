package uk.ac.aber.cs221.gp02.board.pieces;

import uk.ac.aber.cs221.gp02.board.Color;

import java.util.ArrayList;
import java.util.List;


public class King extends Piece {
    private boolean castled = false;

    public King(Color colour) {
        super(colour);
        this.ID = "K";
    }

    public King(){

    }

    @Override
    public List<int[]> getValidMoves(Square[][] boardArray, Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<int[]>();

        int lowX = x - 1;
        int highX = x + 1;
        int lowY = y - 1;
        int highY = y + 1;

        if (lowX < 0) {
            lowX = 0;
        }
        if (lowY < 0) {
            lowY = 0;
        }
        if (highX > 8-1) {
            highX = 8-1;
        }
        if (highY > 8-1) {
            highY = 8-1;
        }

        for (int i = lowX; i <= highX; i++) {
            for (int j = lowY; j <= highY; j++) {
                if (i == x && j == y) {
                    continue;
                } else {
                    if (boardArray[i][j].getPiece() == null) {
                        int[] move = {i, j};
                        returnArray.add(move);
                    } else if (boardArray[i][j].getPiece() != null && boardArray[i][j].getPiece().getPlayer() == piece.getOppositePlayer()) {
                        int[] move = {i, j};
                        returnArray.add(move);
                    }
                }
            }
        }
        return returnArray;
    }

    @Override
    public void noValidMoves(int x, int y) {
        System.err.println("No Valid Moves for King at " + x + "," + y);
    }

    public boolean hasCastled() {
        return castled;
    }

    public void setCastled() {
        castled = true;
    }

    //add move functions
}
