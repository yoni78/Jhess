package org.jhess.core.moves;

import org.jhess.core.board.Square;

public class MoveVector {

    public static final MoveVector FORWARD = new MoveVector(1, 0);
    public static final MoveVector BACKWARD = new MoveVector(-1, 0);
    public static final MoveVector RIGHT = new MoveVector(0, 1);
    public static final MoveVector LEFT = new MoveVector(0, -1);
    public static final MoveVector FORWARD_RIGHT = new MoveVector(1, 1);
    public static final MoveVector FORWARD_LEFT = new MoveVector(1, -1);
    public static final MoveVector BACKWARD_RIGHT = new MoveVector(-1, 1);
    public static final MoveVector BACKWARD_LEFT = new MoveVector(-1, -1);

    public static final MoveVector KNIGHT_FORWARD_CLOSE_RIGHT = new MoveVector(1, 2);
    public static final MoveVector KNIGHT_FORWARD_CLOSE_LEFT = new MoveVector(1, -2);
    public static final MoveVector KNIGHT_FORWARD_FAR_RIGHT = new MoveVector(2, 1);
    public static final MoveVector KNIGHT_FORWARD_FAR_LEFT = new MoveVector(2, -1);
    public static final MoveVector KNIGHT_BACKWARD_CLOSE_RIGHT = new MoveVector(-1, 2);
    public static final MoveVector KNIGHT_BACKWARD_CLOSE_LEFT = new MoveVector(-1, -2);
    public static final MoveVector KNIGHT_BACKWARD_FAR_RIGHT = new MoveVector(-2, 1);
    public static final MoveVector KNIGHT_BACKWARD_FAR_LEFT = new MoveVector(-2, -1);

    private final int rankToAdvance;
    private final int fileToAdvance;

    public MoveVector(int rankToAdvance, int fileToAdvance) {
        this.rankToAdvance = rankToAdvance;
        this.fileToAdvance = fileToAdvance;
    }

    public MoveVector(Square srcSquare, Square destSquare) {
        this(destSquare.getRank() - srcSquare.getRank(),
                destSquare.getFile() - srcSquare.getFile());
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
    public MoveVector extend(int modifier) {
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

        return new MoveVector(newRankToAdvance, newFileToAdvance);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MoveVector &&
                rankToAdvance == ((MoveVector) obj).rankToAdvance &&
                fileToAdvance == ((MoveVector) obj).fileToAdvance;
    }
}
