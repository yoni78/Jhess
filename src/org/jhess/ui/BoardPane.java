package org.jhess.ui;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.jhess.core.board.Board;
import org.jhess.core.pieces.Piece;

import java.text.MessageFormat;

/**
 * Represents a chess board in the UI.
 */
public class BoardPane {

    private final GridPane gridPane = new GridPane();
    private EventHandler<MouseEvent> eventHandler;

    public GridPane getGridPane() {
        return gridPane;
    }

    public void drawBoard(Board board, boolean reverse) {
        gridPane.getChildren().removeAll();

        final int size = 8;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int rank;
                int file;

                if (!reverse){
                    rank = 7 - i;
                    file = j;

                } else {
                    rank = i;
                    file = 7 - j;
                }

                SquarePane square = new SquarePane(board.getSquares()[i][j]);

                setSquareColor(square);

                setSquareImage(square);

                square.getRectangle().widthProperty().bind(gridPane.widthProperty().divide(size));
                square.getRectangle().heightProperty().bind(gridPane.heightProperty().divide(size));

                square.setOnMouseClicked(this::squareClicked);

                gridPane.add(square, file, rank);
            }
        }
    }

    public void drawBoard(Board board) {
        drawBoard(board, false);
    }

    public void setOnMouseClicked(EventHandler<MouseEvent> eventHandler){
        this.eventHandler = eventHandler;
    }

    private void squareClicked(MouseEvent mouseEvent) {
        eventHandler.handle(mouseEvent);
    }

    private void setSquareColor(SquarePane square) {
        int rank = square.getSquare().getRank();
        int file = square.getSquare().getFile();

        Color color = ((rank + file) % 2 == 0) ? Color.valueOf("#FFFACD") : Color.valueOf("#593E1A");
        square.getRectangle().setFill(color);
    }

    private void setSquareImage(SquarePane square) {
        Piece piece = square.getSquare().getPiece();

        if (piece != null) {
            String pathPattern = "/images/pieces/{0}/{1}.png";
            String alliance = piece.getAlliance().toString().toLowerCase();
            String pieceType = piece.getPieceType().toString().toLowerCase();

            Image pieceImage = new Image(getClass().getResource(MessageFormat.format(pathPattern, alliance, pieceType)).toExternalForm());

            square.getImageView().setImage(pieceImage);
        }
    }

}
