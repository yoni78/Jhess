package org.jhess.logic.moves;

import org.jhess.core.Alliance;
import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.moves.MoveAnalysis;
import org.jhess.core.moves.MoveAnalysisBuilder;
import org.jhess.core.moves.MoveVector;
import org.jhess.core.pieces.Piece;
import org.jhess.logic.board.BoardUtils;
import org.jhess.logic.board.PositionAnalyser;
import org.jhess.logic.pieces.PieceUtils;

import static org.jhess.core.moves.MoveVector.*;

// TODO: 2018-07-20 Threefold repetition

public class MoveAnalyser {
    private final Board position;
    private MoveAnalysisBuilder analysisBuilder;

    public MoveAnalyser(Board position) {
        this.position = position;
    }

    /**
     * Determines if the given move is a legal one.
     *
     * @param srcSquare  The source square.
     * @param destSquare The Destination square.
     * @return Whether the move is legal or not.
     */
    public MoveAnalysis analyseMove(Square srcSquare, Square destSquare) {

        PositionAnalyser positionAnalyser = new PositionAnalyser(position);
        analysisBuilder = new MoveAnalysisBuilder();
        analysisBuilder.setIsLegal(true);

        Piece piece = srcSquare.getPiece();
        MoveVector moveVector = new MoveVector(srcSquare, destSquare);

        // If it's not this player's turn
        if (piece.getAlliance() != position.getPlayerToMove()) {
            analysisBuilder.setIsLegal(false);
        }

        if (!hasNoPieceInHisWay(piece, moveVector, srcSquare) || !canLandOnSquare(piece, destSquare)) {
            analysisBuilder.setIsLegal(false);
        }

        switch (piece.getPieceType()) {
            case PAWN:
                validatePawnMove(piece, moveVector, srcSquare, destSquare);
                break;
            case KING:
                validateKingMove(position, piece, moveVector, destSquare);
                break;
            default:
                validateRegularPieceMove(piece, moveVector);
                break;
        }

        if (positionAnalyser.isCheck()) {
            analysisBuilder.setIsCheck(true);

            Board newBoard = MovePerformer.movePiece(position, srcSquare, destSquare);

            if (new PositionAnalyser(newBoard).isCheck()) {
                analysisBuilder.setIsLegal(false);
            }
        }

        if(destSquare.isOccupied()){
            analysisBuilder.setCaptureMove(true);
        }

        return analysisBuilder.createMoveValidation();
    }

    /**
     * Checks if the piece attempts to jump over another piece in it's moveVector.
     *
     * @param piece      The piece to be played.
     * @param moveVector The MoveVector to be played.
     * @param srcSquare  The source square of the moveVector.
     * @return If the path for the moveVector is clear.
     */
    public boolean hasNoPieceInHisWay(Piece piece, MoveVector moveVector, Square srcSquare) {

        // If the piece is skipping over another piece
        if (PieceUtils.isKnight(piece)) {
            return true;
        }

        int distance = Math.max(Math.abs(moveVector.getRankToAdvance()), Math.abs(moveVector.getFileToAdvance()));

        MoveVector tempMoveVector;
        Square tempSquare;
        for (int i = 1; i < distance; i++) {
            tempMoveVector = moveVector.extend(-i);
            tempSquare = BoardUtils.addMoveToSquare(position, srcSquare, tempMoveVector);

            if (tempSquare == null) {
                return true;
            }

            if (tempSquare.isOccupied()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Validates pawn moves.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     */
    private void validatePawnMove(Piece pawn, MoveVector moveVector, Square srcSquare, Square destSquare) {
        if (!isLegalPawnCaptureMove(pawn, moveVector, destSquare, false) &&
                !MoveUtils.isPawnDoubleMove(srcSquare, pawn, moveVector) &&
                !MoveUtils.isRegularPawnMove(pawn, moveVector, destSquare)) {

            if (isEnPassantMove(pawn, moveVector, destSquare)) {
                analysisBuilder.setIsEnPassant(true).setEnPassantSquare(position.getEnPassantTarget()).setCaptureMove(true);

            } else {
                analysisBuilder.setIsLegal(false);
            }
        }

        if (isPawnOnLastRank(destSquare, pawn.getAlliance())) {
            analysisBuilder.setIsPromotionMove(true).setPromotionSquare(destSquare);
        }
    }

    /**
     * Validates regular pieces moves (queens, rooks, bishops and knights)
     *
     * @param piece      The piece to be played.
     * @param moveVector The moveVector to be played
     */
    private void validateRegularPieceMove(Piece piece, MoveVector moveVector) {
        if (!piece.getMoveList().contains(moveVector)) {
            analysisBuilder.setIsLegal(false);
        }
    }

    /**
     * Validates king moves.
     *
     * @param board      The game board.
     * @param king       The king to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     */
    private void validateKingMove(Board board, Piece king, MoveVector moveVector, Square destSquare) {

        if (MoveUtils.isCastlingMove(moveVector)) {
            Square rookSquare = getCastlingRookSquare(board, destSquare, moveVector);
            analysisBuilder.setIsCastlingMove(true);
            analysisBuilder.setRookToCastleSquare(rookSquare);

            if (!canCastle(board, rookSquare)) {
                analysisBuilder.setIsLegal(false);
            }

        } else if (!king.getMoveList().contains(moveVector)) {
            analysisBuilder.setIsLegal(false);
        }
    }

    /**
     * Check if the given moveVector is an En Passant move.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is an En Passant.
     */
    private boolean isEnPassantMove(Piece pawn, MoveVector moveVector, Square destSquare) {
        return isLegalPawnCaptureMove(pawn, moveVector, destSquare, true) && position.getEnPassantTarget() != null && position.getEnPassantTarget().equals(destSquare); // TODO: 2018-07-20 TEST (a possible bug was observed)!
    }

    /**
     * Check if the given moveVector is a pawn's capture moveVector.
     *
     * @param pawn       The pawn to be played.
     * @param moveVector The moveVector to be played.
     * @param destSquare The destination square of the moveVector.
     * @return If the moveVector is a pawn capture moveVector.
     */
    private boolean isLegalPawnCaptureMove(Piece pawn, MoveVector moveVector, Square destSquare, boolean forEnPassant) {
        if (forEnPassant || destSquare.isOccupied()) {

            if (pawn.getAlliance() == Alliance.WHITE && (moveVector.equals(FORWARD_RIGHT) || moveVector.equals(FORWARD_LEFT))) {
                return true;
            }

            return pawn.getAlliance() == Alliance.BLACK && (moveVector.equals(BACKWARD_RIGHT) || moveVector.equals(BACKWARD_LEFT));
        }

        return false;
    }

    /**
     * Checks if the pawn got to the last rank (and should subsequently be promoted)
     *
     * @param destSquare   The destination square of the move.
     * @param pawnAlliance The alliance of the pawn.
     * @return If the pawn got to the last rank.
     */
    private boolean isPawnOnLastRank(Square destSquare, Alliance pawnAlliance) {
        // TODO: 2018-07-21 Move to a different class?
        return (pawnAlliance == Alliance.WHITE && destSquare.getRank() == 7) ||
                (pawnAlliance == Alliance.BLACK && destSquare.getRank() == 0);
    }

    /**
     * Gets the rook that should participate in the castling.
     *
     * @param board      The game board.
     * @param destSquare The destination square of the moveVector.
     * @param moveVector The moveVector to be played.
     * @return The rook that should participate in the castling.
     */
    private Square getCastlingRookSquare(Board board, Square destSquare, MoveVector moveVector) {
        Square rookSquare = null;

        // Short
        if (moveVector.equals(RIGHT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() + 1];

            // Long
        } else if (moveVector.equals(LEFT.extend(1))) {
            rookSquare = board.getSquares()[destSquare.getRank()][destSquare.getFile() - 2];
        }

        return rookSquare;
    }

    /**
     * Checks if the attempted castling move is possible.
     *
     * @param board      The current position.
     * @param rookSquare The square of the rook which will be involved in the castling.
     * @return Is castling possible in these conditions.
     */
    private boolean canCastle(Board board, Square rookSquare) {

        // Test that the player is not in check, and that he will not pass through check
        Square kingSquare = BoardUtils.getKing(board, board.getPlayerToMove());
        MoveVector castleDirection = rookSquare.getFile() == 0 ? MoveVector.LEFT : MoveVector.RIGHT;

        if (new PositionAnalyser(board).isCheck()){
            return false;
        }

        for (int i = 0; i < 2; i++) {
            MoveVector moveVector = castleDirection.extend(i);
            Square newSquare = BoardUtils.addMoveToSquare(board, kingSquare, moveVector);

            if(newSquare != null){
                Board newPosition = MovePerformer.movePiece(board, kingSquare, newSquare);

                if(new PositionAnalyser(newPosition).isCheck()){
                    return false;
                }
            }
        }

        // Checks if there is a knight in the way of the rook in a queen side castling
        if(rookSquare.getFile() == 0){
            int knightRank = board.getPlayerToMove() == Alliance.WHITE ? 0 : 7;
            Square knightSquare = board.getSquares()[knightRank][1];
            if(knightSquare.isOccupied()){
                return false;
            }
        }

        boolean whiteCanCastle = board.getPlayerToMove() == Alliance.WHITE &&
                ((board.isWhiteCanCastleKingSide() && rookSquare.getFile() == 7) ||
                        (board.isWhiteCanCastleQueenSide() && rookSquare.getFile() == 0));

        boolean blackCanCastle = board.getPlayerToMove() == Alliance.BLACK &&
                ((board.isBlackCanCastleKingSide() && rookSquare.getFile() == 7) ||
                        (board.isBlackCanCastleQueenSide() && rookSquare.getFile() == 0));

        return whiteCanCastle || blackCanCastle;
    }

    /**
     * Checks that the destination square isn't occupied, and if so then if it's occupied by an enemy piece.
     *
     * @param piece      The piece to be played.
     * @param destSquare The destination square of the move.
     * @return If the piece can land on the square.
     */
    private boolean canLandOnSquare(Piece piece, Square destSquare) {
        return !destSquare.isOccupied() || destSquare.getPiece().getAlliance() != piece.getAlliance();
    }
}
