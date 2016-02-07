package org.shikato.gradle.android.coverage.check.util

import org.junit.Test

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
