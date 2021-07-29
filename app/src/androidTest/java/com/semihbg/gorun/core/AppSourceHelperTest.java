package com.semihbg.gorun.core;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppSourceHelperTest {

    private static final String TAG=AppSourceHelperTest.class.getName();
    private static final TestModel TEST_MODEL_INSTANCE;
    private static final String TEST_MODEL_JSON;

    static{
        TEST_MODEL_INSTANCE=new TestModel();
        TEST_MODEL_INSTANCE.setField1("field1Value");
        TEST_MODEL_INSTANCE.setField2("field2Value");
        TEST_MODEL_INSTANCE.setField3("field3Value");
        TEST_MODEL_JSON=
                "{\n" +
                "  \"field1\": \"filed1Value\",\n" +
                "  \"field2\": \"filed2Value\",\n" +
                "  \"field3\": \"filed3Value\"\n" +
                "}";
    }

    private AppSourceHelper appSourceHelper;

    @BeforeClass
    public static void initialize(){
        if(!AppContext.initialized()){
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            AppContext.initialize(context);
        }
    }


    @Before
    public void launch(){
        appSourceHelper=AppContext.instance().appSourceHelper;
        Log.i(TAG, "launch: ");
    }

    @Test
    public void readAssetAsString() throws IOException {
        String resource = appSourceHelper.readAsset(AppConstant.TEST_ASSET_FILE_NAME);
        assertEquals(TEST_MODEL_JSON,resource);
        Log.i(TAG, "readAssetAsString: Resource: "+resource);
    }

    public void readAssetAsType() throws IOException{
        TestModel testModel=appSourceHelper.readAsset(AppConstant.TEST_ASSET_FILE_NAME,TestModel.class);
        assertEquals(TEST_MODEL_INSTANCE,testModel);
        Log.i(TAG, "readAssetAsType: Resource instance: "+testModel);
    }

    private static class TestModel{

        private String field1;
        private String field2;
        private String field3;

        public TestModel() {
        }

        public TestModel(String field1, String field2, String field3) {
            this.field1 = field1;
            this.field2 = field2;
            this.field3 = field3;
        }

        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }

        public String getField3() {
            return field3;
        }

        public void setField3(String field3) {
            this.field3 = field3;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestModel testModel = (TestModel) o;

            if (field1 != null ? !field1.equals(testModel.field1) : testModel.field1 != null) return false;
            if (field2 != null ? !field2.equals(testModel.field2) : testModel.field2 != null) return false;
            return field3 != null ? field3.equals(testModel.field3) : testModel.field3 == null;
        }

        @Override
        public int hashCode() {
            int result = field1 != null ? field1.hashCode() : 0;
            result = 31 * result + (field2 != null ? field2.hashCode() : 0);
            result = 31 * result + (field3 != null ? field3.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "TestModel{" +
                    "field1='" + field1 + '\'' +
                    ", field2='" + field2 + '\'' +
                    ", field3='" + field3 + '\'' +
                    '}';
        }

    }

}