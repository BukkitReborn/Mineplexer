package org.jooq.api.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.TYPE})
@Deprecated
public @interface State
{
  String name() default "";
  
  String[] aliases() default {""};
  
  boolean terminal() default false;
}
