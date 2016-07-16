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

class CoverageSourcefile implements Coverage {

    String packageName = DefaultValue.STRING;
    String fileName = DefaultValue.STRING;

    List<CoverageCounter> counterList = new ArrayList<>();

    boolean isExclude = false;

    @Override
    List<CoverageCounter> getCounterList() {
        return counterList;
    }

    @Override
    void setCounterList(List<CoverageCounter> counterList) {
        this.counterList = counterList
    }
}
