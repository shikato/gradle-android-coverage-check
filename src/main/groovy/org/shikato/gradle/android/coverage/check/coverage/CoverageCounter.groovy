package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

class CoverageCounter {

    String type = DefaultValue.STRING;
    int missed = DefaultValue.INT;
    int covered = DefaultValue.INT;

    float rate = DefaultValue.FLOAT;
    boolean isSatisfied = false;
}
