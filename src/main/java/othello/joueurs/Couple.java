package othello.joueurs;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:02:43
 */
public class Couple {

    private int no_ligne;
    private int no_colonne;

    public Couple() {

    }

    public Couple(int no_ligne, int no_colonne) {
        this.no_ligne = no_ligne;
        this.no_colonne = no_colonne;
    }

    public int getNo_ligne() {
        return no_ligne;
    }

    public void setNo_ligne(int no_ligne) {
        this.no_ligne = no_ligne;
    }

    public int getNo_colonne() {
        return no_colonne;
    }

    public void setNo_colonne(int no_colonne) {
        this.no_colonne = no_colonne;
    }

    public boolean equals(Object c) {
        if (c == null)
            return false;
        if (!(c instanceof Couple))
            return false;
        Couple c2;
        c2 = (Couple) c;
        return c2.getNo_colonne() == getNo_colonne() && c2.getNo_ligne() == getNo_ligne();
    }

    public int hashCode() {
        return getNo_colonne() + getNo_ligne();
    }
}
