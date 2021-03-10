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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The class that manages test case of {@link ReflectionParameter} .
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class ReflectionParameterTest {

    /**
     * The nested class for {@link ReflectionParameter#getTypes()} method.
     */
    @Nested
    class TestGetTypes {

        @Test
        void testWhenParameterHasStringType() {

            final ReflectionParameter sut = ReflectionParameter.newInstance();
            sut.add(String.class, "");

            final Class<?>[] types = sut.getTypes();
            assertNotNull(types);
            assertTrue(types.length == 1);
            assertEquals(String.class, types[0]);
        }

        @Test
        void testWhenParameterHasMultipleTypes() {

            final ReflectionParameter sut = ReflectionParameter.newInstance();
            sut.add(String.class, "");
            sut.add(Integer.class, 0);
            sut.add(Boolean.class, true);
            sut.add(int.class, 1);
            sut.add(boolean.class, false);
            sut.add(Map.class, Map.of());

            final Class<?>[] types = sut.getTypes();
            assertNotNull(types);
            assertTrue(types.length == 6);
            assertEquals(String.class, types[0]);
            assertEquals(Integer.class, types[1]);
            assertEquals(Boolean.class, types[2]);
            assertEquals(int.class, types[3]);
            assertEquals(boolean.class, types[4]);
            assertEquals(Map.class, types[5]);
        }

        @Test
        void testWhenParameterIsNotSet() {
            final Class<?>[] classes = assertDoesNotThrow(() -> ReflectionParameter.newInstance().getTypes());
            assertNotNull(classes);
        }
    }

    /**
     * The nested class for {@link ReflectionParameter#getValues()} method.
     */
    @Nested
    class TestGetValues {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "t", "test", "/!*;=" })
        void testWhenParameterHasStringValue(final String parameter) {

            final ReflectionParameter sut = ReflectionParameter.newInstance();
            sut.add(String.class, parameter);

            final Object[] values = sut.getValues();
            assertNotNull(values);
            assertTrue(values.length == 1);
            assertEquals(parameter, values[0]);
        }

        @Test
        void testWhenParameterHasMultipleValues() {

            final ReflectionParameter sut = ReflectionParameter.newInstance();
            sut.add(String.class, "");
            sut.add(Integer.class, 0);
            sut.add(Boolean.class, true);
            sut.add(int.class, 1);
            sut.add(boolean.class, false);

            final Object[] values = sut.getValues();
            assertNotNull(values);
            assertTrue(values.length == 5);
            assertEquals("", values[0]);
            assertEquals(0, values[1]);
            assertEquals(true, values[2]);
            assertEquals(1, values[3]);
            assertEquals(false, values[4]);
        }

        @Test
        void testWhenParameterIsNotSet() {
            final Object[] objects = assertDoesNotThrow(() -> ReflectionParameter.newInstance().getValues());
            assertNotNull(objects);
        }
    }

    /**
     * The nested class for {@link ReflectionParameter#isEmpty()} method.
     */
    @Nested
    class TestIsEmpty {

        @Test
        void testWhenParameterisSet() {
            final ReflectionParameter sut = ReflectionParameter.newInstance();
            sut.add(String.class, "test");
            assertTrue(!sut.isEmpty());
        }

        @Test
        void testWhenParameterisNotSet() {
            final ReflectionParameter sut = ReflectionParameter.newInstance();
            assertTrue(sut.isEmpty());
        }
    }
}
