package org.shikato.gradle.android.coverage.check.coverage

import org.junit.Before
import org.junit.Test

class CoverageAllTest {

    private CoverageAll mCoverageAll;

    @Before
    void before() {
        mCoverageAll = new CoverageAll();
    }

    @Test
    void coverageCounter() {
        CoverageAll coverageAll = new CoverageAll();
        List<CoverageCounter> counterList = new ArrayList<>();
        coverageAll.setCounterList(counterList);
        assert coverageAll.getCounterList() == counterList;
    }

    @Test
    void sourceFileList() {
        CoverageAll coverageAll = new CoverageAll();
        List<CoverageSourcefile> sourceFileList = new ArrayList<>();
        coverageAll.setSourcefileList(sourceFileList);
        assert coverageAll.getSourcefileList() == sourceFileList;
    }
}
