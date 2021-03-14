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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

/**
 * The class that manages test case of {@link ReflectionTestHelper} .
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
public final class ReflectionTestHelperTest {

    /**
     * The field name of reflection field
     */
    private static final String FILED_NAME_REFLECTION_FIELD = "reflectionField";

    /**
     * The field name of reflection method
     */
    private static final String FIELD_NAME_REFLECTION_METHOD = "reflectionMethod";

    @Test
    void testWhenConstructorIsPublic() {

        final ReflectionTestHelper<String, String> sut = ReflectionTestHelper.from(String.class);
        assertNotNull(sut);

        try {
            final Class<?> clazz = sut.getClass();
            final Field reflectionField = clazz.getDeclaredField(FILED_NAME_REFLECTION_FIELD);
            final Field reflectionMethod = clazz.getDeclaredField(FIELD_NAME_REFLECTION_METHOD);

            reflectionField.setAccessible(true);
            reflectionMethod.setAccessible(true);

            assertNotNull(reflectionField.get(sut));
            assertNotNull(reflectionMethod.get(sut));

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWhenConstructorIsPrivate() {

        final ReflectionTestHelper<ReflectionTestWithPrivateConstructor, String> sut = ReflectionTestHelper
                .from(ReflectionTestWithPrivateConstructor.class);
        assertNotNull(sut);

        try {
            final Class<?> clazz = sut.getClass();
            final Field reflectionField = clazz.getDeclaredField(FILED_NAME_REFLECTION_FIELD);
            final Field reflectionMethod = clazz.getDeclaredField(FIELD_NAME_REFLECTION_METHOD);

            reflectionField.setAccessible(true);
            reflectionMethod.setAccessible(true);

            assertNotNull(reflectionField.get(sut));
            assertNotNull(reflectionMethod.get(sut));

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
