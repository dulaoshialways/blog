
package club.dulaoshi.blog.conf.annotation;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: SysLog  
 * @Description: 系统日志注解
 * @author djg
 * @date 2019年04月25日
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
