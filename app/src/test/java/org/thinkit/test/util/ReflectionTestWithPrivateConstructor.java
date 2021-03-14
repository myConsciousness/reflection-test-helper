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

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The class that defines data for testing a class that has a constructor with
 * the access modifier private.
 *
 * @author Kato Shinya
 * @since 1.0.5
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ReflectionTestWithPrivateConstructor {
}
