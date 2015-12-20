package org.shikato.gradle.android.coverage.check.coverage;

interface Coverage {
    public static final String INSTRUCTION = "INSTRUCTION";
    public static final String BRANCH = "BRANCH";

    List<CoverageCounter> getCounterList();
    void setCounterList(List<CoverageCounter> counterList);
}
