package com.semihbkgr.gorun;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class ContextInstrumentedTest {

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.semihbg.gorun", appContext.getPackageName());
    }

    @Test
    public void initializeAppContext() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppContext.initialize(context);
    }

}
