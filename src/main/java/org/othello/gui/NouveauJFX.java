package org.othello.gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class NouveauJFX extends Dialog<NouveauResultat> {

    static final int DEFAULT_WIDTH = 200;
    static final int DEFAULT_HEIGHT = 300;

    private Runnable action;
    private FenetreJFX fenetreJFX;
    private RadioButton button1Joueur1, button1Joueur2, button2Joueur1, button2Joueur2;
    private ToggleGroup groupJoueur1, groupJoueur2;

    public NouveauJFX(FenetreJFX fenetreJFX) {
//        initOwner(fenetreJFX);
        setTitle("Nouveau");
        setResultConverter(x -> convertion(x));
        this.fenetreJFX = fenetreJFX;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        groupJoueur1 = new ToggleGroup();
        button1Joueur1 = new RadioButton("Humain");
        button2Joueur1 = new RadioButton("Ordinateur");
        button1Joueur1.setToggleGroup(groupJoueur1);
        button2Joueur1.setToggleGroup(groupJoueur1);

        groupJoueur2 = new ToggleGroup();
        button1Joueur2 = new RadioButton("Humain");
        button2Joueur2 = new RadioButton("Ordinateur");
        button1Joueur2.setToggleGroup(groupJoueur2);
        button2Joueur2.setToggleGroup(groupJoueur2);

        grid.add(button1Joueur1, 0, 0);
        grid.add(button2Joueur1, 0, 1);
        grid.add(button1Joueur2, 0, 2);
        grid.add(button2Joueur2, 0, 3);

        getDialogPane().setContent(grid);

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }

    private NouveauResultat convertion(ButtonType x) {
        if (x == ButtonType.OK) {
//            if(action != null) {
//                action.run();
//            }
            var toggle1 = groupJoueur1.getSelectedToggle();
            var toggle2 = groupJoueur2.getSelectedToggle();
            if (toggle1 instanceof RadioButton boutonJoueur1 && toggle2 instanceof RadioButton boutonJoueur2) {
                return new NouveauResultat(boutonJoueur1 == button1Joueur1,
                        boutonJoueur2 == button1Joueur2);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

//    public setOnAction(Runnable action){
//        this.action=action;
//    }
}
