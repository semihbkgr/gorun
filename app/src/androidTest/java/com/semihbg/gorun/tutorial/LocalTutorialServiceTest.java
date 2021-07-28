package com.semihbg.gorun.tutorial;


import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.semihbg.gorun.core.AppContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
class LocalTutorialServiceTest {

    @Before
    void initialize(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppContext.initialize(context);
    }

}