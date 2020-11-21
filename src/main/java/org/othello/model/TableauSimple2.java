package org.othello.model;

import org.othello.joueurs.Couple;
import org.othello.utils.CheckUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 14:02:04
 * To change this template use File | Settings | File Templates.
 */
public class TableauSimple2 implements TableauGenerique {

    private final int nb_ligne, nb_colonne;
    //private Couleurs tab[][];
    private long val_noir;
    private long val_blanc;

    public TableauSimple2(int nb_ligne, int nb_colonne) {
        CheckUtils.checkArgument (nb_ligne <= 8);
        CheckUtils.checkArgument (nb_colonne <= 8);
        this.nb_ligne = nb_ligne;
        this.nb_colonne = nb_colonne;
        //tab=new Couleurs[nb_ligne][nb_colonne];
        val_noir = 0L;
        val_blanc = 0L;
    }

    public TableauSimple2(TableauGenerique couleur_pions, int nbLignes, int nbColonnes) {
        this.nb_ligne = nbLignes;
        this.nb_colonne = nbColonnes;
		/*tab=new Couleurs[nb_ligne][nb_colonne];
		for(int i=0;i<nbLignes;i++)
		{
			for(int j=0;j<nbColonnes;j++)
			{
				tab[i][j]=couleur_pions.get(i,j);
			}
		}*/
        if (couleur_pions instanceof TableauSimple2) {
            TableauSimple2 tmp;
            tmp = (TableauSimple2) couleur_pions;
            val_noir = tmp.val_noir;
            val_blanc = tmp.val_blanc;
        } else {
            val_noir = 0L;
            val_blanc = 0L;
            Couleurs c;
            for (int i = 0; i < nbLignes; i++) {
                for (int j = 0; j < nbColonnes; j++) {
                    c = couleur_pions.get(i, j);
                    if (c != null) {
                        if (c == CouleursJoueurs.Noir) {
                            set(c, i, j);
                        } else if (c == CouleursJoueurs.Blanc) {
                            set(c, i, j);
                        } else {
                            CheckUtils.checkArgument (false,  "c=" + c);
                        }
                    }
                }
            }
        }
    }

    private Map<Couple, Couleurs> map_couleurs;

    public Couleurs get(int no_ligne, int no_colonne) {
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_ligne < this.nb_ligne);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_colonne < this.nb_colonne);
        final boolean methode2 = false;
        //return tab[no_ligne][no_colonne];
        if (methode2) {
            Couple coup;
            Couleurs c;
            if (map_couleurs == null) {
                map_couleurs = new HashMap<Couple, Couleurs>();
            }
            coup = new Couple(no_ligne, no_colonne);
            if (map_couleurs.containsKey(coup)) {
                return map_couleurs.get(coup);
            } else {
                if (get_bit(val_noir, no_ligne, no_colonne, nb_colonne)) {
                    c = CouleursJoueurs.Noir;
                    map_couleurs.put(coup, c);
                    return c;
                } else if (get_bit(val_blanc, no_ligne, no_colonne, nb_colonne)) {
                    c = CouleursJoueurs.Blanc;
                    map_couleurs.put(coup, c);
                    return c;
                } else
                    return null;
            }
        } else {
            if (get_bit(val_noir, no_ligne, no_colonne, nb_colonne))
                return CouleursJoueurs.Noir;
            else if (get_bit(val_blanc, no_ligne, no_colonne, nb_colonne))
                return CouleursJoueurs.Blanc;
            else
                return null;
        }
    }

    public void set(Couleurs couleur, int no_ligne, int no_colonne) {
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_ligne < this.nb_ligne);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_colonne < this.nb_colonne);
        if (couleur == null) {
            val_noir = set_bit(val_noir, no_ligne, no_colonne, false, nb_colonne);
            val_blanc = set_bit(val_blanc, no_ligne, no_colonne, false, nb_colonne);
        } else if (couleur == CouleursJoueurs.Noir) {
            val_noir = set_bit(val_noir, no_ligne, no_colonne, true, nb_colonne);
            val_blanc = set_bit(val_blanc, no_ligne, no_colonne, false, nb_colonne);
        } else if (couleur == CouleursJoueurs.Blanc) {
            val_noir = set_bit(val_noir, no_ligne, no_colonne, false, nb_colonne);
            val_blanc = set_bit(val_blanc, no_ligne, no_colonne, true, nb_colonne);
        } else {
            CheckUtils.checkArgument (false);
        }
        //tab[no_ligne][no_colonne]=couleur;
    }

    public static boolean get_bit(long val, int no_ligne, int no_colonne, int nb_colonne) {
        long pos, tmp2;
        long tmp;
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_ligne < nb_colonne);
        pos = no_ligne * nb_colonne + no_colonne;
        tmp2 = 1L << pos;
        return (val & tmp2) != 0;
    }

    public static long set_bit(long val, int no_ligne, int no_colonne, boolean b_true, int nb_colonne) {
        long pos, tmp2;
        long tmp;
        CheckUtils.checkArgument (no_ligne >= 0);
        CheckUtils.checkArgument (no_colonne >= 0);
        CheckUtils.checkArgument (no_ligne < nb_colonne);
        pos = no_ligne * nb_colonne + no_colonne;
        tmp2 = 1L << pos;
        tmp = val;
        if (b_true) {
            tmp = tmp | tmp2;
        } else {
            tmp = tmp & ~tmp2;
        }
        return tmp;
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