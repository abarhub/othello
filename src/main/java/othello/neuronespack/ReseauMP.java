package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Réseau de McCulloch-Pitts</p>
 * <p>Description: Gestion des réseaux de McCulloch-Pitts.</p>
 */
public class ReseauMP extends Reseau {
  public static int ACTIF = 1;
  public static int INHIBE = -1;

  /**
   * Constructeur du réseau
   * @param nbC int : Nombre de couches
   */
  public ReseauMP(int nbC) {
    super(nbC);
  }

  /**
   * Construit un réseau MP
   * @param paramReseau int[] : Nombre de neurones par couche
   * @param vb double[][] : Valeurs des biais (seuils) par couche et par neurone
   */
  public void construitReseau(int paramReseau[], double vb[][]) {
    double paramFT[] = new double[1];
    for(int i=0; i<getNbCouches(); i++) {
      ajouteCouche(paramReseau[i], FonctionTransfert.F_SIGNE, paramFT);
    }
    construitConnexionsBiais(vb);
  }

  /**
   * Création d'une couche
   * @param nbN int : nombre de neurones
   * @param ft int : type de fonction de transfert
   * @param pFT int[] : paramètres de la fonction de transfert
   */
  public void ajouteCouche(int nbN, int ft, double pFT[]) {
    addCouche(new CoucheMP(nbN, ft, pFT));
  }

  /** Propagation avant */
  public void propagation() {
    for(int i=0; i<getNbCouches(); i++) {
      getCouche(i).traiteCouche(this);
    }
    getResultats().setVecteurResultat(getLesConnexions());
  }


}
