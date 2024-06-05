/*
 * @(#) RefreshHighlight.java 0.6 2023/05/03
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */
package uk.ac.aber.cs221.gp02.chesstutor.gui.board;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.moves.CheckChecker;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.Castle;
import uk.ac.aber.cs221.gp02.chesstutor.moves.specialmoves.EnPassant;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates, updates, and destroys the highlights on the board
 *
 * @author Michael [mjs36]
 * @author Abdullah [abd15]
 * @version 0.1 Initial development.
 * @version 0.2 Refactored
 * @version 0.3 Changed to use gold capture circle for enPassant
 * @version 0.4 Refactored and added Javadoc
 * @version 0.5 Fixed Javadoc comment for isKingCheckmate()
 * @version 0.6 moved refreshBoard to refreshGui class
 */
public class RefreshHighlight {

   /**
    * A method to highlight the valid moves of the selected piece
    */
   static void highlightMoves() {
      BoardGui.selectedPiece.setStyle("-fx-background-color: #ffff00");

      Square[][] boardArray = BoardGui.chessGame.getBoard().getBoardArray();

      Piece tempPiece = boardArray[BoardGui.selectedPieceCoordinate[0]][BoardGui.selectedPieceCoordinate[1]].getPiece();
      List<int[]> moves = tempPiece.getPossibleMoves(BoardGui.chessGame.getBoard(), tempPiece, BoardGui.selectedPieceCoordinate[0], BoardGui.selectedPieceCoordinate[1]);

      String filename;

      switch (tempPiece.getPieceType()) {
         case KING:
            List<int[]> castle = Castle.canCastle(BoardGui.chessGame.getBoard(), tempPiece, BoardGui.selectedPieceCoordinate[0], BoardGui.selectedPieceCoordinate[1]);

            if (castle.isEmpty()) break;

            for (int[] ints : castle) {
               filename = BoardGui.filepath.concat("util/");
               filename = (tempPiece.getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                       filename.concat("king_black_gold.png") : filename.concat("king_white_gold.png");

               BoardGui.createImage(filename, ints[0], BoardGui.selectedPieceCoordinate[0]);

               filename = BoardGui.filepath.concat("util/");
               filename = (tempPiece.getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                       filename.concat("rook_black_gold.png") : filename.concat("rook_white_gold.png");

               BoardGui.createImage(filename, ints[2], BoardGui.selectedPieceCoordinate[0]);

               moves.removeIf(move -> move[0] == BoardGui.selectedPieceCoordinate[0] && move[1] == ints[2]);
            }
            break;
         case PAWN:
            List<int[]> enPassant = EnPassant.getMoves(BoardGui.chessGame.getBoard(), tempPiece, BoardGui.selectedPieceCoordinate[0], BoardGui.selectedPieceCoordinate[1]);

            if (enPassant.isEmpty()) break;

            filename = BoardGui.filepath.concat("util/");
            filename = (tempPiece.getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                    filename.concat("pawn_black_gold.png") : filename.concat("pawn_white_gold.png");

            BoardGui.createImage(filename, enPassant.get(0)[1], enPassant.get(0)[0]);

            filename = BoardGui.filepath.concat("util/");
            filename = filename.concat("gold_piece_circle.png");

            BoardGui.createImage(filename, enPassant.get(1)[1], enPassant.get(1)[0]);

            break;
      }

      for (int[] move : moves) {
         filename = BoardGui.filepath.concat("util/");
         filename = (boardArray[move[0]][move[1]].hasPiece()) ?
                 filename.concat("move_piece_circle.png") : filename.concat("move_circle.png");

         BoardGui.createImage(filename, move[1], move[0]);
      }
   }

   /**
    * A method to reset (remove) the highlights currently on the board
    */
   static void resetHighlight() {
      BoardGui.selectedPiece.setStyle("-fx-set-background: transparent"); //Sets selected piece background to transparent.

      ObservableList<Node> children = BoardGui.board.getChildren();
      List<StackPane> removeNodeList = new ArrayList<>();
      for(Node node : children) {
         if(node instanceof StackPane) {
            ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
            for(Node stack : stackChildren) {
               if (stack instanceof ImageView) {
                  String url = ((ImageView) stack).getImage().impl_getUrl();
                  if (url.contains("/util/")) { //is utility
                     StackPane highlight = (StackPane) node; // use what you want to remove
                     removeNodeList.add(highlight);
                  }
               }
            }
         }
      }
      for (StackPane node : removeNodeList) {
         BoardGui.board.getChildren().remove(node);
      }

      isKingInCheck(); //re-adds king in check highlight if piece is clicked
   }

   /**
    * A method to show the King is in Check
    */
   static void isKingInCheck() {
      Square[][] array = BoardGui.chessGame.getBoard().getBoardArray();

      String kingColor = "";
      outerLoop:
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (array[row][col].hasPiece() && array[row][col].getPiece().getPieceType() == Type.KING) {
               if (array[row][col].getPiece().getPieceColor() == BoardGui.chessGame.getTurn()) {
                  if (CheckChecker.checkChecker(BoardGui.chessGame.getBoard(), row, col, array[row][col].getPiece().getEnemyPieceColor()).isEmpty()) return;
                  kingColor = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                          kingColor.concat("black") : kingColor.concat("white");
                  break outerLoop;
               }
            }
         }
      }

      //find piece on gui
      ObservableList<Node> children = BoardGui.board.getChildren();
      for(Node node : children) {
         if(node instanceof StackPane) {
            ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
            for(Node stack : stackChildren) {
               if (stack instanceof ImageView) {
                  String url = ((ImageView) stack).getImage().impl_getUrl();
                  if (url.contains("king") && url.contains(kingColor)) {
                     StackPane piece = (StackPane) stack.getParent();
                     piece.setStyle("-fx-background-color: #fcba03");
                     return;
                  }
               }
            }
         }
      }
   }

   /**
    * A method to show that the King is in CheckMate
    * @return true or false depending on if the King is in check or not
    */
   static boolean isKingCheckMate() {
      Square[][] array = BoardGui.chessGame.getBoard().getBoardArray();

      String kingColor = "";
      outerLoop:
      for (int row = 0; row < 8; row++) {
         for (int col = 0; col < 8; col++) {
            if (array[row][col].hasPiece() && array[row][col].getPiece().getPieceType() == Type.KING) {
               if (array[row][col].getPiece().getPieceColor() == BoardGui.chessGame.getTurn()) {
                  if (!CheckChecker.checkMateChecker(BoardGui.chessGame.getBoard(),row,col)) return false;
                  kingColor = (array[row][col].getPiece().getPieceColor() == uk.ac.aber.cs221.gp02.chesstutor.util.Color.BLACK) ?
                          kingColor.concat("black") : kingColor.concat("white");
                  break outerLoop;
               }
            }
         }
      }

      //find piece on gui
      ObservableList<Node> children = BoardGui.board.getChildren();
      for(Node node : children) {
         if(node instanceof StackPane) {
            ObservableList<Node> stackChildren = ((StackPane) node).getChildren();
            for(Node stack : stackChildren) {
               if (stack instanceof ImageView) {
                  String url = ((ImageView) stack).getImage().impl_getUrl();
                  if (url.contains("king") && url.contains(kingColor)) { //is utility
                     StackPane piece = (StackPane) stack.getParent();
                     piece.setStyle("-fx-background-color: #8f0101");
                     return true;
                  }
               }
            }
         }
      }
      return false;
   }
}
