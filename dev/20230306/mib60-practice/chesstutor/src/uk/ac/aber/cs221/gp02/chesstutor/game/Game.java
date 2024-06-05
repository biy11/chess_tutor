package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

/**
 * @author Michael [mjs36]
 */
public class Game {

   private Board board;
   private Player whitePlayer;
   private Player blackPlayer;
   private Color turn;
   private int roundCount;

   public Game() {
      board = new Board();
   }

   public Board getBoard() {
      return board;
   }

   public void setWhitePlayer(String name) {
      whitePlayer = new Player(Color.WHITE, name);
   }

   public void setBlackPlayer(String name) {
      blackPlayer = new Player(Color.BLACK, name);
   }

   public Player getWhitePlayer() {
      return whitePlayer;
   }

   public Player getBlackPlayer() {
      return blackPlayer;
   }

   public Color getTurn() {
      return turn;
   }

   public void setTurn(Color turn) {
      this.turn = turn;
   }

   public int getRoundCount() {
      return roundCount;
   }

   public void nextRound() {
      roundCount++;
   }
}
