package org.jhess.logic.pgn;

import org.jhess.core.game.Game;
import org.jhess.core.game.GameDetails;
import org.jhess.core.moves.GameMove;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class PgnWriter {

    private final Game game;

    public PgnWriter(Game game) {
        this.game = game;
    }

    private String generateFileName() {
        // TODO: 2018-12-07 Think of a better name\
        String nameFormat = "jhess_{0}_{1}_{2}.pgn";
        String day = Integer.toString(game.getGameDetails().getDate().getDayOfMonth());
        String month = Integer.toString(game.getGameDetails().getDate().getMonthValue());
        String year = Integer.toString(game.getGameDetails().getDate().getYear());

        return MessageFormat.format(nameFormat, year, month, day);
    }

    private List<String> generateTags() {
        List<String> tagList = new ArrayList<>();

        GameDetails gameDetails = game.getGameDetails();
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getEvent()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getSite()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getDate()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getRound()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getWhite()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getBlack()));
        tagList.add(MessageFormat.format("[{0} \"{1}\"]", gameDetails.getResult()));

        return tagList;
    }

    private List<String> generateMoves() {
        List<String> moveList = new ArrayList<>();
        PgnConverter pgnConverter = new PgnConverter();

        for (GameMove gameMove : game.getMoveList()) {
            moveList.add(pgnConverter.moveToPgn(game, gameMove));
        }

        return moveList;
    }

    /**
     * Adds two half moves to one full move and adds the it's number.
     *
     * @param moveNumber The number of white's half move.
     * @param whiteMove  The PGN string representing the white's move.
     * @param blackMove  The PGN string representing the black's move.
     * @return A numbered PGN move string.
     */
    private String formatMoves(int moveNumber, String whiteMove, String blackMove) {
        return MessageFormat.format("{0}. {1} {2}", moveNumber, whiteMove, blackMove);
    }

    public void writeToFile(String destDir) throws FileNotFoundException {

        String fileName = generateFileName();

        List<String> tags = generateTags();
        List<String> moves = generateMoves();

        StringBuilder pgnString = new StringBuilder();

        // Append the tags
        for (String tag : tags) {
            pgnString.append(tag).append(System.lineSeparator());
        }

        pgnString.append(System.lineSeparator());

        // Append the moves of the game
        StringBuilder line = new StringBuilder();

        // TODO: 2018-12-08 Handle odd number of moves
        for (int i = 0; i < moves.size() - 1; i += 2) {

            if ((line.toString() + " " + formatMoves(i + 1, moves.get(i), moves.get(i + 1))).length() > 120) {
                pgnString.append(line.toString()).append(System.lineSeparator());
                line = new StringBuilder(formatMoves(i + 1, moves.get(i), moves.get(i + 1)));

            } else {
                if (i != 0)
                    line.append(" ");

                line.append(formatMoves(i + 1, moves.get(i), moves.get(i + 1)));

                if (i == moves.size() - 2)
                    pgnString.append(line.toString()).append(System.lineSeparator());
            }
        }

        PrintWriter writer = new PrintWriter(Paths.get(destDir, fileName).toString());
        writer.print(pgnString.toString());
        writer.close();
    }
}
