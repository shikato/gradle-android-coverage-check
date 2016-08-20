package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * CoverageSourcefile.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class Class implements Coverage {

    String packageName = DefaultValue.STRING;
    String fileName = DefaultValue.STRING;

    List<Counter> counterList = new ArrayList<>();

    boolean isExclude = false;

    @Override
    List<Counter> getCounterList() {
        return counterList;
    }

    @Override
    void setCounterList(List<Counter> counterList) {
        this.counterList = counterList
    }
}
