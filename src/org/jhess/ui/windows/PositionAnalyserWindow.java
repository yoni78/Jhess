package org.jhess.ui.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jhess.ui.components.GameMoveListItem;
import org.jhess.ui.panes.BoardPane;

public class PositionAnalyserWindow {

    private final Stage stage = new Stage();
    private final BoardPane boardPane = new BoardPane();

    Button btnFlip = new Button("Flip Board");
    Button btnEngine = new Button("engine: off");
    Button btnBack = new Button("<");
    Button btnFwd = new Button(">");
    Button btnRewind = new Button("<<");
    Button btnCurrentPos = new Button(">>");
    Button btnContinue = new Button("Continue from here");

    MenuBar menuBar = new MenuBar();

    Menu engineMenu = new Menu("Engine");
    MenuItem engineSelectMenuItem = new MenuItem("Select An Engine");
    MenuItem engineInfoMenuItem = new MenuItem("Show Selected Engine Information");

    Menu fileMenu = new Menu("File");

    MenuItem savePgnMenuItem = new MenuItem("Save Game As PGN");

    ListView<GameMoveListItem> moveList = new ListView<>();

    // TODO: 2018-11-10 Split into 'init' methods
    public PositionAnalyserWindow() {
        stage.setTitle("Jhess - Position Analyser");

        BorderPane borderPane = new BorderPane();

        AnchorPane anchorPane = new AnchorPane(boardPane.getGridPane());
        AnchorPane.setTopAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setBottomAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setRightAnchor(boardPane.getGridPane(), 0.0);
        AnchorPane.setLeftAnchor(boardPane.getGridPane(), 0.0);

        moveList.setPrefWidth(100);

        AnchorPane moveAnchorPane = new AnchorPane();
        moveAnchorPane.getChildren().add(moveList);
        moveAnchorPane.setPadding(new Insets(10));

        AnchorPane.setTopAnchor(moveList, 0.0);
        AnchorPane.setBottomAnchor(moveList, 0.0);
        AnchorPane.setRightAnchor(moveList, 0.0);
        AnchorPane.setLeftAnchor(moveList, 0.0);

        HBox controlsBox = new HBox();
        controlsBox.setPadding(new Insets(10));
        controlsBox.setSpacing(5);
        controlsBox.getChildren().addAll(btnRewind, btnBack, btnFwd, btnCurrentPos, btnContinue, btnFlip, btnEngine);

        menuBar.prefWidthProperty().bind(stage.widthProperty());
        menuBar.getMenus().addAll(fileMenu, engineMenu);
        fileMenu.getItems().addAll(savePgnMenuItem);
        engineMenu.getItems().addAll(engineSelectMenuItem, engineInfoMenuItem);

        borderPane.setTop(menuBar);
        borderPane.setCenter(anchorPane);
        borderPane.setRight(moveAnchorPane);
        borderPane.setBottom(controlsBox);

        Scene boardScene = new Scene(borderPane);

        stage.setMinHeight(600);
        stage.setMinWidth(650);

        stage.setHeight(600);
        stage.setWidth(650);

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

    public MenuItem getEngineSelectMenuItem() {
        return engineSelectMenuItem;
    }

    public MenuItem getEngineInfoMenuItem() {
        return engineInfoMenuItem;
    }

    public MenuItem getSavePgnMenuItem() {
        return savePgnMenuItem;
    }
}
