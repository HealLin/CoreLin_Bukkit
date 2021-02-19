package corelin.plugins.library.plugin.command.annotation;

import corelin.plugins.library.plugin.command.type.CommandType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 17:05
 * @版本 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HCD {
    String[] cmd();

    int length();

    CommandType[] type();

    String description() default "";

    String trueType() default "";

    String[] permission();
}