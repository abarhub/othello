package org.othello.model;

import org.othello.joueurs.Couple;
import org.othello.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 14:00:16
 * To change this template use File | Settings | File Templates.
 */
public class TableauMap implements TableauGenerique {

    private final int nb_ligne, nb_colonne;
    private final Map<Couple, Couleurs> map;

    public TableauMap(int nb_ligne, int nb_colonne) {
        this.nb_ligne = nb_ligne;
        this.nb_colonne = nb_colonne;
        map = new HashMap<>();
    }


    public TableauMap(TableauGenerique couleur_pions, int nbLignes, int nbColonnes) {
        this.nb_ligne = nbLignes;
        this.nb_colonne = nbColonnes;
        map = new HashMap<>();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                Couleurs c;
                c = couleur_pions.get(i, j);
                if (c != null) {
                    map.put(new Couple(i, j), c);
                }
            }
        }
    }

    public Couleurs get(int no_ligne, int no_colonne) {
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_ligne < this.nb_ligne);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_colonne < this.nb_colonne);
        Couple c;
        c = new Couple(no_ligne, no_colonne);
        if (map.containsKey(c))
            return map.get(c);
        else
            return null;
    }

    public void set(Couleurs couleur, int no_ligne, int no_colonne) {
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_ligne < this.nb_ligne);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_colonne < this.nb_colonne);
        Couple c;
        c = new Couple(no_ligne, no_colonne);
        map.put(c, couleur);
    }

    public int getNbLigne() {
        return nb_ligne;
    }

    public int getNbColonne() {
        return nb_colonne;
    }

    public void undo() {

    }
}
