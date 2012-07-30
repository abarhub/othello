package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Réseau backprop</p>
 * <p>Description: Gestion des réseaux backprop.</p>
 */
public class ReseauBP extends ReseauFF {

  /** Moment */
  private double momentum;

  /**
   * Constructeur du réseau
   * @param nbC int : Nombre de couches
   */
  public ReseauBP(int nbC) {
    super(nbC);
    setEta(1.0D);
    momentum = 0.0D;
  }

  /**
   * Construit un réseau BP selon paramètres
   * @param paramReseau int[] : Nombre de neurones pour chaque couche
   * @param fonction int : Fonction de transfert
   * @param paramFT double[] : Paramètres de la fonction de transfert
   */
  public void construitReseau(int paramReseau[], int fonction,
                              double paramFT[]) {
    construitLesCouches(paramReseau, fonction, paramFT);
    construitLesConnexions();
  }

  /**
   * Construit un réseau BP selon paramètres
   * @param paramReseau int[] : Nombre de neurones pour chaque couche
   * @param fonction int : Fonction de transfert
   * @param paramFT double[] : Paramètres de la fonction de transfert
   * @param vb double[][] : Valeurs des biais (seuils) par couche et par neurone
   */
  public void construitReseau(int paramReseau[], int fonction,
                              double paramFT[], double vb[][]) {
    construitLesCouches(paramReseau, fonction, paramFT);
    construitConnexionsBiais(vb);
    construitLesConnexions();
  }

  /**
   * Construit les couches
   * @param paramReseau int[] : Nombre de neurones pour chaque couche
   * @param fonction int : Fonction de transfert
   * @param paramFT double[]Paramètres de la fonction de transfert
   */
  private void construitLesCouches(int paramReseau[], int fonction,
                                   double paramFT[]) {
    double paramFL[] = new double[1];
    paramFL[0] = 1.0D;
    ajouteCouche(paramReseau[0], FonctionTransfert.F_LINEAIRE, paramFL);
    for(int i=1; i<getNbCouches(); i++) {
      ajouteCouche(paramReseau[i], fonction, paramFT);
    }
  }

  /** Construit les connexions */
  private void construitLesConnexions() {
    construitConnexionsEntreesDirectes();
    for(int i=1; i<getNbCouches(); i++) {
      construitConnexionsCouchesComplete((GroupeUnites) getCouche(i-1),
                                 (GroupeUnites) getCouche(i));
    }
    construitConnexionsSortiesDirectes();
  }

  /**
   * Apprentissage d'un exemple
   * @return double : Erreur totale sur la couche de sortie
   */
  public double apprend() {
    double erreurTotale;
    propagation();
    erreurTotale = calcErreurSortie(((CoucheFF) getCouche(getNbCouches()-1)));
    for(int j=getNbCouches()-2; j>0; j--) {
      calcErreurCachee(((CoucheFF) getCouche(j)));
    }
    corrigePoids();
    return erreurTotale;
  }

  /**
   * Calcul des signaux d'erreurs sur la couche de sortie
   * @param couche CoucheFF : couche à traiter
   * @return double : Erreur totale sur la couche (0.5 carré des écarts)
   */
  public double calcErreurSortie(CoucheFF couche) {
    double err = 0.0D;
    double delta;
    NeuroneFF neurone;
    for(int i=0; i<couche.getNbUnites(); i++) {
      neurone = (NeuroneFF) couche.getUnite(i);
      delta = getDonneesCourantes().getValeurSortieDesiree(i)
          - neurone.getSignalBrut();
      err += delta * delta;
      neurone.calcSignalErreurSortie(getDonneesCourantes()
                                     .getValeurSortieDesiree(i));
    }
    return err * 0.5D;
  }

  /** Calcul des signaux d'erreurs sur les couches cachées */
  public void calcErreurCachee(CoucheFF couche) {
    NeuroneFF neurone;
    for(int i=0; i<couche.getNbUnites(); i++) {
      neurone = ((NeuroneFF) couche.getUnite(i));
      neurone.calcSignalErreurCache();
    }
  }

  /** Correction des poids */
  public void corrigePoids() {
    CoucheFF couche;
    Connexions connexion;
    Synapse sy;
    Unite lienOrig;
    NeuroneFF NeuroneDest;
    double deltaPoids;
    for(int c=getNbCouches()-1; c>0; c--) {
      couche = (CoucheFF) getCouche(c);
      for(int j=0; j<getLesConnexions().size(); j++) {
        connexion = getConnexions(j);
        if(connexion.getGroupeDestinationConnexions() == couche) {
          for (int k=0; k<connexion.getNbSynapses(); k++) {
            sy = connexion.getSynapse(k);
            lienOrig = (Unite) sy.getUniteOrigine();
            NeuroneDest = (NeuroneFF) sy.getUniteDestination();
            deltaPoids = getEta() *
                NeuroneDest.getSignalErreur() * lienOrig.getSignalBrut() +
                momentum * sy.getDeltaPoids();
            sy.setDeltaPoids(deltaPoids);
            sy.setPoids(sy.getPoids() + deltaPoids);
          }
        }
      }
    }
  }

  /** Setter du moment */
  public void setMomentum(double m) {
    momentum = m;
  }

  /** Getter du moment */
  public double getMomentum() {
    return momentum;
  }

}
