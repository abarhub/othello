package othello.model;

import java.awt.*;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:24:02
 */
public enum CouleursJoueurs implements Couleurs {
	Noir(Color.BLACK),Blanc(Color.WHITE);

	private CouleursJoueurs(Color couleur)
	{
		assert(couleur!=null);
		this.couleur= couleur;
	}

	private Color couleur;

	public Color getColor(){
		return couleur;
	}
}
