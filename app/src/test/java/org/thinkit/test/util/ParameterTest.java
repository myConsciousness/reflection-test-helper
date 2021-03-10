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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The class that manages test case of {@link Parameter} .
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class ParameterTest {

    /**
     * The nested class for {@link Parameter#getType()} and
     * {@link Parameter#getValue()} methods.
     */
    @Nested
    class TestGetterMethod {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "t", "test", "/!*;=" })
        void testWhenParametrHasStringArgument(final String expected) {

            final Parameter sut = Parameter.from(String.class, expected);

            assertNotNull(sut);
            assertEquals(String.class, sut.getType());
            assertEquals(expected, sut.getValue());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 1 })
        void testWhenParametrHasIntegerArgument(final int expected) {

            final Parameter sut = Parameter.from(Integer.class, expected);

            assertNotNull(sut);
            assertEquals(Integer.class, sut.getType());
            assertEquals(expected, sut.getValue());
        }

        @Test
        void testWhenParametrHasListArgument() {

            final String expected = "test";
            final Parameter sut = Parameter.from(List.class, List.of(expected));

            assertNotNull(sut);
            assertEquals(List.class, sut.getType());

            @SuppressWarnings("unchecked")
            final List<String> values = (List<String>) sut.getValue();

            assertTrue(values.size() == 1);
            assertEquals(expected, values.get(0));
        }

        @Test
        void testWhenParametrHasMapArgument() {

            final String expectedKey = "key";
            final String expectedValue = "test";
            final Parameter sut = Parameter.from(Map.class, Map.of(expectedKey, expectedValue));

            assertNotNull(sut);
            assertEquals(Map.class, sut.getType());

            @SuppressWarnings("unchecked")
            final Map<String, String> values = (Map<String, String>) sut.getValue();

            assertTrue(values.size() == 1);

            values.forEach((key, value) -> {
                assertEquals(expectedKey, key);
                assertEquals(expectedValue, value);
            });
        }

        @Test
        void testWhenParametrHasSetArgument() {

            final String expected = "test";
            final Parameter sut = Parameter.from(Set.class, Set.of(expected));

            assertNotNull(sut);
            assertEquals(Set.class, sut.getType());

            @SuppressWarnings("unchecked")
            final Set<String> values = (Set<String>) sut.getValue();

            assertTrue(values.size() == 1);

            for (final String value : values) {
                assertEquals(expected, value);
            }
        }

        @Test
        void testWhenParametrTypeIsNull() {

            final NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> Parameter.from(null, "test"));
            assertNotNull(exception);
        }

        @Test
        void testWhenParametrValueIsNull() {
            assertDoesNotThrow(() -> Parameter.from(String.class, null));
        }
    }
}
