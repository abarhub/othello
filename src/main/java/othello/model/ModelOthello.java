package othello.model;

import com.google.common.base.Preconditions;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:18:58
 */
public class ModelOthello {

	public static final int NB_LIGNES=8;
	public static final int NB_COLONNES=8;

	//private boolean cases_non_vides[][];
	//private Couleurs couleur_pions[][];
	private TableauGenerique couleur_pions;

	public ModelOthello()
	{
		//cases_non_vides=new boolean[NB_LIGNES][getNbColonnes()];
		//couleur_pions = new Couleurs[getNbLignes()][getNbColonnes()];
		couleur_pions=creer_tableau(null,getNbLignes(),getNbColonnes());
	}

	public ModelOthello(ModelOthello m) {
		/*couleur_pions = new Couleurs[m.getNbLignes()][m.getNbColonnes()];
		for(int i=0;i< m.getNbLignes();i++)
		{
			for(int j=0;j< m.getNbColonnes();j++)
			{
				couleur_pions[i][j]=m.get(i,j);
			}
		}*/
		couleur_pions=creer_tableau(m.couleur_pions,getNbLignes(),getNbColonnes());
	}

	private TableauGenerique creer_tableau(TableauGenerique tab,int nb_lignes,int nb_colonnes)
	{
		int methode;
		//methode=1;
		//methode=2;
		//methode=3;
		methode=4;
		if(methode==1)
		{
			if(tab==null)
			{
				return new TableauSimple(nb_lignes,nb_colonnes);
			}
			else
			{
				return new TableauSimple(tab,nb_lignes,nb_colonnes);
			}
		}
		else if(methode==2)
		{
			if(tab==null)
			{
				return new TableauMap(nb_lignes,nb_colonnes);
			}
			else
			{
				return new TableauMap(tab,nb_lignes,nb_colonnes);
			}
		}
		else if(methode==3)
		{
			if(tab==null)
			{
				return new TableauMap2(nb_lignes,nb_colonnes);
			}
			else
			{
				return new TableauMap2(tab,nb_lignes,nb_colonnes);
			}
		}
		else if(methode==4)
		{
			if(tab==null)
			{
				return new TableauSimple2(nb_lignes,nb_colonnes);
			}
			else
			{
				return new TableauSimple2(tab,nb_lignes,nb_colonnes);
			}
		}
		else
		{
			return null;
		}
	}

	public int getNbLignes()
	{
		return NB_LIGNES;
	}

	public int getNbColonnes()
	{
		return NB_COLONNES;
	}

	public void setCouleur(Couleurs couleur,int no_ligne,int no_colonne)
	{
		Preconditions.checkArgument(no_ligne>=0);
		Preconditions.checkArgument (no_ligne < getNbLignes());
		Preconditions.checkArgument (no_colonne >= 0);
		Preconditions.checkArgument (no_colonne < getNbColonnes());
		Preconditions.checkArgument(!cases_non_vides(no_ligne, no_colonne));
		Preconditions.checkArgument(couleur!=null);
		//cases_non_vides[no_ligne][no_colonne]=true;
		//couleur_pions[no_ligne][no_colonne] = couleur;
		couleur_pions.set(couleur,no_ligne,no_colonne);
	}

	public Couleurs get(int no_ligne,int no_colonne)
	{
		Preconditions.checkArgument (no_ligne >= 0);
		Preconditions.checkArgument (no_ligne < getNbLignes());
		Preconditions.checkArgument (no_colonne >= 0);
		Preconditions.checkArgument (no_colonne < getNbColonnes());
		if(cases_non_vides(no_ligne, no_colonne))
			//return couleur_pions[no_ligne][no_colonne];
			return couleur_pions.get(no_ligne,no_colonne);
		else
			return null;
	}

    public boolean VerifCouleur(Couleurs couleur, int no_ligne, int no_colonne)
	{
		Preconditions.checkArgument (couleur != null);
		if(no_ligne>=0&& no_ligne < getNbLignes())
		{
			if(no_colonne >= 0&& no_colonne < getNbColonnes())
			{
				if(!cases_non_vides(no_ligne, no_colonne))
				{
					if(case_valide_bord(couleur,no_ligne,no_colonne))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean SetVerifCouleur(Couleurs couleur, int no_ligne, int no_colonne)
	{
		Preconditions.checkArgument (couleur != null);
		if(no_ligne>=0&& no_ligne < getNbLignes())
		{
			if(no_colonne >= 0&& no_colonne < getNbColonnes())
			{
				if(!cases_non_vides(no_ligne, no_colonne))
				{
					if(case_valide_bord(couleur,no_ligne,no_colonne))
					{
						setCouleur(couleur, no_ligne, no_colonne);
						modifie_lignes(couleur, no_ligne, no_colonne);
						return true;
					}
				}
			}
		}
		return false;
	}

	private void modifie_lignes(Couleurs couleur, int no_ligne, int no_colonne) {
		Preconditions.checkArgument (no_ligne >= 0);
		Preconditions.checkArgument (no_ligne < getNbLignes());
		Preconditions.checkArgument (no_colonne >= 0);
		Preconditions.checkArgument (no_colonne < getNbColonnes());
		Preconditions.checkArgument (couleur != null);
		if (!cases_non_vides(no_ligne, no_colonne)) {
			return;
		}
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (!(x == 0 && y == 0)) {
					if (ligne_prise(couleur, no_ligne, no_colonne, x, y)) {
						modifie_ligne(couleur, no_ligne, no_colonne, x, y);
					}
				}
			}
		}
	}

	private void modifie_ligne(Couleurs couleur, int no_ligne, int no_colonne, int x, int y) {
		int x0,y0;
		boolean fin=false;
		Couleurs tmp;
		x0= no_ligne;
		y0= no_colonne;
		do {
			x0 += x;
			y0 += y;
			if (x0 < 0 || x0 >= getNbLignes()) {
				fin = true;
			} else if (y0 < 0 || y0 >= getNbColonnes()) {
				fin = true;
			} else if (!cases_non_vides(x0, y0)) {
				fin = true;
			} else {
				//tmp = couleur_pions[x0][y0];
				tmp=couleur_pions.get(x0,y0);
				if (tmp == couleur) {
					fin=true;
				}
				else
				{
					//couleur_pions[x0][y0]=couleur;
					couleur_pions.set(couleur,x0,y0);
				}
			}
		} while (!fin);
	}

	private boolean case_valide_bord(Couleurs couleur, int no_ligne, int no_colonne) {
		Preconditions.checkArgument (no_ligne >= 0);
		Preconditions.checkArgument (no_ligne < getNbLignes());
		Preconditions.checkArgument (no_colonne >= 0);
		Preconditions.checkArgument (no_colonne < getNbColonnes());
		Preconditions.checkArgument (couleur != null);
		if(cases_non_vides(no_ligne, no_colonne))
		{
			return false;
		}
		for(int x=-1;x<=1;x++)
		{
			for(int y=-1;y<=1;y++)
			{
				if(!(x==0&&y==0))
				{
					int n;
					if (ligne_prise(couleur, no_ligne, no_colonne, x, y))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean ligne_prise(Couleurs couleur, int no_ligne, int no_colonne, int x, int y) {
		Preconditions.checkArgument(!(x==0&&y==0));
		Preconditions.checkArgument (x >= -1 && x<= 1);
		Preconditions.checkArgument (y >= -1 && y <= 1);
		Preconditions.checkArgument (couleur!=null);
		int x0,y0,nb;
		boolean fin=false;
		Couleurs tmp;
		x0= no_ligne;
		y0= no_colonne;
		nb=0;
		do{
			x0+=x;
			y0+=y;
			if(x0<0||x0>= getNbLignes())
			{
				fin=true;
				break;
			}
			else if(y0 < 0 || y0 >= getNbColonnes())
			{
				fin=true;
				break;
			}
			else if(!cases_non_vides(x0, y0))
			{
				fin=true;
				break;
			}
			else
			{
				//tmp= couleur_pions[x0][y0];
				tmp=couleur_pions.get(x0,y0);
				if(tmp==couleur)
				{
					//if(x0== no_ligne+x&&y0== no_colonne+y)
					if(nb==0)
						return false;
					else
						return true;
				}
			}
			nb++;
		}while(!fin);
		return false;
	}

	public boolean isCaseValide(Couleurs couleur, int no_ligne, int no_colonne)
	{
		Preconditions.checkArgument (couleur != null);
		if (no_ligne >= 0 && no_ligne < getNbLignes()) {
			if (no_colonne >= 0 && no_colonne < getNbColonnes()) {
				return case_valide_bord(couleur,no_ligne,no_colonne);
			}
		}
		return false;
	}

	public int getScore(Couleurs couleur) {
		int nb=0;
		Preconditions.checkArgument(couleur!=null);
		for(int i=0;i<getNbLignes();i++)
		{
			for(int j=0;j<getNbColonnes();j++)
			{
				//if(couleur_pions[i][j]==couleur)
				if(couleur_pions.get(i,j)==couleur)
				{
					nb++;
				}
			}
		}
		return nb;
	}

	public boolean peut_jouer(Couleurs couleur) {
		Preconditions.checkArgument (couleur!=null);
		for (int i = 0; i < getNbLignes(); i++) {
			for (int j = 0; j < getNbColonnes(); j++) {
				if(isCaseValide(couleur,i,j))
				{
					return true;
				}
			}
		}
		return false;
	}

	public int score_si(Couleurs couleur, int no_ligne, int no_colonne) {
		Preconditions.checkArgument (no_ligne >= 0);
		Preconditions.checkArgument (no_ligne < getNbLignes());
		Preconditions.checkArgument (no_colonne >= 0);
		Preconditions.checkArgument (no_colonne < getNbColonnes());
		Preconditions.checkArgument (couleur != null);
		int res=0;
		if (cases_non_vides(no_ligne, no_colonne)) {
			return 0;
		}
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				if (!(x == 0 && y == 0)) {
					if(ligne_prise(couleur, no_ligne, no_colonne, x, y))
						res+=1;
				}
			}
		}
		return res;
	}


	private int ligne_prise_score(Couleurs couleur, int no_ligne, int no_colonne, int x, int y) {
		Preconditions.checkArgument (!(x == 0 && y == 0));
		Preconditions.checkArgument (x >= -1 && x <= 1);
		Preconditions.checkArgument (y >= -1 && y <= 1);
		Preconditions.checkArgument (couleur != null);
		int x0, y0, nb;
		boolean fin = false;
		Couleurs tmp;
		x0 = no_ligne;
		y0 = no_colonne;
		nb = 0;
		do {
			x0 += x;
			y0 += y;
			if (x0 < 0 || x0 >= getNbLignes()) {
				fin = true;
				break;
			} else if (y0 < 0 || y0 >= getNbColonnes()) {
				fin = true;
				break;
			} else if (!cases_non_vides(x0, y0)) {
				fin = true;
				break;
			} else {
				//tmp = couleur_pions[x0][y0];
				tmp=couleur_pions.get(x0,y0);
				if (tmp == couleur) {
					if (x0 == no_ligne + x && y0 == no_colonne + y)
						return 0;
					else
						return nb;
				}
			}
			nb++;
		} while (!fin);
		return 0;
	}

	private boolean cases_non_vides(int no_ligne, int no_colonne) {
		//return cases_non_vides[no_ligne][no_colonne];
		//return couleur_pions[no_ligne][no_colonne]!=null;
		return couleur_pions.get(no_ligne,no_colonne)!=null;
	}

	public String toString()
	{
		String s,s2;
		Couleurs c;
		s="";
		for (int no_ligne = 0; no_ligne < getNbLignes(); no_ligne++) {
			for (int no_colonne = 0; no_colonne < getNbColonnes(); no_colonne++) {
				//c=couleur_pions[no_ligne][no_colonne];
				c=couleur_pions.get(no_ligne,no_colonne);
				if(c==null)
					s2="";
				else
					s2= c.toString();
				s+=s2+"|";
			}
			s+="\n";
		}
		return s;
	}

	public void undo() {
		couleur_pions.undo();
	}
}
