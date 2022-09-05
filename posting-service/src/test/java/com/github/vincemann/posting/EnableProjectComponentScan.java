package com.github.vincemann.posting;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@ComponentScan("com.github.vincemann.posting")
public @interface EnableProjectComponentScan {
}
