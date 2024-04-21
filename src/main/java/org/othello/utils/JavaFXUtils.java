package org.othello.utils;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import org.controlsfx.tools.Borders;

public class JavaFXUtils {

    /**
     * Ajout un bord etched
     * @param node le node qui sera a l'interieur du node
     * @param title le titre du pane
     * @return Le node avec le bord etched
     */
    public static Node etchedBorder(Node node, String title) {
        return Borders.wrap(node)
                .etchedBorder()
                .title(title)
                .highlight(Color.gray(0.8))
                .shadow(Color.gray(0.2))
//                .shadow(Color.GRAY)
                .raised()
                .buildAll();
    }

}
