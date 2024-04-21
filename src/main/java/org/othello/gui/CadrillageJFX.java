package org.othello.gui;

import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.othello.model.Controleur;
import org.othello.model.Couleurs;
import org.othello.model.CouleursJoueurs;
import org.othello.model.ModelOthello;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CadrillageJFX extends Canvas implements CadrillageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CadrillageJFX.class);

    private ModelOthello model;
    private List<CliqueListener> listener;
    private Controleur controleur;

    public CadrillageJFX(ModelOthello model) {

        super();
        this.model = model;
        listener = new CopyOnWriteArrayList<>();

        this.setHeight(512);
        this.setWidth(512);

        setOnMouseClicked(this::click);

        widthProperty().addListener(observable -> dessine());
        heightProperty().addListener(observable -> dessine());

        dessine();

        //VBox vbox = new VBox(canvas);

//        Circle circle = new Circle();
//        circle.setCenterX(100);
//        circle.setCenterY(100);
//        circle.setRadius(125);
//        circle.setStroke(Color.valueOf("#ff00ff"));
//        circle.setStrokeWidth(5);
//        circle.setFill(Color.TRANSPARENT);
//
//        Rectangle rectangle = new Rectangle();
//        rectangle.setX(200);
//        rectangle.setY(200);
//        rectangle.setWidth(300);
//        rectangle.setHeight(400);
//        rectangle.setStroke(Color.TRANSPARENT);
//        rectangle.setFill(Color.valueOf("#00ffff"));
//
//
//        this.getChildren().add(circle);
//        this.getChildren().add(rectangle);

    }

    private void click(javafx.scene.input.MouseEvent mouseEvent) {
        if (mouseEvent != null) {
//            Platform.runLater(()-> {
                Point2D point;
                point = new Point2D(mouseEvent.getX(), mouseEvent.getY());
                if (point != null) {
                    Dimension2D dim;
                    int no_ligne, no_colonne;
                    //log.info("point=" + point.getX() + "," + point.getY());
                    dim = getCase(point);
                    if (dim != null) {
                        no_ligne = (int) dim.getWidth();
                        no_colonne = (int) dim.getHeight();
                        //no_ligne= no_ligne-1;
					/*if (model.get(no_ligne, no_colonne) == null) {
						model.setCouleur(CouleursJoueurs.Noir, no_ligne, no_colonne);
					}*/
                        //model.SetVerifCouleur(CouleursJoueurs.Noir, no_ligne, no_colonne);
                        if (listener != null && !listener.isEmpty()) {
                            CliqueListener tmp;
                            for (int i = 0; i < listener.size(); i++) {
                                tmp = listener.get(i);
                                tmp.clique(no_ligne, no_colonne);
                            }
                        }
                        LOGGER.info("ligne=" + no_ligne + ",colonne=" + no_colonne);
                        //repaint();
                        Platform.runLater(()-> {
                            dessine();
                        });
                    }
                }
//            });
        }
    }

    public void dessine(){

        GraphicsContext graphicsContext2D = this.getGraphicsContext2D();

        Rectangle2D rect;
        final int width = getWidthCases();
        final int height = getHeightCases();
        int x, y;
        Couleurs couleur, joueur_courant = null;

        //log.info("taille="+getWidth()+","+getHeight());
        if (controleur != null) {
            joueur_courant = controleur.getCouleurCourrante();
        }
        x = 0;
        y = 0;
        for (int no_lignes = 0; no_lignes < model.getNbLignes(); no_lignes++) {
            x = 0;
            for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
                rect = getCase(no_lignes, no_colonne);

                if (joueur_courant != null && model.isCaseValide(joueur_courant, no_lignes, no_colonne)) {
                    graphicsContext2D.setFill(Color.BLUE);
                } else {
                    graphicsContext2D.setFill(Color.rgb(0,255,100));
                }

                graphicsContext2D.fillRect(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
                couleur = model.get(no_lignes, no_colonne);
                if (couleur != null) {
                    if(couleur== CouleursJoueurs.Noir) {
                        graphicsContext2D.setFill(Color.BLACK);
                    } else if (couleur== CouleursJoueurs.Blanc) {
                        graphicsContext2D.setFill(Color.WHITE);
                    } else {
                        CheckUtils.checkArgument(false,"Couleur invalide:"+couleur.getJfxColor());
                    }
                    graphicsContext2D.fillOval(rect.getMinX(), rect.getMinY(), rect.getWidth(), rect.getHeight());
                }
                x += width + getTailleEntreCases();
            }
            y += height + getTailleEntreCases();
        }
    }

    public Rectangle2D getCase(int no_ligne, int no_colonne) {
        Rectangle2D res;
        int x, y, width, height;
        width = getWidthCases();
        height = getHeightCases();
        y = no_ligne * height + (getTailleEntreCases() * no_ligne);
        x = no_colonne * width + (getTailleEntreCases() * no_colonne);
        res = new Rectangle2D(x, y, width, height);
        return res;
    }

    private int getTailleEntreCases() {
        return 1;
    }


//    private Dimension getCase(Point p) {
//        Rectangle2D rect;
//        for (int i = 0; i < model.getNbLignes(); i++) {
//            for (int j = 0; j < model.getNbColonnes(); j++) {
//                rect = getCase(i, j);
//                if (rect.contains(p)) {
//                    return new Dimension(i, j);
//                }
//            }
//        }
//        return null;
//    }

    private Dimension2D getCase(Point2D p) {
        Rectangle2D rect;
        for (int i = 0; i < model.getNbLignes(); i++) {
            for (int j = 0; j < model.getNbColonnes(); j++) {
                rect = getCase(i, j);
                if (rect.contains(p.getX(),p.getY())) {
                    return new Dimension2D(i, j);
                }
            }
        }
        return null;
    }


    public int getWidthCases() {
        return 40;
    }

    public int getHeightCases() {
        return 40;
    }


    public void setControleur(Controleur controleur) {
        this.controleur = controleur;
    }

//    @Override
//    public void mouseClicked(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }


    public void addListener(CliqueListener e) {
        this.listener.add(e);
    }

    public void removeListener(CliqueListener e) {
        this.listener.remove(e);
    }

    public void setModel(ModelOthello model) {
        this.model=model;
    }

    @Override
    public boolean isResizable() {
        return true;
    }
}
