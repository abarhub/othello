package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.*;

/**
 * <p>Title: Biais</p>
 * <p>Description: Gestion du biais.</p>
 */
public class Biais extends UniteExterne implements GroupeUnites {

  /** Pointeur sur les unités (le biais) */
  private Vector lesUnites;

  /**
   * Construit un biais avec un identifiant
   * @param Id int : Identifiant
   */
  public Biais(int Id) {
    super(Id);
    lesUnites = new Vector(1);
    lesUnites.addElement(this);
    setSignalCourant(-1.0D);
  }

  /** Force le signal à -1 */
  public void setSignalCourant(double v) {
    super.setSignalCourant(-1.0D);
  }

  /** Getter d'une unité */
  public Unite getUnite(int u) {
    return (Unite) lesUnites.elementAt(0);
  }

  /** Getter des unités */
  public Vector getLesUnites() {
    return lesUnites;
  }

  /** Getter du nombre d'unités */
  public int getNbUnites() {
    return 1;
  }

}
