package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * CoverageAll.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class All implements Coverage {

    public static final String INSTRUCTION = "INSTRUCTION";
    public static final String BRANCH = "BRANCH";

    boolean isHavingUnsatisfiedCoverage = false;

    String reportPath = DefaultValue.STRING;

    List<Class> classList = new ArrayList<>();
    List<Sourcefile> sourcefileList = new ArrayList<>();
    List<Counter> counterList = new ArrayList<>();

    @Override
    List<Counter> getCounterList() {
        return this.counterList;
    }

    @Override
    void setCounterList(List<Counter> counterList) {
        this.counterList = counterList;
    }
}
