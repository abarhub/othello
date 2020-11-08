package othello.model;

import othello.joueurs.Joueur;

/**
 * User: Barret
 * Date: 9 déc. 2009
 * Time: 21:53:40
 */
public interface EtatJeuxListener {

	public void changement_joueur(Joueur joueur);

	public void joueur_bloque(Joueur joueur);

	public void fin_partie();

	public void case_incorrecte(Couleurs couleur, int no_ligne, int no_colonne);

    public void joueur_joue(Couleurs couleur, int no_ligne, int no_colonne);
}
