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

import org.apache.commons.lang3.StringUtils;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 * The class that manages operations on method invoked using reflection.
 *
 * @author Kato Shinya
 * @since 1.0.3
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ReflectionMethod<T, R> implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = -5578363497739828947L;

    /**
     * The sut instance
     */
    private T sutInstance;

    /**
     * The refrection parameter
     */
    private ReflectionParameter parameter;

    /**
     * The constructor.
     *
     * @param sutInstance The class in which the method to be invoked is defined
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    private ReflectionMethod(@NonNull final T sutInstance) {
        this.sutInstance = sutInstance;
        this.parameter = ReflectionParameter.newInstance();
    }

    /**
     * Returns the new instance of {@link ReflectionMethod} based on the arguments.
     *
     * @param <T>         The type of SUT
     * @param <R>         The type returned by the method to be invoked
     * @param sutInstance The class in which the method to be invoked is defined
     * @return The new instance of {@link ReflectionMethod}
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     */
    protected static <T, R> ReflectionMethod<T, R> from(@NonNull final T sutInstance) {
        return new ReflectionMethod<>(sutInstance);
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
    @SuppressWarnings("unchecked")
    protected R invokeMethod(final String methodName) {

        if (StringUtils.isEmpty(methodName)) {
            throw new IllegalArgumentException("Method name must not be empty.");
        }

        try {
            return (R) this.getMethod(methodName).invoke(this.sutInstance, this.parameter.getValues());
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Adds the argument types and values defined for the target method to be
     * invoked in reflection. Argument types are not allowed to be {@code null} ,
     * but argument values are allowed to be {@code null} .
     *
     * @param argumentType  The type of argument
     * @param argumentValue The value of argument
     * @return This instance
     *
     * @exception NullPointerException If the argument {@code argumentType} is null
     */
    protected ReflectionMethod<T, R> addArgument(@NonNull Class<?> argumentType, Object argumentValue) {
        this.parameter.add(argumentType, argumentValue);
        return this;
    }

    /**
     * Returns the method object to be invoked by reflection.
     *
     * @param methodName The method name to be invoked by reflection
     * @return The method object to be invoked
     *
     * @exception NullPointerException If {@code null} is passed as an argument
     * @throws NoSuchMethodException If a matching method is not found
     * @throws SecurityException     If a security manager, <i>s</i>, is present and
     *                               any of the following conditions is met:
     *
     *                               <ul>
     *                               <li>the caller's class loader is not the same
     *                               as the class loader of this class and
     *                               invocation of
     *                               {@link SecurityManager#checkPermission
     *                               s.checkPermission} method with
     *                               {@code RuntimePermission("accessDeclaredMembers")}
     *                               denies access to the declared constructor
     *
     *                               <li>the caller's class loader is not the same
     *                               as or an ancestor of the class loader for the
     *                               current class and invocation of
     *                               {@link SecurityManager#checkPackageAccess
     *                               s.checkPackageAccess()} denies access to the
     *                               package of this class
     *                               </ul>
     */
    private Method getMethod(@NonNull final String methodName) throws NoSuchMethodException, SecurityException {
        final Method method = this.sutInstance.getClass().getDeclaredMethod(methodName, this.parameter.getTypes());
        method.setAccessible(true);
        return method;
    }
}
