package org.shikato.gradle.android.coverage.check.log

class ColorLogMessage {

    private static final String RELEASE = "\u001b[m";
    private static final String RED_BOLD_CHAR = "\u001b[31;1m";
    private static final String GREEN_BOLD_CHAR = "\u001b[32;1m";
    private static final String YELLOW_BOLD_CHAR = "\u001b[33;1m";
    private static final String WHITE_BOLD_CHAR = "\u001b[1m";

    public static String boldWhite(String message) {
        if (message == null) return;
        return WHITE_BOLD_CHAR + message + RELEASE;
    }

    public static String boldRed(String message) {
        if (message == null) return;
        return RED_BOLD_CHAR + message + RELEASE;
    }

    public static String boldGreen(String message) {
        if (message == null) return;
        return GREEN_BOLD_CHAR + message + RELEASE;
    }

    public static String boldYellow(String message) {
        if (message == null) return;
        return YELLOW_BOLD_CHAR + message + RELEASE;
    }
}

