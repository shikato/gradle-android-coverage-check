package org.shikato.gradle.android.coverage.check.util

import org.junit.Test

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

