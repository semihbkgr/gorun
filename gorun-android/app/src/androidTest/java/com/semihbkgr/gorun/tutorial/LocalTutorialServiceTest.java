package com.semihbkgr.gorun.tutorial;


import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import com.semihbkgr.gorun.core.AppContext;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LocalTutorialServiceTest {

    private static final String TAG = LocalTutorialServiceTest.class.getName();

    private LocalTutorialService localTutorialService;

    @BeforeClass
    public static void initialize(){
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppContext.initialize(context);
    }
    
    @Before
    public void launch() {
        localTutorialService = (LocalTutorialService) AppContext.instance().tutorialService;
        Log.i(TAG, "launch: Required objects have been created successfully");
    }

    @After
    public void destroy() {
        int count = AppContext.instance().appDatabaseHelper.getWritableDatabase()
                .delete(TutorialContract.TutorialSubject.TABLE_NAME, null, null);
        Log.i(TAG, String.format("destroy: %s table has been cleared", TutorialContract.TutorialSubject.TABLE_NAME));
    }

    @Test
    public void checkEmptyTableCount() {
        long count = localTutorialService.subjectCount();
        assertEquals(0, count);
        Log.i(TAG, "checkEmptyTableCount: Count: " + count);
    }

    @Test
    public void saveSubjectAndCheckCount() {
        Subject subject = new Subject(0, "title", "description");
        boolean saved = localTutorialService.save(subject);
        assertTrue(saved);
        Log.i(TAG, "saveSubjectAndCheckCount: Subject has been saved successfully, " + subject);
        long count = localTutorialService.subjectCount();
        assertEquals(1, count);
        Log.i(TAG, "saveSubjectAndCheckCount: Count: " + count);
    }

    @Test
    public void saveListOfSubjectsAndCheckCount() {
        List<Subject> subjectList = Arrays.asList(
                new Subject(1, "title", "description"),
                new Subject(1, "title", "description"),
                new Subject(1, "title", "description"));
        boolean saved = localTutorialService.saveAll(subjectList);
        assertTrue(saved);
        Log.i(TAG, "saveListOfSubjectsAndCheckCount: Subject has been saved successfully, " + subjectList);
        long count = localTutorialService.subjectCount();
        assertEquals(subjectList.size(), count);
        Log.i(TAG, "saveSubjectAndCheckCount: Count: " + count);
    }

    @Test
    public void saveSubjectAndCheckFindAllList(){
        Subject subject = new Subject(0, "title", "description");
        boolean saved = localTutorialService.save(subject);
        assertTrue(saved);
        Log.i(TAG, "saveSubjectAndCheckFindAllList: Subject has been saved successfully, " + subject);
        List<Subject> subjectList=localTutorialService.findSubjects();
        Log.i(TAG, "saveSubjectAndCheckFindAllList: All subjects: "+subjectList);
        assertEquals(1,subjectList.size());
        assertEquals(subject.getTitle(),subjectList.get(0).getTitle());
        assertEquals(subject.getDescription(),subjectList.get(0).getDescription());
    }


    @Test
    public void saveListOfSubjectsAndCheckFindAllList(){
        List<Subject> subjectList = Arrays.asList(
                new Subject(1, "title", "description"),
                new Subject(1, "title", "description"),
                new Subject(1, "title", "description"));
        boolean saved = localTutorialService.saveAll(subjectList);
        assertTrue(saved);
        Log.i(TAG, "saveListOfSubjectsAndCheckFindAllList: Subject has been saved successfully, " + subjectList);
        List<Subject> savedSubjectList=localTutorialService.findSubjects();
        Log.i(TAG, "saveListOfSubjectsAndCheckFindAllList: All subjects: "+savedSubjectList);
        assertEquals(subjectList.size(),savedSubjectList.size());
        for(int i=0;i<subjectList.size();i++){
            Subject subject=subjectList.get(i);
            Subject savedSubject=savedSubjectList.get(i);
            assertEquals(subject.getTitle(),savedSubject.getTitle());
            assertEquals(subject.getDescription(),savedSubject.getDescription());
        }

    }




}