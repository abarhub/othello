package org.othello;

import org.othello.gui.FenetreJFX;
import org.othello.gui.FenetreSimple;
import org.othello.joueurs.Couple;
import org.othello.joueurs.ListeAlgos;
import org.othello.model.JeuxAuto;
import org.othello.model.TableauSimple2;
import org.othello.utils.CheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:13:19
 */
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] arg) {
        //config_log();
        test1();
        //test2();
        //test3();
        //test4();
//        test5(arg);
    }

    private static void test1() {
        FenetreSimple fenetre;
        fenetre = new FenetreSimple();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

    private static void config_log() {
//        Logger tmp;
        //FileHandler hand = new FileHandler("vk.log");
//        ConsoleHandler cons = new ConsoleHandler();
//        tmp = AlgoMinMax.log;
//        tmp.addHandler(cons);
//        tmp.setLevel(Level.ALL);
//        tmp.info("coucou1");
//        tmp.fine("coucou2");
//        tmp.info("coucou3");
    }

    private static void test2() {
        JeuxAuto jeux;
        long debut, fin;
        //debut=System.currentTimeMillis();
        //jeux=new JeuxAuto(ListeAlgos.Simple1,ListeAlgos.Simple1);
        //jeux=new JeuxAuto(ListeAlgos.Simple1,ListeAlgos.MinMax2);
        jeux = new JeuxAuto(ListeAlgos.Simple1, ListeAlgos.AlphaBeta2);
        jeux.demarrage();
        //fin=System.currentTimeMillis();
        //log.info("Durée : "+(fin-debut)+"("+conv(fin-debut)+")");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("fin!");
    }

    private static void test3() {
        JeuxAuto jeux;
        long debut, fin;
        Map<Couple, Integer> map;
        Couple c1, c2;
        CheckUtils.checkArgument((new Couple(1, 2)).equals(new Couple(1, 2)));
        CheckUtils.checkArgument(!(new Couple(2, 2)).equals(new Couple(1, 2)));
        CheckUtils.checkArgument((new Couple(3, 3)).equals(new Couple(3, 3)));
        CheckUtils.checkArgument((new Couple(3, 3)).hashCode() == (new Couple(3, 3).hashCode()));
        map = new HashMap<Couple, Integer>();
        c1 = new Couple(3, 3);
        c2 = new Couple(3, 3);
        CheckUtils.checkArgument(c1.equals(c2));
        map.put(c1, 7);
        CheckUtils.checkArgument(map.containsKey(c2));
        CheckUtils.checkArgument(map.get(c2) == 7);
        CheckUtils.checkArgument(map.get(c1) == 7);
    }

    private static void test4() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 0, 0, true, 8);
        CheckUtils.checkArgument(tmp1 == 1L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 1, true, 8);
        CheckUtils.checkArgument(tmp1 == 2L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 2, true, 8);
        CheckUtils.checkArgument(tmp1 == 4L, "tmp1=" + tmp1);

    }

    private static void test5(String[] arg){
        FenetreJFX.launch(arg);
    }

}
