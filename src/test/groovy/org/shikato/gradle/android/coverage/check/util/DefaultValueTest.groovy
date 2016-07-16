package org.shikato.gradle.android.coverage.check.util

import org.junit.Test

/**
 * DefaultValueTest.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class DefaultValueTest {

    @Test
    void values() {
        assert DefaultValue.INT == 0;
        assert DefaultValue.FLOAT == 0F;
        assert DefaultValue.STRING == "";
    }

    @Test
    void constructor() {
        new DefaultValue();
    }
}

