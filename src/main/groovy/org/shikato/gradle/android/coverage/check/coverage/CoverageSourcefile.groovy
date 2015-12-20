package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

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
    def setCounterList(List<CoverageCounter> counterList) {
        this.counterList = counterList
    }
}
