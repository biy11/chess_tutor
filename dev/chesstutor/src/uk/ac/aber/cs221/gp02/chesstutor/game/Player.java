/*
 * @(#) Player.java 0.3 2023/05/01
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
 * @version 0.4 changed zero argument constructor to initialize takenPieces
 * @version 0.5 Fixed warnings
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
      takenPieces =  new ArrayList<>();
   }

   /**
    * Zero argument constructor used by the XML parser
    */
   public Player(){
      //If XML parser loads a Player and takenPieces is null, create a new ArrayList
      //This is necessary because if takenPieces is empty it will not be saved to the
      //xml file. Suppressed this warning because IntelliJ does not know about the XML parser


      //noinspection ConstantValue
      if(takenPieces == null) takenPieces = new ArrayList<>();
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
