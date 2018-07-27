package org.jhess.logic;

import org.jhess.core.Alliance;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.*;

import java.text.MessageFormat;

/**
 * Converts to and from the PGN format.
 */
public class PgnConverter {

    /**
     * Serializers a Piece to PGN format.
     *
     * @param piece The piece to serialize.
     * @return The character which represents the piece.
     */
    public char pieceToPgn(Piece piece) {
        switch (piece.getPieceType()) {
            case PAWN:
                return piece.getAlliance() == Alliance.WHITE ? 'P' : 'p';

            case BISHOP:
                return piece.getAlliance() == Alliance.WHITE ? 'B' : 'b';

            case KNIGHT:
                return piece.getAlliance() == Alliance.WHITE ? 'N' : 'n';

            case ROOK:
                return piece.getAlliance() == Alliance.WHITE ? 'R' : 'r';

            case QUEEN:
                return piece.getAlliance() == Alliance.WHITE ? 'Q' : 'q';

            case KING:
                return piece.getAlliance() == Alliance.WHITE ? 'K' : 'k';

            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid piece type: {0}", piece.getPieceType()));
        }
    }

    /**
     * Deserializes from PGN format to a Piece object.
     *
     * @param pieceChar The character representing the piece.
     * @return The Piece object which is represented by the character.
     */
    public Piece pgnToPiece(char pieceChar) {

        Alliance pieceAlliance = Character.isUpperCase(pieceChar) ? Alliance.WHITE : Alliance.BLACK;

        switch (Character.toLowerCase(pieceChar)) {
            case 'p':
                return new Pawn(pieceAlliance);

            case 'b':
                return new Bishop(pieceAlliance);

            case 'n':
                return new Knight(pieceAlliance);

            case 'r':
                return new Rook(pieceAlliance);

            case 'q':
                return new Queen(pieceAlliance);

            case 'k':
                return new King(pieceAlliance);

            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid piece type: {0}", pieceChar));
        }
    }

    /**
     * Serializers an Alliance to PGN format.
     *
     * @param alliance The Alliance to serialize.
     * @return The character which represents the alliance.
     */
    public char allianceToPgn(Alliance alliance) {
        switch (alliance) {
            case WHITE:
                return 'w';
            case BLACK:
                return 'b';
            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid alliance: {0}", alliance));
        }
    }

    /**
     * Deserializes from PGN format to an Alliance object.
     *
     * @param allianceChar The character representing the piece.
     * @return The Alliance object which is represented by the character.
     */
    public Alliance pgnToAlliance(char allianceChar) {
        switch (allianceChar) {
            case 'w':
                return Alliance.WHITE;
            case 'b':
                return Alliance.BLACK;
            default:
                throw new IllegalArgumentException(MessageFormat.format("Invalid alliance: {0}", allianceChar));
        }
    }

    /**
     * Serializers a Square to PGN format.
     *
     * @param square The piece to serialize.
     * @return The string which represents the square.
     */
    public String squareToPgn(Square square) {
        char rank = (char) (square.getRank() + 64);
        char file = Integer.toString(square.getFile()).charAt(0);

        return MessageFormat.format("{0}{1}", file, rank);
    }

    /**
     * Deserializes from PGN format to a Square object.
     *
     * @param squareString The string representing the square.
     * @return The Square object which is represented by the string.
     */
    public Square pgnToSquare(String squareString){
        int rank = Integer.parseInt(squareString.substring(1, 2)) - 1;
        int file = (squareString.charAt(0) - 97);

        return new Square(rank, file);
    }
}
