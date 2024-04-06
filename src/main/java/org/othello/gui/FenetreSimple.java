package org.othello.gui;

import org.othello.joueurs.*;
import org.othello.model.*;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:30:57
 */
public class FenetreSimple extends JFrame implements ActionListener, EtatJeuxListener {

    public static Logger log = LoggerFactory.getLogger(FenetreSimple.class);

    private static final int DEFAULT_WIDTH = 530;
    private static final int DEFAULT_HEIGHT = 500;

    private ModelOthello model;

    private Cadrillage cadrillage;

    //private Couleurs joueur_courant;

    private Joueur joueur[];
    //private int joueur_courant;

    private Controleur controleur;

    private JMenuItem nouveau;
    private JMenuItem quitter;

    private JLabel joueur1, score1, message1;
    private JLabel joueur2, score2, message2;

    public FenetreSimple() {
        setTitle("Othello");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setLocationRelativeTo(getParent());
        //setResizable(false);

        model = new ModelOthello();

        cadrillage = new Cadrillage(model, controleur);
        //add(cadrillage, BorderLayout.CENTER);

        JPanel panel;
        Border border;
        Dimension dim, dim2, tmp1, tmp2;
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "Othello");
        panel = new JPanel();
        panel.add(cadrillage, BorderLayout.CENTER);
        tmp1 = cadrillage.getMinimumSize();
        tmp2 = cadrillage.getPreferredSize();
        dim = new Dimension((int) (tmp1.getWidth() + 20), (int) (tmp1.getHeight() + 20));
        panel.setMinimumSize(dim);
        dim = new Dimension((int) (tmp2.getWidth() + 20), (int) (tmp2.getHeight() + 20));
        panel.setPreferredSize(dim);
        panel.setBorder(border);
        add(panel, BorderLayout.CENTER);

        tmp1 = panel.getMinimumSize();
        tmp2 = panel.getPreferredSize();
        dim = new Dimension((int) (tmp1.getWidth() + 100), (int) (tmp1.getHeight() + 100));
        setMinimumSize(dim);

        dim = new Dimension((int) (tmp2.getWidth() + 100), (int) (tmp2.getHeight() + 100));
        setPreferredSize(dim);

        //addMouseListener(this);
        cadrillage.addMouseListener(cadrillage);
        //cadrillage.addListener(this);


        init(model);

        init_menu();
        init_composants();
    }


    private void init(ModelOthello model) {
        init(model, true, true, null, null);
    }

    private void init(ModelOthello model, boolean joueur1_humain, boolean joueur2_humain,
                      ListeAlgos algo1, ListeAlgos algo2) {
        int pos;
        AlgoRecherche algo;
        if (true) {
            pos = 3;
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos);
            model.setCouleur(CouleursJoueurs.Noir, pos, pos + 1);
            model.setCouleur(CouleursJoueurs.Noir, pos + 1, pos);
            model.setCouleur(CouleursJoueurs.Blanc, pos + 1, pos + 1);
        } else if (false) {
            pos = 0;
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos);
            model.setCouleur(CouleursJoueurs.Noir, pos, pos + 1);
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos + 2);
            model.setCouleur(CouleursJoueurs.Blanc, pos, pos + 4);
        } else if (false) {
            for (int no_ligne = 0; no_ligne < 8; no_ligne++) {
                for (int no_colonne = 0; no_colonne < 8; no_colonne++) {
                    if ((no_ligne == 2 && no_colonne == 6) || (no_ligne == 3 && no_colonne == 7)) {
                        model.setCouleur(CouleursJoueurs.Blanc, no_ligne, no_colonne);
                    } else if (no_ligne > 3 && no_colonne == 7) {
                        //model.setCouleur(CouleursJoueurs.Blanc, no_ligne, no_colonne);
                    } else {
                        model.setCouleur(CouleursJoueurs.Noir, no_ligne, no_colonne);
                    }
                }
            }
        } else if (false) {
            model.setCouleur(CouleursJoueurs.Blanc, 0, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 1);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 2);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 3);
            model.setCouleur(CouleursJoueurs.Blanc, 0, 4);

            model.setCouleur(CouleursJoueurs.Blanc, 1, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 1, 1);
            model.setCouleur(CouleursJoueurs.Blanc, 1, 2);
            model.setCouleur(CouleursJoueurs.Noir, 1, 3);

            model.setCouleur(CouleursJoueurs.Blanc, 2, 0);
            model.setCouleur(CouleursJoueurs.Blanc, 2, 1);
            model.setCouleur(CouleursJoueurs.Noir, 2, 2);
            model.setCouleur(CouleursJoueurs.Noir, 2, 3);

            model.setCouleur(CouleursJoueurs.Blanc, 3, 0);
            model.setCouleur(CouleursJoueurs.Noir, 3, 2);
            model.setCouleur(CouleursJoueurs.Noir, 3, 3);
            model.setCouleur(CouleursJoueurs.Noir, 3, 4);

            model.setCouleur(CouleursJoueurs.Noir, 4, 3);
            model.setCouleur(CouleursJoueurs.Noir, 4, 4);
            model.setCouleur(CouleursJoueurs.Noir, 4, 5);

        }
        //joueur_courant= CouleursJoueurs.Noir;
        controleur = new Controleur(model, this, cadrillage);
        cadrillage.setControleur(controleur);
        joueur = new Joueur[2];
        if (joueur1_humain) {
            joueur[0] = new JoueurHumain(model, CouleursJoueurs.Noir, controleur);
        } else {
            CheckUtils.checkArgument (algo1 != null);
            //algo = new Algo1(model, CouleursJoueurs.Noir);
            algo = ListeAlgos.getAlgo(algo1, model, CouleursJoueurs.Noir);
            joueur[0] = new JoueurOrdiSimple(model, CouleursJoueurs.Noir, controleur, algo);
        }
        //cadrillage.addListener(joueur[0]);
        //joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        if (joueur2_humain) {
            joueur[1] = new JoueurHumain(model, CouleursJoueurs.Blanc, controleur);
        } else {
            CheckUtils.checkArgument (algo2 != null);
            //algo=new Algo1(model, CouleursJoueurs.Blanc);
            algo = ListeAlgos.getAlgo(algo2, model, CouleursJoueurs.Blanc);
            joueur[1] = new JoueurOrdiSimple(model, CouleursJoueurs.Blanc, controleur, algo);
        }
        //cadrillage.addListener(joueur[1]);
        //joueur_courant=0;
        //controleur.demarrage(joueur,0);
    }


    private void init_menu() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menu = new JMenu("A Menu");
        menuBar.add(menu);

        nouveau = new JMenuItem("Nouveau");
        nouveau.addActionListener(this);
        menu.add(nouveau);

        quitter = new JMenuItem("Quitter");
        quitter.addActionListener(this);
        menu.add(quitter);
    }

    private void init_composants() {
        JPanel panel, panel1, panel2;
        Box vBox1, vbox2;
        Border border;
        panel = new JPanel();
        add(panel, BorderLayout.EAST);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "Joueur Noir");
        panel1.setBorder(border);
        vBox1 = Box.createVerticalBox();
        panel1.add(vBox1);
        joueur1 = new JLabel("Joueur " + CouleursJoueurs.Noir + " :");
        vBox1.add(joueur1);
        score1 = new JLabel("Score : ");
        vBox1.add(score1);
        message1 = new JLabel("En attente");
        vBox1.add(message1);
        panel.add(panel1);

        panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
        border = BorderFactory.createEtchedBorder();
        border = BorderFactory.createTitledBorder(border, "Joueur Blanc");
        panel2.setBorder(border);
        vbox2 = Box.createVerticalBox();
        panel2.add(vbox2);
        joueur2 = new JLabel("Joueur " + CouleursJoueurs.Blanc + " :");
        vbox2.add(joueur2);
        score2 = new JLabel("Score : ");
        vbox2.add(score2);
        message2 = new JLabel("En attente");
        vbox2.add(message2);
        panel.add(panel2);
    }

    public void actionPerformed(ActionEvent e) {
        if (e != null) {
            if (e.getSource() == nouveau) {
                Nouveau tmp;
                boolean joueur1_humain, joueur2_humain;
                ListeAlgos algo1, algo2;
                tmp = new Nouveau(this);
                tmp.setVisible(true);
                if (tmp.isOk()) {
                    joueur1_humain = tmp.isJoueur1_humain();
                    algo1 = tmp.getAlgo1();
                    joueur2_humain = tmp.isJoueur2_humain();
                    algo2 = tmp.getAlgo2();
                    debut(joueur1_humain, joueur2_humain, algo1, algo2);
                }
            } else if (e.getSource() == quitter) {
                System.exit(0);
            }
        }
    }

    private void debut(boolean joueur1_humain, boolean joueur2_humain, ListeAlgos algo1, ListeAlgos algo2) {
        model = new ModelOthello();
        cadrillage.setModel(model);
        init(model, joueur1_humain, joueur2_humain, algo1, algo2);
        controleur.demarrage(joueur, 0);
        repaint();
        mise_a_jour();
    }

    public void mise_a_jour() {
        boolean joueur1_humain, joueur2_humain;
        String message_joueur1, message_joueur2;
        int score_joueur1, score_joueur2;
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
        cadrillage.repaint();
        log.info("durée:" + controleur.affiche_temps());
    }

    public void changement_joueur(Joueur joueur) {
        mise_a_jour();
    }

    public void joueur_bloque(Joueur joueur) {
        mise_a_jour();
        JOptionPane.showMessageDialog(this, "Le joueur " + joueur.getCouleur() + " ne peut pas jouer.\n Il saute son tour.");
    }

    public void fin_partie() {
        String message;
        Joueur liste[];
        Couleurs tmp, gagnant;
        int max, score;
        boolean exequo;
        mise_a_jour();
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
        JOptionPane.showMessageDialog(this, message);
    }

    public void case_incorrecte(Couleurs couleur, int no_ligne, int no_colonne) {

    }

    public void joueur_joue(Couleurs couleur, int no_ligne, int no_colonne) {

    }
}
