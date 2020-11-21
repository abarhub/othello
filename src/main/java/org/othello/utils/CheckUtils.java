package org.othello.utils;

public class CheckUtils {

    public static void checkArgument(boolean expression){
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkArgument(boolean expression, String message){
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
