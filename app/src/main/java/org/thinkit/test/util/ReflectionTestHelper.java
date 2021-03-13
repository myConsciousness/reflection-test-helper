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
public final class ReflectionTestHelper<T, R> implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 6572977559365139187L;

    /**
     * The reflection field
     */
    private ReflectionField<T> reflectionField;

    /**
     * The reflection method
     */
    private ReflectionMethod<T, R> reflectionMethod;

    /**
     * The constructor.
     *
     * @param clazz The class in which the method to be invoked is defined
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ReflectionTestHelper(@NonNull final Class<?> clazz) {
        try {
            final T sutInstance = this.getSutInstance(clazz);
            this.reflectionField = ReflectionField.from(sutInstance);
            this.reflectionMethod = ReflectionMethod.from(sutInstance);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Returns the new instance of {@link ReflectionTestHelper} based on the
     * argument.
     *
     * @param <R>   The type returned by the method to be invoked
     * @param clazz The class in which the method to be invoked is defined
     * @return The new instance of {@link ReflectionTestHelper}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    public static <T, R> ReflectionTestHelper<T, R> from(@NonNull final Class<?> clazz) {
        return new ReflectionTestHelper<>(clazz);
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
    public ReflectionTestHelper<T, R> setFieldValue(@NonNull final String fieldName, final Object fieldValue) {
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
    public R invokeMethod(final String methodName) {
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
    public ReflectionTestHelper<T, R> addArgument(@NonNull Class<?> argumentType, Object argumentValue) {
        this.reflectionMethod.addArgument(argumentType, argumentValue);
        return this;
    }

    /**
     * Returns the instance of the class in which the method to be invoked by
     * reflection is defined.
     *
     * @return The instance of the class in which the method to be invoked by
     *         reflection is defined
     *
     * @throws InstantiationException    If the class that declares the underlying
     *                                   constructor represents an abstract class
     * @throws IllegalAccessException    If this {@code Constructor} object is
     *                                   enforcing Java language access control and
     *                                   the underlying constructor is inaccessible
     * @throws IllegalArgumentException  If the number of actual and formal
     *                                   parameters differ; if an unwrapping
     *                                   conversion for primitive arguments fails;
     *                                   or if, after possible unwrapping, a
     *                                   parameter value cannot be converted to the
     *                                   corresponding formal parameter type by a
     *                                   method invocation conversion; if this
     *                                   constructor pertains to an enum type
     * @throws InvocationTargetException If the underlying constructor throws an
     *                                   exception
     * @throws NoSuchMethodException     If a matching method is not found
     * @throws SecurityException         If a security manager, <i>s</i>, is present
     *                                   and any of the following conditions is met:
     *
     *                                   <ul>
     *                                   <li>the caller's class loader is not the
     *                                   same as the class loader of this class and
     *                                   invocation of
     *                                   {@link SecurityManager#checkPermission
     *                                   s.checkPermission} method with
     *                                   {@code RuntimePermission("accessDeclaredMembers")}
     *                                   denies access to the declared constructor
     *
     *                                   <li>the caller's class loader is not the
     *                                   same as or an ancestor of the class loader
     *                                   for the current class and invocation of
     *                                   {@link SecurityManager#checkPackageAccess
     *                                   s.checkPackageAccess()} denies access to
     *                                   the package of this class
     *                                   </ul>
     */
    @SuppressWarnings("unchecked")
    private T getSutInstance(@NonNull final Class<?> clazz) throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return (T) clazz.getDeclaredConstructor().newInstance();
    }
}
