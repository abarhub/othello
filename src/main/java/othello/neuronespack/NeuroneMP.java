package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

/**
 * <p>Title: Neurone McCulloch-Pitts</p>
 * <p>Description: Gestion des neurones de McCulloch-Pitts.</p>
 */
public class NeuroneMP extends Neurone {

    public NeuroneMP(Couche c, int Id) {
        super(c, Id, 2);
        setPotentiel(-1.0D);
    }

    public void run() {
        setPotentiel(calcPotentiel());
        setSignal(calcSignal());
    }
}
