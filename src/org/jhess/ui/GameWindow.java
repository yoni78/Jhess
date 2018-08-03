package org.jhess.ui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameWindow {

    private final Stage stage = new Stage();
    private final BoardPane boardPane = new BoardPane();

    public GameWindow() {
        stage.setTitle("Jhess - Two Player Game");

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
