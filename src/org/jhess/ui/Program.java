package org.jhess.ui;
// TODO: Change package name to org.*name*.jhess...?
// TODO: 2018-07-10 Add unit tests
// TODO: 2018-08-03 Add exception dialogs

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Program extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));

        Scene menuScene = new Scene(root);

        primaryStage.setTitle("Jhess");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
