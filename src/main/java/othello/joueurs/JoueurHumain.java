package othello.joueurs;

import othello.model.ModelOthello;
import othello.model.Couleurs;
import othello.model.Controleur;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 09:42:04
 */
public class JoueurHumain extends JoueurNormal {

	public JoueurHumain(ModelOthello model, Couleurs couleur, Controleur controleur)
	{
		super(model, couleur, controleur);
	}

	public void clique(int no_ligne, int no_colonne) {
		if(joue)
		{
			setChoixCase(no_ligne,no_colonne);
		}
	}
}
