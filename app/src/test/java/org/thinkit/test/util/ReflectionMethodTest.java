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
 * The class that manages test case of {@link ReflectionMethod} .
 *
 * @author Kato Shinya
 * @since 1.0.3
 */
public final class ReflectionMethodTest {

    /**
     * The expected success value
     */
    private static final String EXPECTED_STR_SUCCESS = "success";

    /**
     * The expected failure value
     */
    private static final String EXPECTED_STR_FAILURE = "failure";

    /**
     * The nested class for {@link ReflectionMethod#invokeMethod(String)} method.
     */
    @Nested
    class TestInvokeMethod {

        @Test
        void testReturnStringWithNoArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String actual = sut.invokeMethod(TestMethod.RETURN_STRING_WITH_NO_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnStringSuccessWithArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String actual = sut.addArgument(String.class, "test")
                    .invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnStringFailureWithArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String actual = sut.addArgument(String.class, "")
                    .invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_FAILURE, actual);
        }

        @Test
        void testReturnStringWithArguments() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            sut.addArgument(String.class, "").addArgument(int.class, 0).addArgument(boolean.class, true);
            final String actual = sut.invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnIntegerWithNoArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final int actual = sut.invokeMethod(TestMethod.RETURN_INTEGER_WITH_NO_ARGUMENT.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnIntegerTrueWithArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final int actual = sut.addArgument(boolean.class, true)
                    .invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnIntegerFalseWithArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final int actual = sut.addArgument(boolean.class, false)
                    .invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(0, actual);
        }

        @Test
        void testReturnIntegerWithArguments() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            sut.addArgument(int.class, 1).addArgument(String.class, "test").addArgument(boolean.class, false);
            final int actual = sut.invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENTS.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnBooleanWithNoArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final boolean actual = sut.invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_NO_ARGUMENT.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnBooleanTrueWithArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final boolean actual = sut.addArgument(int.class, 1)
                    .invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnBooleanFalseWithArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final boolean actual = sut.addArgument(int.class, 0)
                    .invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(false, actual);
        }

        @Test
        void testReturnBooleanWithArguments() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            sut.addArgument(int.class, 0).addArgument(String.class, "test").addArgument(boolean.class, true);
            final boolean actual = sut.invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENTS.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnListWithArguments() {
            final ReflectionMethod<List<String>> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String[] expectedSequences = new String[] { "test6", "test1", "test100" };

            for (String expectedSequence : expectedSequences) {
                sut.addArgument(String.class, expectedSequence);
            }

            final List<String> actual = sut.invokeMethod(TestMethod.RETURN_LIST_WITH_ARGUMENTS.getName());
            final int actualSize = actual.size();

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertTrue(actualSize == expectedSequences.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedSequences[i], actual.get(i));
            }
        }

        @Test
        void testReturnMapWithArguments() {
            final ReflectionMethod<Map<String, Integer>> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final int[] expectedNumbers = new int[] { 100, 1000, 1 };

            for (Integer expectedSequence : expectedNumbers) {
                sut.addArgument(int.class, expectedSequence.intValue());
            }

            final Map<String, Integer> actual = sut.invokeMethod(TestMethod.RETURN_MAP_WITH_ARGUMENTS.getName());
            final int actualSize = actual.size();

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertTrue(actualSize == expectedNumbers.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedNumbers[i], actual.get(String.format("result%s", i + 1)).intValue());
            }
        }

        @Test
        void testWhenMethodNameIsEmpty() {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> ReflectionMethod.from(ReflectionTestDataSet.class).invokeMethod(""));
            assertEquals("Method name must not be empty.", exception.getMessage());
        }
    }

    /**
     * The nested class for {@link ReflectionMethod#invokeMethod(String)} method.
     */
    @Nested
    class TestInvokeStaticMethod {

        @Test
        void testReturnStringWithNoArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final String actual = sut.invokeMethod(TestMethod.RETURN_STRING_WITH_NO_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnStringSuccessWithArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final String actual = sut.addArgument(String.class, "test")
                    .invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnStringFailureWithArgument() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final String actual = sut.addArgument(String.class, "")
                    .invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_FAILURE, actual);
        }

        @Test
        void testReturnStringWithArguments() {
            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            sut.addArgument(String.class, "").addArgument(int.class, 0).addArgument(boolean.class, true);
            final String actual = sut.invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testReturnIntegerWithNoArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final int actual = sut.invokeMethod(TestMethod.RETURN_INTEGER_WITH_NO_ARGUMENT.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnIntegerTrueWithArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final int actual = sut.addArgument(boolean.class, true)
                    .invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnIntegerFalseWithArgument() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final int actual = sut.addArgument(boolean.class, false)
                    .invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENT.getName());

            assertEquals(0, actual);
        }

        @Test
        void testReturnIntegerWithArguments() {
            final ReflectionMethod<Integer> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            sut.addArgument(int.class, 1).addArgument(String.class, "test").addArgument(boolean.class, false);
            final int actual = sut.invokeMethod(TestMethod.RETURN_INTEGER_WITH_ARGUMENTS.getName());

            assertEquals(1, actual);
        }

        @Test
        void testReturnBooleanWithNoArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final boolean actual = sut.invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_NO_ARGUMENT.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnBooleanTrueWithArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final boolean actual = sut.addArgument(int.class, 1)
                    .invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnBooleanFalseWithArgument() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final boolean actual = sut.addArgument(int.class, 0)
                    .invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENT.getName());

            assertEquals(false, actual);
        }

        @Test
        void testReturnBooleanWithArguments() {
            final ReflectionMethod<Boolean> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            sut.addArgument(int.class, 0).addArgument(String.class, "test").addArgument(boolean.class, true);
            final boolean actual = sut.invokeMethod(TestMethod.RETURN_BOOLEAN_WITH_ARGUMENTS.getName());

            assertEquals(true, actual);
        }

        @Test
        void testReturnListWithArguments() {
            final ReflectionMethod<List<String>> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final String[] expectedSequences = new String[] { "test6", "test1", "test100" };

            for (String expectedSequence : expectedSequences) {
                sut.addArgument(String.class, expectedSequence);
            }

            final List<String> actual = sut.invokeMethod(TestMethod.RETURN_LIST_WITH_ARGUMENTS.getName());
            final int actualSize = actual.size();

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertTrue(actualSize == expectedSequences.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedSequences[i], actual.get(i));
            }
        }

        @Test
        void testReturnMapWithArguments() {
            final ReflectionMethod<Map<String, Integer>> sut = ReflectionMethod.from(ReflectionStaticTestDataSet.class);
            final int[] expectedNumbers = new int[] { 100, 1000, 1 };

            for (Integer expectedSequence : expectedNumbers) {
                sut.addArgument(int.class, expectedSequence.intValue());
            }

            final Map<String, Integer> actual = sut.invokeMethod(TestMethod.RETURN_MAP_WITH_ARGUMENTS.getName());
            final int actualSize = actual.size();

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertTrue(actualSize == expectedNumbers.length);

            for (int i = 0; i < actualSize; i++) {
                assertEquals(expectedNumbers[i], actual.get(String.format("result%s", i + 1)).intValue());
            }
        }

        @Test
        void testWhenMethodNameIsEmpty() {
            final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                    () -> ReflectionMethod.from(ReflectionTestDataSet.class).invokeMethod(""));
            assertEquals("Method name must not be empty.", exception.getMessage());
        }
    }

    /**
     * The nested class for {@link ReflectionMethod#addArgument(Class, Object)}
     * method.
     */
    @Nested
    class TestAddArgument {

        @Test
        void testSimplePattern() {

            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String actual = sut.addArgument(String.class, "test")
                    .invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENT.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
        }

        @Test
        void testMethodChainPattern() {

            final ReflectionMethod<String> sut = ReflectionMethod.from(ReflectionTestDataSet.class);
            final String actual = sut.addArgument(String.class, "test").addArgument(int.class, 0)
                    .addArgument(boolean.class, true).invokeMethod(TestMethod.RETURN_STRING_WITH_ARGUMENTS.getName());

            assertNotNull(actual);
            assertTrue(!actual.isEmpty());
            assertEquals(EXPECTED_STR_SUCCESS, actual);
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
