package org.jhess.ui.windows;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jhess.core.Alliance;
import org.jhess.core.pieces.PieceType;

import java.text.MessageFormat;

public class PromotionWindow {

    private final Stage stage = new Stage();
    private final HBox hBox = new HBox(5);

    private PieceType selectedPieceType;

    public PromotionWindow(Alliance alliance) {
        stage.setTitle("Select a piece to promote to");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setHeight(120);
        stage.setWidth(280);
        stage.setResizable(false);

        stage.setScene(new Scene(hBox));

        String bishopPath = MessageFormat.format("/images/pieces/{0}/bishop.png", alliance.toString().toLowerCase());
        String knightPath = MessageFormat.format("/images/pieces/{0}/knight.png", alliance.toString().toLowerCase());
        String rookPath = MessageFormat.format("/images/pieces/{0}/rook.png", alliance.toString().toLowerCase());
        String queenPath = MessageFormat.format("/images/pieces/{0}/queen.png", alliance.toString().toLowerCase());

        addPieceImage(bishopPath, PieceType.BISHOP);
        addPieceImage(knightPath, PieceType.KNIGHT);
        addPieceImage(rookPath, PieceType.ROOK);
        addPieceImage(queenPath, PieceType.QUEEN);

        stage.showAndWait();
    }

    public PieceType getSelectedPieceType() {
        return selectedPieceType;
    }

    private void addPieceImage(String imagePath, PieceType pieceType) {
        String imageUrl = getClass().getResource(imagePath).toExternalForm();
        ImageView imageView = new ImageView(new Image(imageUrl));

        imageView.setOnMouseClicked(event -> {
            selectedPieceType = pieceType;
            stage.close();
        });

        hBox.getChildren().add(imageView);
    }
}
