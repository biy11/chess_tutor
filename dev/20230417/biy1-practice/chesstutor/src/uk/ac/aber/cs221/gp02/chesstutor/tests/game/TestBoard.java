package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Game;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

   @Test
   void rookLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      assertEquals(Type.ROOK, array[0][0].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[0][7].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[7][0].getPiece().getPieceType());
      assertEquals(Type.ROOK, array[7][7].getPiece().getPieceType());
   }

   @Test
   void knightLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      assertEquals(Type.KNIGHT, array[0][1].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[0][6].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[7][1].getPiece().getPieceType());
      assertEquals(Type.KNIGHT, array[7][6].getPiece().getPieceType());
   }

   @Test
   void bishopLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      assertEquals(Type.BISHOP, array[0][2].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[0][5].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[7][2].getPiece().getPieceType());
      assertEquals(Type.BISHOP, array[7][5].getPiece().getPieceType());
   }

   @Test
   void queenLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      assertEquals(Type.QUEEN, array[0][3].getPiece().getPieceType());
      assertEquals(Type.QUEEN, array[0][3].getPiece().getPieceType());
   }

   @Test
   void kingLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      assertEquals(Type.KING, array[0][4].getPiece().getPieceType());
      assertEquals(Type.KING, array[0][4].getPiece().getPieceType());
   }

   @Test
   void pawnLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      for (int i=0 ; i < 8; i++) {
         assertEquals(Type.PAWN, array[1][i].getPiece().getPieceType());
         assertEquals(Type.PAWN, array[6][i].getPiece().getPieceType());
      }
   }

   @Test
   void whiteLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      for (int i=0 ; i < 8; i++) {
         assertEquals(Color.WHITE, array[6][i].getPiece().getPieceColor());
         assertEquals(Color.WHITE, array[7][i].getPiece().getPieceColor());
      }
   }

   @Test
   void blackLocationsAllCorrect(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      for (int i=0 ; i < 8; i++) {
         assertEquals(Color.BLACK, array[0][i].getPiece().getPieceColor());
         assertEquals(Color.BLACK, array[1][i].getPiece().getPieceColor());
      }
   }

   @Test
   void noExtraPiecesOnBoard(){
      Board board = new Board();
      board.boardReset();
      Square[][] array ;
      array = board.getBoardArray();

      int pieceNmb = 0;
      int x = 0;
      int y = 0;
      for (int i=0;i<64;i++){
         x = i%8;
         y = i/8;
         if (array[y][x].isHasPiece())pieceNmb++;
      }
      assertEquals(32, pieceNmb);
   }

}
