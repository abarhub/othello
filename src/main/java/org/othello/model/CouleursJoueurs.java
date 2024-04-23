package org.othello.model;

import org.othello.utils.CheckUtils;

import java.awt.*;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:24:02
 */
public enum CouleursJoueurs implements Couleurs {
    Noir(Color.BLACK, javafx.scene.paint.Color.BLACK), Blanc(Color.WHITE, javafx.scene.paint.Color.WHITE);

    CouleursJoueurs(Color couleur, javafx.scene.paint.Color couleurJfx) {
        CheckUtils.checkArgument (couleur != null);
        CheckUtils.checkArgument (couleurJfx != null);
        this.couleur = couleur;
        this.couleurJfx=couleurJfx;
    }

    private Color couleur;

    private javafx.scene.paint.Color couleurJfx;

    @Override
    public Color getColor() {
        return couleur;
    }

    @Override
    public javafx.scene.paint.Color getJfxColor() {
        return couleurJfx;
    }
}
