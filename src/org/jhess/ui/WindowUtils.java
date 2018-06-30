package org.jhess.ui;

import java.awt.*;

public final class WindowUtils {
    private WindowUtils() {
    }

    /**
     * Centers the given window.
     *
     * @param window The window to center.
     */
    public static void centerWindow(Window window) {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();

        int xPos = (dim.width / 2) - (window.getWidth() / 2);
        int yPos = (dim.height / 2) - (window.getHeight() / 2);

        window.setLocation(xPos, yPos);
    }
}
