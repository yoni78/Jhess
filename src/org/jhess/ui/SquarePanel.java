package org.jhess.ui;

import org.jhess.core.board.Square;
import org.jhess.core.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square in the UI.
 */
class SquarePanel extends JPanel {

    private final Square square;
    private final List<SquareClickedListener> listenerList = new ArrayList<>();

    public SquarePanel(BoardPanel boardPanel, Square square, Dimension dimension) {
        super(new GridBagLayout());

        this.square = square;

        setPreferredSize(dimension);
        addMouseControls();

        drawSquare();

        validate();
    }

    private synchronized void fireSquareClickedEvent(MouseEvent mouseEvent){
        SquareClickedEvent event = new SquareClickedEvent(this, mouseEvent, square.getRank(), square.getFile());
        for (SquareClickedListener listener : listenerList){
            listener.handleSquareClicked(event);
        }
    }

    private void addMouseControls() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fireSquareClickedEvent(e);
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

    private void assignSquareColor() {
        Color lightTileColor = Color.decode("#FFFACD");
        Color darkTileColor = Color.decode("#593E1A");

        boolean isRowEven = square.getRank() % 2 == 0;
        boolean isColEven = square.getFile() % 2 == 0;

        setBackground(isRowEven == isColEven ? darkTileColor : lightTileColor);
    }

    private void assignSquarePieceIcon() {
        removeAll();

        String pathPattern = "resources/images/pieces/{0}/{1}.png";

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

    public Square getSquare() {
        return square;
    }

    public void highLightBorder() {
        setBorder(BorderFactory.createLineBorder(Color.cyan));
    }

    public void removeHighLight(){
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void drawSquare() {
        assignSquareColor();
        assignSquarePieceIcon();

        validate();
        repaint();
    }

    public synchronized void addSquareClickedListener(SquareClickedListener l){
        listenerList.add(l);
    }

    public synchronized void removeSquareClickedListener(SquareClickedListener l){
        listenerList.remove(l);
    }
}
