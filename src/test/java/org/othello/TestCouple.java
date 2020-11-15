package org.othello;

import org.junit.jupiter.api.Test;
import org.othello.joueurs.Couple;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 17:13:44
 * To change this template use File | Settings | File Templates.
 */
public class TestCouple {

    @Test
    public void test_assert() {
        boolean b = false;
        System.out.println("b=" + b);
        assert (b = true);
        System.out.println("b2=" + b);
        assertTrue(b);
    }

    @Test
    public void test1() {
        long debut, fin;
        Map<Couple, Integer> map;
        Couple c1, c2;
        assert ((new Couple(1, 2)).equals(new Couple(1, 2)));
        assert (!(new Couple(2, 2)).equals(new Couple(1, 2)));
        assert ((new Couple(3, 3)).equals(new Couple(3, 3)));
        assert ((new Couple(3, 3)).hashCode() == (new Couple(3, 3).hashCode()));
        map = new HashMap<Couple, Integer>();
        c1 = new Couple(3, 3);
        c2 = new Couple(3, 3);
        assert (c1.equals(c2));
        map.put(c1, 7);
        assert (map.containsKey(c2));
        assert (map.get(c2) == 7);
        assert (map.get(c1) == 7);
    }
}
