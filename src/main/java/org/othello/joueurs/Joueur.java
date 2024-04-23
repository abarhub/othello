package org.othello.joueurs;

import org.othello.gui.CliqueListener;
import org.othello.model.Couleurs;

import java.awt.geom.Point2D;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 09:40:36
 */
public interface Joueur extends CliqueListener {

    void setJoue(boolean joue);

    Couleurs getCouleur();

    void reflechie();

}
