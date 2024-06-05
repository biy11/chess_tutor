package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael [mjs36]
 */
public class Player {

   private Color color;
   private String name;
   private List<Piece> takenPieces; //list of enemy pieces that have been taken

   public Player(Color color, String name) {
      this.color = color;
      this.name = name;
      takenPieces =  new ArrayList<Piece>();
   }

   public Color getColor() {
      return color;
   }

   public String getName() {
      return name;
   }

   public List<Piece> getTakenPieces() {
      return takenPieces;
   }

   public void addTakenPieces(Piece piece) {
      takenPieces.add(piece);
   }
}
