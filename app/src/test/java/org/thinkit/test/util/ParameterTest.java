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

    @Nested
    class TestGetterMethod {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "t", "test", "/!*;=" })
        void testWhenParametrHasStringArgument(final String parameter) {

            final Parameter parameterEntity = Parameter.from(String.class, parameter);

            assertNotNull(parameterEntity);
            assertEquals(String.class, parameterEntity.getType());
            assertEquals(parameter, parameterEntity.getValue());
        }

        @ParameterizedTest
        @ValueSource(ints = { -1, 0, 1 })
        void testWhenParametrHasIntegerArgument(final int parameter) {

            final Parameter parameterEntity = Parameter.from(Integer.class, parameter);

            assertNotNull(parameterEntity);
            assertEquals(Integer.class, parameterEntity.getType());
            assertEquals(parameter, parameterEntity.getValue());
        }

        @Test
        void testWhenParametrHasListArgument() {

            final Parameter parameterEntity = Parameter.from(List.class, List.of("test"));

            assertNotNull(parameterEntity);
            assertEquals(List.class, parameterEntity.getType());

            @SuppressWarnings("unchecked")
            final List<String> values = (List<String>) parameterEntity.getValue();

            assertTrue(values.size() == 1);
            assertEquals("test", values.get(0));
        }

        @Test
        void testWhenParametrHasMapArgument() {

            final Parameter parameterEntity = Parameter.from(Map.class, Map.of("key", "test"));

            assertNotNull(parameterEntity);
            assertEquals(Map.class, parameterEntity.getType());

            @SuppressWarnings("unchecked")
            final Map<String, String> values = (Map<String, String>) parameterEntity.getValue();

            assertTrue(values.size() == 1);

            values.forEach((key, value) -> {
                assertEquals("key", key);
                assertEquals("test", value);
            });
        }

        @Test
        void testWhenParametrHasSetArgument() {

            final Parameter parameterEntity = Parameter.from(Set.class, Set.of("test"));

            assertNotNull(parameterEntity);
            assertEquals(Set.class, parameterEntity.getType());

            @SuppressWarnings("unchecked")
            final Set<String> values = (Set<String>) parameterEntity.getValue();

            assertTrue(values.size() == 1);

            for (final String value : values) {
                assertEquals("test", value);
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

            final NullPointerException exception = assertThrows(NullPointerException.class,
                    () -> Parameter.from(String.class, null));
            assertNotNull(exception);
        }
    }
}
