package org.othello.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckUtilsTest {

    @Test
    void checkArgumentMessageOK() {

        // vérifie qu'il n'y a pas d'exceptions levés
        CheckUtils.checkArgument(true, "test");
    }

    @Test
    void checkArgumentMessageKO() {

        var res = assertThrows(IllegalArgumentException.class, () -> CheckUtils.checkArgument(false, "test"));

        assertEquals("test", res.getMessage());
    }

    @Test
    void CheckArgumentSansMessageOK() {

        // vérifie qu'il n'y a pas d'exceptions levés
        CheckUtils.checkArgument(true);
    }

    @Test
    void CheckArgumentSansMessageKO() {

        var res = assertThrows(IllegalArgumentException.class, () -> CheckUtils.checkArgument(false));

        assertNull(res.getMessage());
    }
}