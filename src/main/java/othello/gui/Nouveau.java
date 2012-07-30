package othello.gui;

import othello.joueurs.ListeAlgos;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.List;

/**
 * User: Barret
 * Date: 6 déc. 2009
 * Time: 16:39:06
 */
public class Nouveau extends JDialog implements ActionListener {

	static final int DEFAULT_WIDTH = 200;
	static final int DEFAULT_HEIGHT = 300;

	private FenetreSimple fenetre;
	private JButton bouton_ok,bouton_annuler;

	private ButtonGroup groupe1,groupe2;
	private JRadioButton humain1,ordi1;
	private JRadioButton humain2, ordi2;
	private JComboBox ordi_algo1,ordi_algo2;

	private boolean ok;
	private boolean joueur1_humain;
	private ListeAlgos algo1;
	private boolean joueur2_humain;
	private ListeAlgos algo2;

	public Nouveau(FenetreSimple fenetre)
	{
		super(fenetre,"Nouvelle partie",true);
		this.fenetre= fenetre;
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLocationRelativeTo(getParent());
		//setResizable(false);
		init();
	}

	private void init() {
		Box hBox1 = Box.createVerticalBox();
		Box hBox2 = Box.createVerticalBox();
		List<ListeAlgos> liste;
		ListeAlgos tab[]=ListeAlgos.values();
		JPanel panel_joueur1,panel_joueur2;
		Border border1,border2;
		panel_joueur1=new JPanel();
		border1=BorderFactory.createEtchedBorder();
		border1= BorderFactory.createTitledBorder(border1,"Joueur noir");
		panel_joueur1.setBorder(border1);
		hBox2.add(panel_joueur1);
		panel_joueur2 = new JPanel();
		border2 = BorderFactory.createEtchedBorder();
		border2 = BorderFactory.createTitledBorder(border2, "Joueur blanc");
		panel_joueur2.setBorder(border2);
		hBox2.add(panel_joueur2);

		hBox1 = Box.createVerticalBox();
		humain1=new JRadioButton("Humain");
		humain1.setSelected(true);
		hBox1.add(humain1);
		ordi1 = new JRadioButton("Ordinateur");
		hBox1.add(ordi1);
		groupe1=new ButtonGroup();
		groupe1.add(humain1);
		groupe1.add(ordi1);
		ordi_algo1=new JComboBox(tab);
		hBox1.add(ordi_algo1);
		panel_joueur1.add(hBox1);

		hBox1 = Box.createVerticalBox();
		humain2 = new JRadioButton("Humain");
		humain2.setSelected(true);
		hBox1.add(humain2);
		ordi2 = new JRadioButton("Ordinateur");
		hBox1.add(ordi2);
		groupe2 = new ButtonGroup();
		groupe2.add(humain2);
		groupe2.add(ordi2);
		ordi_algo2 = new JComboBox(tab);
		hBox1.add(ordi_algo2);
		panel_joueur2.add(hBox1);

		Box vBox = Box.createHorizontalBox();
		bouton_ok=new JButton("Ok");
		bouton_ok.addActionListener(this);
		vBox.add(bouton_ok);
		bouton_annuler = new JButton("Annuler");
		bouton_annuler.addActionListener(this);
		vBox.add(bouton_annuler);
		hBox2.add(vBox);

		add(hBox2, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent e) {
		if(e!=null)
		{
			if(e.getSource()== bouton_ok)
			{
				ok=true;
				if(humain1.isSelected())
				{
					joueur1_humain=true;
					algo1=null;
				}
				else
				{
					joueur1_humain = false;
					algo1= (ListeAlgos) ordi_algo1.getSelectedItem();
				}
				if (humain2.isSelected())
				{
					joueur2_humain = true;
					algo2 = null;
				}
				else
				{
					joueur2_humain = false;
					algo2 = (ListeAlgos) ordi_algo2.getSelectedItem();
				}
				setVisible(false);
			}
			else if(e.getSource() == bouton_annuler)
			{
				ok = false;
				setVisible(false);
			}
		}
	}

	public boolean isOk() {
		return ok;
	}

	public boolean isJoueur1_humain() {
		return joueur1_humain;
	}

	public boolean isJoueur2_humain() {
		return joueur2_humain;
	}

	public ListeAlgos getAlgo1() {
		return algo1;
	}

	public ListeAlgos getAlgo2() {
		return algo2;
	}
}
