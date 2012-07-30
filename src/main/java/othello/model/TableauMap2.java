package othello.model;

import othello.joueurs.Couple;

import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 14:00:16
 * To change this template use File | Settings | File Templates.
 */
public class TableauMap2 implements TableauGenerique  {

	private final int nb_ligne,nb_colonne;
	private final Map<Couple,Couleurs> map;
	private Couple premier;

	public TableauMap2(int nb_ligne,int nb_colonne)
	{
		this.nb_ligne=nb_ligne;
		this.nb_colonne=nb_colonne;
		map=new LinkedHashMap<Couple,Couleurs>();
	}


	public TableauMap2(TableauGenerique couleur_pions, int nbLignes, int nbColonnes) {
		this.nb_ligne = nbLignes;
		this.nb_colonne = nbColonnes;
		//tab=new Couleurs[nb_ligne][nb_colonne];
		if(couleur_pions instanceof TableauMap2)
		{
			TableauMap2 tmp;
			tmp= (TableauMap2) couleur_pions;
			map=tmp.map;
		}
		else
		{
			map=new HashMap<Couple,Couleurs>();
			for(int i=0;i<nbLignes;i++)
			{
				for(int j=0;j<nbColonnes;j++)
				{
					Couleurs c;
					c=couleur_pions.get(i,j);
					if(c!=null)
					{
						map.put(new Couple(i,j),c);
					}
				}
			}
		}
	}

	public Couleurs get(int no_ligne, int no_colonne) {
		assert(no_ligne>=0);
		assert(no_ligne<this.nb_ligne);
		assert(no_colonne>=0);
		assert(no_colonne<this.nb_colonne);
		Couple c;
		c=new Couple(no_ligne,no_colonne);
		if(map.containsKey(c))
			return map.get(c);
		else
			return null;
		//return tab[no_ligne][no_colonne];
	}

	public void set(Couleurs couleur, int no_ligne, int no_colonne) {
		assert(no_ligne>=0);
		assert(no_ligne<this.nb_ligne);
		assert(no_colonne>=0);
		assert(no_colonne<this.nb_colonne);
		Couple c;
		c=new Couple(no_ligne,no_colonne);
		if(premier==null)
			premier=c;
		map.put(c,couleur);
		//tab[no_ligne][no_colonne]=couleur;
	}

	public int getNbLigne() {
		return nb_ligne;
	}

	public int getNbColonne() {
		return nb_colonne;
	}

	public void undo() {
		Set<Couple> set;
		boolean supprimer;
		Couple c;
		if(premier!=null)
		{
			supprimer=false;
			set=map.keySet();
			for(Map.Entry<Couple,Couleurs> entry:map.entrySet())
			{
				c=entry.getKey();
				if(supprimer)
				{
					map.remove(c);
				}
				else
				{
					if(c.equals(premier))
					{
						map.remove(c);
					}
				}
			}
		}
	}
}