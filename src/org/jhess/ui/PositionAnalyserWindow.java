package org.jhess.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PositionAnalyserWindow {

    private final Stage stage = new Stage();
    private final BoardPane boardPane = new BoardPane();

    public PositionAnalyserWindow() {
        stage.setTitle("Jhess - Position Analyser");

        Scene boardScene = new Scene(boardPane.getGridPane());

        stage.setMinHeight(600);
        stage.setMinWidth(600);

        stage.setHeight(600);
        stage.setWidth(600);

        stage.setScene(boardScene);
    }

    public Stage getStage() {
        return stage;
    }

    public BoardPane getBoardPane() {
        return boardPane;
    }
}
