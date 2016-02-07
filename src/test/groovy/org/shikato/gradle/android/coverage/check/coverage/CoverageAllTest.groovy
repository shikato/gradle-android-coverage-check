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
        List<CoverageCounter> counterList = new ArrayList<>();
        mCoverageAll.setCounterList(counterList);
        assert mCoverageAll.getCounterList() == counterList;
    }

    @Test
    void sourceFileList() {
        List<CoverageSourcefile> sourceFileList = new ArrayList<>();
        mCoverageAll.setSourcefileList(sourceFileList);
        assert mCoverageAll.getSourcefileList() == sourceFileList;
    }
}
