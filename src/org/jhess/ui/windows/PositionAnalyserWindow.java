package org.jhess.ui.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jhess.ui.GameMoveListItem;
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

    ListView<GameMoveListItem> moveList = new ListView<>();

    public PositionAnalyserWindow() {
        stage.setTitle("Jhess - Position Analyser");

        BorderPane borderPane = new BorderPane();

        AnchorPane anchorPane = new AnchorPane(boardPane.getGridPane());
        AnchorPane.setTopAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setBottomAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setRightAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setLeftAnchor(boardPane.getGridPane(), 0.0);

        moveList.setPadding(new Insets(10));

        HBox controlsBox = new HBox();
        controlsBox.setPadding(new Insets(10));
        controlsBox.setSpacing(5);
        controlsBox.getChildren().addAll(btnRewind, btnBack, btnFwd, btnCurrentPos, btnContinue, btnFlip, btnEngine);

        borderPane.setCenter(anchorPane);
        borderPane.setRight(moveList);
        borderPane.setBottom(controlsBox);

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

    public ListView<GameMoveListItem> getMoveList() {
        return moveList;
    }
}
