## JSR-303

### 一.  概述

JSR-303 是 Java 规范请求（Java Specification Request，JSR）的一部分，它定义了一种 Java 标准的方法，用于在应用程序中进行数据验证和约束的处理。JSR-303 的全名是 "Bean Validation"，也被称为 Bean 验证或 Java 验证。它的目标是提供一种基于注解的方式来描述和验证 JavaBean 对象的约束条件，以确保数据的合法性和完整性。

JSR-303 的主要特点和功能包括：

1. **基于注解的验证规则**：JSR-303 允许开发人员使用注解来定义在 JavaBean 类上的验证规则，例如，规定一个属性不能为空、范围在某个特定值内等。
2. **内置验证约束**：JSR-303 提供了一组内置的验证约束，可以用于处理常见的验证场景，例如，@NotNull、@Size、@Min、@Max 等。这些注解可以直接应用于 JavaBean 属性上。
3. **自定义验证约束**：除了内置的验证约束外，开发人员还可以创建自定义的验证约束注解，并定义验证逻辑的实现。这使得可以根据特定的业务需求定义和使用自定义的验证规则。
4. **统一的验证 API**：JSR-303 提供了一个统一的验证 API，开发人员可以使用它来触发验证过程，检查 JavaBean 对象是否符合定义的约束。
5. **集成到 Java EE 和其他框架**：JSR-303 是 Java EE（Java Platform, Enterprise Edition）的一部分，因此可以在 Java EE 应用程序中方便地使用。此外，它也可以与许多其他框架和库（如 Spring）集成使用。

### 二.  技术原理

### 三.  开发说明

### 四.  参考资料 

- https://www.jb51.net/article/263439.htm
- https://juejin.cn/post/6844904162480619534