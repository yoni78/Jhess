package org.jhess.ui;

import java.awt.event.MouseEvent;
import java.util.EventObject;

// TODO: 2018-05-21 Delete?
public class SquareClickedEvent extends EventObject {
    private final MouseEvent mouseEvent;
    private final int rank;
    private final int file;

    /**
     * Constructs a square clicked Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public SquareClickedEvent(Object source, MouseEvent mouseEvent, int rank, int file) {
        super(source);

        this.rank = rank;
        this.file = file;
        this.mouseEvent = mouseEvent;
    }

    public int getRank() {
        return rank;
    }

    public int getFile() {
        return file;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }
}
