package org.othello;


import org.junit.jupiter.api.Test;
import org.othello.model.TableauSimple2;
import org.othello.utils.CheckUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: Alain
 * Date: 24 janv. 2010
 * Time: 17:01:41
 * To change this template use File | Settings | File Templates.
 */
public class TestTableauSimple2 {

    @Test
    public void test1() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 0, 0, true, 8);
        assertTrue(tmp1 == 1L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 1, true, 8);
        assertTrue (tmp1 == 2L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 2, true, 8);
        assertTrue (tmp1 == 4L, "tmp1=" + tmp1);
    }

    @Test
    public void test2() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 0, 0, true, 8);
        assertTrue (tmp1 == 1L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 1, true, 8);
        assertTrue (tmp1 == 2L, "tmp1=" + tmp1);
        tmp1 = TableauSimple2.set_bit(0L, 0, 2, true, 8);
        assertTrue (tmp1 == 4L, "tmp1=" + tmp1);

        tmp2 = 1L;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tmp1 = TableauSimple2.set_bit(0L, i, j, true, 8);
                assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2 + "(" + i + "," + j + ")");
                tmp2 = tmp2 << 1L;
            }
        }
    }

    @Test
    public void test3() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 3, 7, true, 8);
        tmp2 = 2147483648L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
    }

    @Test
    public void test4() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 6, 7, true, 8);
        tmp2 = 36028797018963968L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
        tmp1 = TableauSimple2.set_bit(0L, 7, 6, true, 8);
        tmp2 = 4611686018427387904L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
        tmp1 = TableauSimple2.set_bit(0L, 7, 7, true, 8);
        tmp2 = -9223372036854775808L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
    }


    @Test
    public void test5() {
        long tmp1, tmp2, fin;
        tmp1 = TableauSimple2.set_bit(0L, 6, 7, true, 8);
        tmp2 = 36028797018963968L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
        tmp1 = TableauSimple2.set_bit(0L, 7, 6, true, 8);
        tmp2 = 4611686018427387904L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
        tmp1 = TableauSimple2.set_bit(0L, 7, 7, true, 8);
        tmp2 = -9223372036854775808L;
        assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2);
        assertTrue (TableauSimple2.get_bit(tmp2, 7, 7, 8));
    }

    @Test
    public void test6() {
        long tmp1, tmp2, tmp3;
        tmp2 = 1L;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tmp1 = TableauSimple2.set_bit(0L, i, j, true, 8);
                assertTrue (tmp1 == tmp2, "tmp1=" + tmp1 + "!=" + tmp2 + "(" + i + "," + j + ")");
                assertTrue (TableauSimple2.get_bit(tmp1, i, j, 8), "tmp1=" + tmp1 + "!=" + tmp2 + "(" + i + "," + j + ")");
                tmp2 = tmp2 << 1L;
            }
        }
    }
}
