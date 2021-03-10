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

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * The class that defines a function that makes it easier to use the reflection
 * process when calling methods with private access modifiers. The functionality
 * of this class was created specifically for external calls to private methods
 * when testing a particular class.
 *
 * <p>
 * When creating a new instance of the {@link ReflectionTestHelper} class, pass
 * the class information of the class in which the method to be invoked is
 * defined to the {@link #from(Class)} method. The generic type of the
 * {@link ReflectionTestHelper} class should be the type returned by the method
 * to be invoked.
 *
 * <p>
 * Then you can execute the target method by creating a new instance of
 * {@link ReflectionTestHelper} and then invoking the
 * {@link #invokeMethod(String)} method with the name of the method to be
 * invoked as the argument.
 *
 * <p>
 * If the method to be invoked requires the specification of arguments, call the
 * {@link #addArgument(Class, Object)} method and add the argument types and
 * values that need to be set.
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionTestHelper<T> implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 6572977559365139187L;

    /**
     * The reflection field
     */
    private ReflectionField reflectionField;

    /**
     * The reflection method
     */
    private ReflectionMethod<T> reflectionMethod;

    /**
     * The constructor.
     *
     * @param clazz The class in which the method to be invoked is defined
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ReflectionTestHelper(@NonNull final Class<?> clazz) {
        this.reflectionField = ReflectionField.from(clazz);
        this.reflectionMethod = ReflectionMethod.from(clazz);
    }

    /**
     * Set {@code fieldValue} to the field associated with {@code fieldName}
     * specified as an argument.
     *
     * @param fieldName  The field name to be processed for reflection
     * @param fieldValue The value to be set to the field by reflection
     * @return This instance
     *
     * @exception IllegalStateException If an error occurs in the reflection process
     */
    public ReflectionTestHelper<T> setFieldValue(@NonNull final String fieldName, final Object fieldValue) {
        this.reflectionField.setFieldValue(fieldName, fieldValue);
        return this;
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
    public Object getFieldValue(@NonNull final String fieldName) {
        return this.reflectionField.getFieldValue(fieldName);
    }

    /**
     * Returns the new instance of {@link ReflectionTestHelper} based on the
     * argument.
     *
     * @param <T>   The type returned by the method to be invoked
     * @param clazz The class in which the method to be invoked is defined
     * @return The new instance of {@link ReflectionTestHelper}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T> ReflectionTestHelper<T> from(@NonNull final Class<?> clazz) {
        return new ReflectionTestHelper<>(clazz);
    }

    /**
     * Invokes the indicated method by reflection. The value defined in the target
     * method to be called in the reflection will be returned.
     *
     * @param methodName The method name to invoked by reflection
     * @return The value returned from the method name executed in the reflection
     *         process
     *
     * @exception IllegalArgumentException If the argument {@code methodName} is
     *                                     {@code null} or empty
     * @exception IllegalStateException    If an error occurs in the reflection
     *                                     process
     */
    public T invokeMethod(final String methodName) {
        return this.reflectionMethod.invokeMethod(methodName);
    }

    /**
     * Adds the argument types and values defined for the target method to be
     * invoked in reflection. Argument types are not allowed to be {@code null} ,
     * but argument values are allowed to be {@code null} .
     *
     * @param argumentType  The type of argument
     * @param argumentValue The value of argument
     * @return this instance
     *
     * @exception NullPointerException If the argument {@code argumentType} is null
     */
    public ReflectionTestHelper<T> addArgument(@NonNull Class<?> argumentType, Object argumentValue) {
        this.reflectionMethod.addArgument(argumentType, argumentValue);
        return this;
    }
}
