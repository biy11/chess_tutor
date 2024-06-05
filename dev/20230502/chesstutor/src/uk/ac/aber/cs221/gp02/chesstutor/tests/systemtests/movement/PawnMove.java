package uk.ac.aber.cs221.gp02.chesstutor.tests.systemtests.movement;

/**
 * @author Lance [lvs1]
 * version 0.1 framework
 *
 * This class is for testing FR5 - Movement
 * (specifically for pawns)
 */
public class PawnMove {
    /**
     * Test pawns display the correct moves
     * (on the first move)
     */
    public void testDisplayFirstMove(){}

    /**
     * Test pawns can move one square
     * (on the first move)
     */
    public void testFirstMoveOneSquare(){}

    /**
     * Test pawns can move two squares
     * (on the first move)
     */
    public void testFirstMoveTwoSquare(){}

    /**
     * Test pawns display the correct moves
     * (on the second move)
     */
    public void testDisplaySecondMove(){}

    /**
     * Test pawns cannot move more than three squares
     * (on the first move)
     */
    public void testFirstMove3Plus(){}

    /**
     * Test pawns can move one square
     * (on the second move and onwards)
     */
    public void testSecondPlusOneSquare(){}

    /**
     * Test pawns cannot move two squares
     * (on the second move and onwards)
     */
    public void testSecondPlusTwoSquare(){}

    /**
     * Test pawns cannot move three or more squares
     * (on the second move and onwards)
     */
    public void testSecondPlusThreePlus(){}

    /**
     * Test pawns cannot move through other pieces when blocked
     * (on the first move)
     */
    public void testFirstMovePawnBlocked(){}
}
