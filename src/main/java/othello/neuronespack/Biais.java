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
 * <p>Title: Biais</p>
 * <p>Description: Gestion du biais.</p>
 */
public class Biais extends UniteExterne implements GroupeUnites {

  /** Pointeur sur les unit�s (le biais) */
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

  /** Force le signal � -1 */
  public void setSignalCourant(double v) {
    super.setSignalCourant(-1.0D);
  }

  /** Getter d'une unit� */
  public Unite getUnite(int u) {
    return (Unite) lesUnites.elementAt(0);
  }

  /** Getter des unit�s */
  public Vector getLesUnites() {
    return lesUnites;
  }

  /** Getter du nombre d'unit�s */
  public int getNbUnites() {
    return 1;
  }

}
