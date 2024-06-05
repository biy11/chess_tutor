package uk.ac.aber.cs22120.gp02.chesstutor;

import uk.ac.aber.cs22120.gp02.chesstutor.GUI.ChessGUI;

import java.util.ArrayList;
import java.util.List;

public class Main {
/*
    public static void startGUI(String args[]) {
        Runnable GUI = () -> {
            ChessGUI.main(args);
        };
        Thread GUIthread = new Thread(GUI);
        GUIthread.start();
    }*/
    public static void main(String[] args) throws Exception {

        Board board = new Board();
        board.printBoard();

        ChessGUI.main(args, board);

        //List<int[]> test = board.possibleMoves(board.getSquare(0,0));

        //test.forEach((a) -> System.out.println(a[0] + "," + a[1]));
    }
}
