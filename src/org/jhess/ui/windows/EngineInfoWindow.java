package org.jhess.ui.windows;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jhess.core.engine.EngineInfo;

import java.text.MessageFormat;

public class EngineInfoWindow {

    public EngineInfoWindow(EngineInfo engineInfo) {
        Stage stage = new Stage();
        stage.setTitle("Selected Engine Information");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setHeight(130);
        stage.setWidth(350);
        stage.setResizable(false);

        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(10));
        stage.setScene(new Scene(vBox));

        String name = "-";
        String author = "-";

        if (engineInfo != null) {
            name = engineInfo.getName();
            author = engineInfo.getAuthor();
        }

        Label nameLabel = new Label(MessageFormat.format("Name: {0}", name));
        Label authorLabel = new Label(MessageFormat.format("Author: {0}", author));

        vBox.getChildren().addAll(nameLabel, authorLabel);

        stage.showAndWait();
    }
}
