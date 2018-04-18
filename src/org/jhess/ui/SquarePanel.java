package org.jhess.ui;

import org.jhess.core.board.Board;
import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;
import org.jhess.utils.SquareUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

class SquarePanel extends JPanel {
    private final int squareId;
    private BoardPanel boardPanel;

    SquarePanel(BoardPanel boardPanel, int squareId, Dimension dimension) {
        super(new GridBagLayout());

        this.squareId = squareId;
        this.boardPanel = boardPanel;

        setPreferredSize(dimension);
        addMouseControls();

        assignSquareColor();
        assignSquarePieceIcon(this.boardPanel.getBoard());

        validate();
    }

    // TODO: Move both assign methods to BoardPanel

    private void assignSquareColor() {
        Color lightTileColor = Color.decode("#FFFACD");
        Color darkTileColor = Color.decode("#593E1A");

        int tileRow = SquareUtils.getSquareRow(squareId);
        int tileCol = SquareUtils.getSquareFile(squareId);

        boolean isRowEven = tileRow % 2 == 0;
        boolean isColEven = tileCol % 2 == 0;

        setBackground(isRowEven == isColEven ? darkTileColor : lightTileColor);
    }

    private void assignSquarePieceIcon(Board board) {
        removeAll();

        String pathPattern = "resources/images/pieces/{0}/{1}.png";
        Square square = board.getSquare(squareId);

        if (square.isOccupied()) {
            Piece piece = square.getPiece();

            String alliance = piece.getAlliance().toString().toLowerCase();
            String pieceType = piece.getClass().getSimpleName().toLowerCase();

            try {
                BufferedImage image = ImageIO.read(new File(MessageFormat.format(pathPattern, alliance, pieceType)));
                add(new JLabel(new ImageIcon(image)));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addMouseControls (){
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boardPanel.handleSquareClicked(e, squareId);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    void drawSquare(Board board){
        assignSquareColor();
        assignSquarePieceIcon(board);
        validate();
        repaint();
    }
}
