package org.dante.springboot.annotation;

import static org.dante.springboot.config.EncryptConstant.ENCRYPT;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptMethod {
	String type() default ENCRYPT;
}
