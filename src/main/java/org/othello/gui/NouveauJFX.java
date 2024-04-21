package org.othello.gui;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.controlsfx.tools.Borders;
import org.othello.joueurs.ListeAlgos;
import org.othello.utils.CheckUtils;
import org.othello.utils.JavaFXUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class NouveauJFX extends Dialog<NouveauResultat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NouveauJFX.class);

    static final int DEFAULT_WIDTH = 200;
    static final int DEFAULT_HEIGHT = 300;

    private Runnable action;
    private FenetreJFX fenetreJFX;
    private RadioButton button1Joueur1, button1Joueur2, button2Joueur1, button2Joueur2;
    private ToggleGroup groupJoueur1, groupJoueur2;
    private ComboBox<String> comboBoxJoueur1, comboBoxJoueur2;

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

        ObservableList<String> list;
        ListeAlgos[] tab = ListeAlgos.values();
        List<String> tab2 = Arrays.stream(tab).map(ListeAlgos::getNom).toList();
        list = FXCollections.observableArrayList(tab2);
        comboBoxJoueur1 = new ComboBox<>(list);
//        comboBoxJoueur1.getSelectionModel().select(1);

        groupJoueur2 = new ToggleGroup();
        button1Joueur2 = new RadioButton("Humain");
        button2Joueur2 = new RadioButton("Ordinateur");
        button1Joueur2.setToggleGroup(groupJoueur2);
        button2Joueur2.setToggleGroup(groupJoueur2);

        list = FXCollections.observableArrayList(tab2);
        comboBoxJoueur2 = new ComboBox<>(list);

        VBox vBox = new VBox();
        Node wrappedVBox1 = JavaFXUtils.etchedBorder(vBox,"Joueur Blanc");

        vBox.getChildren().addAll(new Label("Joueur Blanc"),
                button1Joueur1,
                button2Joueur1,
                comboBoxJoueur1);

        VBox vBox2 = new VBox();
        Node wrappedVBox2 =JavaFXUtils.etchedBorder(vBox2,"Joueur Noir");

        vBox2.getChildren().addAll(new Label("Joueur Noir"),
                button1Joueur2,
                button2Joueur2,
                comboBoxJoueur2);

        grid.add(wrappedVBox1, 0, 0);
        grid.add(wrappedVBox2, 0, 1);

        getDialogPane().setContent(grid);

        this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        var boutonOk = getDialogPane().lookupButton(ButtonType.OK);
        boutonOk
                .disableProperty()
                .bind(Bindings.createBooleanBinding(
                        () -> /*!controller.getHoursField.getText().matches("[0-7]") ||
                                !controller.getMinutesField.getText().matches("^[0-5]?[0-9]$"),
                        controller.getHoursField.textProperty(),
                        controller.getMinutesField.textProperty()*/
                                groupJoueur1.getSelectedToggle() == null ||
                                        groupJoueur2.getSelectedToggle() == null ||
                                        (groupJoueur1.getSelectedToggle() == button2Joueur1 &&
                                                (comboBoxJoueur1.getValue() == null))||
                                        (groupJoueur2.getSelectedToggle() == button2Joueur2 &&
                                                (comboBoxJoueur2.getValue() == null))
                        ,
                        groupJoueur1.selectedToggleProperty(),
                        groupJoueur2.selectedToggleProperty(),
                        comboBoxJoueur1.valueProperty(),
                        comboBoxJoueur2.valueProperty()
//                        comboBoxJoueur1.selectionModelProperty(),
//                        comboBoxJoueur2.selectionModelProperty()
                        //comboBoxJoueur1.
//                        button1Joueur1.getProperties()
                ));

        boutonOk.disableProperty().addListener((observableValue, oldValue, newValue) -> {
            LOGGER.info("modification ok: {} -> {}",oldValue, newValue);
        });
        boutonOk.disableProperty().addListener(observable -> {
            LOGGER.info("disable ok");
        });
//        boutonOk.getProperties().addListener((MapChangeListener<? super Object,? super Object> listener)-> {
//            //LOGGER.info("modification");
//        });

//        boutonOk.getProperties().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
//            System.out.printf("B changé : %d -> %d", oldValue, newValue).println();
//        });

        comboBoxJoueur1.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> groupJoueur1.getSelectedToggle() != button2Joueur1,
                        groupJoueur1.selectedToggleProperty()
                ));

        comboBoxJoueur2.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> groupJoueur2.getSelectedToggle() != button2Joueur2,
                        groupJoueur2.selectedToggleProperty()
                ));

        comboBoxJoueur1.disableProperty().addListener((observableValue, oldValue, newValue) -> {
            LOGGER.info("modification disable combo1: {} -> {}",oldValue, newValue);
        });
        comboBoxJoueur1.disableProperty().addListener(observable -> {
            LOGGER.info("disable disable combo1");
        });

//        comboBoxJoueur1.selectionModelProperty().addListener((observableValue, oldValue, newValue) -> {
//            LOGGER.info("modification selection combo1: {} -> {}",oldValue, newValue);
//        });
//        comboBoxJoueur1.selectionModelProperty().addListener(observable -> {
//            LOGGER.info("disable selection combo1");
//        });

//        comboBoxJoueur1.onActionProperty().addListener((observableValue, oldValue, newValue) -> {
//            LOGGER.info("modification2 selection combo1: {} -> {}",oldValue, newValue);
//        });
        comboBoxJoueur1.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            LOGGER.info("modification selection combo1: {} -> {}",oldValue, newValue);
        });

        //comboBoxJoueur1.onAction().Property()

    }

    private NouveauResultat convertion(ButtonType x) {
        if (x == ButtonType.OK) {
            var toggle1 = groupJoueur1.getSelectedToggle();
            var toggle2 = groupJoueur2.getSelectedToggle();
            if (toggle1 instanceof RadioButton boutonJoueur1 && toggle2 instanceof RadioButton boutonJoueur2) {
                ListeAlgos algoJoueur1 = null, algoJoueur2 = null;
                boolean joueur1Humain = boutonJoueur1 == button1Joueur1;
                boolean joueur2Humain = boutonJoueur2 == button1Joueur2;
                if (!joueur1Humain) {
                    String value = comboBoxJoueur1.getValue();
                    algoJoueur1 = ListeAlgos.getByName(value);
                    CheckUtils.checkArgument(algoJoueur1 != null,
                            "L'algorithme n'est pas renseigné pour le joueur 1");
                }
                if (!joueur2Humain) {
                    String value = comboBoxJoueur2.getValue();
                    algoJoueur2 = ListeAlgos.getByName(value);
                    CheckUtils.checkArgument(algoJoueur2 != null,
                            "L'algorithme n'est pas renseigné pour le joueur 2");
                }
                return new NouveauResultat(joueur1Humain, algoJoueur1,
                        joueur2Humain, algoJoueur2);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

}
