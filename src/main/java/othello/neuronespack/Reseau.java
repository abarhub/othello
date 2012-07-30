package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.*;

/**
 * <p>Title: R�seau</p>
 * <p>Description: Gestion des fonctions de base d'un r�seau.</p>
 */
public abstract class Reseau {
  public static int CNX_ENTREE = 0;
  public static int CNX_SORTIE = 1;
  public static int CNX_COUCHE = 2;
  public static int CNX_BIAIS = 3;

  /** Nombre de couches */
  private int nbCouches;
  /** Liste des couches */
  private Vector lesCouches;
  /** Liste des connexions */
  private Vector lesConnexions;
  /** Donn�es courantes */
  private DonneesEntree donneesCourantes;
  /** Donn�es de r�sultats */
  private Resultats lesResultats;
  /** Biais */
  private Biais leBiais;

  /**
   * Constructeur du r�seau
   * @param nbC int : nombre de couches
   */
  public Reseau(int nbC) {
    nbCouches = nbC;
    lesCouches = new Vector(nbCouches);
    lesConnexions = new Vector();
  }

  /** Ajout d'une couche */
  public void addCouche(Couche c) {
    lesCouches.addElement(c);
  }

  /** Getter d'une couche */
  public Couche getCouche(int c) {
    return (Couche) lesCouches.elementAt(c);
  }

  /**
   * Construction des connexions d'entr�es
   * @param mc boolean[][] : Matrice des connexions
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsEntrees(boolean mc[][]) {
    Connexions connexion = ajouteConnexionsEntrees(donneesCourantes,
                            (GroupeUnites) getCouche(0), mc);
    connexion.setPoidsFixes(1.0D);
    return connexion;
  }

  /**
   * Construction des connexions directes d'entr�es
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsEntreesDirectes() {
    int nbe; // Nombre d'unit�s �mettrices
    int nbr; // Nombre d'unit�s r�ceptrices
    boolean mc[][]; // Matrice de connexion
    nbe = donneesCourantes.getTailleVecteurEntree();
    nbr = getCouche(0).getNbUnites();
    mc = new boolean[nbe][nbr];
    Connexions.construitConnexionsDirectes(nbe, mc);
    return construitConnexionsEntrees(mc);
  }

  /**
   * Ajout des connexions d'entr�e
   * @param dataE GroupeUnites : Ensemble des unit�s d'origine
   * @param c GroupeUnites : Ensemble des unit�s de destination
   * @param mc boolean[][] : Matrice des connexions
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions ajouteConnexionsEntrees(GroupeUnites dataE, GroupeUnites c,
                                      boolean mc[][]) {
    Connexions connexion = new Connexions(Reseau.CNX_ENTREE, dataE, c, mc);
    lesConnexions.addElement(connexion);
    return connexion;
  }

  /**
   * Construction des connexions de sorties
   * @param mc boolean[][] : Matrice des connexions
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsSorties(boolean mc[][]) {
    Connexions connexion = ajouteConnexionsSorties(
       (GroupeUnites) getCouche(nbCouches-1), lesResultats, mc);
    connexion.setPoidsFixes(1.0D);
    return connexion;
  }

  /**
   * Construction des connexions directes de sorties
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsSortiesDirectes() {
    int nbe; // Nombre d'unit�s �mettrices
    int nbr; // Nombre d'unit�s r�ceptrices
    nbe = getCouche(nbCouches-1).getNbUnites();
    nbr = lesResultats.getNbUnites();
    boolean mc[][] = new boolean[nbe][nbr];
    Connexions.construitConnexionsDirectes(nbe, mc);
    return construitConnexionsSorties(mc);
  }

  /**
   * Ajoute des connexions de sortie
   * @param c GroupeUnites : Ensemble des unit�s d'origine
   * @param dataR GroupeUnites : Ensemble des unit�s de destination
   * @param mc boolean[][] : Matrice des connexions
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions ajouteConnexionsSorties(GroupeUnites c, GroupeUnites dataR,
                                      boolean mc[][]) {
    Connexions connexion = new Connexions(Reseau.CNX_SORTIE, c, dataR, mc);
    lesConnexions.addElement(connexion);
    return connexion;
  }

  /**
   * Construction des connexions entre deux couches
   * @param c1 GroupeUnites : Ensemble des unit�s d'origine
   * @param c2 GroupeUnites : Ensemble des unit�s de destination
   * @param mc boolean[][] : Matrice des connexions
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsCouches(
    GroupeUnites c1, GroupeUnites c2, boolean mc[][]) {
    Connexions connexion = ajouteConnexionsCouches(c1, c2, mc);
    connexion.setPoidsAleat(0.0D, 1.0D);
    return connexion;
  }

  /**
   * Construction de connexions compl�tes entre deux couches
   * @param c1 GroupeUnites : Ensemble des unit�s d'origine
   * @param c2 GroupeUnites : Ensemble des unit�s de destination
   * @return Connexions : Objet Connexions cr��
   */
  public Connexions construitConnexionsCouchesComplete(GroupeUnites c1, GroupeUnites c2) {
    int nbe; // Nombre d'unit�s �mettrices
    int nbr; // Nombre d'unit�s r�ceptrices
    boolean mc[][]; // Matrice des connexions
    nbe = c1.getNbUnites();
    nbr = c2.getNbUnites();
    mc = new boolean[nbe][nbr];
    Connexions.construitConnexionsCompletes(nbe, nbr, mc);
    return construitConnexionsCouches(c1, c2, mc);
  }

  /**
   * Connexion de 2 couches
   * @param c1 CoucheBase : Couche d'origine
   * @param c2 CoucheBase : Couche de destination
   * @param mc boolean[][] : Matrice de connexions
   * @return ConnexionsCouches : Connexion cr��e
   */
  public Connexions ajouteConnexionsCouches(GroupeUnites c1,
                                      GroupeUnites c2, boolean mc[][]) {
    Connexions connexion = new Connexions(Reseau.CNX_COUCHE, c1, c2, mc);
    lesConnexions.addElement(connexion);
    return connexion;
  }

  /**
   * Construction des connexions au biais
   * @param vb double[][] : Valeurs des biais (seuils) par couche et par neurone
   */
  public void construitConnexionsBiais(double vb[][]) {
    leBiais = new Biais(0);
    for(int i=0; i<nbCouches; i++) {
     ajouteConnexionsBiais(getCouche(i), vb[i]);
    }
  }

  /**
   * Ajoute les connexions de biais
   * @param couche CoucheMP : Couche concern�e
   * @param val double[] : Valeurs des biais (seuils) par neurone
   */
  public void ajouteConnexionsBiais(Couche couche, double val[]) {
    boolean mc[][] = new boolean[1][couche.getNbUnites()];
    double biais[][] = new double[1][couche.getNbUnites()];
    for(int i=0; i<couche.getNbUnites(); i++) {
      mc[0][i] = true;
      biais[0][i] = val[i];
    }
    Connexions connexion = new Connexions(Reseau.CNX_BIAIS, leBiais, couche, mc);
    connexion.setMatricePoids(biais);
    lesConnexions.addElement(connexion);
  }

  /**
   * Fixe les poids des connexions selon matrice poids
   * @param c int : Couche d'origine des connexions
   * @param pc int : Type de connexion de la couche
   * @param p double[][] : Matrice des poids
   */
  public void setPoidsConnexions(Connexions cnx, double p[][]) {
    cnx.setMatricePoids(p);
 }

  /** Getter du nombre couches */
  public int getNbCouches() {
    return nbCouches;
  }

  /** Getter des couches */
  public Vector getLesCouches() {
    return lesCouches;
  }

  /** Getter d'une connexion */
  public Connexions getConnexions(int c) {
    return (Connexions) lesConnexions.elementAt(c);
  }

  /** Getter des Connexions */
  public Vector getLesConnexions() {
    return lesConnexions;
  }

  /** Getter du biais */
  public Biais getLeBiais() {
    return leBiais;
  }

  /**
   * Construit l'objet donn�es courantes
   * @param tve int : Taille du vecteur d'entr�e
   * @param tvs int : Taille du vecteur de sortie d�sir�es
   */
  public void setDonneesCourantes(int tve, int tvs) {
    donneesCourantes = new DonneesEntree(tve, tvs);
  }

  /** Getter des donn�es courantes */
  public DonneesEntree getDonneesCourantes() {
    return donneesCourantes;
  }

  /**
   * Construit l'objet r�sultat
   * @param tvr int : Taille du vecteur r�sultat (sortie)
   */
  public void setResultat(int tvr) {
    lesResultats =  new Resultats(tvr);
  }

  /** Getter des r�sultats */
  public Resultats getResultats() {
    return lesResultats;
  }

  /**
   * Connecte un fichier d'entr�e
   * @param pat Patterns : Objet Patterns concern�
   * @param nomfic String : Nom du fichier
   */
  public void setFichierEntree(DonneesEntree de, String nomFic) {
    de.setFichierEntree(nomFic);
  }

  /**
   * Connecte un fichier de r�sultats (sorties)
   * @param res Resultats : Objet R�sultats concern�
   * @param nomFic String : Nom du fichier
   */
  public void setFichierResultat(Resultats res, String nomFic) {
    res.setFichierSortie(nomFic);
  }

// *********************************************************************
  /** traitement du r�seau */
  public abstract void propagation();
}
