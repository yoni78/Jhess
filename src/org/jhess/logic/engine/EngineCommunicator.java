package org.jhess.logic.engine;

import com.google.common.collect.Iterables;
import org.jhess.core.engine.EngineInfo;

import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Communicates with a UCI compatible chess engine.
 */
public class EngineCommunicator {

    private final Process engineProcess;
    private final BufferedReader input;
    private final BufferedWriter output;

    public EngineCommunicator(String engineBin) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(engineBin);
        processBuilder.redirectErrorStream(true);
        engineProcess = processBuilder.start();

        input = new BufferedReader(new InputStreamReader(engineProcess.getInputStream()));
        output = new BufferedWriter(new OutputStreamWriter(engineProcess.getOutputStream()));
    }

    /**
     * Sends a command to the engine.
     *
     * @param command The command to send.
     * @throws IOException If the send failed.
     */
    private void sendCommand(String command) throws IOException {
        output.write(command + "\n");
        output.flush();
    }

    /**
     * Reads a response from the engine until the point it reaches the expected text.
     *
     * @param expected The text which should be at the end of the output.
     * @return A list containing the lines of the response.
     * @throws IOException If the read failed.
     */
    private List<String> readResponse(String expected) throws IOException {
        // TODO: 2018-08-04 Add a timeout
        // TODO: 2018-11-10 Can you just look for a '\n'?
        List<String> lines = new ArrayList<>();

        while (true) {
            String line = input.readLine();
            lines.add(line);

            if (line.startsWith(expected))
                return lines;
        }
    }

    /**
     * Parses the information lines from a UCI engine.
     *
     * @param infoLines The information lines.
     * @return An EngineInfo object which contains information about the engine.
     */
    private EngineInfo parseEngineInfo(List<String> infoLines) {

        String name = "";
        String author = "";

        for (String line : infoLines) {
            if (line.startsWith("id name")) {
                name = line.substring(8);
            } else if (line.startsWith("id author")) {
                author = line.substring(10);
            }
        }

        return new EngineInfo(name, author);
    }

    /**
     * Tells the engine to use the Universal Chess Interface.
     *
     * @return Info about the engine.
     * @throws IOException If it couldn't write to the engine.
     */
    public EngineInfo useUci() throws IOException {
        // TODO: 2018-08-04 Store all available options?
        sendCommand("uci");
        List<String> response = readResponse("uciok");

        return parseEngineInfo(response);
    }

    /**
     * Tells the engine that it is searching on a game that is hasn't searched on before.
     *
     * @throws IOException If it couldn't write to the engine.
     */
    public void startNewGame() throws IOException {
        sendCommand("ucinewgame");
    }

    /**
     * Sets the position of the internal board of the engine to the given FEN.
     *
     * @param position The position to set.
     * @throws IOException If it couldn't write to the engine.
     */
    public void setPositionWithFen(String position) throws IOException {
        sendCommand(MessageFormat.format("{0} fen {1}", "position", position));
    }

    /**
     * Sets the position of the internal board of the engine to the start position and adding to it the given moves.
     *
     * @param moves The moves played in the game.
     * @throws IOException If it couldn't write to the engine.
     */
    public void setPositionWithMoves(List<String> moves) throws IOException {
        sendCommand("position startpos moves " + String.join(" ", moves));
    }

    /**
     * Gets from the engine the best move for the current position.
     *
     * @return The best move for the current position.
     * @throws IOException If it couldn't write to the engine.
     */
    public String getBestMove() throws IOException {
        sendCommand("go");
        List<String> response = readResponse("bestmove");

        return Iterables.getLast(response).split(" ")[1];
    }

    /**
     * Shuts down the engine process.
     */
    public void quit() {
        try {
            sendCommand("quit");

        } catch (IOException e) {
            e.printStackTrace(); // TODO: 2018-08-17 Log
            engineProcess.destroy();
        }

    }
}
