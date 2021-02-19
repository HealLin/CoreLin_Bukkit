package corelin.plugins.library.api.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2021/1/13 19:48
 * @版本 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SpigotPlugin {

    /**
     * 插件名称
     * @return
     */
    String pluginName();

    /**
     * 版本
     * @return
     */
    String version();


    String author();

    /**
     * 日志输出前缀
     * @return
     */
    String prefix() default "";

    /**
     * 描述
     * @return
     */
    String description() default "";

    /**
     * 依赖
     * @return
     */
    String depend() default "";

    /**
     * 可选依赖
     * @return
     */
    String softDepend() default "";

    /**
     * 无
     * @return
     */
    String loadBefore() default "";


}
