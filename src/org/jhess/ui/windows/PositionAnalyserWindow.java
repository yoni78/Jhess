package org.jhess.ui.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jhess.ui.panes.BoardPane;

public class PositionAnalyserWindow {

    private final Stage stage = new Stage();
    private final BoardPane boardPane = new BoardPane();

    Button btnFlip = new Button("Flip Board");
    Button btnEngine = new Button("Engine: off");
    Button btnBack = new Button("<");
    Button btnFwd = new Button(">");
    Button btnRewind = new Button("<<");
    Button btnCurrentPos = new Button(">>");
    Button btnContinue = new Button("Continue from here");

    public PositionAnalyserWindow() {
        stage.setTitle("Jhess - Position Analyser");

        BorderPane borderPane = new BorderPane();

        AnchorPane anchorPane = new AnchorPane(boardPane.getGridPane());
        AnchorPane.setTopAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setBottomAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setRightAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setLeftAnchor(boardPane.getGridPane(), 0.0);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5));
        vBox.getChildren().addAll(btnFlip, btnEngine);

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(5));
        hBox.getChildren().addAll(btnRewind, btnBack, btnFwd, btnCurrentPos, btnContinue);

        borderPane.setCenter(anchorPane);
        borderPane.setRight(vBox);
        borderPane.setBottom(hBox);

        Scene boardScene = new Scene(borderPane);

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

    public Button getBtnFlip() {
        return btnFlip;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public Button getBtnFwd() {
        return btnFwd;
    }

    public Button getBtnRewind() {
        return btnRewind;
    }

    public Button getBtnCurrentPos() {
        return btnCurrentPos;
    }

    public Button getBtnEngine() {
        return btnEngine;
    }

    public Button getBtnContinue() {
        return btnContinue;
    }
}
