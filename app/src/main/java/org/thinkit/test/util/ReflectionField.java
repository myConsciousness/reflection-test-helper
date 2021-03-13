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

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * The class that manages operations on field invoked using reflection.
 *
 * @author Kato Shinya
 * @since 1.0.3
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED, staticName = "from")
final class ReflectionField<T> implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 2195533227298076362L;

    /**
     * The sut instance
     */
    private T sutInstance;

    /**
     * Set {@code fieldValue} to the field associated with {@code fieldName}
     * specified as an argument.
     *
     * @param fieldName  The field name to be processed for reflection
     * @param fieldValue The value to be set to the field by reflection
     *
     * @exception IllegalStateException If an error occurs in the reflection process
     */
    protected void setFieldValue(@NonNull final String fieldName, final Object fieldValue) {

        try {
            this.getDeclaredField(fieldName).set(this.sutInstance, fieldValue);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns the value from the field associated with {@code fieldName} specified
     * as an argument.
     *
     * @param fieldName The field name to be processed for reflection
     * @return The value retrieved from the field by reflection
     *
     * @exception IllegalStateException If an error occurs in the reflection process
     */
    protected Object getFieldValue(@NonNull final String fieldName) {
        try {
            return this.getDeclaredField(fieldName).get(this.sutInstance);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    private Field getDeclaredField(@NonNull final String fieldName) throws NoSuchFieldException, SecurityException {
        final Field field = this.sutInstance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }
}
