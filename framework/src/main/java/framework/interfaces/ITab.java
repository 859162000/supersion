package framework.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 项目名称：framework<br>
 * *********************************<br>
 * <P>类名称：ITab</P>
 * *********************************<br>
 * <P>类描述：tab标识</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-29 上午11:48:51<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-29 上午11:48:51<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) 
public @interface ITab {
	String name() default "";
}
