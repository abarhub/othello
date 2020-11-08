package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalités de base des réseaux neuronaux.
 * Réseaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */


import java.io.*;
import java.util.*;

/**
 * Gestion des données d'apprentissage et de test.
 * Pour les fichiers, la première ligne contient :
 * type (apprend/test); nombre de vecteurs d'entrée; taille de ces vecteurs;
 * nombre de vecteurs de sortie; taille de ces vecteurs
 * Les lignes suivantes contiennent :
 * les scalaires des vecteurs d'entrée suivis des sorties désirées (si apprend)
 */

/**
 * <p>Title: Données d'entrée</p>
 * <p>Description: Gestion des données d'entrée.</p>
 */
public class DonneesEntree implements GroupeUnites {
  public static final char SEPARATEUR = ';';

  /** taille des vecteurs d'entrée */
  private int tailleVecteurEntree;
  /** Vecteur d'entrée courante */
  private double vecteurEntree[];
  /** taille des vecteurs de sortie */
  private int tailleVecteurSortie;
  /** Vecteur sortie désiré */
  private double vecteurSortieDesire[];
  /** Liste de pointeurs sur les unités */
  private Vector lesUnites;
  /** Fichier d'entrée */
  protected FileReader fichierEntrees;
  /** Position courante dans le fichier */
  private int posCrt;

  /**
   * Constructeur : Crée un objet de gestion des entrées
   * @param tve int Taille vecteur entrée
   * @param tvs int Taille vecteur sortie
   */
  public DonneesEntree(int tve, int tvs) {
    posCrt = 0;
    tailleVecteurEntree = tve;
    tailleVecteurSortie = tvs;
    vecteurEntree = new double[tailleVecteurEntree];
    lesUnites = new Vector(tailleVecteurEntree);
    for (int i=0; i <tailleVecteurEntree; i++) {
      lesUnites.addElement(new UniteExterne(i));
    }
    vecteurSortieDesire = new double[tailleVecteurSortie];
  }

  /** Mise à jour du vecteur d'entrée courant */
  public void setVecteurEntree(double e[]) {
    for(int i=0; i<tailleVecteurEntree; i++) {
      vecteurEntree[i] = e[i];
      ((UniteExterne) lesUnites.elementAt(i)).setSignalCourant(vecteurEntree[i]);
    }
  }

  /** Getter du vecteur d'entrée */
  public double[] getVecteurEntree() {
    return vecteurEntree;
  }

  /** Getter d'une valeur de sortie */
  public double getValeurEntree(int i) {
    return vecteurEntree[i];
  }

  /** Getter taille vecteur d'entrée */
  public int getTailleVecteurEntree() {
    return tailleVecteurEntree;
  }

  /** Connecte un fichier d'entrée */
  public void setFichierEntree(String nomFic) {
    if(fichierEntrees != null) {
      fermeFichierDonnees();
    }
    try {
      fichierEntrees = new FileReader(nomFic);
    }  catch (IOException e) {
      System.out.println("Erreur fichier : "+e.toString()); }
  }

  /** Mise à jour du vecteur de sortie désiré courant */
  public void setVecteurSortieDesire(double e[]) {
    for(int i=0; i<tailleVecteurSortie; i++) {
      vecteurSortieDesire[i] = e[i];
    }
  }

  /** Getter du vecteur de sortie désiré */
  public double[] getVecteurSortieDesiree() {
    return vecteurSortieDesire;
  }

  /** Getter d'une valeur de sortie désirée */
  public double getValeurSortieDesiree(int i) {
    return vecteurSortieDesire[i];
  }

  /** Getter taille vecteur de sortie */
  public int getTailleVecteurSortie() {
    return tailleVecteurSortie;
  }

  /** Lit l'entree suivante du fichier */
  public void litEntreeSuivante() {
    litVecteurEntree();
  }

  /** lit l'exemple d'apprentissage suivant */
  public void litExempleSuivant() {
    litVecteurEntree();
    litVecteurSortieDesire();
  }

  /** Lecture du vecteur d'entrée dans le fichier */
  private void litVecteurEntree() {
    posCrt++;
    for(int i=0; i<tailleVecteurEntree; i++) {
      vecteurEntree[i] = litVal();
      ((UniteExterne) lesUnites.elementAt(i)).setSignalCourant(vecteurEntree[i]);
    }
  }

  /** Lecture du vecteur de sortie désiré dans le fichier */
  private void litVecteurSortieDesire() {
    for(int i=0; i<tailleVecteurSortie; i++) {
      vecteurSortieDesire[i] = litVal();
    }
  }

  /** Lecture da la valeur suivante du fichier */
  private double litVal() {
    char c = '_';
    String s = "";
    try {
      c=(char)fichierEntrees.read();
      while (c!=SEPARATEUR) {
        if (c != '\n' && c != '\r') {
          s += c;
        }
        c = (char)fichierEntrees.read();
      }
    } catch (IOException e) {
        System.out.println("Erreur fichier : "+e.toString()); }
    return Double.valueOf(s).doubleValue();
  }

  /** Retourne au début du fichier */
  private void reInitFichier() {
    try {
      fichierEntrees.reset();
      posCrt = 0;
    } catch (IOException e) {
      System.out.println("Erreur fichier : "+e.toString()); }
  }

  /** Fermeture fichier */
  public void fermeFichierDonnees() {
    try {
      fichierEntrees.close();
    } catch (IOException e) {
      System.out.println("Erreur fichier : "+e.toString()); }
  }

  /** Getter d'une unité */
  public Unite getUnite(int u) {
    return (Unite) lesUnites.elementAt(u);
  }

  /** Getter des unités */
  public Vector getLesUnites() {
    return lesUnites;
  }

  /** Getter du nombre d'unités */
  public int getNbUnites() {
    return tailleVecteurEntree;
  }
}
