package corelin.plugins.library.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 择忆霖心
 * @简述 TODO
 * @时间 2020/11/22 19:42
 * @版本 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CoreModule {

    /**
     * 模块名称
     * @return 模块名称
     */
    String name();

    /**
     *
     * @return 模块描述
     */
    String description();

    /**
     * @return 模块版本
     */
    String version();

    String gameVersion();

    String[] rely() default {};

    /**
     *
     * @return 游戏版本
     */
    String useVersion();



}
