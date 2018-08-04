package org.jhess.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.jhess.core.Game;

public class MainMenu {
    public Button btnExit;

    public void btnExitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnPositionAnalyserClicked(ActionEvent actionEvent) {
        Game game = new Game();
        PositionAnalyserWindow gameWindow = new PositionAnalyserWindow();
        PositionAnalyserController gameController = new PositionAnalyserController(game, gameWindow);

        gameWindow.getStage().show();
    }

    public void btnTwoPlayerGameClicked(ActionEvent actionEvent) {
        Game game = new Game();
        GameWindow gameWindow = new GameWindow();
        GameController gameController = new GameController(game, gameWindow);

        gameWindow.getStage().show();
    }
}
