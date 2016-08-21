package org.shikato.gradle.android.coverage.check.coverage

import org.junit.Before
import org.junit.Test

/**
 * ClassTest.groovy
 *
 * Copyright (c) 2016 shikato
 *
 * This software is released under the MIT license.
 * http://opensource.org/licenses/mit-license.html
 */

class ClassTest {

    private Class mCoverageSourcefile;

    @Before
    void before() {
        mCoverageSourcefile = new Class();
    }

    @Test
    void getterAndSetter() {
        List<Counter> counterListExpected = new ArrayList<>();
        mCoverageSourcefile.setCounterList(counterListExpected);
        boolean isExcludeExpected = false;
        mCoverageSourcefile.setIsExclude(isExcludeExpected);
        String fileNameExpected = "shikatoFileName";
        mCoverageSourcefile.setClassName(fileNameExpected);
        String packageNameExpected = "shikatoPackageName";
        mCoverageSourcefile.setPackageName(packageNameExpected)

        assert mCoverageSourcefile.getCounterList() == counterListExpected;
        assert mCoverageSourcefile.getIsExclude() == isExcludeExpected;
        assert mCoverageSourcefile.getClassName() == fileNameExpected;
        assert mCoverageSourcefile.getPackageName() == packageNameExpected;
    }
}
