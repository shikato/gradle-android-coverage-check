package org.shikato.gradle.android.coverage.check.coverage

import org.shikato.gradle.android.coverage.check.util.DefaultValue

class CoverageAll implements Coverage {

    public static final String INSTRUCTION = "INSTRUCTION";
    public static final String BRANCH = "BRANCH";

    boolean isHavingUnsatisfiedCoverage = false;

    String reportPath = DefaultValue.STRING;

    // TODO: packageごとのcoverage

    List<CoverageSourcefile> sourcefileList = new ArrayList<>();
    List<CoverageCounter> counterList = new ArrayList<>();

    @Override
    List<CoverageCounter> getCounterList() {
        return counterList;
    }

    @Override
    def setCounterList(List<CoverageCounter> counterList) {
        this.counterList = counterList;
    }
}
