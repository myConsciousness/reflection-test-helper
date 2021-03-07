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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The test data set of static method.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
final class ReflectionStaticTestDataSet {

    /**
     * Returns the string {@code "sccess"} without argument.
     *
     * @return The string {@cpde "success"}
     */
    @SuppressWarnings("unused")
    private static String returnStringWithNoArgument() {
        return "success";
    }

    /**
     * Returns the string {@code "success"} if the argument {@code arg} is not
     * {@code null} and not empty, otherwise {@code "failure"} .
     *
     * @param arg The argument
     * @return {@code "success"} if the argument {@code arg} is not {@code null} and
     *         not empty, otherwise {@code "failure"}
     */
    @SuppressWarnings("unused")
    private static String returnStringWithArgument(String arg) {
        return arg == null || arg.isEmpty() ? "failure" : "success";
    }

    /**
     * Returns the string {@code "success"} with some arguments.
     *
     * @param arg1 The argument 1
     * @param arg2 The argument 2
     * @param arg3 The argument 3
     * @return The string {@code "success"}
     */
    @SuppressWarnings("unused")
    private static String returnStringWithArguments(String arg1, int arg2, boolean arg3) {
        return "success";
    }

    /**
     * Returns the int {@code 1} without the argument.
     *
     * @return The int {@code 1}
     */
    @SuppressWarnings("unused")
    private static int returnIntegerWithNoArgument() {
        return 1;
    }

    /**
     * Returns the int {@code 1} if the boolean {@code true} , otherwise {@code 0} .
     *
     * @param arg The argument
     * @return The int {@code 1} if the boolean {@code true} , otherwise {@code 0}
     */
    @SuppressWarnings("unused")
    private static int returnIntegerWithArgument(boolean arg) {
        return arg ? 1 : 0;
    }

    /**
     * Returns the int {@code 1} with some arguments.
     *
     * @param arg1 The argument 1
     * @param arg2 The argument 2
     * @param arg3 The argument 3
     * @return The int {@code 1}
     */
    @SuppressWarnings("unused")
    private static int returnIntegerWithArguments(int arg1, String arg2, boolean arg3) {
        return 1;
    }

    /**
     * Returns the boolean {@code true} without the argument.
     *
     * @return The boolean {@code true}
     */
    @SuppressWarnings("unused")
    private static boolean returnBooleanWithNoArgument() {
        return true;
    }

    /**
     * Returns the boolean {@code true} if the argument is equals to the int
     * {@code 1} , otherwise {@code false} .
     *
     * @param arg The argument
     * @return The boolean {@code true} if the argument is equals to the int
     *         {@code 1} , otherwise {@code false}
     */
    @SuppressWarnings("unused")
    private static boolean returnBooleanWithArgument(int arg) {
        return arg == 1;
    }

    /**
     * Returns the boolean {@code true} with some arguments.
     *
     * @param arg1 The argument 1
     * @param arg2 The argument 2
     * @param arg3 The argument 3
     * @return The boolean {@code true}
     */
    @SuppressWarnings("unused")
    private static boolean returnBooleanWithArguments(int arg1, String arg2, boolean arg3) {
        return true;
    }

    /**
     * Returns the list based on the arguments.
     *
     * @param arg1 The argument 1
     * @param arg2 The argument 2
     * @param arg3 The argument 3
     * @return The list based on the arguments
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    @SuppressWarnings("unused")
    private static List<String> returnListWithArguments(String arg1, String arg2, String arg3) {
        final List<String> result = new ArrayList<>(3);
        result.add(arg1);
        result.add(arg2);
        result.add(arg3);
        return result;
    }

    /**
     * Returns the map based on the arguments.
     *
     * @param arg1 The argument 1
     * @param arg2 The argument 2
     * @param arg3 The argument 3
     * @return The map based on the arguments
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    @SuppressWarnings("unused")
    private static Map<String, Integer> returnMapWithArguments(int arg1, int arg2, int arg3) {
        final Map<String, Integer> result = new HashMap<>(3);
        result.put("result1", arg1);
        result.put("result2", arg2);
        result.put("result3", arg3);
        return result;
    }
}
