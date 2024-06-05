package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import java.util.Arrays;

/**
 * @author Michael [mjs36]
 */
public class Board {

   private Square[][] boardArray;

   public Board() {
      boardArray = new Square[8][8];
   }

   public void boardResetForCastleTest(){
      for (int i = 0; i <= 7; i++) {
         for (int j = 0; j <= 7; j++) {
            boardArray[i][j] = new Square();
         }
      }

      boardArray[7][4] = new Square(new King(Color.WHITE));
      boardArray[7][0] = new Square(new Rook(Color.WHITE));
      boardArray[7][7] = new Square(new Rook(Color.WHITE));

      boardArray[0][0] = new Square(new Rook(Color.BLACK));
      boardArray[0][7] = new Square(new Rook(Color.BLACK));
      boardArray[0][6] = new Square(new Knight(Color.BLACK));
      boardArray[0][4] = new Square(new King(Color.BLACK));
   }

   public Square[][] getBoardArray() {
      return boardArray;
   }

   public void boardReset() {

      boardArray[0][0] = new Square(new Rook(Color.BLACK));
      boardArray[0][7] = new Square(new Rook(Color.BLACK));
      boardArray[0][1] = new Square(new Knight(Color.BLACK));
      boardArray[0][6] = new Square(new Knight(Color.BLACK));
      boardArray[0][2] = new Square(new Bishop(Color.BLACK));
      boardArray[0][5] = new Square(new Bishop(Color.BLACK));
      boardArray[0][3] = new Square(new Queen(Color.BLACK));
      boardArray[0][4] = new Square(new King(Color.BLACK));

      boardArray[7][0] = new Square(new Rook(Color.WHITE));
      boardArray[7][7] = new Square(new Rook(Color.WHITE));
      boardArray[7][1] = new Square(new Knight(Color.WHITE));
      boardArray[7][6] = new Square(new Knight(Color.WHITE));
      boardArray[7][2] = new Square(new Bishop(Color.WHITE));
      boardArray[7][5] = new Square(new Bishop(Color.WHITE));
      boardArray[7][3] = new Square(new Queen(Color.WHITE));
      boardArray[7][4] = new Square(new King(Color.WHITE));

      for (int i = 0; i <= 7; i ++) {
         boardArray[1][i] = new Square(new Pawn(Color.BLACK));
         boardArray[6][i] = new Square(new Pawn(Color.WHITE));
      }

      for (int i = 2; i <= 5; i++) {
         for (int j = 0; j <= 7; j++) {
            boardArray[i][j] = new Square();
         }
      }
   }

   public void printBoard() { //for debugging only
      String[][] array = new String[8][8];
      for (int i = 0; i <= 7; i++) {
         for (int j = 0; j <= 7; j++) {
            if (!boardArray[i][j].isHasPiece()) {
               array[i][j] = "     ";
            } else {
               array[i][j] = boardArray[i][j].getPiece().getPieceType().name();
            }
         }
      }
      System.out.println(Arrays.deepToString(array).replace("],", "]\n").replace("[[", " [").replace("]]","]"));
   }
}