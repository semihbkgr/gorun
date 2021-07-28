package com.semihbg.gorun.tutorial;


import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.platform.app.InstrumentationRegistry;
import com.semihbg.gorun.core.AppContext;
import com.semihbg.gorun.core.AppDatabaseHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LocalTutorialServiceTest {

    private static final String TAG=LocalTutorialServiceTest.class.getName();

    private LocalTutorialService localTutorialService;

    @Before
    public void initialize(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppContext.initialize(context);
        localTutorialService=(LocalTutorialService)AppContext.instance().tutorialService;
    }

    @After
    public void destroy(){
    }

   @Test
    public void countReturnZero(){
        long count=localTutorialService.subjectCount();
        assertEquals(0,count);
        Log.i(TAG, "countReturnZero: Count: "+count);
    }
/*
   @Test
    public void saveSubject(){
        Subject subject=new Subject(0,"title","description");
        boolean saved=localTutorialService.save(subject);
        saved=localTutorialService.save(subject);
        assertTrue(saved);
        Log.i(TAG, "saveSubject: Subject has been saved successfully, "+subject);
       int count=localTutorialService.subjectCount();
       assertEquals(0,count);
       Log.i(TAG, "saveSubject: "+count);
    }

 */

//    @Test
//    public void saveSubjects(){
//        Arrays.asList(
//                new Subject(1,"title","description"),
//                new Subject(1,"title","description"),
//                new Subject(1,"title","description"));
//        long id=localTutorialService.save(saveSubject());
//        assertNotEquals(0,id);
//        Log.i(TAG, "saveSubject: Subject has been saved successfully, id: "+id);
//    }

}