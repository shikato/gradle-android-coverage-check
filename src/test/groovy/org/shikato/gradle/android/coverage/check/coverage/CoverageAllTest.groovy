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
    void getterAndSetter() {
        List<CoverageCounter> counterList = new ArrayList<>();
        mCoverageAll.setCounterList(counterList);
        List<CoverageSourcefile> sourceFileList = new ArrayList<>();
        mCoverageAll.setSourcefileList(sourceFileList);
        String reportPathExpected = "hoge/report.xml";
        mCoverageAll.setReportPath(reportPathExpected);

        assert mCoverageAll.getSourcefileList() == sourceFileList;
        assert mCoverageAll.getCounterList() == counterList;
        assert mCoverageAll.getReportPath() == reportPathExpected;
    }
}
