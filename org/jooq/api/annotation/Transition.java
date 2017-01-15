package org.jooq.api.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.METHOD})
@Deprecated
public @interface Transition
{
  String name() default "";
  
  String[] args() default {""};
  
  String from() default "";
  
  String to() default "";
}
