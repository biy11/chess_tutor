package uk.ac.aber.cs22120.gp02.chesstutor;

import uk.ac.aber.cs22120.gp02.chesstutor.Pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public Square[][] boardArray; //2d array of squares

    public Board() {
        this.boardArray = new Square[8][8];
        this.boardReset2(); //constructor can use same code as the reset
    }

    public void boardReset2() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }

        //boardArray[0][4] = new Square(0, 4, new Castle(Piece.Player.WHITE));
        boardArray[0][0] = new Square(0, 0, new Pawn(Piece.Player.BLACK));
        boardArray[1][0] = new Square(1, 0, new Pawn(Piece.Player.WHITE));
        boardArray[1][1] = new Square(1, 1, new Pawn(Piece.Player.WHITE));
    }

    public Square getSquare(int x, int y) throws Exception {
        try {

        }
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new Exception("Index out of bounds");
        }

        return boardArray[x][y];
    }

    public void boardReset() {

        boardArray[0][0] = new Square(0, 0, new Castle(Piece.Player.WHITE));
        boardArray[0][7] = new Square(0, 7, new Castle(Piece.Player.WHITE));
        boardArray[0][1] = new Square(0, 1, new Knight(Piece.Player.WHITE));
        boardArray[0][6] = new Square(0, 6, new Knight(Piece.Player.WHITE));
        boardArray[0][2] = new Square(0, 2, new Bishop(Piece.Player.WHITE));
        boardArray[0][5] = new Square(0, 5, new Bishop(Piece.Player.WHITE));
        boardArray[0][3] = new Square(0, 3, new Queen(Piece.Player.WHITE));
        boardArray[0][4] = new Square(0, 4, new King(Piece.Player.WHITE));

        boardArray[7][0] = new Square(7, 0, new Castle(Piece.Player.BLACK));
        boardArray[7][7] = new Square(7, 7, new Castle(Piece.Player.BLACK));
        boardArray[7][1] = new Square(7, 1, new Knight(Piece.Player.BLACK));
        boardArray[7][6] = new Square(7, 6, new Knight(Piece.Player.BLACK));
        boardArray[7][2] = new Square(7, 2, new Bishop(Piece.Player.BLACK));
        boardArray[7][5] = new Square(7, 5, new Bishop(Piece.Player.BLACK));
        boardArray[7][3] = new Square(7, 3, new Queen(Piece.Player.BLACK));
        boardArray[7][4] = new Square(7, 4, new King(Piece.Player.BLACK));

        for (int i = 0; i <= 7; i ++) {
            boardArray[1][i] = new Square(1, i, new Pawn(Piece.Player.WHITE));
            boardArray[6][i] = new Square(1, i, new Pawn(Piece.Player.BLACK));
        }

        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                boardArray[i][j] = new Square(i, j);
            }
        }
    }

    public void printBoard() throws Exception {
        String[][] array = new String[8][8];
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                Piece piece = getSquare(i,j).getPiece();
                if (piece == null) {
                    array[i][j] = " ";
                } else {
                    if (piece instanceof Bishop) {
                        array[i][j] = "B";
                    } else if (piece instanceof Castle) {
                        array[i][j] = "C";
                    } else if (piece instanceof King) {
                        array[i][j] = "K";
                    } else if (piece instanceof Knight) {
                        array[i][j] = "Kt";
                    } else if (piece instanceof Pawn) {
                        array[i][j] = "P";
                    } else if (piece instanceof Queen) {
                        array[i][j] = "Q";
                    }
                }
            }
        }
        System.out.println(Arrays.deepToString(array).replace("],", "],\n"));
    }

    public List<int[]> possibleMoves(Square s1) {
        //blank
        Piece piece = s1.getPiece();
        int x = s1.getX();
        int y = s1.getY();

        List<int[]> returnArray = new ArrayList<int[]>();

        if (piece instanceof King) {
            returnArray = possibleKingMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for King at " + x + "," + y);
            }
        } else if (piece instanceof Castle) {
            returnArray = possibleCastleMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for Castle at " + x + "," + y);
            }
        } else if (piece instanceof Bishop) {
            returnArray = possibleBishopMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for Bishop at " + x + "," + y);
            }
        } else if (piece instanceof Queen) {
            returnArray = possibleQueenMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for Queen at " + x + "," + y);
            }
        } else if (piece instanceof Knight) {
            returnArray = possibleKnightMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for Knight at " + x + "," + y);
            }
        } else if (piece instanceof Pawn) {
            returnArray = possiblePawnMoves(piece, x, y);
            if (returnArray.size() == 0) {
                System.err.println("No Valid Moves for Pawn at " + x + "," + y);
            }
        }

        return returnArray;
    }

    public List<int[]> possibleKingMoves(Piece piece, int x, int y) {
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

    public List<int[]> possibleCastleMoves(Piece piece, int x, int y) {
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

    public List<int[]> possibleBishopMoves(Piece piece, int x, int y) {
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

    public List<int[]> possibleQueenMoves(Piece piece, int x, int y) {
        List<int[]> returnArray = new ArrayList<>();

        returnArray.addAll(possibleCastleMoves(piece,x,y));
        returnArray.addAll(possibleBishopMoves(piece,x,y));
        return returnArray;
    }

    public List<int[]> possibleKnightMoves(Piece piece, int x, int y) {
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

    public List<int[]> possiblePawnMoves(Piece piece, int x, int y) {
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
}
