package org.jhess.ui;

import java.awt.event.MouseEvent;

@FunctionalInterface
public interface SquareClickHandler {
    void handleSquareClicked(MouseEvent e, int squareId);
}
