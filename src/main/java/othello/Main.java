package othello;

import othello.gui.FenetreSimple;
import othello.joueurs.AlgoMinMax;
import othello.joueurs.ListeAlgos;
import othello.joueurs.Couple;
import othello.model.JeuxAuto;
import othello.model.TableauSimple2;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.ConsoleHandler;
import java.util.Map;
import java.util.HashMap;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:13:19
 */
public class Main {

    public static Logger log = Logger.getLogger("othello.Main");

	public static void main(String arg[])
	{
		//config_log();
		test1();
        //test2();
        //test3();
		//test4();
	}

	private static void test1() {
		FenetreSimple fenetre;
		fenetre = new FenetreSimple();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}

	private static void config_log() {
		Logger tmp;
		//FileHandler hand = new FileHandler("vk.log");
		ConsoleHandler cons=new ConsoleHandler();
		tmp= AlgoMinMax.log;
		tmp.addHandler(cons);
		tmp.setLevel(Level.ALL);
		tmp.info("coucou1");
		tmp.fine("coucou2");
		tmp.info("coucou3");
	}

    private static void test2() {
        JeuxAuto jeux;
        long debut,fin;
        //debut=System.currentTimeMillis();
        //jeux=new JeuxAuto(ListeAlgos.Simple1,ListeAlgos.Simple1);
	    //jeux=new JeuxAuto(ListeAlgos.Simple1,ListeAlgos.MinMax2);
	    jeux=new JeuxAuto(ListeAlgos.Simple1,ListeAlgos.AlphaBeta2);
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
        long debut,fin;
		Map<Couple,Integer> map;
		Couple c1,c2;
	    assert((new Couple(1,2)).equals(new Couple(1,2)));
	    assert(!(new Couple(2,2)).equals(new Couple(1,2)));
	    assert((new Couple(3,3)).equals(new Couple(3,3)));
	    assert((new Couple(3,3)).hashCode()==(new Couple(3,3).hashCode()));
		map=new HashMap<Couple,Integer>();
		c1=new Couple(3,3);
		c2=new Couple(3,3);
		assert(c1.equals(c2));
		 map.put(c1,7);
		 assert(map.containsKey(c2));
		 assert(map.get(c2)==7);
		 assert(map.get(c1)==7);
	 }

	 private static void test4() {
        long tmp1,tmp2,fin;
		 tmp1=TableauSimple2.set_bit(0L,0,0,true,8);
		 assert(tmp1==1L):"tmp1="+tmp1;
		 tmp1=TableauSimple2.set_bit(0L,0,1,true,8);
		 assert(tmp1==2L):"tmp1="+tmp1;
		 tmp1=TableauSimple2.set_bit(0L,0,2,true,8);
		 assert(tmp1==4L):"tmp1="+tmp1;
		 
	 }

}
