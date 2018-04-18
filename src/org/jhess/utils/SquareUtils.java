package org.jhess.utils;

public final class SquareUtils {
    private SquareUtils() {
    }

    /**
     * Returns the number of the row the square is on by his ID.
     *
     * @param squareId The ID of the square.
     * @return Row number.
     */
    public static int getSquareRow(int squareId) {

        if (squareId < 0 || squareId > 63) {
            return -1;
        }

        return squareId / 8;
    }

    /**
     * Returns the number of the file the square is on by his ID.
     *
     * @param squareId The ID of the square.
     * @return File number.
     */
    public static int getSquareFile(int squareId) {
        if (squareId < 0 || squareId > 63) {
            return -1;
        }

        return squareId % 8;
    }

    /**
     * Returns the ID of the square by his row and file numbers.
     *
     * @param squareRow The number of the row the square is on.
     * @param squareFile The number of the file the square is on.
     * @return The ID of the square.
     */
    public static int getSquareId(int squareRow, int squareFile) {
        return squareRow * 8 + squareFile;
    }

    /**
     * Determines whether the given square is a valid square.
     * @param squareId The ID of the square.
     * @return If the square is in the board or not.
     */
    public static boolean isInBoard(int squareId){
        return squareId >= 0 && squareId <= 63;
    }
}
