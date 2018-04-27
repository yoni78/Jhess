package org.jhess.core.moves;

import org.jhess.core.board.Square;

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

    public Move(Square srcSquare, Square destSquare) {
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
