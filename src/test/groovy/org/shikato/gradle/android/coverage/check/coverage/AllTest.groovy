package org.shikato.gradle.android.coverage.check.coverage

import org.junit.Before
import org.junit.Test

/**
 * CoverageAllTest.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class AllTest {

    private All mCoverageAll;

    @Before
    void before() {
        mCoverageAll = new All();
    }

    @Test
    void getterAndSetter() {
        List<Counter> counterListExpected = new ArrayList<>();
        mCoverageAll.setCounterList(counterListExpected);
        List<Class> sourceFileList = new ArrayList<>();
        mCoverageAll.setSourcefileList(sourceFileList);
        String reportPathExpected = "hoge/report.xml";
        mCoverageAll.setReportPath(reportPathExpected);

        assert mCoverageAll.getSourcefileList() == sourceFileList;
        assert mCoverageAll.getCounterList() == counterListExpected;
        assert mCoverageAll.getReportPath() == reportPathExpected;
    }
}
