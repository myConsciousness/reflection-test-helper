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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * The class that manages test case of {@link ReflectionField} .
 *
 * @author Kato Shinya
 * @since 1.0.3
 */
public final class ReflectionFieldTest {

    /**
     * The field name for testing
     */
    private static final String TEST_FIELD_NAME = "testField";

    @Nested
    class TestSetAndGetFieldValue {

        @ParameterizedTest
        @ValueSource(strings = { "", " ", "　", "t", "test", " test", "test ", " test ", "^/.!" })
        void testWhenValueIsNotNull(final String expected) {

            final ReflectionField<TestEntityForReflectionField> sut = ReflectionField
                    .from(TestEntityForReflectionField.newInstance());

            sut.setFieldValue(TEST_FIELD_NAME, expected);
            final String actual = String.valueOf(sut.getFieldValue(TEST_FIELD_NAME));

            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void testWhenValueIsNull() {

            final String expected = null;
            final ReflectionField<TestEntityForReflectionField> sut = ReflectionField
                    .from(TestEntityForReflectionField.newInstance());

            assertDoesNotThrow(() -> sut.setFieldValue(TEST_FIELD_NAME, expected));
            final String actual = assertDoesNotThrow(() -> (String) sut.getFieldValue(TEST_FIELD_NAME));

            assertEquals(expected, actual);
        }
    }
}
