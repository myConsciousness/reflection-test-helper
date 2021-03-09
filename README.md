![Build](https://img.shields.io/badge/Build-Automated-2980b9.svg?style=for-the-badge)
![Latest Version](https://img.shields.io/badge/Latest_Version-v1.0.0-27ae60.svg?style=for-the-badge)
![License](https://img.shields.io/badge/License-Apache_2.0-e74c3c.svg?style=for-the-badge)</br>
![Java CI with Gradle](https://github.com/myConsciousness/reflection-test-helper/workflows/Java%20CI%20with%20Gradle/badge.svg)

# Reflection Test Helper

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [What is it?](#what-is-it)
- [How To Use](#how-to-use)
  - [1. Add the dependencies](#1-add-the-dependencies)
  - [2. Import `Reflection Test Helper`](#2-import-reflection-test-helper)
  - [3. Create new instance of `ReflectionTestHelper`](#3-create-new-instance-of-reflectiontesthelper)
  - [4. Invoke method](#4-invoke-method)
  - [5. Invoke method with the argument](#5-invoke-method-with-the-argument)
- [License](#license)
- [More Information](#more-information)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## What is it?

The `Reflection Test Helper` was designed and implemented to provide a simple and cool way to write reflections, which are verbose and relatively difficult to implement. This feature is especially useful when testing a specific class with limited access features such as private access modifiers.

The `Reflection Test Helper` is easy to use; all you need to be aware of when using it is the class in which the method to be reflected is defined, and the name and arguments of the method to be invoked in the reflection.

## How To Use

### 1. Add the dependencies

> **_Note:_**</br>
> Replace version you want to use. Check the latest [Packages](https://github.com/myConsciousness/reflection-test-helper/packages).</br>
> Please contact me for a token to download the package.

**_Maven_**

```xml
<dependency>
  <groupId>org.thinkit.framework.content</groupId>
  <artifactId>reflection-test-helper</artifactId>
  <version>v1.0.2</version>
</dependency>

<servers>
  <server>
    <id>github</id>
    <username>myConsciousness</username>
    <password>xxxxxxxxxxxxxxxxxx</password>
  </server>
</servers>
```

**_Gradle_**

```gradle
repositories {
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/myConsciousness/reflection-test-helper")
        credentials {
          username = "myConsciousness"
          password = "xxxxxxxxxxxxxxxxxx"
        }
    }
}

dependencies {
    implementation 'org.thinkit.test.util:reflection-test-helper:v1.0.2'
}
```

### 2. Import `Reflection Test Helper`

```java
import org.thinkit.test.util.ReflectionTestHelper;
```

### 3. Create new instance of `ReflectionTestHelper`

```java
// If the method to be invoked is in ConcreteClass, and it returns the string
final ReflectionTestHelper<String> reflectionTestHelper = ReflectionTestHelper.from(ConcreteClass.class);
```

> **_Note:_**</br>
> The static constructor of the ReflectionTestHelper class, the from method, should be passed the class object of the class in which the method to be invoked by reflection is defined as an argument.
> Also, the ReflectionTestHelper generic should specify the type of the return value of the method to be called in reflection.

### 4. Invoke method

By executing the invoke method with the name of the method to be called in reflection as an argument, the specified method can be executed in reflection.

If you need to specify arguments for the method to be called by reflection, you can use [this way](#5-invoke-method-with-the-argument) to specify the arguments.

```java
// Specify the name of the method to be invoked as an argument
final String result = reflectionTestHelper.invokeMethod("methodName");
```

### 5. Invoke method with the argument

By calling the add method with **_the first argument as the argument type_** and **_the second argument as the argument value_**, you can specify the arguments required to invoke the target method to be invoked by reflection.

```java

// Set the arguments required for method execution
reflectionTestHelper.addArgument(String.class, "firstArgument");
reflectionTestHelper.addArgument(Integer.class, 1000);

// Specify the name of the method to be invoked as an argument
final String result = reflectionTestHelper.invokeMethod("methodNameWithArgument");
```

## License

```license
Copyright 2021 Kato Shinya.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
or implied. See the License for the specific language governing permissions and limitations under
the License.
```

## More Information

`Reflection Test Helper` was designed and implemented by Kato Shinya, who works as a freelance developer.

Regardless of the means or content of communication, I would love to hear from you if you have any questions or concerns. I do not check my email box very often so a response may be delayed, anyway thank you for your interest!

- [Creator Profile](https://github.com/myConsciousness)
- [Creator Website](https://myconsciousness.github.io/)
- [License](https://github.com/myConsciousness/reflection-test-helper/blob/master/LICENSE)
- [Release Note](https://github.com/myConsciousness/reflection-test-helper/releases)
- [Package](https://github.com/myConsciousness/reflection-test-helper/packages)
- [File a Bug](https://github.com/myConsciousness/reflection-test-helper/issues)
- [Guide](https://myconsciousness.github.io/reflection-test-helper/)
- [Reference](https://myconsciousness.github.io/reflection-test-helper/reference/org/thinkit/test/util/package-summary.html)
