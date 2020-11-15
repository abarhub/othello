package org.othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.util.Vector;

/**
 * <p>Title: Unités externes</p>
 * <p>Description: Gestion des unités externes (liens hors du réseau en entrée et en sortie).</p>
 */
public class UniteExterne implements Unite {

    /**
     * Liste des synapses entrantes
     */
    private Vector synapsesIn;
    /**
     * Liste des synapses sortantes
     */
    private Vector synapsesOut;
    /**
     * ID de l’unité
     */
    private int idUnite;
    /**
     * Signal associé à l’unité
     */
    private double signalCourant;

    /**
     * Constructeur avec un identifiant
     */
    public UniteExterne(int Id) {
        idUnite = Id;
        signalCourant = 0.0D;
        synapsesIn = new Vector(1);
        synapsesOut = new Vector(1);
    }

    /**
     * Ajoute une synapse entrant dans l'unité
     */
    public void addSynapsesIn(Synapse s) {
        synapsesIn.addElement(s);
    }

    /**
     * Ajoute une synapse sortant de l'unité
     */
    public void addSynapsesOut(Synapse s) {
        synapsesOut.addElement(s);
    }

    /**
     * Getter d'une synapse entrante
     */
    public Synapse getSynapseIn(int n) {
        return (Synapse) synapsesIn.elementAt(n);
    }

    /**
     * Getter d'une synapse sortante
     */
    public Synapse getSynapseOut(int n) {
        return (Synapse) synapsesOut.elementAt(n);
    }

    /**
     * Getter du signal
     */
    public double getSignalBrut() {
        return signalCourant;
    }

    /**
     * Getter dde l'identifiant
     */
    public int getID() {
        return idUnite;
    }

    /**
     * Setter du signal
     */
    public void setSignalCourant(double s) {
        signalCourant = s;
    }

    /**
     * Getter du signal courant
     */
    public double getSignalCourant() {
        return signalCourant;
    }
}
