package org.jhess.core.moves;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

/**
 * Represents a game move.
 */
public class GameMove {

    private final Piece playedPiece;
    private final Square srcSquare;
    private final Square destSquare;

    public GameMove(Piece playedPiece, Square srcSquare, Square destSquare) {
        this.playedPiece = playedPiece;
        this.srcSquare = srcSquare;
        this.destSquare = destSquare;
    }

    public Piece getPlayedPiece() {
        return playedPiece;
    }

    public Square getSrcSquare() {
        return srcSquare;
    }

    public Square getDestSquare() {
        return destSquare;
    }
}
