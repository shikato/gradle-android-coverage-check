package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

/**
 * Class.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class Class implements Coverage {

    String packageName = DefaultValue.STRING;
    String className = DefaultValue.STRING;
    boolean isTarget = false;
    boolean isExclude = false;
    List<Counter> counterList = new ArrayList<>();

    @Override
    List<Counter> getCounterList() {
        return counterList;
    }

    @Override
    void setCounterList(List<Counter> counterList) {
        this.counterList = counterList
    }
}
