package org.jhess.core;

import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import java.util.List;

public class Move {

    public static final Move FORWARD = new Move(1, 0);
    public static final Move BACKWARD = new Move(-1, 0);
    public static final Move RIGHT = new Move(0, 1);
    public static final Move LEFT = new Move(0, -1);
    public static final Move FORWARD_RIGHT = new Move(1, 1);
    public static final Move FORWARD_LEFT = new Move(1, -1);
    public static final Move BACKWARD_RIGHT = new Move(-1, 1);
    public static final Move BACKWARD_LEFT = new Move(-1, -1);

    public static final Move KNIGHT_FORWARD_CLOSE_RIGHT = new Move(1, 2);
    public static final Move KNIGHT_FORWARD_CLOSE_LEFT = new Move(1, -2);
    public static final Move KNIGHT_FORWARD_FAR_RIGHT = new Move(2, 1);
    public static final Move KNIGHT_FORWARD_FAR_LEFT = new Move(2, -1);
    public static final Move KNIGHT_BACKWARD_CLOSE_RIGHT = new Move(-1, 2);
    public static final Move KNIGHT_BACKWARD_CLOSE_LEFT = new Move(-1, -2);
    public static final Move KNIGHT_BACKWARD_FAR_RIGHT = new Move(-2, 1);
    public static final Move KNIGHT_BACKWARD_FAR_LEFT = new Move(-2, -1);

    private final int rankToAdvance;
    private final int fileToAdvance;

    private Move(int rankToAdvance, int fileToAdvance) {
        this.rankToAdvance = rankToAdvance;
        this.fileToAdvance = fileToAdvance;
    }

    /**
     * Determines if the given move is a legal one.
     * @param board The game board.
     * @param srcSquare The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public static boolean isValidMove(Board board, Square srcSquare, Square destSquare) {

        Move move = new Move(destSquare.getRank() - srcSquare.getRank(),
                destSquare.getFile() - srcSquare.getFile());

        Piece piece = srcSquare.getPiece();
        Piece otherPiece = destSquare.getPiece();

        // If the destination square contains a friendly piece
        if (destSquare.isOccupied() && otherPiece.getAlliance() == piece.getAlliance()) {
            return false;
        }

        // If it's a pawn and it can capture
        if (piece instanceof Pawn && destSquare.isOccupied() && (move.equals(FORWARD_RIGHT) || move.equals(FORWARD_LEFT))){
            return true;
        }

        // If the move isn't in the piece's possible move list
        if (!piece.getMoveList().contains(move)) {
            return false;
        }

        // If the piece is skipping over another piece
        if (!(piece instanceof Rook) && !(piece instanceof Bishop) && !(piece instanceof Queen)) {
            return true;
        }

        int distance = Math.max(Math.abs(move.getRankToAdvance()), Math.abs(move.getFileToAdvance()));

        Move tempMove;
        Square tempSquare;
        for (int i = 1; i < distance ; i++) {
            tempMove = move.extend(-i);
            tempSquare = board.addMoveToSquare(srcSquare, tempMove);

            if (tempSquare.isOccupied()){
                return false;
            }
        }

        return true;
    }

    public int getRankToAdvance() {
        return rankToAdvance;
    }

    public int getFileToAdvance() {
        return fileToAdvance;
    }

    /**
     * Extends the move by the given modifier.
     *
     * @param modifier The modifier to extend the move by.
     * @return A new move extended by the modifier.
     */
    public Move extend(int modifier) {
        int newRankToAdvance = rankToAdvance;
        int newFileToAdvance = fileToAdvance;

        if (rankToAdvance > 0){
            newRankToAdvance += modifier;

        } else if (rankToAdvance < 0){
            newRankToAdvance -= modifier;
        }

        if (fileToAdvance > 0){
            newFileToAdvance += modifier;

        } else if (fileToAdvance < 0){
            newFileToAdvance -= modifier;
        }

        return new Move(newRankToAdvance, newFileToAdvance);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Move &&
                rankToAdvance == ((Move) obj).rankToAdvance &&
                fileToAdvance == ((Move) obj).fileToAdvance;
    }
}
