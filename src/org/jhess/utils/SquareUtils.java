package org.jhess.utils;

public final class SquareUtils {
    private SquareUtils() {
    }

    public static int getSquareRow(int squareId){

        if (squareId < 0 || squareId > 63){
            return -1;
        }

        return squareId / 8;
    }

    public static int getSquareFile(int squareId){
        if (squareId < 0 || squareId > 63){
            return -1;
        }

        return squareId % 8;
    }
}
