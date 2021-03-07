/*
 * Copyright 2021 Kato Shinya.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.thinkit.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.RequiredArgsConstructor;

/**
 * The class that manages test case of {@link ReflectionTestHelper} .
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class ReflectionTestHelperTest {

    /**
     * The expected success value
     */
    private static final String STR_SUCCESS = "success";

    /**
     * The expected failure value
     */
    private static final String STR_FAILURE = "failure";

    @Nested
    class TestInvoke {

        @Test
        void testReturnStringWithNoArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final String actualResult = reflection.invoke(TestMethod.RETURN_STRING_WITH_NO_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnStringSuccessWithArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final String actualResult = reflection.add(String.class, "test")
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnStringFailureWithArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final String actualResult = reflection.add(String.class, "")
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_FAILURE, actualResult);
        }

        @Test
        void testReturnStringWithArguments() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            reflection.add(String.class, "").add(int.class, 0).add(boolean.class, true);
            final String actualResult = reflection.invoke(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnIntegerWithNoArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final int actualResult = reflection.invoke(TestMethod.RETURN_INTEGER_WITH_NO_ARGUMENT.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnIntegerTrueWithArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final int actualResult = reflection.add(boolean.class, true)
                    .invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnIntegerFalseWithArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final int actualResult = reflection.add(boolean.class, false)
                    .invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(0, actualResult);
        }

        @Test
        void testReturnIntegerWithArguments() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            reflection.add(int.class, 1).add(String.class, "test").add(boolean.class, false);
            final int actualResult = reflection.invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENTS.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnBooleanWithNoArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final boolean actualResult = reflection.invoke(TestMethod.RETURN_BOOLEAN_WITH_NO_ARGUMENT.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnBooleanTrueWithArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final boolean actualResult = reflection.add(int.class, 1)
                    .invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnBooleanFalseWithArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final boolean actualResult = reflection.add(int.class, 0)
                    .invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(false, actualResult);
        }

        @Test
        void testReturnBooleanWithArguments() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            reflection.add(int.class, 0).add(String.class, "test").add(boolean.class, true);
            final boolean actualResult = reflection.invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENTS.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnListWithArguments() {
            final ReflectionTestHelper<List<String>> reflection = ReflectionTestHelper
                    .from(ReflectionTestDataSet.class);
            final String[] expectedSequences = new String[] { "test6", "test1", "test100" };

            for (String expectedSequence : expectedSequences) {
                reflection.add(String.class, expectedSequence);
            }

            final List<String> actualResult = reflection.invoke(TestMethod.RETURN_LIST_WITH_ARGUMENTS.getName());
            final int actualSize = actualResult.size();

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertTrue(actualSize == expectedSequences.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedSequences[i], actualResult.get(i));
            }
        }

        @Test
        void testReturnMapWithArguments() {
            final ReflectionTestHelper<Map<String, Integer>> reflection = ReflectionTestHelper
                    .from(ReflectionTestDataSet.class);
            final int[] expectedNumbers = new int[] { 100, 1000, 1 };

            for (Integer expectedSequence : expectedNumbers) {
                reflection.add(int.class, expectedSequence.intValue());
            }

            final Map<String, Integer> actualResult = reflection.invoke(TestMethod.RETURN_MAP_WITH_ARGUMENTS.getName());
            final int actualSize = actualResult.size();

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertTrue(actualSize == expectedNumbers.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedNumbers[i], actualResult.get(String.format("result%s", i + 1)).intValue());
            }
        }

        @Test
        void testWhenMethodNameIsEmpty() {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> ReflectionTestHelper.from(ReflectionTestDataSet.class).invoke(""));
            assertEquals("Method name must not be empty.", exception.getMessage());
        }
    }

    /**
     * The nested class for {@link ReflectionTestHelper#invoke(String)} method.
     */
    @Nested
    class TestInvokeStatic {

        @Test
        void testReturnStringWithNoArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final String actualResult = reflection.invoke(TestMethod.RETURN_STRING_WITH_NO_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnStringSuccessWithArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final String actualResult = reflection.add(String.class, "test")
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnStringFailureWithArgument() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final String actualResult = reflection.add(String.class, "")
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_FAILURE, actualResult);
        }

        @Test
        void testReturnStringWithArguments() {
            final ReflectionTestHelper<String> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            reflection.add(String.class, "").add(int.class, 0).add(boolean.class, true);
            final String actualResult = reflection.invoke(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals(STR_SUCCESS, actualResult);
        }

        @Test
        void testReturnIntegerWithNoArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final int actualResult = reflection.invoke(TestMethod.RETURN_INTEGER_WITH_NO_ARGUMENT.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnIntegerTrueWithArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final int actualResult = reflection.add(boolean.class, true)
                    .invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnIntegerFalseWithArgument() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final int actualResult = reflection.add(boolean.class, false)
                    .invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(0, actualResult);
        }

        @Test
        void testReturnIntegerWithArguments() {
            final ReflectionTestHelper<Integer> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            reflection.add(int.class, 1).add(String.class, "test").add(boolean.class, false);
            final int actualResult = reflection.invoke(TestMethod.RETURN_INTEGER_WITH_ARGUMENTS.getName());

            assertEquals(1, actualResult);
        }

        @Test
        void testReturnBooleanWithNoArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final boolean actualResult = reflection.invoke(TestMethod.RETURN_BOOLEAN_WITH_NO_ARGUMENT.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnBooleanTrueWithArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final boolean actualResult = reflection.add(int.class, 1)
                    .invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnBooleanFalseWithArgument() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final boolean actualResult = reflection.add(int.class, 0)
                    .invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(false, actualResult);
        }

        @Test
        void testReturnBooleanWithArguments() {
            final ReflectionTestHelper<Boolean> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            reflection.add(int.class, 0).add(String.class, "test").add(boolean.class, true);
            final boolean actualResult = reflection.invoke(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENTS.getName());

            assertEquals(true, actualResult);
        }

        @Test
        void testReturnListWithArguments() {
            final ReflectionTestHelper<List<String>> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final String[] expectedSequences = new String[] { "test6", "test1", "test100" };

            for (String expectedSequence : expectedSequences) {
                reflection.add(String.class, expectedSequence);
            }

            final List<String> actualResult = reflection.invoke(TestMethod.RETURN_LIST_WITH_ARGUMENTS.getName());
            final int actualSize = actualResult.size();

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertTrue(actualSize == expectedSequences.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedSequences[i], actualResult.get(i));
            }
        }

        @Test
        void testReturnMapWithArguments() {
            final ReflectionTestHelper<Map<String, Integer>> reflection = ReflectionTestHelper
                    .from(ReflectionStaticTestDataSet.class);
            final int[] expectedNumbers = new int[] { 100, 1000, 1 };

            for (Integer expectedSequence : expectedNumbers) {
                reflection.add(int.class, expectedSequence.intValue());
            }

            final Map<String, Integer> actualResult = reflection.invoke(TestMethod.RETURN_MAP_WITH_ARGUMENTS.getName());
            final int actualSize = actualResult.size();

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertTrue(actualSize == expectedNumbers.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedNumbers[i], actualResult.get(String.format("result%s", i + 1)).intValue());
            }
        }

        @Test
        void testWhenMethodNameIsEmpty() {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> ReflectionTestHelper.from(ReflectionTestDataSet.class).invoke(""));
            assertEquals("Method name must not be empty.", exception.getMessage());
        }
    }

    /**
     * The nested class for {@link ReflectionTestHelper#add(Class, Object)} method.
     */
    @Nested
    class TestAdd {

        @Test
        void testSimplePattern() {

            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final String actualResult = reflection.add(String.class, "test")
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals("success", actualResult);
        }

        @Test
        void testMethodChainPattern() {

            final ReflectionTestHelper<String> reflection = ReflectionTestHelper.from(ReflectionTestDataSet.class);
            final String actualResult = reflection.add(String.class, "test").add(int.class, 0).add(boolean.class, true)
                    .invoke(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actualResult);
            assertTrue(!actualResult.isEmpty());
            assertEquals("success", actualResult);
        }
    }

    /**
     * The enum that manages method name to be called by reflection process.
     */
    @RequiredArgsConstructor
    private enum TestMethod {

        /**
         * The method that returns string without argument.
         */
        RETURN_STRING_WITH_NO_ARGUMENT(Name.returnStringWithNoArgument),

        /**
         * The method that returns string with argument.
         */
        RETURN_STRING_WITH_ARGUMENT(Name.returnStringWithArgument),

        /**
         * The method that returns string with some arguments.
         */
        RETURN_STRING_WITH_ARGUMENTS(Name.returnStringWithArguments),

        /**
         * The method that returns int without argument.
         */
        RETURN_INTEGER_WITH_NO_ARGUMENT(Name.returnIntegerWithNoArgument),

        /**
         * The method that returns int with argument.
         */
        RETURN_INTEGER_WITH_ARGUMENT(Name.returnIntegerWithArgument),

        /**
         * The method that returns int with some arguments.
         */
        RETURN_INTEGER_WITH_ARGUMENTS(Name.returnIntegerWithArguments),

        /**
         * The method that returns boolean without argument.
         */
        RETURN_BOOLEAN_WITH_NO_ARGUMENT(Name.returnBooleanWithNoArgument),

        /**
         * The method that returns boolean with argument.
         */
        RETURN_BOOLEAN_WITH_ARGUMENT(Name.returnBooleanWithArgument),

        /**
         * The method that returns boolean with some arguments.
         */
        RETURN_BOOLEAN_WITH_ARGUMENTS(Name.returnBooleanWithArguments),

        /**
         * The method that returns list with some arguments.
         */
        RETURN_LIST_WITH_ARGUMENTS(Name.returnListWithArguments),

        /**
         * The method that returns map with some arguments.
         */
        RETURN_MAP_WITH_ARGUMENTS(Name.returnMapWithArguments);

        /**
         * The method name
         */
        private final Name methodName;

        /**
         * The inner enum that manages method name.
         */
        private enum Name {
            returnStringWithNoArgument, returnStringWithArgument, returnStringWithArguments,
            returnIntegerWithNoArgument, returnIntegerWithArgument, returnIntegerWithArguments,
            returnBooleanWithNoArgument, returnBooleanWithArgument, returnBooleanWithArguments, returnListWithArguments,
            returnMapWithArguments;
        }

        /**
         * Returns the method name
         *
         * @return The method name
         */
        private String getName() {
            return this.methodName.name();
        }
    }
}
