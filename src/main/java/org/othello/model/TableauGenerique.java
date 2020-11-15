package org.othello.model;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 13:58:23
 * To change this template use File | Settings | File Templates.
 */
public interface TableauGenerique {

    public Couleurs get(int no_ligne, int no_colonne);

    public void set(Couleurs couleur, int no_ligne, int no_colonne);

    public int getNbLigne();

    public int getNbColonne();

    void undo();
}
