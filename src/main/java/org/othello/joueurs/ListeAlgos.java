package org.othello.joueurs;

import org.othello.model.Couleurs;
import org.othello.model.ModelOthello;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 17:10:56
 */
public enum ListeAlgos {
    Simple1("Alogo simple"), Simple2("Algo simple2"), Simple3("Algo simple3"),
    MinMax1("MinMax 1 niveau"), MinMax2("MinMax 2 niveaux"), MinMax3("MinMax 3 niveaux"),
    AlphaBeta1("AlphaBeta 1 niveau"), AlphaBeta2("AlphaBeta 2 niveaux"), AlphaBeta3("AlphaBeta 3 niveaux");

    ListeAlgos(String nom) {
        this.nom=nom;
    }

    public String getNom() {
        return nom;
    }

    public static AlgoRecherche getAlgo(ListeAlgos algo, ModelOthello model, Couleurs couleur) {
        switch (algo) {
            case Simple1:
                return new Algo1(model, couleur);
            case Simple2:
                return new Algo2(model, couleur);
            case Simple3:
                return new Algo3(model, couleur);
            case MinMax1:
                return new AlgoMinMax(model, couleur, 1);
            case MinMax2:
                return new AlgoMinMax(model, couleur, 2);
            case MinMax3:
                return new AlgoMinMax(model, couleur, 3);
            case AlphaBeta1:
                return new AlgoAlphaBeta(model, couleur, 1);
            case AlphaBeta2:
                return new AlgoAlphaBeta(model, couleur, 2);
            case AlphaBeta3:
                return new AlgoAlphaBeta(model, couleur, 3);
        }
        return null;
    }

    public static ListeAlgos getByName(String nom) {
        for (ListeAlgos algo : ListeAlgos.values()) {
            if (algo.getNom().equals(nom)) {
                return algo;
            }
        }
        return null;
    }

    private String nom;


}
