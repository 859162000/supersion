package framework.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD) 
public @interface ICondition {
	
	public enum Comparison {  
	    NONE,EQ,GT,GE,LT,LE,ANYWHERE,START,END,EXACT
	}
	String target() default "";
	String targetField() default "";
	boolean isVisible() default true;
    String description() default "";
    boolean isASC() default true;
    int order() default 0;
    Comparison comparison() default Comparison.NONE;
}
