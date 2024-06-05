/*
 * @(#) Player.java 0.3 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import uk.ac.aber.cs221.gp02.chesstutor.util.Color;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 Initial development
 * @version 0.2 Added XML annotations and added zero argument constructor - mib60
 * @version 0.3 complete Javadoc added
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Player { //Marks the class as an XML element

   @XmlElement(name = "color") //Save this to the XML save file
   private Color color;

   @XmlElement(name = "name") //Save this to the XML save file
   private String name;

   @XmlElement(name = "takenPieces") //Save this to the XML save file
   private List<Piece> takenPieces; //list of enemy pieces that have been taken

   /**
    * Constructor
    * Creates new Player object
    */
   public Player(Color color, String name) {
      this.color = color;
      this.name = name;
      takenPieces =  new ArrayList<Piece>();
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public Player(){

   }

   /**
    * Getter
    * Gets the color field
    *
    * @return Color
    */
   public Color getColor() {
      return color;
   }

   /**
    * Getter
    * Gets the name field
    *
    * @return String
    */
   public String getName() {
      return name;
   }

   /**
    * Getter
    * Gets the takenPiece field
    *
    * @return List<Piece>
    */
   public List<Piece> getTakenPieces() {
      return takenPieces;
   }

   /**
    * Setter
    * Adds a Piece to the takenPieces list field
    */
   public void addTakenPieces(Piece piece) {
      takenPieces.add(piece);
   }
}
