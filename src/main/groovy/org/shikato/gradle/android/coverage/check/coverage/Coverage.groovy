package org.shikato.gradle.android.coverage.check.coverage;

/**
 * Coverage.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

interface Coverage {
    public static final String INSTRUCTION = "INSTRUCTION";
    public static final String BRANCH = "BRANCH";

    List<Counter> getCounterList();
    void setCounterList(List<Counter> counterList);
}
