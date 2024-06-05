package uk.ac.aber.cs221.gp02.chesstutor.specialmoves;

import uk.ac.aber.cs221.gp02.chesstutor.game.Board;
import uk.ac.aber.cs221.gp02.chesstutor.game.Square;
import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import uk.ac.aber.cs221.gp02.chesstutor.util.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckChecker {

   /**
    * Returns a list of coordinates of pieces that are attacking a square
    *
    * @param boardArray The board array
    * @param x X coordinate of the square
    * @param y Y coordinate of the square
    * @param attackingColor The attacking colour to check for
    * @return Returns a list of x y coordinates of pieces that are attacking the square
    */
   public static List<int[]> checkChecker(Square[][] boardArray, int x, int y, Color attackingColor){
      List<int[]> returnArray = new ArrayList<>();

      //iterate through pieces on the board
      for(int i = 0; i < 8; i++){
         for(int j = 0; j < 8; j++){

            //Get the current piece
            Piece currentPiece = boardArray[i][j].getPiece();

            //Skip over the piece if it is null or the same colour as the king
            if(currentPiece == null || !currentPiece.getPieceColor().equals(attackingColor)) continue;

            //Get the possible moves for the current piece
            List<int[]> currentPieceMoves = currentPiece.getValidMoves(boardArray, currentPiece, i, j);

            //If any of the moves will place the piece on the king it is in check
            for(int[] currentMove:currentPieceMoves){
               if(currentMove[0] == x && currentMove[1] == y) returnArray.add(new int[]{i, j});
            }
         }
      }


      return returnArray;
   }

   /**
    * Checks whether a king is in check mate
    *
    * @param board The board
    * @param x The X position of the king
    * @param y The Y position of the king
    * @param attackingColor The colour attacking the king
    * @return Returns true if in checkmate, false if not. Will return false if the square is not a king
    */
   public static boolean checkMateChecker(Board board, int x, int y, Color attackingColor){

      Piece king = board.getBoardArray()[x][y].getPiece();

      if(king == null || !king.getPieceType().equals(Type.KING)){
         System.out.println("checkMateChecker was called on a square that is not a king and returned false");
         return false;
      }

      Square[][] simBoardArray = board.getBoardArray().clone();

      //Get the pieces attacking the king and the possible moves the king can make
      List<int[]> attackers = checkChecker(simBoardArray, x, y, attackingColor);
      List<int[]> possibleKingMoves = king.getValidMoves(board.getBoardArray(), king, x, y);

      //If there is more than one attacker the king is in double check and has to move to escape
      if(attackers.size() >= 2){
         //Iterate through each move the king can make
         for(int[] currentMove:possibleKingMoves){
            //If checkChecker returns an empty list, the current move gets the king out of check, so return false
            if(checkChecker(simBoardArray, currentMove[0], currentMove[1], attackingColor).isEmpty()) return false;
         }

         return true; //Otherwise king is in checkmate so return true
      }


      //Otherwise if the king is in check and not double check
      else if(attackers.size() == 1){
         //Check all of king's possible moves to see if any of them take him out of check

         //Iterate through each move the king can make
         for(int[] currentMove:possibleKingMoves){

            //Get the current square and create a new array to simulate the moves in
            simBoardArray = board.getBoardArray().clone();
            Square oldSquare = simBoardArray[currentMove[0]][currentMove[1]];

            simBoardArray[currentMove[0]][currentMove[1]] = new Square();

            System.out.println(Arrays.toString(currentMove));
            //If checkChecker returns an empty list, the current move gets the king out of check, so return false
            if(checkChecker(simBoardArray, currentMove[0], currentMove[1], attackingColor).isEmpty()){
               System.out.println("Found a move king can make to get out of check");

               //Return the square to its old state
               simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;

               return false;
            }
            //Return the square to its old state
            simBoardArray[currentMove[0]][currentMove[1]] = oldSquare;
         }

         //Iterate through pieces
         for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){

               //Get the current square and create a new array to simulate the moves in
               simBoardArray = board.getBoardArray().clone();
               Square currentSquare = simBoardArray[i][j];

               //If current square is empty or occupied by a friendly piece skip over it
               if(currentSquare.getPiece() == null) continue;
               if(currentSquare.getPiece().getPieceColor() == attackingColor) continue;

               //Get the piece's moves and the original square of the piece
               List<int[]> moves = currentSquare.getPiece().getValidMoves(simBoardArray, currentSquare.getPiece(), i, j);
               Square oldCurrentSquare = simBoardArray[i][j];

               for(int[] newMove:moves){

                  //Get the X and Y coordinates of the new move
                  int newMoveX = newMove[0];
                  int newMoveY = newMove[1];

                  //Get the square the piece would be moved to
                  Square oldNewSquare = simBoardArray[newMoveX][newMoveY];

                  //Move the piece to the current move and set the old position to empty
                  simBoardArray[newMoveX][newMoveY] = oldCurrentSquare;
                  simBoardArray[i][j] = new Square();


                  List<int[]> newAttackers;

                  //If the king has moved, get the attackers of the new square he has moved to
                  if(currentSquare.getPiece().getPieceType() == Type.KING){
                     newAttackers = checkChecker(simBoardArray, newMoveX, newMoveY, attackingColor);
                  }

                  //Otherwise get the attackers of the square if the king has not moved
                  else{
                     newAttackers = checkChecker(simBoardArray, x, y, attackingColor);
                  }

                  //Set the pieces in the simulation board to their original states for the next move
                  simBoardArray[i][j] = oldCurrentSquare;
                  simBoardArray[newMoveX][newMoveY] = oldNewSquare;

                  //If there are no attackers now, the king is not in checkmate
                  if(newAttackers.isEmpty()){
                     System.out.println("King can escape check by moving piece at "+i+","+j+" to "+newMoveX+","+newMoveY);
                     return false;
                  }
               }
            }
         }

         System.out.println("king is in checkmate");
         return true;
      }

      else return false;
   }
}
