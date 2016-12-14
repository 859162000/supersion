package framework.interfaces;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.TYPE) 
public @interface IEntity {
    String description() default "";
    String navigationName() default "";
}
