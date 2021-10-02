package com.semihbkgr.gorun.core;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import com.semihbkgr.gorun.AppConstants;
import com.semihbkgr.gorun.AppContext;
import com.semihbkgr.gorun.AppResourceHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AppResourceHelperTest {

    private static final String TAG = AppResourceHelperTest.class.getName();
    private static final TestModel TEST_MODEL_INSTANCE;
    private static final String TEST_MODEL_JSON;

    static {
        TEST_MODEL_INSTANCE = new TestModel();
        TEST_MODEL_INSTANCE.setField1("field1Value");
        TEST_MODEL_INSTANCE.setField2("field2Value");
        TEST_MODEL_INSTANCE.setField3("field3Value");
        TEST_MODEL_JSON =
                "{\r\n" +
                        "  \"field1\": \"field1Value\",\r\n" +
                        "  \"field2\": \"field2Value\",\r\n" +
                        "  \"field3\": \"field3Value\"\r\n" +
                        "}";
    }

    private AppResourceHelper appResourceHelper;

    @BeforeClass
    public static void initialize() {
        if (!AppContext.initialized()) {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            AppContext.initialize(context);
            Log.i(TAG, "initialize: AppContext has been initialized");
        } else Log.i(TAG, "initialize: AppContext has already been initialized");
    }

    @Before
    public void launch() {
        appResourceHelper = AppContext.instance().resourceHelper;
        Log.i(TAG, "launch: Required objects have been created successfully");
    }

    @Test
    public void readAssetAsString() throws IOException {
        String resource = appResourceHelper.readAsset(AppConstants.Resources.TEST_ASSET_FILE_NAME);
        assertEquals(TEST_MODEL_JSON, resource);
        Log.i(TAG, "readAssetAsString: Resource: " + resource);
    }

    @Test
    public void readAssetAsType() throws IOException {
        TestModel testModel = appResourceHelper.readAsset(AppConstants.Resources.TEST_ASSET_FILE_NAME, TestModel.class);
        assertEquals(TEST_MODEL_INSTANCE, testModel);
        Log.i(TAG, "readAssetAsType: Resource instance: " + testModel);
    }

    private static class TestModel {

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

            if (!Objects.equals(field1, testModel.field1)) return false;
            if (!Objects.equals(field2, testModel.field2)) return false;
            return Objects.equals(field3, testModel.field3);
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