package corelin.plugins.library.plugin.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @描述: TODO
 * @作者: 择忆霖心
 * @author: 择忆霖心
 * @时间: 2020/4/5 23:47
 * @版本: 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CoreConfig {

    boolean title() default true;

    String type() default "";
}
