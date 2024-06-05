package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

public class TestPlayer {

   @Test
   public void testPlayerObject(){
      Player obj = new Player(Color.WHITE, "Jack");
      assertEquals("Jack", obj.getName());
      assertEquals(Color.WHITE, obj.getColor());
   }

   @Test
   public void pawnPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Pawn piece = new Pawn(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.PAWN, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void rookPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Rook piece = new Rook(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.ROOK, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void knightPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Knight piece = new Knight(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.KNIGHT, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void bishopPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Bishop piece = new Bishop(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.BISHOP, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void queenPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Queen piece = new Queen(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.QUEEN, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void kingPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      King piece = new King(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.KING, obj.getTakenPieces().get(0).getPieceType());
   }

   @Test
   public void testAllPossibleCaptures(){
      Player obj = new Player(Color.WHITE, "Jack");
      Piece[][] capturedPieces = new Piece[2][8];
      capturedPieces[0][0] = new Pawn(Color.BLACK);

      assertEquals(Type.KING, obj.getTakenPieces().get(0).getPieceType());
   }

}
