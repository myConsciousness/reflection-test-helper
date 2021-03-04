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
import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 *
 * @author Kato Shinya
 * @since 1.0.0
 */
@ToString
@EqualsAndHashCode
@NoArgsConstructor(staticName = "newInstance")
final class ReflectionParameter implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 4405640637388930830L;

    private final List<Parameter> parameters = new ArrayList<>(0);

    public <T> void add(@NonNull Class<?> argumentType, @NonNull T argumentValue) {
        this.parameters.add(Parameter.from(argumentType, argumentValue));
    }

    public Class<?>[] getTypes() {

        if (this.parameters.isEmpty()) {
            throw new IllegalStateException("No parameter is set. Parameter is required.");
        }

        final int parameterSize = this.parameters.size();
        final Class<?>[] types = new Class[parameterSize];

        for (int i = 0; i < parameterSize; i++) {
            types[i] = parameters.get(i).getType();
        }

        return types;
    }

    public Object[] getValues() {

        if (this.parameters.isEmpty()) {
            throw new IllegalStateException("No parameter is set. Parameter is required.");
        }

        final int paramaterSize = this.parameters.size();
        final Object[] values = new Object[paramaterSize];

        for (int i = 0; i < paramaterSize; i++) {
            values[i] = parameters.get(i).getValue();
        }

        return values;
    }

    public boolean isEmpty() {
        return this.parameters.isEmpty();
    }
}
