package othello.gui;

import othello.model.ModelOthello;
import othello.gui.CliqueListener;
import othello.model.Couleurs;
import othello.model.Controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * User: Barret
 * Date: 5 déc. 2009
 * Time: 15:37:25
 */
public class Cadrillage extends JPanel implements MouseListener {

	public static Logger log = Logger.getLogger("othello.gui.Cadrillage");

	private ModelOthello model;
	private List<CliqueListener> listener;
	private Controleur controleur;

	public Cadrillage(ModelOthello model, Controleur controleur)
	{
		super();
		this.model= model;
		this.controleur= controleur;
		listener=new Vector<CliqueListener>();
		Dimension dim;
		int max_x,max_y,n;
		Rectangle2D rect;
		max_x=100;
		max_y=100;
		for (int no_lignes = 0; no_lignes < model.getNbLignes(); no_lignes++) {
			for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++) {
				rect = getCase(no_lignes, no_colonne);
				n= (int) (rect.getX()+ rect.getWidth());
				if(n>max_x)
					max_x=n;
				n = (int) (rect.getY() + rect.getHeight());
				if (n > max_y)
					max_y = n;
			}
		}
		dim=new Dimension(max_x, max_y);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2= (Graphics2D) g;
		Rectangle2D rect;
		Ellipse2D ellipse;
		final int width= getWidthCases();
		final int height = getHeightCases();
		int x,y;
		Couleurs couleur,joueur_courant=null;

		//log.info("taille="+getWidth()+","+getHeight());
		if(controleur!=null)
		{
			joueur_courant=controleur.getCouleurCourrante();
		}
		x=0;
		y=0;
		for (int no_lignes = 0; no_lignes < model.getNbLignes(); no_lignes++) {
			x=0;
			for (int no_colonne = 0; no_colonne < model.getNbColonnes(); no_colonne++){
				//rect=new Rectangle2D.Double(x,y, width, height);
				rect = getCase(no_lignes, no_colonne);

				if(joueur_courant!=null&&model.isCaseValide(joueur_courant,no_lignes,no_colonne))
				{
					g2.setPaint(Color.BLUE);
				}
				else
				{
					//g2.setPaint(Color.GREEN);
					g2.setPaint(new Color(0,255,100));
				}
				//if(no_lignes%2==0)
					//g2.setPaint(Color.GREEN);
				//else
					//g2.setPaint(Color.RED);
				g2.fill(rect);
				couleur= model.get(no_lignes, no_colonne);
				if(couleur!=null)
				{
					ellipse=new Ellipse2D.Double();
					ellipse.setFrame(rect);
					g2.setPaint(couleur.getColor());
					g2.fill(ellipse);
				}
				x+= width+ getTailleEntreCases();
			}
			y+= height+ getTailleEntreCases();
		}
	}

	public int getWidthCases(){
		return 40;
	}

	public int getHeightCases() {
		return 40;
	}

	public Rectangle2D.Double getCase(int no_ligne,int no_colonne)
	{
		Rectangle2D.Double res;
		int x,y,width,height;
		width= getWidthCases();
		height= getHeightCases();
		y= no_ligne* height+(getTailleEntreCases() *no_ligne);
		x = no_colonne * width + (getTailleEntreCases() * no_colonne);
		res= new Rectangle2D.Double(x, y, width, height);
		return res;
	}

	private int getTailleEntreCases() {
		return 1;
	}

	public Dimension getCase(Point p)
	{
		Rectangle2D rect;
		for(int i=0;i<model.getNbLignes();i++)
		{
			for(int j=0;j<model.getNbColonnes();j++)
			{
				rect= getCase(i,j);
				if(rect.contains(p))
				{
					return new Dimension(i,j);
				}
			}
		}
		return null;
	}


	public void mouseClicked(MouseEvent e) {
		if (e != null) {
			Point point;
			point = e.getPoint();
			if (point != null) {
				Dimension dim;
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
					if(listener!=null&&!listener.isEmpty())
					{
						CliqueListener tmp;
						for(int i=0;i<listener.size();i++)
						{
							tmp=listener.get(i);
							tmp.clique(no_ligne, no_colonne);
						}
					}
					log.info("ligne=" + no_ligne + ",colonne=" + no_colonne);
					repaint();
				}
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void mouseReleased(MouseEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void mouseEntered(MouseEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void mouseExited(MouseEvent e) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public void addListener(CliqueListener e)
	{
		this.listener.add(e);
	}

	public void removeListener(CliqueListener e) {
		this.listener.remove(e);
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}

	public void setModel(ModelOthello model) {
		this.model = model;
	}
}
