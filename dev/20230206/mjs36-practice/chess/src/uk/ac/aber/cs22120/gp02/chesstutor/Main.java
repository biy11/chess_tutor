package uk.ac.aber.cs22120.gp02.chesstutor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        board.printBoard();

        List<int[]> test = board.possibleMoves(board.getSquare(0,0));

        test.forEach((a) -> System.out.println(a[0] + "," + a[1]));
    }
}
