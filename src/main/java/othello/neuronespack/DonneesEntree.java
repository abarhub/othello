package othello.neuronespack;

/**
 * Package Neurones
 * Fonctionalit�s de base des r�seaux neuronaux.
 * R�seaux Neauronaux, Vuibert 2006.
 * Jean-Philippe Rennard
 * version 1.0, 17/3/2006
 */


import java.io.*;
import java.util.*;

/**
 * Gestion des donn�es d'apprentissage et de test.
 * Pour les fichiers, la premi�re ligne contient :
 * type (apprend/test); nombre de vecteurs d'entr�e; taille de ces vecteurs;
 * nombre de vecteurs de sortie; taille de ces vecteurs
 * Les lignes suivantes contiennent :
 * les scalaires des vecteurs d'entr�e suivis des sorties d�sir�es (si apprend)
 */

/**
 * <p>Title: Donn�es d'entr�e</p>
 * <p>Description: Gestion des donn�es d'entr�e.</p>
 */
public class DonneesEntree implements GroupeUnites {
  public static final char SEPARATEUR = ';';

  /** taille des vecteurs d'entr�e */
  private int tailleVecteurEntree;
  /** Vecteur d'entr�e courante */
  private double vecteurEntree[];
  /** taille des vecteurs de sortie */
  private int tailleVecteurSortie;
  /** Vecteur sortie d�sir� */
  private double vecteurSortieDesire[];
  /** Liste de pointeurs sur les unit�s */
  private Vector lesUnites;
  /** Fichier d'entr�e */
  protected FileReader fichierEntrees;
  /** Position courante dans le fichier */
  private int posCrt;

  /**
   * Constructeur : Cr�e un objet de gestion des entr�es
   * @param tve int Taille vecteur entr�e
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

  /** Mise � jour du vecteur d'entr�e courant */
  public void setVecteurEntree(double e[]) {
    for(int i=0; i<tailleVecteurEntree; i++) {
      vecteurEntree[i] = e[i];
      ((UniteExterne) lesUnites.elementAt(i)).setSignalCourant(vecteurEntree[i]);
    }
  }

  /** Getter du vecteur d'entr�e */
  public double[] getVecteurEntree() {
    return vecteurEntree;
  }

  /** Getter d'une valeur de sortie */
  public double getValeurEntree(int i) {
    return vecteurEntree[i];
  }

  /** Getter taille vecteur d'entr�e */
  public int getTailleVecteurEntree() {
    return tailleVecteurEntree;
  }

  /** Connecte un fichier d'entr�e */
  public void setFichierEntree(String nomFic) {
    if(fichierEntrees != null) {
      fermeFichierDonnees();
    }
    try {
      fichierEntrees = new FileReader(nomFic);
    }  catch (IOException e) {
      System.out.println("Erreur fichier : "+e.toString()); }
  }

  /** Mise � jour du vecteur de sortie d�sir� courant */
  public void setVecteurSortieDesire(double e[]) {
    for(int i=0; i<tailleVecteurSortie; i++) {
      vecteurSortieDesire[i] = e[i];
    }
  }

  /** Getter du vecteur de sortie d�sir� */
  public double[] getVecteurSortieDesiree() {
    return vecteurSortieDesire;
  }

  /** Getter d'une valeur de sortie d�sir�e */
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

  /** Lecture du vecteur d'entr�e dans le fichier */
  private void litVecteurEntree() {
    posCrt++;
    for(int i=0; i<tailleVecteurEntree; i++) {
      vecteurEntree[i] = litVal();
      ((UniteExterne) lesUnites.elementAt(i)).setSignalCourant(vecteurEntree[i]);
    }
  }

  /** Lecture du vecteur de sortie d�sir� dans le fichier */
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

  /** Retourne au d�but du fichier */
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

  /** Getter d'une unit� */
  public Unite getUnite(int u) {
    return (Unite) lesUnites.elementAt(u);
  }

  /** Getter des unit�s */
  public Vector getLesUnites() {
    return lesUnites;
  }

  /** Getter du nombre d'unit�s */
  public int getNbUnites() {
    return tailleVecteurEntree;
  }
}
