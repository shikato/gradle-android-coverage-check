package org.shikato.gradle.android.coverage.check.coverage

import org.junit.Before
import org.junit.Test

class CoverageSourcefileTest {

    private CoverageSourcefile mCoverageSourcefile;

    @Before
    void before() {
        mCoverageSourcefile = new CoverageSourcefile();
    }

    @Test
    void getterAndSetter() {
        List<CoverageCounter> counterListExpected = new ArrayList<>();
        mCoverageSourcefile.setCounterList(counterListExpected);
        boolean isExcludeExpected = false;
        mCoverageSourcefile.setIsExclude(isExcludeExpected);
        String fileNameExpected = "shikatoFileName";
        mCoverageSourcefile.setFileName(fileNameExpected);
        String packageNameExpected = "shikatoPackageName";
        mCoverageSourcefile.setPackageName(packageNameExpected)

        assert mCoverageSourcefile.getCounterList() == counterListExpected;
        assert mCoverageSourcefile.getIsExclude() == isExcludeExpected;
        assert mCoverageSourcefile.getFileName() == fileNameExpected;
        assert mCoverageSourcefile.getPackageName() == packageNameExpected;
    }
}
