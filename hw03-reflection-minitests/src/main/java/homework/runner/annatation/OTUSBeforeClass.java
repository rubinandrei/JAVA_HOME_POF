package homework.runner.annatation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface OTUSBeforeClass {
    public boolean runIfFiled() default false;
}
