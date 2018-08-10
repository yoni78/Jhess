package org.jhess.logic.engine;

import com.google.common.collect.Iterables;

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
        List<String> lines = new ArrayList<>();

        while (true) {
            String line = input.readLine();
            lines.add(line);

            if (line.startsWith(expected))
                return lines;
        }
    }

    /**
     * Tells the engine to use the Universal Chess Interface.
     *
     * @throws IOException If it couldn't write to the engine.
     */
    public void useUci() throws IOException {
        // TODO: 2018-08-04 Store all available options?
        sendCommand("uci");
        List<String> response = readResponse("uciok");
    }

    public void setPosition(String position) throws IOException{
        sendCommand(MessageFormat.format("{0} fen {1}", "position", position)); // TODO: 2018-08-10 Switch to using startpos + moves
    }

    public String getBestMove(String position) throws IOException {
        setPosition(position);

        sendCommand("go");
        List<String> response = readResponse("bestmove");

        return Iterables.getLast(response).split(" ")[1];
    }
}
