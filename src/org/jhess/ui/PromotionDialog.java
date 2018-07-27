package org.jhess.ui;

import org.jhess.core.Alliance;
import org.jhess.core.pieces.PieceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

public class PromotionDialog extends JDialog {

    private final JPanel panel = new JPanel();
    private PieceType selectedPieceType;

    public PromotionDialog(Alliance alliance) {
        setTitle("Select a piece to promote to.");
        setLayout(new BorderLayout());
        setModal(true);

        setSize(250, 120);
        setResizable(false);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        WindowUtils.centerWindow(this);

        initPanel(alliance);

        setVisible(true);
    }

    private void initPanel(Alliance alliance) {
        panel.setLayout(new GridLayout(1, 4));

        String bishopPath = MessageFormat.format("resources/images/pieces/{0}/bishop.png", alliance.toString().toLowerCase());
        String knightPath = MessageFormat.format("resources/images/pieces/{0}/knight.png", alliance.toString().toLowerCase());
        String rookPath = MessageFormat.format("resources/images/pieces/{0}/rook.png", alliance.toString().toLowerCase());
        String queenPath = MessageFormat.format("resources/images/pieces/{0}/queen.png", alliance.toString().toLowerCase());

        addPieceLabel(bishopPath, PieceType.BISHOP);
        addPieceLabel(knightPath, PieceType.KNIGHT);
        addPieceLabel(rookPath, PieceType.ROOK);
        addPieceLabel(queenPath, PieceType.QUEEN);

        panel.validate();
        panel.repaint();

        add(panel);
    }

    private void addPieceLabel(String imagePath, PieceType pieceType){
        try{
            BufferedImage image = ImageIO.read(new File(imagePath));
            JLabel pieceLabel = new JLabel(new ImageIcon(image));

            pieceLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedPieceType = pieceType;
                    PromotionDialog.this.setVisible(false);
                }
            });

            panel.add(pieceLabel);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public PieceType getSelectedPieceType() {
        return selectedPieceType;
    }
}
