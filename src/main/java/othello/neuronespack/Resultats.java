package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * <p>Title: Résultats.</p>
 * <p>Description: Gestion des résultats (sorties du réseau).</p>
 */
public class Resultats implements GroupeUnites {
    public static String newLine = System.getProperty("line.separator");
    public static final char SEPARATEUR = ';';

    /**
     * taille des vecteurs de sortie
     */
    private int tailleVecteurSortie;
    /**
     * Vecteur de sortie
     */
    private double vecteurSortie[];
    /**
     * Liste de pointeurs sur les unités
     */
    private Vector lesUnites;
    /**
     * Fichier des résultats
     */
    private FileWriter fichierResultats;

    /**
     * Constructeur : Crée un objet de gestion des sorties
     *
     * @param tvs int : Taille du vecteur de sortie
     */
    public Resultats(int tvs) {
        tailleVecteurSortie = tvs;
        vecteurSortie = new double[tailleVecteurSortie];
        lesUnites = new Vector(tailleVecteurSortie);
        for (int i = 0; i < tailleVecteurSortie; i++) {
            lesUnites.addElement(new UniteExterne(i));
        }
    }

    /**
     * MAJ du vecteur de sortie
     */
    public void setVecteurResultat(Vector lesCnx) {
        Connexions cnx;
        int num = 0;
        for (int i = 0; i < lesCnx.size(); i++) {
            cnx = (Connexions) lesCnx.elementAt(i);
            if (cnx.getTypeConnexions() == Reseau.CNX_SORTIE) {
                for (int j = 0; j < cnx.getNbSynapses(); j++) {
                    vecteurSortie[num] =
                            cnx.getSynapse(num).
                                    getUniteOrigine().getSignalBrut();
                    ((UniteExterne) lesUnites.elementAt(num)).setSignalCourant(vecteurSortie[num]);
                    num++;
                }
            }
        }
    }

    /**
     * Getter du vecteur de sortie
     */
    public double[] getVecteurSortie() {
        return vecteurSortie;
    }

    /**
     * Getter d'une valeur de sortie
     */
    public double getValeurSortie(int i) {
        return vecteurSortie[i];
    }

    /**
     * Getter de la taille du vecteur de sortie
     */
    public int getTailleVecteurSortie() {
        return tailleVecteurSortie;
    }

    /**
     * Connecte un fichier de sortie
     */
    public void setFichierSortie(String nomFic) {
        try {
            fichierResultats = new FileWriter(nomFic);
        } catch (IOException e) {
            System.out.println("Erreur fichier : " + e.toString());
        }
    }

    /**
     * Enregistrement des résultats dans le fichier
     */
    public void enregistreResultats() {
        for (int i = 0; i < tailleVecteurSortie; i++) {
            enregistreUnResultat(i);
        }
        try {
            fichierResultats.write(newLine);
        } catch (IOException e) {
            System.out.println("Erreur fichier : " + e.toString());
        }

    }

    /**
     * Enregistrement d'un scalaire dans le fichier
     */
    private void enregistreUnResultat(int num) {
        try {
            fichierResultats.write
                    (Double.toString(getUnite(num).getSignalBrut()));
            fichierResultats.write(SEPARATEUR);
        } catch (IOException e) {
            System.out.println("Erreur fichier : " + e.toString());
        }
    }

    /**
     * Fermeture fichier
     */
    public void fermeResultats() {
        try {
            fichierResultats.close();
        } catch (IOException e) {
            System.out.println("Erreur fichier : " + e.toString());
        }
    }

    /**
     * Getter d'une unité
     */
    public Unite getUnite(int u) {
        return (Unite) lesUnites.elementAt(u);
    }

    /**
     * Getter des neurones
     */
    public Vector getLesUnites() {
        return lesUnites;
    }

    /**
     * Getter nombre de neurones
     */
    public int getNbUnites() {
        return tailleVecteurSortie;
    }
}
