package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Réseau de Kohonen</p>
 * <p>Description: Gestion des réseaux de Kohonen</p>
 */
public class ReseauKoho extends Reseau {

  /** Pas d'apprentissage initial */
  private double eta0;
  /** Rayon initial d'influence latérale */
  private double sigma0;
  /** Nombre maximum d'itérations */
  private int maxIteration;
  /** Numéro d'itération courante */
  public  int numIteration;
  /** Constante temporelle */
  private  double tau;
  /** Best Matching Unit courante */
  private NeuroneKoho bestMU = null;
  /** Dimension de la couche en X */
  private int tailleX;
  /** Dimension de la couche en Y */
  private int tailleY;

  /** Constructeur */
  public ReseauKoho() {
    super(1);
  }

  /**
   * Construit une carte de Kohonen en 2 dimensions
   * @param tX int : Taille en x
   * @param tY int : Taille en y
   */
  public void construitReseau(int tX, int tY) {
    tailleX = tX;
    tailleY = tY;
    double paramFT[] = new double[1];
    ajouteCouche(tailleX, tailleY, FonctionTransfert.F_SIGNE, paramFT);
  }

  /**
   * Création d'une couche de Kohonen
   * @param tailleX int : Taille en x
   * @param tailleY int : Taille en y
   * @param ft int : Fonction de transfert
   * @param pFT double[]  : Paramètres de la fonction de transfert
   */
  public void ajouteCouche(int tailleX, int tailleY, int ft, double pFT[]) {
    addCouche(new CoucheKoho(tailleX, tailleY, ft, pFT));
  }

  /** Construit les connexions d'entrées */
  public void construitConnexionsEntrees() {
    boolean mc[][] =
        new boolean[getCouche(0).getNbUnites()]
        [tailleX*tailleY];
    Connexions.construitConnexionsCompletes
        (getDonneesCourantes().getTailleVecteurEntree(), tailleX*tailleY, mc);
    construitConnexionsEntrees(mc);
    getConnexions(0).setPoidsAleat(0.0D, 1.0D);
  }

  /** Apprentissage */
  public void apprend() {
    propagation();
    corrigePoids();
  }

  /** Propagation avant */
  public void propagation() {
    getCouche(0).traiteCouche(this);
    getResultats().setVecteurResultat(getLesConnexions());
  }

  /** Correction des poids */
  public void corrigePoids() {
    Connexions connexion;
    Synapse sy;
    NeuroneKoho neuroneDest;
    double delta;
    connexion = getConnexions(0);
    for (int i=0; i<connexion.getNbSynapses(); i++) {
      sy = connexion.getSynapse(i);
      neuroneDest = (NeuroneKoho) sy.getUniteDestination();
      delta = getFonctionVoisinage(bestMU, neuroneDest)
          * getEtaT() * (sy.getUniteOrigine().getSignalBrut() - sy.getPoids());
      sy.setDeltaPoids(delta);
      sy.setPoids(sy.getPoids() + delta);
    }
  }

  /** Renvoie la valeur de la fonction de voisinage entre 2 neurones */
  private double getFonctionVoisinage(NeuroneKoho neurone1, NeuroneKoho neurone2) {
    double dist;
    dist = ((CoucheKoho) getCouche(0))
        .getDistanceNeurones(neurone1, neurone2);
    return Math.exp(-1.0D * ((dist*dist) / (2.0D * Math.pow(getSigmaT(), 2))));
  }

  /** Setter BMU */
  public void setBMU(NeuroneKoho neurone) {
    bestMU = neurone;
  }

  /** Getter BMU */
  public NeuroneKoho getBMU() {
    return bestMU;
  }

  /** Setter Eta0 */
  public void setEta0(double e) {
    eta0 = e;
  }

  /** Getter Eta0 */
  public double getEta0() {
    return eta0;
  }

  /** Getter etaT */
  public double getEtaT() {
    return eta0 * Math.exp(-1.0D * (numIteration / maxIteration));
  }

  /** Setter constante temporelle */
  public void setTau() {
    tau = maxIteration /  Math.log(sigma0);
  }

  /** Getter constante temporelle */
  public double getTau() {
    return tau;
  }

  /** Setter Sigma0 */
  public void setSigma0(double s) {
    sigma0 = s;
  }

  /** Getter Sigma0 */
  public double getSigma0() {
    return sigma0;
  }

  /** Getter sigmaT */
  public double getSigmaT() {
    return sigma0 * Math.exp(-1.0D*(numIteration/tau));
  }

  /** Setter nb total d'itérations */
  public void setMaxIteration(int m) {
    maxIteration = m;
  }

  /** Getter nb total d'itérations */
  public int getMaxIteration() {
    return maxIteration;
  }

  /** Getter taille X */
  public int getTailleX() {
    return tailleX;
  }

  /** Getter taille Y */
  public int getTailleY() {
    return tailleY;
  }

  /** Renvoie un neurone selon sa position (x, y) */
  public NeuroneKoho getNeuroneXY(int x, int y) {
    if(x > tailleX || y > tailleY) return null;
    CoucheKoho couche = (CoucheKoho) getCouche(0);
    return (NeuroneKoho) couche.getUnite(x * tailleX + y);
  }
}
