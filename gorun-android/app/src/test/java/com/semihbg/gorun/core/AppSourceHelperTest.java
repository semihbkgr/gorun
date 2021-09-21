package com.semihbg.gorun.core;

import android.util.Log;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppSourceHelperTest {

    private static final String TAG = AppSourceHelperTest.class.getName();
    private static final String TEST_RESOURCE_FILE_NAME = "test.json";
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

    private AppSourceHelper appSourceHelper;

    @BeforeEach
    public void launch() {
        appSourceHelper = new AppSourceHelper(null, new GsonBuilder().create());
    }

    @Test
    void checkIfLocalResourceFileExistsAndNotEmpty() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_RESOURCE_FILE_NAME)) {
            assertNotNull(is);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder resourceContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                resourceContent.append(line)
                        .append(System.lineSeparator());
            assertTrue(resourceContent.length() > 0);
            Log.i(TAG, "checkIfLocalResourceFileExistsAndNotEmpty: Resource content: " + resourceContent.toString());
            br.close();
            isr.close();
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void readStringFromStreamAndCheckIfEqual() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_RESOURCE_FILE_NAME)) {
            String resourceContent = appSourceHelper.readStringFromStream(is);
            assertEquals(TEST_MODEL_JSON, resourceContent);
            Log.i(TAG, "readStringFromStreamAndCheckIfEqual: Resource content: " + resourceContent.toString());
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void readTypeFromReaderAndCheckIfEquals() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_RESOURCE_FILE_NAME);
             Reader r = new InputStreamReader(is)) {
            TestModel testModel=appSourceHelper.readTypeFromReader(r,TestModel.class);
            assertEquals(TEST_MODEL_INSTANCE,testModel);
            Log.i(TAG, "readTypeFromReaderAndCheckIfEquals: TestModel content: "+testModel);
        } catch (IOException e) {
            fail(e);
        }
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