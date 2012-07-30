package othello.model;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 14:02:04
 * To change this template use File | Settings | File Templates.
 */
public class TableauSimple implements TableauGenerique {

	private final int nb_ligne, nb_colonne;
	private Couleurs tab[][];

	public TableauSimple(int nb_ligne,int nb_colonne)
	{
		this.nb_ligne = nb_ligne;
		this.nb_colonne = nb_colonne;
		tab=new Couleurs[nb_ligne][nb_colonne];
	}

	public TableauSimple(TableauGenerique couleur_pions, int nbLignes, int nbColonnes) {
		this.nb_ligne = nbLignes;
		this.nb_colonne = nbColonnes;
		tab=new Couleurs[nb_ligne][nb_colonne];
		for(int i=0;i<nbLignes;i++)
		{
			for(int j=0;j<nbColonnes;j++)
			{
				tab[i][j]=couleur_pions.get(i,j);
			}
		}
	}

	public Couleurs get(int no_ligne, int no_colonne) {
		assert(no_ligne>=0);
		assert(no_ligne<this.nb_ligne);
		assert(no_colonne>=0);
		assert(no_colonne<this.nb_colonne);
		return tab[no_ligne][no_colonne];
	}

	public void set(Couleurs couleur, int no_ligne, int no_colonne) {
		assert(no_ligne>=0);
		assert(no_ligne<this.nb_ligne);
		assert(no_colonne>=0);
		assert(no_colonne<this.nb_colonne);
		tab[no_ligne][no_colonne]=couleur;
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
