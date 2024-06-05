/*
 * @(#) TestPlayer.java 0.4 2023/05/04
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
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
 * Unit tests for TestPlayer.
 *
 * @author Lance [lvs1]
 * @version 0.1 Framework.
 * @version 0.2 Initial Development.
 * @version 0.3 Bug fixes.
 * @version 0.4 Added comments.
 */

public class TestPlayer {

   /**
    * Tests to check if Player Object works for white player.
    * Creates a new Player object and checks if it is stored correctly.
    * */
   @Test
   public void testPlayerObject(){
      Player obj = new Player(Color.WHITE, "Jack");
      assertEquals("Jack", obj.getName());
      assertEquals(Color.WHITE, obj.getColor());
   }

   /**
    * Tests to check if Player Object works as it should for black player.
    * Creates a new Player object and checks if it is stored correctly.
    * */
   @Test
   public void pawnPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Pawn piece = new Pawn(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.PAWN, obj.getTakenPieces().get(0).getPieceType());
   }
   /**
    * Tests to check if adding taken pieces works as it should.
    * Creates a new Player object, adds a capture rook to player and checks if it is stored correctly.
    * */
   @Test
   public void rookPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Rook piece = new Rook(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.ROOK, obj.getTakenPieces().get(0).getPieceType());
   }

   /**
    * Tests to check if adding taken pieces works as it should.
    * Creates a new Player object, adds a capture Knight to player and checks if it is stored correctly.
    * */
   @Test
   public void knightPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Knight piece = new Knight(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.KNIGHT, obj.getTakenPieces().get(0).getPieceType());
   }
   /**
    * Tests to check if adding taken pieces works as it should.
    * Creates a new Player object, adds a capture Bishop to player and checks if it is stored correctly.
    * */
   @Test
   public void bishopPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Bishop piece = new Bishop(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.BISHOP, obj.getTakenPieces().get(0).getPieceType());
   }
   /**
    * Tests to check if adding taken pieces works as it should.
    * Creates a new Player object, adds a capture Queen to player and checks if it is stored correctly.
    * */
   @Test
   public void queenPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      Queen piece = new Queen(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.QUEEN, obj.getTakenPieces().get(0).getPieceType());
   }
   /**
    * Tests to check if adding taken pieces works as it should. Used if there is error with loading game.
    * For example: A game where an error of a king being captured. The game should end.
    * Creates a new Player object, adds a capture King to player and checks if it is stored correctly.
    * */
   @Test
   public void kingPieceTaken(){
      Player obj = new Player(Color.WHITE, "Jack");
      King piece = new King(Color.BLACK);
      obj.addTakenPieces(piece);

      assertEquals(Type.KING, obj.getTakenPieces().get(0).getPieceType());
   }
   /**
    * Tests to check if players can capture all pieces.
    * Creates two new Player objects, two piece sets. Sets players to have captured all.
    * Checks if they have been captured correctly.
    * */
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
