package org.shikato.gradle.android.coverage.check.log

import org.junit.Test
import org.shikato.gradle.android.coverage.check.util.ErrorValue

/**
 * ColorLogMessageTest.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ColorLogMessageTest {

    private static final String RELEASE = "\u001b[m";
    private static final String RED_BOLD_CHAR = "\u001b[31;1m";
    private static final String GREEN_BOLD_CHAR = "\u001b[32;1m";
    private static final String YELLOW_BOLD_CHAR = "\u001b[33;1m";
    private static final String WHITE_BOLD_CHAR = "\u001b[1m";

    @Test
    void ifArgIsNull_RetIsErrorValue_STRING() {
        assert ColorLogMessage.boldGreen(null) == ErrorValue.STRING;
        assert ColorLogMessage.boldRed(null) == ErrorValue.STRING;
        assert ColorLogMessage.boldYellow(null) == ErrorValue.STRING;
        assert ColorLogMessage.boldWhite(null) == ErrorValue.STRING;
    }

    @Test
    void ifArgIsStringVal_RetIsColorChar() {
        String target = "shikato";
        assert ColorLogMessage.boldGreen(target) == GREEN_BOLD_CHAR + target + RELEASE;
        assert ColorLogMessage.boldRed(target) == RED_BOLD_CHAR + target + RELEASE;
        assert ColorLogMessage.boldYellow(target) == YELLOW_BOLD_CHAR + target + RELEASE;
        assert ColorLogMessage.boldWhite(target) == WHITE_BOLD_CHAR + target + RELEASE;
    }

    @Test
    void constructor() {
        new ColorLogMessage();
    }
}
