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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 * the class information of the class in which the method to be called is
 * defined to the {@link #from(Class)} method. The generic type of the
 * {@link ReflectionTestHelper} class should be the type returned by the method
 * to be called.
 *
 * <p>
 * If the argument of the method to be invoked is not required, you can execute
 * the target method by creating a new instance of {@link ReflectionTestHelper}
 * and then invoking the {@link #invoke(String)} method with the name of the
 * method to be invoked as the argument. If the method to be called is static,
 * call the {@link #invokeStatic(String)} method with the name of the method to
 * be called as the argument.
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
     * The refrection parameter
     */
    private ReflectionParameter parameter;

    /**
     * The class
     */
    private Class<?> clazz;

    /**
     * The constructor.
     *
     * @param clazz The class in which the method to be called is defined
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ReflectionTestHelper(@NonNull final Class<?> clazz) {
        this.parameter = ReflectionParameter.newInstance();
        this.clazz = clazz;
    }

    /**
     * Returns the new instance of {@link ReflectionTestHelper} based on the
     * argument.
     *
     * @param <T>   The type returned by the method to be called
     * @param clazz The class in which the method to be called is defined
     * @return The new instance of {@link ReflectionTestHelper}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T> ReflectionTestHelper<T> from(@NonNull final Class<?> clazz) {
        return new ReflectionTestHelper<>(clazz);
    }

    public T invokeStatic(@NonNull final String methodName) {
        return this.invoke(methodName, true);
    }

    public T invoke(@NonNull final String methodName) {
        return this.invoke(methodName, false);
    }

    @SuppressWarnings("unchecked")
    private T invoke(@NonNull final String methodName, final boolean isStatic) {

        if (methodName.isEmpty()) {
            throw new IllegalArgumentException("Method name is required.");
        }

        try {
            final Object clazzInstance = isStatic ? null : this.clazz.getDeclaredConstructor().newInstance();

            if (this.parameter.isEmpty()) {
                Method method = this.clazz.getDeclaredMethod(methodName);
                method.setAccessible(true);
                return (T) method.invoke(clazzInstance);
            }

            final Method method = this.clazz.getDeclaredMethod(methodName, this.parameter.getTypes());
            method.setAccessible(true);
            return (T) method.invoke(clazzInstance, this.parameter.getValues());

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    public ReflectionTestHelper<T> add(@NonNull Class<?> argumentType, @NonNull Object argumentValue) {
        this.parameter.add(argumentType, argumentValue);
        return this;
    }
}
