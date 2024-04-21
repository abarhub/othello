package org.othello.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.othello.joueurs.*;
import org.othello.model.*;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FenetreJFX extends Application implements EtatJeuxListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FenetreJFX.class);

    private static final int DEFAULT_WIDTH = 530;
    private static final int DEFAULT_HEIGHT = 500;

    private ModelOthello model;
    private Controleur controleur;
    private CadrillageJFX cadrillage;
    private Joueur joueur[];
    private Label joueur1, score1, message1;
    private Label joueur2, score2, message2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setWidth(1024);
        primaryStage.setHeight(968);
//        primaryStage.setWidth(DEFAULT_WIDTH);
//        primaryStage.setHeight(DEFAULT_HEIGHT);
        primaryStage.setTitle("Othello");

//        Group group = new Group();
//        VBox vbox = new VBox();
//        vbox.getChildren().addAll(new Text("Code Java pur"));
//        group.getChildren().add(vbox);

        model = new ModelOthello();

        GridPane gridPane = new GridPane();

        cadrillage = new CadrillageJFX(model);

        Pane paneCentral = new Pane();
        //paneCentral.getChildren().add(new Label("Central"));
        paneCentral.getChildren().add(cadrillage);
        gridPane.add(paneCentral, 0, 0, 1, 1);

//        Pane  paneJoueurs  = new Pane();
        VBox vbox = new VBox();
        joueur1 = new Label("Joueur Noir");
        score1 = new Label("Score : ");
        message1 = new Label("");
        VBox vboxJoueur1 = new VBox(joueur1, score1, message1);
        vbox.getChildren().add(vboxJoueur1);
        joueur2 = new Label("Joueur Blanc");
        score2 = new Label("Score : ");
        message2 = new Label("");
        VBox vboxJoueur2 = new VBox(joueur2, score2, message2);
        vbox.getChildren().add(vboxJoueur2);
        gridPane.add(vbox, 1, 0, 1, 1);

        MenuBar menuBar = new MenuBar();

        Menu menu1 = new Menu("Menu");
        menuBar.getMenus().add(menu1);

        MenuItem menuNouveau = new MenuItem("Nouveau");
        menuNouveau.setOnAction(actionEvent -> {
            actionNouveau();
        });
        menu1.getItems().add(menuNouveau);

        MenuItem menuQuitter = new MenuItem("Quitter");
        menuQuitter.setOnAction(actionEvent -> {
            actionQuitter();
        });
        menu1.getItems().add(menuQuitter);

        VBox vBox = new VBox(menuBar);
        vBox.getChildren().addAll(gridPane);

        primaryStage.setScene(new Scene(vBox));

        init(model);

        primaryStage.show();
    }

    private void actionNouveau() {
        NouveauJFX tmp;

        tmp = new NouveauJFX(this);
        tmp.showAndWait().ifPresent(x -> {
            LOGGER.info("fin: {}", x);
            boolean joueur1_humain, joueur2_humain;
            ListeAlgos algo1, algo2;
            joueur1_humain = x.joueur1Humain();
            algo1 = x.algoJoueur1();
            if (!joueur1_humain) {
                CheckUtils.checkArgument(algo1 != null,
                        "L'algorithme n'est pas renseigné pour le joueur 1");
            }
            joueur2_humain = x.joueur2Humain();
            algo2 = x.algoJoueur2();
            if (!joueur2_humain) {
                CheckUtils.checkArgument(algo2 != null,
                        "L'algorithme n'est pas renseigné pour le joueur 2");
            }
            debut(joueur1_humain, joueur2_humain, algo1, algo2);
        });
//        tmp.setVisible(true);
//        if (tmp.isOk()) {
//            joueur1_humain = tmp.isJoueur1_humain();
//            algo1 = tmp.getAlgo1();
//            joueur2_humain = tmp.isJoueur2_humain();
//            algo2 = tmp.getAlgo2();
//            debut(joueur1_humain, joueur2_humain, algo1, algo2);
//        }
    }

    private void actionQuitter() {
        System.exit(0);
    }


    private void debut(boolean joueur1_humain, boolean joueur2_humain, ListeAlgos algo1, ListeAlgos algo2) {
        model = new ModelOthello();
        cadrillage.setModel(model);
        init(model, joueur1_humain, joueur2_humain, algo1, algo2);
        controleur.demarrage(joueur, 0);
        //repaint();
        Platform.runLater(() -> {
            cadrillage.dessine();
            mise_a_jour();
        });
    }


    private void mise_a_jour() {
//        boolean joueur1_humain, joueur2_humain;
//        String message_joueur1, message_joueur2;
//        int score_joueur1, score_joueur2;
        if (controleur.isDemarrer()) {
            Joueur liste[], tmp;
            Couleurs couleur;
            int score;
            String s, msg;
            liste = controleur.getListe_joueurs();
            if (liste != null && liste.length == 2) {
                tmp = liste[0];
                couleur = tmp.getCouleur();
                score = model.getScore(couleur);
                if (controleur.getJoueur_courant() == 0)
                    msg = "En Cours";
                else
                    msg = "Attente";
                if (tmp instanceof JoueurHumain) {
                    s = "Humain";
                } else {
                    s = "Ordinateur";
                }
                joueur1.setText("Joueur " + couleur + " : " + s);
                score1.setText("Score : " + score);
                message1.setText(msg);

                tmp = liste[1];
                couleur = tmp.getCouleur();
                score = model.getScore(couleur);
                if (controleur.getJoueur_courant() == 1)
                    msg = "En Cours";
                else
                    msg = "Attente";
                if (tmp instanceof JoueurHumain) {
                    s = "Humain";
                } else {
                    s = "Ordinateur";
                }
                joueur2.setText("Joueur " + couleur + " : " + s);
                score2.setText("Score : " + score);
                message2.setText(msg);
            }
        } else {

        }
//        cadrillage.repaint();

        LOGGER.info("durée:" + controleur.affiche_temps());
    }


    private void init(ModelOthello model) {
        init(model, true, true, null, null);
    }

    private void init(ModelOthello model, boolean joueur1_humain, boolean joueur2_humain,
                      ListeAlgos algo1, ListeAlgos algo2) {
        int pos;
        AlgoRecherche algo;
        pos = 3;
        model.setCouleur(CouleursJoueurs.Blanc, pos, pos);
        model.setCouleur(CouleursJoueurs.Noir, pos, pos + 1);
        model.setCouleur(CouleursJoueurs.Noir, pos + 1, pos);
        model.setCouleur(CouleursJoueurs.Blanc, pos + 1, pos + 1);
        //joueur_courant= CouleursJoueurs.Noir;
        controleur = new Controleur(model, this, cadrillage);
        cadrillage.setControleur(controleur);
        joueur = new Joueur[2];
        if (joueur1_humain) {
            joueur[0] = new JoueurHumain(model, CouleursJoueurs.Noir, controleur);
        } else {
            CheckUtils.checkArgument(algo1 != null);
            //algo = new Algo1(model, CouleursJoueurs.Noir);
            algo = ListeAlgos.getAlgo(algo1, model, CouleursJoueurs.Noir);
            joueur[0] = new JoueurOrdiSimple(model, CouleursJoueurs.Noir, controleur, algo);
        }
        //cadrillage.addListener(joueur[0]);
        //joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        if (joueur2_humain) {
            joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        } else {
            CheckUtils.checkArgument(algo2 != null);
            //algo=new Algo1(model, CouleursJoueurs.Blanc);
            algo = ListeAlgos.getAlgo(algo2, model, CouleursJoueurs.Blanc);
            joueur[1] = new JoueurOrdiSimple(model, CouleursJoueurs.Blanc, controleur, algo);
        }
        //cadrillage.addListener(joueur[1]);
        //joueur_courant=0;
        //controleur.demarrage(joueur,0);
    }


    @Override
    public void changement_joueur(Joueur joueur) {
        Platform.runLater(() -> {
            cadrillage.dessine();
            mise_a_jour();
        });
    }

    @Override
    public void joueur_bloque(Joueur joueur) {
        Platform.runLater(() -> {
            cadrillage.dessine();
            mise_a_jour();
            //JOptionPane.showMessageDialog(this, "Le joueur " + joueur.getCouleur() + " ne peut pas jouer.\n Il saute son tour.");
            String message = "Le joueur " + joueur.getCouleur() + " ne peut pas jouer.\n Il saute son tour.";
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Blocage");
            alert.setHeaderText(null);
            alert.setContentText(message);

            alert.showAndWait();
        });
    }

    @Override
    public void fin_partie() {
        String message;
        Joueur liste[];
        Couleurs tmp, gagnant;
        int max, score;
        boolean exequo;
        Platform.runLater(() -> {
            cadrillage.dessine();
                    mise_a_jour();
                });
        message = "La partie est terminée.\n";
        liste = controleur.getListe_joueurs();
        if (liste != null && liste.length > 0) {
            gagnant = null;
            max = 0;
            exequo = false;
            for (Joueur joueur : liste) {
                tmp = joueur.getCouleur();
                score = model.getScore(tmp);
                message += "Le joueur " + tmp + " a fait :" + score + "\n";
                if (score > max) {
                    gagnant = tmp;
                    max = score;
                    exequo = false;
                } else if (score == max) {
                    exequo = true;
                } else {
                    exequo = false;
                }
            }
            if (exequo) {
                message += "Les joueurs sont exequo !\n";
            } else {
                message += "Le joueur " + gagnant + " a gagné !\n";
            }
        }
//        JOptionPane.showMessageDialog(this, message);
        var message0=message;
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fin de partie");
            alert.setHeaderText(null);
            alert.setContentText(message0);

            alert.showAndWait();
        });
    }

    @Override
    public void case_incorrecte(Couleurs couleur, int no_ligne, int no_colonne) {

    }

    @Override
    public void joueur_joue(Couleurs couleur, int no_ligne, int no_colonne) {

    }

}
