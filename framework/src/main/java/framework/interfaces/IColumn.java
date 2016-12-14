package framework.interfaces;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.FIELD) 
public @interface IColumn {
	boolean isListShow() default false;
    boolean isKeyName() default false;
    boolean isIdListField() default false;
    boolean isFileField() default false;
    boolean isSystemDate() default false;
    boolean isLoginTag() default false;
    boolean isSingleTagHidden() default false;
    boolean isLevelShow() default false;
    boolean isNullable() default true;
    String target() default "";
    String mappedBy() default "";
    String description() default "";
    String tagMethodName() default "";
    String aliasName() default "";
    //是否允许特殊字符，true,允许，false,不允许
    boolean isSpecialCharCheck() default false;
}
