package org.jhess.ui;


import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import org.jhess.core.board.Square;

/**
 * Represents a square on a chess board in the UI.
 */
public class SquarePane extends StackPane {

    private final Square square;

    //    private final StackPane stackPane = new StackPane(); // TODO: 2018-08-02 Use Composition instead of inheritance (problem: mouse event)
    private final Rectangle rectangle = new Rectangle();
    private final ImageView imageView = new ImageView();

    public SquarePane(Square square) {
        this.square = square;

        rectangle.setStrokeType(StrokeType.INSIDE);
        rectangle.setStrokeWidth(4);

        imageView.fitWidthProperty().bind(rectangle.widthProperty().subtract(10));
        imageView.fitHeightProperty().bind(rectangle.heightProperty().subtract(10));
        imageView.setPreserveRatio(true);

        getChildren().addAll(rectangle, imageView);
    }

    public Square getSquare() {
        return square;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void highLightBorder(Color color) {
        rectangle.setStroke(color);
    }

    public void removeHighLight() {
        rectangle.setStroke(null);
    }
}
