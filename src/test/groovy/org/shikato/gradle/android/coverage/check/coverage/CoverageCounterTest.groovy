package org.shikato.gradle.android.coverage.check.coverage

import org.junit.Before
import org.junit.Test

class CoverageCounterTest {

    private CoverageCounter mCounter;

    @Before
    void before() {
        mCounter = new CoverageCounter();
    }

    @Test
    void getterAndSetter() {
        boolean satisfiedExpected = false;
        mCounter.setIsSatisfied(satisfiedExpected);
        String typeExpected = "type";
        mCounter.setType(typeExpected);
        int coverdExpected = 20;
        mCounter.setCovered(coverdExpected);
        int missedExpected = 2;
        mCounter.setMissed(missedExpected);
        float rateExpected = 3F;
        mCounter.setRate(rateExpected);

        assert mCounter.getIsSatisfied() == satisfiedExpected;
        assert mCounter.getType() == typeExpected;
        assert mCounter.getCovered() == coverdExpected;
        assert mCounter.getMissed() == missedExpected;
        assert mCounter.getRate() == rateExpected;
    }
}
