package uk.ac.aber.cs221.gp02.chesstutor.tests.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.*;
import uk.ac.aber.cs221.gp02.chesstutor.game.Player;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import org.junit.jupiter.api.Test;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

/**
 * author
 */
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
      Player p1 = new Player(Color.WHITE, "Jack");
      Player p2 = new Player(Color.BLACK,  "John");
      ArrayList<Piece> blackCapturedPieces = new ArrayList<Piece>();
      ArrayList<Piece> whiteCapturedPieces = new ArrayList<Piece>();


      for (int i=0; i<8;i++){
         System.out.print(i);
         blackCapturedPieces.add(new Pawn(Color.BLACK));
      }

      blackCapturedPieces.add(new Rook(Color.BLACK));
      blackCapturedPieces.add(new Rook(Color.BLACK));

      blackCapturedPieces.add(new Knight(Color.BLACK));
      blackCapturedPieces.add(new Knight(Color.BLACK));

      blackCapturedPieces.add(new Bishop(Color.BLACK));
      blackCapturedPieces.add(new Bishop(Color.BLACK));

      blackCapturedPieces.add(new Queen(Color.BLACK));


      for (int i=0; i<8;i++){
         whiteCapturedPieces.add(new Pawn(Color.WHITE));
      }
      whiteCapturedPieces.add(new Rook(Color.WHITE));
      whiteCapturedPieces.add(new Rook(Color.WHITE));

      whiteCapturedPieces.add(new Knight(Color.WHITE));
      whiteCapturedPieces.add(new Knight(Color.WHITE));

      whiteCapturedPieces.add(new Bishop(Color.WHITE));
      whiteCapturedPieces.add(new Bishop(Color.WHITE));

      whiteCapturedPieces.add(new Queen(Color.WHITE));


      for (int i=0; i< blackCapturedPieces.size();i++){
         p1.addTakenPieces(blackCapturedPieces.get(i));
         p2.addTakenPieces(whiteCapturedPieces.get(i));
      }

      for (int i=0; i< blackCapturedPieces.size();i++) {
         assertEquals(blackCapturedPieces.get(i), p1.getTakenPieces().get(i));
         assertEquals(blackCapturedPieces.get(i), p1.getTakenPieces().get(i));
      }
   }

}
