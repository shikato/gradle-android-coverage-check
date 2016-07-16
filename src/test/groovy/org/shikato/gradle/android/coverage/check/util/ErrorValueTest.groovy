package org.shikato.gradle.android.coverage.check.util

import org.junit.Test

/**
 * ErrorValueTest.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ErrorValueTest {

    @Test
    void values() {
        assert ErrorValue.STRING == null;
        assert ErrorValue.INT == -1;
        assert ErrorValue.FLOAT == -1F;
    }

    @Test
    void constructor() {
        new ErrorValue();
    }
}
