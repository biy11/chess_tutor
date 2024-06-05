/*
 * @(#) Game.java 0.3 2023/03/05
 *
 * Copyright (c) 2023 Aberystwyth University.
 * All rights reserved.
 */

package uk.ac.aber.cs221.gp02.chesstutor.game;

import uk.ac.aber.cs221.gp02.chesstutor.pieces.Piece;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Michael [mjs36]
 * @author Micah [mib60]
 * @version 0.1 Initial Development
 * @version 0.2 Added XML annotations - mib60
 * @version 0.3 complete Javadoc added
 */
public class Square {

   @XmlElement(name = "piece") //Save this to the XML save file
   private Piece piece;

   @XmlElement(name = "hasPiece") //Save this to the XML save file
   private boolean hasPiece;

   /**
    * Constructor
    * Creates new Square object
    * <p></p>
    * This constructor is for empty Squares - ones that do not hold a piece
    */
   public Square() {
      hasPiece = false;
   }

   /**
    * Constructor
    * Creates new Square object
    * <p></p>
    * This constructor is for Squares that hold pieces
    */
   public Square(Piece piece) {
      this.piece = piece;
      hasPiece = true;
   }

   /**
    * Getter
    * Gets the piece field
    *
    * @return Piece
    */
   @XmlTransient
   public Piece getPiece() {
      return piece;
   }

   /**
    * Getter
    * Gets the isHasPiece field
    * <p></p>
    * Used has to avoid checking for nulls - which can not be fully accurate
    * Use instead of getPiece == null
    *
    * @return boolean
    */
   @XmlTransient
   public boolean isHasPiece() {
      return hasPiece;
   }

}
