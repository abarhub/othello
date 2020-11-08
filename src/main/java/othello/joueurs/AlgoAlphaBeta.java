package othello.joueurs;

import othello.model.ModelOthello;
import othello.model.Couleurs;
import othello.model.CouleursJoueurs;

import java.awt.geom.Point2D;
import java.awt.*;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;
import java.util.List;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:00:47
 */
public class AlgoAlphaBeta implements AlgoRecherche {

	public static Logger log = Logger.getLogger("othello.joueurs.AlgoAlphaBeta");

	private ModelOthello model;
	private Couleurs couleur;
	private int niveau_profondeur;

	public AlgoAlphaBeta(ModelOthello model, Couleurs couleur,int niveau_profondeur) {
		this.model = model;
		this.couleur = couleur;
		this.niveau_profondeur= niveau_profondeur;
	}


	public Point2D cherche_case() {
		Point2D res = null;
		Point2D meilleur=null,tmp;
		final int arbre= niveau_profondeur;
		Position pos;
		int profondeur= arbre * 2;
		log.setLevel(Level.ALL);
		System.out.println("Message avec println");
		log.fine("Message avec log");
		System.out.println("Message 2 avec println");
		log.info("Recherche case..."+model+"profondeur="+ profondeur);
		pos= meilleur_score(model,couleur, profondeur,true,
				Integer.MIN_VALUE, Integer.MAX_VALUE);
		log.info("Fin de recherche case..." + model + "profondeur=" + profondeur);
		meilleur=pos.getPoint();
		assert(meilleur!=null);
		return meilleur;
	}

	private static final int tab_score[][]={
			{200,-200,100,0,0,100,-200,200},
			{-200,-200,100,0,0,100,-200,-200},
			{100,100,100,0,0,100,100,100},
			{0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0},
			{100,100,100,0,0,100,100,100},
			{-200,-200,100,0,0,100,-200,-200},
			{200,-200,100,0,0,100,-200,200}
		};

	private int calcul_score(ModelOthello model, int no_ligne, int no_colonne, Couleurs couleur) {
		assert(model!=null);
		assert(couleur!=null);
		assert(no_ligne>=0);
		assert(no_ligne<model.getNbLignes());
		assert(no_colonne>=0);
		assert(no_colonne<model.getNbColonnes());
		if(true)
		{
			//return model.getScore(this.couleur);
			return model.getScore(couleur);
		}
		else
		{
			return model.getScore(couleur)+tab_score[no_ligne][no_colonne];
		}
	}

	private Position meilleur_score(ModelOthello model, Couleurs couleur, int niveau, boolean max,
									int min_score0, int max_score0)
	{
		Point2D tmp;
		int score_meilleur, score;
		int max_score,min_score;
		List<Position> liste;
		Position tmp2;
		ModelOthello m;
		Couleurs c;
		Position pos, meilleur = null;
		List<Couple> liste_coups;
		log.entering("AlgoAlphaBeta", "meilleur_score",new Object[]{couleur,niveau});
		assert(niveau>-1);
		liste_coups=new ArrayList<Couple>();
		for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
			for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
				if (model.isCaseValide(couleur, no_ligne, no_colonne)) {
					liste_coups.add(new Couple(no_ligne,no_colonne));
				}
			}
		}
		trie(liste_coups,couleur,model,max);
		liste=new ArrayList<Position>();
		max_score=Integer.MAX_VALUE;
		min_score=Integer.MIN_VALUE;
		for(Couple c2:liste_coups){
		//for (int no_ligne = 0; no_ligne < model.getNbLignes(); no_ligne++) {
			//for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
			int no_ligne,no_colonne;
			no_ligne=c2.getNo_ligne();
			no_colonne=c2.getNo_colonne();
				assert(model.isCaseValide(couleur, no_ligne, no_colonne));
					//log.finest("model="+model+"case(" + no_ligne+","+no_colonne + ")couleur="+couleur+",niveau=" + niveau);
					tmp = new Point(no_ligne, no_colonne);
					m = new ModelOthello(model);
					m.SetVerifCouleur(couleur, no_ligne, no_colonne);
					c= donneAutreJoueur(couleur);
					if (m.peut_jouer(c))
					{
						if(niveau<=0)
						{
							boolean methode1=false;
							/*if(methode1)
							{
								score=m.getScore(this.couleur);
							}
							else*/
							{
								score=calcul_score(model,no_ligne,no_colonne,this.couleur);
							}
						}
						else
						{
							pos=meilleur_score(m,c,niveau-1,!max, min_score, max_score);
							score=pos.getScore();
						}
					}
					else
					{// l'autre joueur ne peut pas jouer => on continue avec le meme joueur
						if (niveau <= 0) {
							//score = m.getScore(this.couleur);
							score=calcul_score(model,no_ligne,no_colonne,this.couleur);
						}
						else
						{
							pos = meilleur_score(m, couleur, niveau - 1, max, min_score, max_score);
							score = pos.getScore();
						}
					}
					if(max)
					{
						if(max_score<score)
						{
							max_score=score;
						}
					}
					else
					{
						if (min_score > score) {
							min_score = score;
						}
					}
					m.undo();
					m=null;
					//tmp2 = new Position(tmp, m);
					tmp2 = new Position(tmp, null);
					tmp2.setScore(score);
					tmp2.setPoint(tmp);
					liste.add(tmp2);
					if(max)
					{
						if(max_score< min_score0)
						{
							log.info("elagage max");
							break;
						}
					}
					else
					{
						if(min_score>max_score0)
						{
							log.info("elagage min");
							break;
						}
					}
				//}
			//}
		}
		//log.fine("liste="+liste.size());
		//log.finest("liste=" + liste.size());
		if(!liste.isEmpty())
		{// il y a au moins un coup à jouer
			if (max) {
				//score_meilleur = 0;
				score_meilleur = Integer.MIN_VALUE;
			}
			else
			{
				score_meilleur = Integer.MAX_VALUE;
			}
			meilleur=null;
			for(Position p:liste)
			{
				if(max)
				{
					if(p.getScore()>score_meilleur)
					{
						score_meilleur= p.getScore();
						meilleur=p;
					}
				}
				else
				{
					if (p.getScore() < score_meilleur) {
						score_meilleur = p.getScore();
						meilleur = p;
					}
				}
			}
		}
		else
		{// il n'y a pas de coup a jouer
			meilleur=new Position(null,model);
			//meilleur.setScore(model.getScore(this.couleur));
			meilleur.setScore(calcul_score(model,0,0,this.couleur));
		}
		assert (meilleur != null);
		log.exiting("AlgoAlphaBeta", "meilleur_score", meilleur);
		return meilleur;
	}

	private void trie(List<Couple> liste_coups, Couleurs couleur, ModelOthello model, boolean max) {
		assert(liste_coups!=null);
		assert(couleur!=null);
		assert(model!=null);
		if(!liste_coups.isEmpty())
		{
			Map<Integer,List<Couple>> map;
			List<Couple> tmp;
			ModelOthello m;
			int no_ligne,no_colonne,score;
			map=new TreeMap<Integer,List<Couple>>();
			for(Couple c:liste_coups)
			{
				no_ligne=c.getNo_ligne();
				no_colonne=c.getNo_colonne();
				m = new ModelOthello(model);
				m.SetVerifCouleur(couleur, no_ligne, no_colonne);
				//score = m.getScore(this.couleur);
				score=calcul_score(model,no_ligne,no_colonne,this.couleur);
				if(map.containsKey(score))
				{
					tmp=map.get(score);
				}
				else
				{
					tmp=new ArrayList<Couple>();
					map.put(score,tmp);
				}
				tmp.add(c);
			}
			liste_coups.clear();
			//log.info("Ordre:");
			for(int n:map.keySet())
			{
				//log.info("n="+n);
				tmp=map.get(n);
				for(Couple c:tmp)
				{
					liste_coups.add(0,c);
				}
			}
			if(!max)
			{
				Collections.reverse(liste_coups);
			}
		}
	}

	private Couleurs donneAutreJoueur(Couleurs couleur)
	{
		if(couleur== CouleursJoueurs.Blanc)
			return CouleursJoueurs.Noir;
		else
			return CouleursJoueurs.Blanc;
	}
}