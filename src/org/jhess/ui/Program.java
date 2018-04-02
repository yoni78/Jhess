package org.jhess.ui;

import org.jhess.core.Board;
import org.jhess.core.Square;
import org.jhess.core.pieces.Piece;

public class Program {
    public static void main(String[] args){
        Board board = new Board();
        Square square = board.getSquares()[7][3];
        Piece piece = square.getPiece();

        System.out.println(piece);
    }
}
