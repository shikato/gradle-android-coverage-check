package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * Counter.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class Counter {

    String type = DefaultValue.STRING;
    int missed = DefaultValue.INT;
    int covered = DefaultValue.INT;

    float rate = DefaultValue.FLOAT;
    boolean isSatisfied = false;
}
