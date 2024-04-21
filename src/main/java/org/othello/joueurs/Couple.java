package org.othello.joueurs;

import org.othello.utils.CheckUtils;

/**
 * User: Barret
 * Date: 10 déc. 2009
 * Time: 22:02:43
 */
public record Couple(int no_ligne, int no_colonne) {

    public Couple {
        CheckUtils.checkArgument(no_ligne >= 0);
        CheckUtils.checkArgument(no_colonne >= 0);
    }

}
